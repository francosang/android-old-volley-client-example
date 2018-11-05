package com.jfranco.volleyexample.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jfranco.volleyexample.dto.Album;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class GsonUtils {

    public static <T> List<T> parseList(String json, Class<T[]> clazz) {
        T[] arr = new Gson().fromJson(json, clazz);
        return Arrays.asList(arr);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

}
