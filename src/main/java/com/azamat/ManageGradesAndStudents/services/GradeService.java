package com.azamat.ManageGradesAndStudents.services;

import com.azamat.ManageGradesAndStudents.models.Grade;
import com.azamat.ManageGradesAndStudents.repositories.GradeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    public Grade findById(long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Класс не найден"));
    }

    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }

    public void rename(long id, String name) {
        Grade grade = findById(id);
        grade.setName(name);
        gradeRepository.save(grade);
    }

    public void delete(Grade grade) {
        gradeRepository.delete(grade);
    }
}
