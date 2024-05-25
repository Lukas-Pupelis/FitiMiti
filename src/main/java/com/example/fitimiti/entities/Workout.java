package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

   @Column(name = "DATE", nullable = false)
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate date;

   @Column(name = "NAME")
   private String name;

   @ManyToOne
   private Member member;

   @OneToMany(mappedBy = "workout")
   private List<Workout_exercise> workout_exercises;
}