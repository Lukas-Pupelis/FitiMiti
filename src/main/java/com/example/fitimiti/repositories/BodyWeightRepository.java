package com.example.fitimiti.repositories;

import com.example.fitimiti.entities.Member_weight_entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BodyWeightRepository extends JpaRepository<Member_weight_entry, Long> {
    List<Member_weight_entry> findByMemberId(Long memberId);
}
