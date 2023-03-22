package com.asylum.apimanagement.dao;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

    private List<SearchRequest> searchReq;
    
    private GlobalOperator globalOperator;

    public enum GlobalOperator{
        AND, OR;
    }
}
