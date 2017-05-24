package com.periode4groep2.employeeapp.DomainModel;

import java.io.Serializable;

/**
 * Created by Niels on 5/5/2017.
 */

public class Account implements Serializable {

    private String email;
    private String IBAN;
    private Double balance;
    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private String zipCode;
    private String city;
    private String dateOfBirth;
    private boolean isEmployee;
    private String phoneNumber;

    public Account(String email, String IBAN, Double balance, String firstName, String lastName, String password, String dateOfBirth, boolean isEmployee) {
        this.email = email;
        this.IBAN = IBAN;
        this.balance = balance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.isEmployee = isEmployee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", balance=" + balance +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", isEmployee=" + isEmployee +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
