package com.waw.hr.mutils.model;

import java.util.List;

public class ProvinceModel {

    private  String name;

    private List<CityModel> city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCity() {
        return city;
    }

    public void setCity(List<CityModel> city) {
        this.city = city;
    }
}
