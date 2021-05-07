package com.example.mongodb_demo.controller;


import com.example.mongodb_demo.model.Issue;
import com.example.mongodb_demo.repository.IssueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
@Tag(name = "Issue", description = "Issue REST controller API")
public class IssueRestController {

    private final IssueRepository issueRepository;

    public IssueRestController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }


    @Operation(summary = "Get all issues", tags = { "Issue" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Issue.class)))) })
    @GetMapping(value = "/issues", produces = {"application/json"})
    public Collection<Issue> getAll() {
        return issueRepository.findAll();
    }


    @Operation(summary = "Get single issue by id", tags = { "Issue" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(schema = @Schema(implementation = Issue.class)))
    })
    @GetMapping(value = "/issues/issue/{id}", produces = ("/application/json"))
    @ResponseBody
    public Issue getOne(@Parameter(description = "id of the issue to be gotten", required = true)
                            @PathVariable String id) {
        Optional<Issue> byId = issueRepository.findById(id);

        if(byId.isPresent()) {
            return byId.get();
        }

        return new Issue();
    }

    @Operation(summary = "Delete issue by id", tags = { "Issue" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation")
    })
    @DeleteMapping(value = "/issues/issue/delete/{id}")
    public ResponseEntity deleteOne(@Parameter(description = "Id of the issue to be deleted", required = true)
                                        @PathVariable String id) {
        issueRepository.deleteById(id);
        return new ResponseEntity(String.format("Issue with id=%s was successfully delete!", id), HttpStatus.OK);
    }

    @Operation(summary = "Save issue to DB", tags = { "Issue" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
            content = @Content(schema = @Schema(implementation = Issue.class)))
    })
    @PostMapping(value = "/issues/issue", produces = "application/json")
    @ResponseBody
    public Issue saveOne(@Parameter(description = "Issue to save", required = true,
            content = @Content(schema = @Schema(implementation = Issue.class)))
                             @RequestBody Issue issue) {
        return issueRepository.save(issue);
    }

    @Operation(summary = "update issue by id", tags = { "Issue" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "400", description = "bad request")
    })
    @PutMapping(value = "/issues/issue/update/{id}")
    public ResponseEntity updateOne(@Parameter(description = "Issue with id to be update", required = true)
                                        @PathVariable String id,
                                    @Parameter(description = "Issue to update", required = true,
                                    content = @Content(schema = @Schema(implementation = Issue.class)))
                                    @RequestBody Issue issue) {
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
