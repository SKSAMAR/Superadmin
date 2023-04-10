package com.fintech.scnpay.data.model;

public class PackageType {
        public String name;
        public long value;

    public PackageType(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
