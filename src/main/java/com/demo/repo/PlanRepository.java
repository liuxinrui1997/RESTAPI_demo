package com.demo.repo;

import com.github.fge.jsonpatch.JsonPatchException;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

import java.io.IOException;
import java.util.Map;

public interface PlanRepository {
    Map<String, JSONObject> findAllInMap();
    JSONObject findById(String id);
    void save(String id, JSONObject newPlan);
    void deleteById(String id);
    boolean hasKey(String id);
    JSONObject update(String id, JSONObject changedBody) throws JsonPatchException, IOException, ParseException;
}
