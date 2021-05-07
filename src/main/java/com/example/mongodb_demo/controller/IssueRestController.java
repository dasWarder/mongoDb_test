package com.example.mongodb_demo.controller;


import com.example.mongodb_demo.model.Issue;
import com.example.mongodb_demo.repository.IssueRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class IssueRestController {

    private final IssueRepository issueRepository;

    public IssueRestController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }


    @GetMapping("/issues")
    public Collection<Issue> getAll() {
        return issueRepository.findAll();
    }


    @GetMapping(value = "/issues/issue/{id}")
    @ResponseBody
    public Issue getOne(@PathVariable String id) {
        Optional<Issue> byId = issueRepository.findById(id);

        if(byId.isPresent()) {
            return byId.get();
        }

        return new Issue();
    }

    @DeleteMapping(value = "/issues/issue/delete/{id}")
    public ResponseEntity deleteOne(@PathVariable String id) {
        issueRepository.deleteById(id);
        return new ResponseEntity(String.format("Issue with id=%s was successfully delete!", id), HttpStatus.OK);
    }

    @PostMapping("/issues/issue")
    @ResponseBody
    public Issue saveOne(@RequestBody Issue issue) {
        return issueRepository.save(issue);
    }

    @PutMapping(value = "/issues/issue/update/{id}")
    public ResponseEntity updateOne(@PathVariable String id, @RequestBody Issue issue) {
        Optional<Issue> byId = issueRepository.findById(id);

        if(byId.isPresent()) {
            Issue stored = byId.get();

            stored.setDescription(issue.getDescription());
            stored.setSeverity(issue.getSeverity());
            stored.setAssignee(issue.getAssignee());

            issueRepository.save(stored);

            return new ResponseEntity(String.format("Issue with id=%s was successfully stored!", id), HttpStatus.OK);
        }

        return new ResponseEntity(String.format("Issue with id=%s doesn't exist!", id), HttpStatus.BAD_REQUEST);
    }
}
