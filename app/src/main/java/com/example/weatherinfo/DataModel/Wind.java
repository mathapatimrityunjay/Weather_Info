package com.example.weatherinfo.DataModel;

import com.google.gson.annotations.Expose;

/**
 * Created by kundan on 8/8/2015.
 */
public class Wind {

    @Expose
    private Double speed;
    @Expose
    private Integer deg;

    /**
     *
     * @return
     * The speed
     */
    public Double getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     * The speed
     */
    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     * The deg
     */
    public Integer getDeg() {
        return deg;
    }

    /**
     *
     * @param deg
     * The deg
     */
    public void setDeg(Integer deg) {
        this.deg = deg;
    }
}
