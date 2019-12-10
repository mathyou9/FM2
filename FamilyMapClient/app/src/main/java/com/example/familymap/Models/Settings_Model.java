package com.example.familymap.Models;

public class Settings_Model {

    private boolean isLifeStoryLines = true;
    private boolean isFamilyTreeLines = true;
    private boolean isSpouseLines = true;
    private boolean isFathersSide = true;
    private boolean isMothersSide = true;
    private boolean isMaleEvents = true;
    private boolean isFemaleEvents = true;

    private static Settings_Model instance;


    private Settings_Model(){}
    public static Settings_Model getInstance(){
        if(instance == null){
            instance = new Settings_Model();
        }
        return instance;
    }

    public boolean isLifeStoryLines() {
        return isLifeStoryLines;
    }

    public void setLifeStoryLines(boolean lifeStoryLines) {
        isLifeStoryLines = lifeStoryLines;
    }

    public boolean isFamilyTreeLines() {
        return isFamilyTreeLines;
    }

    public void setFamilyTreeLines(boolean familyTreeLines) {
        isFamilyTreeLines = familyTreeLines;
    }

    public boolean isSpouseLines() {
        return isSpouseLines;
    }

    public void setSpouseLines(boolean spouseLines) {
        isSpouseLines = spouseLines;
    }

    public boolean isFathersSide() {
        return isFathersSide;
    }

    public void setFathersSide(boolean fathersSide) {
        isFathersSide = fathersSide;
    }

    public boolean isMothersSide() {
        return isMothersSide;
    }

    public void setMothersSide(boolean mothersSide) {
        isMothersSide = mothersSide;
    }

    public boolean isMaleEvents() {
        return isMaleEvents;
    }

    public void setMaleEvents(boolean maleEvents) {
        isMaleEvents = maleEvents;
    }

    public boolean isFemaleEvents() {
        return isFemaleEvents;
    }

    public void setFemaleEvents(boolean femaleEvents) {
        isFemaleEvents = femaleEvents;
    }
}
