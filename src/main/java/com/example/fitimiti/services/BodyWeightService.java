package com.example.fitimiti.services;

import com.example.fitimiti.entities.Member_weight_entry;
import com.example.fitimiti.repositories.BodyWeightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BodyWeightService {
    private final BodyWeightRepository bodyWeightRepository;

    @Autowired
    public BodyWeightService(BodyWeightRepository bodyWeightRepository) {
        this.bodyWeightRepository = bodyWeightRepository;
    }

    public List<Member_weight_entry> getBodyWeightByMemberId(Long memberId, String period) {
        try {
            return bodyWeightRepository.findByMemberId(memberId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching body weight entries for member id: " + memberId);
        }
    }

    public void saveBodyWeight(Member_weight_entry bodyWeight) {
        try {
            bodyWeightRepository.save(bodyWeight);
        } catch (Exception e) {
            e.printStackTrace();
            // Optionally, throw a custom exception
            throw new RuntimeException("Error saving body weight entry");
        }
    }
}
