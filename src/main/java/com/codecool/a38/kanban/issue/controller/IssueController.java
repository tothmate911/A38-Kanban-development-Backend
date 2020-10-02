package com.codecool.a38.kanban.issue.controller;

import com.codecool.a38.kanban.issue.model.Project;
import com.codecool.a38.kanban.issue.model.graphQLResponse.Label;
import com.codecool.a38.kanban.issue.model.graphQLResponse.Milestone;
import com.codecool.a38.kanban.issue.model.transfer.AssigneeIssues;
import com.codecool.a38.kanban.issue.model.transfer.Filter;
import com.codecool.a38.kanban.issue.model.transfer.StoryIssues;
import com.codecool.a38.kanban.issue.service.DataManager;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class IssueController {

    private DataManager dataManager;

    @GetMapping("/issues/orderByAssignee")
    public List<AssigneeIssues> getAssigneeIssuesList(@RequestBody Filter filter) {
        return dataManager.getAssigneeIssuesList(filter);
    }

    @GetMapping("/issues/orderByStory")
    public List<StoryIssues> getStoryIssuesList(@RequestBody Filter filter) {
        return dataManager.getStoryIssuesList(filter);
    }

    @GetMapping("/statuses")
    public List<String> getStatuses() {
        return DataManager.getSTATUS_TITLES();
    }

    @GetMapping("/projects")
    public Set<Project> getProjects() {
        return dataManager.getProjects();
    }

    @GetMapping("/milestones")
    public Set<Milestone> getMilestones() {
        return dataManager.getMilestones();
    }

    @GetMapping("/stories")
    public Set<Label> getStories() {
        return dataManager.getStories();
    }

}
