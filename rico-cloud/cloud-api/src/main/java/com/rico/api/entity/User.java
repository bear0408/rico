package com.rico.api.entity;

import lombok.Data;

@Data
public class User {

    private Integer id;

    private String name;

    private String comment;

    private Integer deleteFlag;
}
