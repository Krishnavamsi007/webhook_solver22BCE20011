package com.example.webhooksolver.dto;

public class SubmitQueryRequest {
    private String finalQuery;

    public SubmitQueryRequest(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() {
        return finalQuery;
    }
}
