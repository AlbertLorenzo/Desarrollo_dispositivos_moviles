package es.umh.dadm.ticket;

import android.database.Cursor;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Ticket {

    protected int id;
    protected String image;
    protected int category;
    protected double price;
    protected String date;
    protected String shortDesc;
    protected String longDesc;
    protected String location;

    public Ticket(String image, int category, double price, String date, String shortDesc, String longDesc, String location) {
        this.image = image;
        this.category = category;
        this.price = price;
        this.date = date;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.location = location;
    }

    public Ticket() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", foto='" + image + '\'' +
                ", categoria=" + category +
                ", precio=" + price +
                ", fecha='" + date + '\'' +
                ", desCorta='" + shortDesc + '\'' +
                ", desLarga='" + longDesc + '\'' +
                ", localizacion='" + location + '\'' +
                '}';
    }
}