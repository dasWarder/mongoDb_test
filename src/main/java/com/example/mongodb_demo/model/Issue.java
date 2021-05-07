package com.example.mongodb_demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "issues")
public class Issue {

    @Id
    private String id;
    private String description;
    private int severity;
    private String assignee;

    public Issue(String description, int severity, String assignee) {
        this.description = description;
        this.severity = severity;
        this.assignee = assignee;
    }
}
