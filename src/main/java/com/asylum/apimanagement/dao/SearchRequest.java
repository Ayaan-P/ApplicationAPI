package com.asylum.apimanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {

    String column;
    String value;
    Operation operation;

    public enum Operation{
        EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN, BETWEEN;
    }

}