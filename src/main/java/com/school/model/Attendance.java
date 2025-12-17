package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("attendance")
public class Attendance {

    @Id
    private ObjectId id;
    private int studentId;
    private String date;
    private boolean present;

    public Attendance() {
    }

    public Attendance(int studentId, String date, boolean present) {
        this.studentId = studentId;
        this.date = date;
        this.present = present;
    }
}
