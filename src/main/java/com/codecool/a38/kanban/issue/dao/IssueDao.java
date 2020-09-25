package com.codecool.a38.kanban.issue.dao;

import com.codecool.a38.kanban.issue.model.Assignee;
import com.codecool.a38.kanban.issue.model.Issue;
import com.codecool.a38.kanban.issue.model.MileStone;
import com.codecool.a38.kanban.issue.model.Project;
import com.codecool.a38.kanban.issue.model.transfer.AssigneesIssues;

import java.util.List;
import java.util.Map;

public interface IssueDao {

    List<Issue> getAll();

    void save(Issue build);

    List<String> getStatuses();

    List<Project> getProjects();

    List<MileStone> getMilestones();

    List<AssigneesIssues> getIssuesOrderedByAssignee();

    Map<String, List<Issue>> getIssuesOrderedByStory();

}
