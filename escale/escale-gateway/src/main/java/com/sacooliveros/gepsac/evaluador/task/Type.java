/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.gepsac.evaluador.task;

/**
 * @author Ricardo
 */
public enum Type {

    ALUMNO("com.sacooliveros.gepsac.evaluador.task.PostulanteTimerTask",
            "com.sacooliveros.gepsac.evaluador.task.PostulanteEvaluadorTask"),
    EVALUACION("com.sacooliveros.gepsac.evaluador.task.TimerTask",
            "com.sacooliveros.gepsac.evaluador.task.EvaluadorTask");

    private String timer;
    private String evaluator;

    private Type(String timer, String evaluator) {
        this.timer = timer;
        this.evaluator = evaluator;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(String evaluator) {
        this.evaluator = evaluator;
    }

}
