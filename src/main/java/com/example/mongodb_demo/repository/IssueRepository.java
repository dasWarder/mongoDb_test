package com.example.mongodb_demo.repository;

import com.example.mongodb_demo.model.Issue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {
}
