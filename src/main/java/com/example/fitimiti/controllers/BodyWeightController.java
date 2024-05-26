package com.example.fitimiti.controllers;

import com.example.fitimiti.dtos.DateWeight;
import com.example.fitimiti.entities.Member;
import com.example.fitimiti.entities.Member_weight_entry;
import com.example.fitimiti.services.BodyWeightService;
import com.example.fitimiti.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bodyWeight")
public class BodyWeightController {
    private final BodyWeightService bodyWeightService;
    private final MemberService memberService;

    @Autowired
    public BodyWeightController(BodyWeightService bodyWeightService, MemberService memberService) {
        this.bodyWeightService = bodyWeightService;
        this.memberService = memberService;
    }

    @PostMapping
    public String addWeight(@AuthenticationPrincipal OAuth2User principal, @ModelAttribute Member_weight_entry weightEntry, BindingResult result, Model model) {
        String email = principal.getAttribute("email");
        Member member = memberService.getMemberByEmail(email);
        if (member == null) {
            return "/register";
        }
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println("Validation error: " + error.getDefaultMessage()));
            return "/bodyWeight";
        }
        weightEntry.setMember(member);
        weightEntry.setDate(new java.util.Date());

        bodyWeightService.addWeight(email, weightEntry);
        return "redirect:/bodyWeight";
    }

    @GetMapping(produces = "text/html")
    public String getWeights(@RequestParam(defaultValue = "3m") String period, Model model, @AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        Member member = memberService.getMemberByEmail(email);
        if (member == null) {
            return "redirect:/register";
        }

        List<Member_weight_entry> weights = bodyWeightService.getBodyWeightByMemberId(member.getId(), period);
        Member_weight_entry weight = null;
        try {
            weight = weights.get(weights.size() - 1);
        } catch (IndexOutOfBoundsException e) {
            weight = new Member_weight_entry();
        }
        model.addAttribute("weights", weights);
        model.addAttribute("member", member);
        model.addAttribute("weight", weight);
        return "/bodyWeight";
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<DateWeight> getWeights(@RequestParam(defaultValue = "3m") String period, @AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        Member member = memberService.getMemberByEmail(email);
        return bodyWeightService.getDateWeightByMemberId(member.getId());
    }
}
