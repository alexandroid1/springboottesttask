package com.springboottesttask.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.springboottesttask.domain.Suggestion;
import com.sun.xml.internal.stream.Entity;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Александр on 16.10.2017.
 */
@Component
public class GoEuroApiClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${application.suggestionUrlTemplate}")
    private String suggestionUrl;

    public List<Suggestion> findSuggestionsByCity(@NonNull String city) {
        ResponseEntity<Suggestion[]> response =
                restTemplate.getForEntity(suggestionUrl, Suggestion[].class, ImmutableMap.of("city", city));

        return ImmutableList.copyOf(response.getBody());
    }
}
