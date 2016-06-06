package com.sacooliveros.escale.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rcastillejo on 02/06/2016.
 */
public class Filter {
    private List<String> levels;
    private List<String> states;
    private List<String> years;
    private int start;

    public Filter() {
        this.levels = new ArrayList<String>();
        this.states= new ArrayList<String>();
        this.years= new ArrayList<String>();
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getLevels() {
        return levels;
    }

    public List<String> getYears() {
        return years;
    }

    public void addStates(String... states) {
        if(states != null && states.length > 0){
            this.states = Arrays.asList(states);
        }
    }

    public void addLevels(String... levels) {
        if(levels != null && levels.length > 0){
            this.levels = Arrays.asList(levels);
        }
    }

    public void addYears(String... years) {
        if(years != null && years.length > 0){
            this.years = Arrays.asList(years);
        }
    }
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
