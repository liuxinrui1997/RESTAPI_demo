package com.demo.config;


import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONException;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class JsonValidator {

    public void validator(InputStream inputStream, String jsonString) {
        try {
            org.json.JSONObject rawSchema = new org.json.JSONObject(new JSONTokener(inputStream));
            Schema schema = SchemaLoader.load(rawSchema);
            try {
                schema.validate(new org.json.JSONObject(jsonString));
            } catch (JSONException e) {
                throw e;
            }
        } catch (ValidationException e) {
            throw e;
        }
    }
}
