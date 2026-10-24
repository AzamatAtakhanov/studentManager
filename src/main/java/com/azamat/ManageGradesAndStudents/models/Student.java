package com.azamat.ManageGradesAndStudents.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.stream.IntStream;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Введите ФИО")
    private String name;

    @NotBlank(message = "Выберите пол")
    private String gender;

    // 0 means "no mark yet"
    @Min(0) @Max(5)
    private int firstQuarter;
    @Min(0) @Max(5)
    private int secondQuarter;
    @Min(0) @Max(5)
    private int thirdQuarter;
    @Min(0) @Max(5)
    private int fourthQuarter;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Grade grade;

    /** Average of the quarters that already have a mark, or 0 if there are none. */
    public double getAverage() {
        return IntStream.of(firstQuarter, secondQuarter, thirdQuarter, fourthQuarter)
                .filter(mark -> mark > 0)
                .average()
                .orElse(0);
    }
}
