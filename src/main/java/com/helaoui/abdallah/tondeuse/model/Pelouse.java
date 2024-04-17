package com.helaoui.abdallah.tondeuse.model;

public class Pelouse {
    private static int topRightX;
    private static int topRightY;

    public Pelouse(int topRightX, int topRightY) {
        this.topRightX = topRightX;
        this.topRightY = topRightY;
    }

    public  static int getTopRightX() {
        return topRightX;
    }

    public  static void setTopRightX(int topRightX) {
        topRightX = topRightX;
    }

    public  static int getTopRightY() {
        return topRightX;
    }

    public  static void setTopRightY(int topRightY) {
        topRightY = topRightY;
    }


}
