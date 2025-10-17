package com.a38.kanban.issue.model;

import com.a38.kanban.issue.model.graphQLResponse.Label;
import com.a38.kanban.issue.model.graphQLResponse.Milestone;
import com.a38.kanban.issue.model.graphQLResponse.User;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Issue implements Comparable<Issue> {

    private String id;

    private String title;

    private String description;

    private String webUrl;

    private String dueDate;

    private Integer userNotesCount;

    private String reference;


    private User assignee;

    private Milestone mileStone;


    private Label status;

    private Label story;

    private Label priority;


    private Project project;

    @Override
    public int compareTo(@NonNull Issue otherIssue) {
        if (this.priority == null) return 1;
        if (otherIssue.priority == null) return -1;
        return this.priority.getPriorityNum() - otherIssue.priority.getPriorityNum();
    }
}
