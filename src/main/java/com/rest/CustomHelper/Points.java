package com.rest.CustomHelper;

/**
 * Class to represent the Start coordinates(x1,y1) and the end coordinates(x2,y2) of the matched template within the input file.
 */
public class Points {
    int x1;
    int x2;

    int y1;
    int y2;

    /**
     *
     * @return x coordinate of start point
     */
    public int getX1() {
        return x1;
    }

    /**
     * Sets x coordinate of start point
     * @param x1
     */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * x coordinate of end point
     * @return
     */
    public int getX2() {
        return x2;
    }

    /**
     * Sets x coordinate of end point
     * @param x2
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     *
     * @return y coordinate of start point
     */
    public int getY1() {
        return y1;
    }

    /**
     *
     * @param y1 Sets y coordinate of start point
     */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     *
     * @return y coordinate of end point
     */
    public int getY2() {
        return y2;
    }

    /**
     *
     * @param y2 y coordinate of end point
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }
}
