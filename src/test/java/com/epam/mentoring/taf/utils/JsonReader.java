package com.epam.mentoring.taf.utils;

import com.epam.mentoring.taf.pojos.FollowUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {
    public static List<FollowUser> readAuthorsFromJson(String filePath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(new File(filePath), new TypeReference<List<FollowUser>>() {});
    }
}