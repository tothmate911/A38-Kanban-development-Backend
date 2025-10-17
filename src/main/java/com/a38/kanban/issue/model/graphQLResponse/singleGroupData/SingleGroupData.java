package com.a38.kanban.issue.model.graphQLResponse.singleGroupData;

import com.a38.kanban.issue.model.graphQLResponse.Group;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleGroupData {

    @JsonProperty("group")
    private Group group;

}

