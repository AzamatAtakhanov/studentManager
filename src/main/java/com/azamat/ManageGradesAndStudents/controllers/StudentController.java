package com.azamat.ManageGradesAndStudents.controllers;

import com.azamat.ManageGradesAndStudents.models.Grade;
import com.azamat.ManageGradesAndStudents.models.Student;
import com.azamat.ManageGradesAndStudents.services.GradeService;
import com.azamat.ManageGradesAndStudents.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/student/{gradeId}")
public class StudentController {

    private final StudentService studentService;
    private final GradeService gradeService;

    public StudentController(StudentService studentService, GradeService gradeService) {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    // id and grade come from the URL, not from the form
    @InitBinder("student")
    void restrictBinding(WebDataBinder binder) {
        binder.setDisallowedFields("id", "grade");
    }

    @GetMapping
    public String studentList(@PathVariable Long gradeId, Model model) {
        model.addAttribute("grade", gradeService.findById(gradeId));
        model.addAttribute("students", studentService.findByGrade(gradeId));
        return "student-list";
    }

    @GetMapping("/add")
    public String addStudentForm(@PathVariable Long gradeId, Model model) {
        model.addAttribute("grade", gradeService.findById(gradeId));
        model.addAttribute("student", new Student());
        return "student-form";
    }

    @PostMapping("/add")
    public String addStudent(@PathVariable Long gradeId, @Valid @ModelAttribute("student") Student student,
                             BindingResult result, Model model) {
        Grade grade = gradeService.findById(gradeId);
        if (result.hasErrors()) {
            model.addAttribute("grade", grade);
            return "student-form";
        }
        studentService.addToGrade(student, grade);
        return "redirect:/student/" + gradeId;
    }

    @GetMapping("/{id}/edit")
    public String editStudentForm(@PathVariable Long gradeId, @PathVariable Long id, Model model) {
        model.addAttribute("grade", gradeService.findById(gradeId));
        model.addAttribute("student", studentService.findInGrade(id, gradeId));
        return "student-form";
    }

    @PostMapping("/{id}/edit")
    public String editStudent(@PathVariable Long gradeId, @PathVariable Long id,
                              @Valid @ModelAttribute("student") Student form, BindingResult result, Model model) {
        Student existing = studentService.findInGrade(id, gradeId);
        if (result.hasErrors()) {
            form.setId(id);
            model.addAttribute("grade", gradeService.findById(gradeId));
            return "student-form";
        }
        studentService.update(existing, form);
        return "redirect:/student/" + gradeId;
    }

    @PostMapping("/{id}/delete")
    public String deleteStudent(@PathVariable Long gradeId, @PathVariable Long id) {
        studentService.delete(studentService.findInGrade(id, gradeId));
        return "redirect:/student/" + gradeId;
    }
}
