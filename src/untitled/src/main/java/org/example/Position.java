package org.example;
//TODO:添加日志记录
public class Position {
    private String name;
    private int no;
    private double longitude;
    private double latitude;

    //两种构造方法
    /**
     * 构造方法1，传入地点名称
     * @param name
     */
    public Position(String name){
        this.name=name;
        //TODO:通过名称对应经纬度
        //this.longitude=
        //this.latitude=
    }

    /**
     * 构造方法2，传入地点编码
     * @param no
     */
    public Position(int no){
        this.no=no;
        //TODO:通过编码对应经纬度
        //this.longitude=
        //this.latitude=
    }

    public double getLongitude(){
        return this.longitude;
    }
    public double getLatitude(){
        return this.latitude;
    }

    public double distance(Position p){
        //TODO:计算球面上两点坐标
        return 0.0;
    }
    public double direction(Position p){
        //TODO:计算球面上两点的方向
        return 0.0;
    }
}
