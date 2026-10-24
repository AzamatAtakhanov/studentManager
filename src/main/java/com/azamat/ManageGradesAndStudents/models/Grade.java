package com.azamat.ManageGradesAndStudents.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введите название класса")
    private String name;

    // excluded so Grade <-> Student don't call each other's toString/hashCode forever
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "grade", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
}
