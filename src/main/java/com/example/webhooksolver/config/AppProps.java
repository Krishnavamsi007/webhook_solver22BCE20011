package com.example.webhooksolver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProps {

    @Value("${challenge.api.base:https://bfhldevapigw.healthrx.co.in/hiring}")
    private String baseApi;

    public String getBaseApi() {
        return baseApi;
    }
}
