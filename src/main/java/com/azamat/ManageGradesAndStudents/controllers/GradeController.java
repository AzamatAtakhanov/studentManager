package com.azamat.ManageGradesAndStudents.controllers;

import com.azamat.ManageGradesAndStudents.models.Grade;
import com.azamat.ManageGradesAndStudents.services.GradeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/grade")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    // the form may only send the name, never an id or a list of students
    @InitBinder("grade")
    void restrictBinding(WebDataBinder binder) {
        binder.setAllowedFields("name");
    }

    @GetMapping
    public String gradeList(Model model) {
        model.addAttribute("grades", gradeService.findAll());
        return "grade-list";
    }

    @GetMapping("/add")
    public String addGradeForm(Model model) {
        model.addAttribute("grade", new Grade());
        return "add-grade";
    }

    @PostMapping("/add")
    public String addGrade(@Valid @ModelAttribute("grade") Grade grade, BindingResult result) {
        if (result.hasErrors()) {
            return "add-grade";
        }
        gradeService.save(grade);
        return "redirect:/grade";
    }

    @GetMapping("/{id}/edit")
    public String editGradeForm(@PathVariable Long id, Model model) {
        model.addAttribute("grade", gradeService.findById(id));
        return "add-grade";
    }

    @PostMapping("/{id}/edit")
    public String editGrade(@PathVariable Long id, @Valid @ModelAttribute("grade") Grade form,
                            BindingResult result) {
        if (result.hasErrors()) {
            form.setId(id); // so the form keeps showing the "edit" title and action
            return "add-grade";
        }
        gradeService.rename(id, form.getName());
        return "redirect:/grade";
    }

    @PostMapping("/{id}/delete")
    public String deleteGrade(@PathVariable Long id) {
        gradeService.delete(gradeService.findById(id));
        return "redirect:/grade";
    }
}
