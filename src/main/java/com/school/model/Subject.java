package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("subjects")
public class Subject {

    @Id
    private ObjectId id;
    private String code;
    private String name;

    public Subject() {
    }

    public Subject(String code, String name) {
        this.code = code;
        this.name = name;
    }
    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return code + " | " + name;
    }
}
