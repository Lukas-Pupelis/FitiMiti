package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MEMBER")
public class Member {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", nullable = false)
   private Long id;

   @Basic
   @Column(name = "EMAIL")
   private String email;

   @Basic
   @Column(name = "NAME")
   private String name;

   @OneToMany(mappedBy = "member")
   private List<Member_weight_entry> member_weight_entries;

   @OneToMany(mappedBy = "member")
   private List<Workout> workouts;

   @OneToMany(mappedBy = "member")
   private List<Exercise> exercises;
}
