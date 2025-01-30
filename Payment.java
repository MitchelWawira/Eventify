package com.example.eventify;

public class Payment {

    private String name;
    private String email;
    private double amount;
    private String actionType;

    // Default constructor (required for Firebase)
    public Payment() {}

    // Constructor with parameters
    public Payment(String name, String email, double amount, String actionType) {
        this.name = name;
        this.email = email;
        this.amount = amount;
        this.actionType = actionType;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
