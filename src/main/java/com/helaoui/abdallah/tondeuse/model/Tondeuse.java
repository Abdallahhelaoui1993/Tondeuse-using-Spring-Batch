package com.helaoui.abdallah.tondeuse.model;



public class Tondeuse {
    private int x;
    private int y;
    private char orientation;

    public Tondeuse() {
    }

    public char getOrientation() {
        return orientation;
    }

    public void setOrientation(char orientation) {
        this.orientation = orientation;
    }

    private String instructions;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public Tondeuse(int x, int y, char orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;

    }

    public Tondeuse(int x, int y, char orientation , String instructions) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.instructions=instructions;

    }

    // Getters and Setters

    @Override
    public String toString() {
        return x + " " + y + " " + orientation;
    }
}
