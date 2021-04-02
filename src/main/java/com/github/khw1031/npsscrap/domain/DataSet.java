package com.github.khw1031.npsscrap.domain;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;

public class DataSet extends LinkedHashMap<String, Object> {

    public String getText(String key) {
        Object value = this.get(key);
        return value == null ? "" : value.toString();
    }

    public String toJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
