package com.springboottesttask.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboottesttask.domain.Suggestion;
import com.springboottesttask.dto.CsvSuggestionDto;
import lombok.NonNull;
import org.springframework.stereotype.Component;

/**
 * Created by Александр on 17.10.2017.
 */
@Component
public class CsvSuggestionConverter {

    public CsvSuggestionDto toCsvSuggestionDto(@NonNull Suggestion input) {
        CsvSuggestionDto dto = new CsvSuggestionDto();
        dto.setId(input.getId());
        dto.setName(input.getName());
        dto.setType(input.getType());
        dto.setLatitude(input.getGeoPosition().getLatitude());
        dto.setLongitude(input.getGeoPosition().getLongitude());
        return dto;
    }
}
