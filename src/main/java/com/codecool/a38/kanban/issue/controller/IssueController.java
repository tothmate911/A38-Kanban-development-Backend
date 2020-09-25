package com.codecool.a38.kanban.issue.controller;

import com.codecool.a38.kanban.issue.dao.IssueDao;
import com.codecool.a38.kanban.issue.model.Assignee;
import com.codecool.a38.kanban.issue.model.Issue;
import com.codecool.a38.kanban.issue.model.MileStone;
import com.codecool.a38.kanban.issue.model.Project;
import com.codecool.a38.kanban.issue.model.transfer.AssigneesIssues;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class IssueController {

    private IssueDao issueDao;

    @GetMapping("/issues")
    public List<Issue> getIssues() {
        return issueDao.getAll();
    }

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return issueDao.getProjects();
    }

    @GetMapping("/milestones")
    public List<MileStone> getMilestones() {
        return issueDao.getMilestones();
    }

    @GetMapping("/statuses")
    public List<String> getStatuses() {
        return issueDao.getStatuses();
    }

    @GetMapping("/issues/orderByAssignee")
    public List<AssigneesIssues> getIssuesOrderedByAssignee() {
        return issueDao.getIssuesOrderedByAssignee();
    }

    @GetMapping("/issues/orderByStory")
    public Map<String, List<Issue>> getIssuesOrderedByStory() {
        return issueDao.getIssuesOrderedByStory();
    }

}
