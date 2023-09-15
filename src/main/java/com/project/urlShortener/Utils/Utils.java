package com.project.urlShortener.Utils;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.urlShortener.Model.Url;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {
    public static Map<String, String> createHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("X-amazon-author", "Gabriel");
        headers.put("X-amazon-apiVersion", "v1");
        return headers;
    }

    public static String convertObjToString(Url url, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(url);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }

    public static Url convertStringToObj(String jsonBody, Context context) {
        Url url = null;
        try {
            url = new ObjectMapper().readValue(jsonBody, Url.class);
        } catch (IOException e) {
            context.getLogger().log("Error while converting string to obj:::" + e.getMessage());
        }
        return url;
    }


    public static String convertListOfObjToString(List<Url> urls, Context context) {
        String jsonBody = null;
        try {
            jsonBody = new ObjectMapper().writeValueAsString(urls);
        } catch (JsonProcessingException e) {
            context.getLogger().log("Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
}