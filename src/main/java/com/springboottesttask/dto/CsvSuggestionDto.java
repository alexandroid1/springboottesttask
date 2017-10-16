package com.springboottesttask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Александр on 15.10.2017.
 */
@Data
public class CsvSuggestionDto {
    @JsonProperty("_id")
    private long id;
    private String name;
    private String type;
    private double latitude;
    private double longitude;

}
