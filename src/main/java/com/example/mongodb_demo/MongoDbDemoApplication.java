package com.example.mongodb_demo;

import com.example.mongodb_demo.repository.IssueRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = IssueRepository.class)
@SpringBootApplication
public class MongoDbDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbDemoApplication.class, args);
    }

}
