package com.example.fitimiti.repositories;

import com.example.fitimiti.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
   Optional<Member> findByEmail(String email);

}
