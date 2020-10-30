package com.demo.controller;


import com.demo.config.JsonValidator;
import com.demo.config.PlanAlreadyExistException;
import com.demo.config.PlanNotFoundException;
import com.demo.repo.PlanRepository;
import com.github.fge.jsonpatch.JsonPatchException;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.everit.json.schema.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


@RestController
public class PlanController {

    private final PlanRepository repository;
    private final JsonValidator jsonValidator;

    public PlanController(PlanRepository repository, JsonValidator jsonValidator) {
        this.repository = repository;
        this.jsonValidator = jsonValidator;
    }

    @GetMapping("/plan")
    public Map<String, JSONObject> all(){
        return repository.findAllInMap();
    }
    

    @GetMapping("/plan/{id}")
    JSONObject one(@PathVariable String id) throws PlanNotFoundException {
        if (!repository.hasKey(id)) {
            throw new PlanNotFoundException(id);
        }
        return repository.findById(id);
    }


    @PostMapping("/plan")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    String post(@RequestBody String jsonString) throws ValidationException, ParseException, PlanAlreadyExistException {
        InputStream inputStream = getClass().getResourceAsStream("schema.json");
        jsonValidator.validator(inputStream, jsonString);
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            String id = jsonObject.getAsString("objectId");
//            if (repository.hasKey(id)) {
//                throw new PlanAlreadyExistException(id);
//            }
            repository.save(id, jsonObject);
            return "Plan Saved";
        } catch (ParseException e) {
            throw e;
        }
    }

//    @PutMapping("/plan")
//    @ResponseStatus(HttpStatus.CREATED)
//    void add(@RequestBody String json) throws ParseException {
//        InputStream inputStream = getClass().getResourceAsStream("schema.json");
//        jsonValidator.validator(inputStream, json);
//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) parser.parse(json);
//        String id = jsonObject.getAsString("objectId");
//        repository.save(id, json);
//    }

    @DeleteMapping("/plan/{id}")
    String deletePlan(@PathVariable String id) throws PlanNotFoundException{
        if (!repository.hasKey(id)) {
            throw new PlanNotFoundException(id);
        }
        repository.deleteById(id);
        return "Plan deleted with id: " +id;
    }

    @PatchMapping("/plan/{id}")
    @ResponseStatus(HttpStatus.OK)
    JSONObject update(@RequestBody String jsonString, @PathVariable String id) throws PlanNotFoundException, ParseException, JsonPatchException, IOException {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            JSONObject rep = repository.update(id, jsonObject);
            return rep;
        } catch (ParseException | JsonPatchException | IOException e) {
            throw e;
        }
    }



}
