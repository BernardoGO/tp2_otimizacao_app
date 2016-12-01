package com.tproteiri.tp2otimizacao.models;

import java.util.List;

/**
 * Created by josue on 12/1/16.
 */

public class Problem {

    private String type;
    private List<Variable> objective;
    private List<Constraint> constraints;

    public Problem(String type, List<Variable> objective, List<Constraint> constraints) {
        this.constraints = constraints;
        this.objective = objective;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Variable> getObjective() {
        return objective;
    }

    public void setObjective(List<Variable> objective) {
        this.objective = objective;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }
}
