package com.example.eventify;

public class Event {
    private String eventId;
    private String name;
    private String date;
    private String theme;
    private String price;

    // Empty constructor for Firebase
    public Event() {}

    // Constructor
    public Event(String eventId, String name, String date, String theme, String price) {
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.theme = theme;
        this.price = price;
    }

    // Getters and setters
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
