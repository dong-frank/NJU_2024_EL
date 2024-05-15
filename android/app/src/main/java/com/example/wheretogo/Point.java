package com.example.wheretogo;

public class Point extends com.baidu.lbsapi.tools.Point{
    protected double x;
    protected double y;

    protected String name;

    public Point(double x, double y) {
        super(x, y);
    }

    public Point(double x, double y, String name) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double[] getPoint() {
        return new double[]{x, y};
    }
}
