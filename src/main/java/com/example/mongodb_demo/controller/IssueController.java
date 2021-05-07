package com.example.mongodb_demo.controller;


import com.example.mongodb_demo.model.Issue;
import com.example.mongodb_demo.repository.IssueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class IssueController {

    private final IssueRepository issueRepository;

    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping(value = "/issues")
    public String getAll(Model model) {
        List<Issue> all = issueRepository.findAll();
        model.addAttribute("issues", all);
        return "list-issues";
    }

    @PostMapping(value = "/issues/issue")
    public String add(@ModelAttribute("issue") Issue issue) {
        Issue save = issueRepository.save(issue);
        return "redirect:/issues";
    }

    @GetMapping(value = "/issues/issue")
    public String createForm(Model model) {
        model.addAttribute("issue", new Issue());
        return "create-issue";
    }

    @GetMapping(value = "/issues/issue/{id}")
    public String delete(@PathVariable("id") String id) {
        Optional<Issue> byId = issueRepository.findById(id);

        if(byId.isPresent()) {
            issueRepository.delete(byId.get());
        }

        return "redirect:/issues";
    }
}
