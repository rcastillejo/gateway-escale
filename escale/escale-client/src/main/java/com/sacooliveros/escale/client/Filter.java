package com.sacooliveros.escale.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rcastillejo on 02/06/2016.
 */
public class Filter implements Cloneable{
    private List<String> levels;
    private List<String> states;
    private String year;
    private int start;
    private String expandLevel;

    public Filter() {
        this.levels = new ArrayList<String>();
        this.states= new ArrayList<String>();
    }

    public List<String> getStates() {
        return states;
    }

    public List<String> getLevels() {
        return levels;
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


    public void setPrefixLevel(String level) {
        if(level != null && !level.isEmpty()){
            this.levels.clear();
            this.levels.add(level.substring(0,1));
        }
    }

    public void setYear(String year) {
        this.year = year;
    }
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getExpandLevel() {
        return expandLevel;
    }

    public void setExpandLevel(String expandLevel) {
        this.expandLevel = expandLevel;
    }

    public String getYear() {
        return year;
    }

    public String getFirstlevel() {
        return levels == null ? null : levels.get(0);
    }

    @Override
    public Filter clone() {
        try {
            return (Filter) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

    @Override
    public String toString() {
        return "Filter{" +
                "levels=" + levels +
                ", states=" + states +
                ", year='" + year + '\'' +
                ", start=" + start +
                ", expandLevel='" + expandLevel + '\'' +
                '}';
    }
}
