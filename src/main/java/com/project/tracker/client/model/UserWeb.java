package com.project.tracker.client.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserWeb {
    private String email;
    private String firstName;
    private Integer id;
    private String lastName;
}
