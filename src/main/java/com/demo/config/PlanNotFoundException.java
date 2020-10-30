package com.demo.config;

public class PlanNotFoundException extends Exception{
    public PlanNotFoundException(String id) {
        super("Couldn't find plan with id: " + id);
    }
}
