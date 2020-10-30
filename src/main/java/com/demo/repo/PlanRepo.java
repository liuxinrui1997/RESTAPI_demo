package com.demo.repo;

import com.demo.model.Plan;
import com.github.fge.jsonpatch.JsonPatchException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class PlanRepo implements PlanRepository {

    @Autowired
    private RedisTemplate<String, Plan> redisTemplate;
    private HashOperations hashOperations; //to access Redis cache
    @Autowired
    private RedisKeyValueTemplate redisKeyValueTemplate;

    public PlanRepo(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }


    @Override
    public Map<String, JSONObject> findAllInMap() {
        return hashOperations.entries("PLAN");
    }

    @Override
    public JSONObject findById(String id) {
        return (JSONObject) hashOperations.get("PLAN",id);
    }

    @Override
    public void save(String id, JSONObject json) {

        hashOperations.put("PLAN",id,json);
    }

    @Override
    public void deleteById(String id) {

        hashOperations.delete("PLAN",id);
    }

    @Override
    public boolean hasKey(String id) {
        return hashOperations.hasKey("PLAN", id);
    }

    @Override
    public JSONObject update(String id, JSONObject changedBody) {

        JSONObject exist = (JSONObject)hashOperations.get("PLAN", id);
        JSONObject combine = deepMerge(exist, changedBody);
        hashOperations.put("PLAN", id, combine);
        return combine;

    }

    private static JSONObject deepMerge(JSONObject source, JSONObject target) throws JSONException {
        for (String key : target.keySet()) {
            if (!source.containsKey(key)) {
                source.put(key, target.get(key));
            }
            else {
                Object targetValue = target.get(key);
                Object sourceValue = source.get(key);

                if (targetValue instanceof JSONArray && sourceValue instanceof JSONArray) {
                    JSONArray tempTarget = (JSONArray) targetValue;
                    JSONArray tempSource = (JSONArray) sourceValue;
                    for (Object tarObj : tempTarget) {
                        if (!((JSONArray) sourceValue).contains(tarObj)) {
                            boolean isNew = true;
                            for (Object sourceObj : tempSource) {
                                if (sourceObj instanceof JSONObject && tarObj instanceof JSONObject) {
                                    if (((JSONObject) sourceObj).get("objectId").equals(((JSONObject) tarObj).get("objectId"))) {
                                        isNew = false;
                                        deepMerge((JSONObject)sourceObj, (JSONObject)tarObj);
                                    }
                                }
                            }
                            if (isNew) {
                                ((JSONArray) sourceValue).add(tarObj);
                            }
                        }
                    }
                    source.put(key, sourceValue);
                }
                else if (targetValue instanceof JSONObject && sourceValue instanceof JSONObject) {
                    if (((JSONObject) targetValue).get("objectId").equals(((JSONObject) sourceValue).get("objectId"))) {
                        source.put(key, targetValue);
                    } else {
                        JSONArray tarArray = new JSONArray();
                        tarArray.add(sourceValue);
                        tarArray.add(targetValue);
                        source.put(key, tarArray);
                    }
                }
            }
        }
        return source;
    }



}
