package com.periode4groep2.customerapp.DomainModel;

/**
 * Created by Niels on 5/5/2017.
 */

public class DeviceInfo {

    private String fingerPrint;
    private String email;
    private String baseOS;
    private String brand;
    private String model;
    private String manufacturer;
    private String device;
    private String hardware;

    public DeviceInfo(String fingerPrint, String email, String baseOS, String brand, String model, String manufacturer, String device, String hardware) {
        this.fingerPrint = fingerPrint;
        this.email = email;
        this.baseOS = baseOS;
        this.brand = brand;
        this.model = model;
        this.manufacturer = manufacturer;
        this.device = device;
        this.hardware = hardware;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBaseOS() {
        return baseOS;
    }

    public void setBaseOS(String baseOS) {
        this.baseOS = baseOS;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "fingerPrint='" + fingerPrint + '\'' +
                ", email='" + email + '\'' +
                ", baseOS='" + baseOS + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", device='" + device + '\'' +
                ", hardware='" + hardware + '\'' +
                '}';
    }
}
