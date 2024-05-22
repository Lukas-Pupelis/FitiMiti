package com.example.fitimiti.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MEMBER_WEIGHT_ENTRY")
public class Member_weight_entry {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID", nullable = false)
   private Long id;

   @Basic
   @Column(name = "DATE")
   private java.util.Date date;

   @Basic
   @Column(name = "WEIGHT")
   private Float weight;

   @ManyToOne
   private Member member;
}