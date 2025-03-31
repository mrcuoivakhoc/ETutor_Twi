package com.example.Comp1640.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    private LocalDate birthday;

    private String imageFile;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_major")
    private Major major;

    @OneToMany( mappedBy="tutor",targetEntity = Classroom.class, cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Classroom> classrooms;

    @OneToMany( mappedBy="tutor",targetEntity = Schedule.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Schedule> schedules;

    public Tutor( String name, LocalDate birthday, User user, Major major) {
        this.name = name;
        this.birthday = birthday;
        this.user = user;
        this.major = major;
    }

    public Tutor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }
}
