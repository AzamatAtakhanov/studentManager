package com.azamat.ManageGradesAndStudents;

import com.azamat.ManageGradesAndStudents.models.Grade;
import com.azamat.ManageGradesAndStudents.models.Student;
import com.azamat.ManageGradesAndStudents.services.GradeService;
import com.azamat.ManageGradesAndStudents.services.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class GradeFlowTests {

    @Autowired MockMvc mvc;
    @Autowired GradeService gradeService;
    @Autowired StudentService studentService;

    @Test
    void gradePageRequiresLogin() throws Exception {
        mvc.perform(get("/grade")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void addGradeAndStudent() throws Exception {
        mvc.perform(post("/grade/add").param("name", "9A").with(csrf()))
                .andExpect(redirectedUrl("/grade"));
        Grade grade = gradeService.findAll().get(0);

        mvc.perform(post("/student/" + grade.getId() + "/add")
                        .param("name", "Aidar").param("gender", "Мужской")
                        .param("firstQuarter", "5").with(csrf()))
                .andExpect(redirectedUrl("/student/" + grade.getId()));

        assertEquals(1, studentService.findByGrade(grade.getId()).size());
    }

    @Test
    @WithMockUser
    void blankStudentNameIsRejected() throws Exception {
        Grade grade = gradeService.save(new Grade());
        grade.setName("10B");

        mvc.perform(post("/student/" + grade.getId() + "/add").param("name", "")
                        .param("gender", "Женский").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("student-form"));
    }
}
