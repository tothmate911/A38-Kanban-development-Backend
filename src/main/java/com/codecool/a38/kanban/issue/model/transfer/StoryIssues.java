package com.codecool.a38.kanban.issue.model.transfer;

import com.codecool.a38.kanban.issue.model.Issue;
import com.codecool.a38.kanban.issue.model.graphQLResponse.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoryIssues {

    private Label story;

    private LinkedHashMap<String, List<Issue>> statusIssuesMap;

}
