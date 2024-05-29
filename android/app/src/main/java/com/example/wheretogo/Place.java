package com.example.wheretogo;

public class Place {
    private String[] name;
    private String address;
    private String city;
    private String[] intro;
    private String pid;

    public Place(String[] name, String city, String address, String pid, String[] intro) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.intro = intro;
        this.pid = pid;
    }

    public Place(String city, String address) {
        this.address = address;
        this.city = city;
    }

    public String[] getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String[] getIntro() {
        return intro;
    }

    public String getPid() {
        return pid;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setIntro(String[] intro) {
        this.intro = intro;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
