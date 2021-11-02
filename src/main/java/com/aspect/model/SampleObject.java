package com.aspect.model;

public class SampleObject {

    private String name;
    private int intValue;

    public SampleObject(String name, int intValue){
        this.name = name;
        this.intValue = intValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
}
