package com.example.appscanner;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainActivityTest {


    @org.junit.jupiter.api.Test
    void generateJSONArray() throws JSONException {
        MainActivity a = new MainActivity();
        String[] ids = {"a", "b", "c"};
        JSONArray array = new JSONArray();
        array.put("a");
        array.put("b");
        array.put("c");
        JSONArray test = a.generateJSONArray(ids);
        Assertions.assertEquals(test, array);
    }
}