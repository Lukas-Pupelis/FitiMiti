package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workout_exercise")
public class Workout_exercise {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", nullable = false)
   private Long id;

   @ManyToOne
   private Workout workout;

   @ManyToOne
   private Exercise exercise;

   @Basic
   @Column(name = "EXERCISE_NUMBER")
   private Integer exercise_number;

   @Basic
   @Column(name = "SET_NUMBER")
   private Integer set_number;

   @Basic
   @Column(name = "REPS")
   private Integer reps;

   @Basic
   @Column(name = "WEIGHT")
   private Float weight;
}