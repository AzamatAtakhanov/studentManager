package com.azamat.ManageGradesAndStudents.repositories;

import com.azamat.ManageGradesAndStudents.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByGradeIdOrderByNameAsc(Long gradeId);
}
