package com.fintech.prepe.data.model;

public class CircleModel {
    public String circle, circleCode;

    public CircleModel(String circle, String circleCode) {
        this.circle = circle;
        this.circleCode = circleCode;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getCircleCode() {
        return circleCode;
    }

    public void setCircleCode(String circleCode) {
        this.circleCode = circleCode;
    }
}
