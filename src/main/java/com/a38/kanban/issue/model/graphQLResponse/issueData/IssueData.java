package com.a38.kanban.issue.model.graphQLResponse.issueData;

import com.a38.kanban.issue.model.graphQLResponse.IssueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class IssueData {

	@JsonProperty("issue")
	private IssueNode issue;

}
