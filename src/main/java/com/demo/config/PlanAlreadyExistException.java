package com.demo.config;

public class PlanAlreadyExistException extends Exception{
    public PlanAlreadyExistException(String id) {
        super("Plan already exists with id: " + id);
    }
}
