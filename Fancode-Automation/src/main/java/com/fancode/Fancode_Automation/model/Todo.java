package com.fancode.Fancode_Automation.model;

import lombok.Data;

@Data
public class Todo {
    private int userId;
    private int id;
    private String title;
    private boolean completed;

}
