package com.demo.model;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@RedisHash("Plan")
public class Plan implements Serializable {
    @Id
    private String id;
    private PlanCostShare[] planCostShares;

    public Plan() {}

    public Plan(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlanCostShare[] getPlanCostShares() {
        return planCostShares;
    }

    public void setPlanCostShares(PlanCostShare[] planCostShares) {
        this.planCostShares = planCostShares;
    }
}
