package com.foodcourt.user_service.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Role {
    private Long id;
    private String name;
    private String description;
}
