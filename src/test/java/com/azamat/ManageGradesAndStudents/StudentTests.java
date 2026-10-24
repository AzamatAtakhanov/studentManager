package com.azamat.ManageGradesAndStudents;

import com.azamat.ManageGradesAndStudents.models.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentTests {

    @Test
    void averageIgnoresQuartersWithoutMark() {
        Student student = new Student();
        student.setFirstQuarter(5);
        student.setSecondQuarter(4);

        assertEquals(4.5, student.getAverage());
    }

    @Test
    void averageIsZeroWithoutMarks() {
        assertEquals(0, new Student().getAverage());
    }
}
