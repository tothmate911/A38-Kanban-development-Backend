package com.codecool.a38.kanban.config.service;

import com.codecool.a38.kanban.config.model.JsonProperties;
import com.codecool.a38.kanban.config.model.LabelProperty;
import com.codecool.a38.kanban.config.model.PriorityDisplayNum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class ConfigDataProvider {

    private String storyPrefix;

    private List<String> statusDisplayTitles;

    private LinkedHashMap<String, String> statusTitleDisplayMap = new LinkedHashMap<>();

    private Map<String, PriorityDisplayNum> priorityTitleDisplayNumMap = new HashMap<>();

    private final String configJsonFilePath = "/configprops.json";

    /**
     * Loads the config data from the provided json config file
     */
    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<JsonProperties> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream(configJsonFilePath);
        try {
            JsonProperties jsonProperties = mapper.readValue(inputStream, typeReference);
            setStoryPrefix(jsonProperties);
            setStatusDisplayTitles(jsonProperties);
            setStatusTitleDisplayMap(jsonProperties);
            setPriorityTitleDisplayNumMap(jsonProperties);
        } catch (IOException e) {
            log.info("Unable to read file: " + configJsonFilePath);
            e.printStackTrace();
        }
    }

    private void setStoryPrefix(JsonProperties jsonProperties) {
        storyPrefix = jsonProperties.getStoryPrefix();
        log.info("Story prefix loaded from config Json: {}", storyPrefix);
    }

    private void setStatusDisplayTitles(JsonProperties jsonProperties) {
        statusDisplayTitles = jsonProperties.getStatuses().stream()
                .map(LabelProperty::getDisplay)
                .collect(Collectors.toList());
        log.info("Status titles loaded from config Json: {}", statusDisplayTitles);
    }

    /**
     * The status label titles and the given corresponding display titles are put in a map.
     * This map is a LinkedHashMap ot maintain the order of the statuses.
     *
     * @param jsonProperties the json properties containing the config data
     */
    private void setStatusTitleDisplayMap(JsonProperties jsonProperties) {
        jsonProperties.getStatuses().forEach(status -> statusTitleDisplayMap
                .put(status.getTitle(), status.getDisplay()));
        log.info("Status title display map loaded from config Json: {}", statusTitleDisplayMap.toString());
    }

    /**
     * The priority label titles and the given corresponding display titles are put in a map.
     * A priority number is added to each priority, which corresponds to their order in the config file.
     * This priority number will be later used to sort issues.
     *
     * @param jsonProperties the json properties containing the config data
     */
    private void setPriorityTitleDisplayNumMap(JsonProperties jsonProperties) {
        int serial = 0;
        for (LabelProperty labelProperty : jsonProperties.getPriorities()) {
            priorityTitleDisplayNumMap.put(labelProperty.getTitle(),
                    new PriorityDisplayNum(labelProperty.getDisplay(), serial++));
        }
        log.info("Priority title display map loaded from config Json: {}", priorityTitleDisplayNumMap.toString());
    }

}
