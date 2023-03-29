package com.fintech.prepe.data.model;

public class SliderItem {
    String images;
    String link;

    public SliderItem(String images, String link) {
        this.images = images;
        this.link = link;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "SliderItem{" +
                "images='" + images + '\'' +
                '}';
    }
}
