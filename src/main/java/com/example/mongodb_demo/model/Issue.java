package com.example.mongodb_demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "issues")
public class Issue {

    @Id
    @Schema(description = "unique identifier of the issue",
            example = "fasf344f4f3h", required = true)
    private String id;

    @Schema(description = "description of the issue",
            example = "Fix the bug", required = true)
    @NotBlank
    @Size(max = 250)
    private String description;

    @Schema(description = "severity of the issue",
            example = "4", required = true)
    private int severity;

    @Schema(description = "Assignee for working out the solution",
            example = "Jack", required = true)
    @NotBlank
    @NotEmpty
    @Size(max = 100)
    private String assignee;

    public Issue(String description, int severity, String assignee) {
        this.description = description;
        this.severity = severity;
        this.assignee = assignee;
    }
}
