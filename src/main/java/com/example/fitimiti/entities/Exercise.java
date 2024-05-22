package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "exercise")
public class Exercise {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", nullable = false)
   private Long id;

   @Basic
   @Column(name = "NAME")
   private String name;

   @ManyToOne
   private Member member;

   @Basic
   @Column(name = "SHARED")
   private Boolean shared;

   @OneToMany(mappedBy = "exercise")
   private List<Workout_exercise> workoutExerciseList;
}