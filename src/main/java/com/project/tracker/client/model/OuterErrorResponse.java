package com.project.tracker.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OuterErrorResponse {
    private Integer errorStatus;
    private String message;
    private Map<String, List<String>> errorResponse;
}
