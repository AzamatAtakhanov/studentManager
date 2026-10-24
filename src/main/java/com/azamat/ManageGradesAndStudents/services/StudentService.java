package com.azamat.ManageGradesAndStudents.services;

import com.azamat.ManageGradesAndStudents.models.Grade;
import com.azamat.ManageGradesAndStudents.models.Student;
import com.azamat.ManageGradesAndStudents.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public List<Student> findByGrade(Long gradeId) {
        return studentRepository.findByGradeIdOrderByNameAsc(gradeId);
    }

    /** Finds a student that belongs to the given grade, otherwise answers 404. */
    public Student findInGrade(Long id, Long gradeId) {
        return studentRepository.findById(id)
                .filter(student -> student.getGrade().getId().equals(gradeId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ученик не найден"));
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public Student addToGrade(Student student, Grade grade) {
        student.setGrade(grade);
        return studentRepository.save(student);
    }

    public void update(Student existing, Student form) {
        existing.setName(form.getName());
        existing.setGender(form.getGender());
        existing.setFirstQuarter(form.getFirstQuarter());
        existing.setSecondQuarter(form.getSecondQuarter());
        existing.setThirdQuarter(form.getThirdQuarter());
        existing.setFourthQuarter(form.getFourthQuarter());
        studentRepository.save(existing);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }
}
