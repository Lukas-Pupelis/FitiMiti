package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "WORKOUT")
public class Workout {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", nullable = false)
   private Long id;

   @Basic
   @Column(name = "DATE")
   private java.util.Date date;


   @ManyToOne
   private Member member;

   @OneToMany(mappedBy = "workout")
   private List<Workout_exercise> workout_exercises;
}