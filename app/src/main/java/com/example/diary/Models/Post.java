package com.example.diary.Models;

public class Post {

    int day;
    int month;
    int year;
    String photo;

    String emotion;
    String post;

    public Post() {
    }

    public Post(int day, int month, int year, String emotion, String post) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.emotion = emotion;
        this.post = post;
        this.photo = null;
    }

    public Post(int day, int month, int year, String photo, String emotion, String post) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.photo = photo;
        this.emotion = emotion;
        this.post = post;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
