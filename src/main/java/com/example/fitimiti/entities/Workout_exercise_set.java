package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workout_exercise_set")
public class Workout_exercise_set {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", nullable = false)
   private Long id;

   @Basic
   @Column(name = "SET_NUMBER")
   private Integer set_number;

   @Basic
   @Column(name = "REPS")
   private Integer reps;

   @Basic
   @Column(name = "WEIGHT")
   private Float weight;

   @ManyToOne
   private Workout_exercise workout_exercise;
}