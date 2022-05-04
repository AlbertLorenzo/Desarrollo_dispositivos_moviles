package es.umh.dadm.category;

import com.google.gson.Gson;

import java.io.Serializable;

public class Category implements Serializable {
    private String id;
    private String shortDesc;
    private String longDesc;
    private String details;
    private String imageName;

    public Category(String id, String shortDesc, String longDesc, String details, String imageName) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.details = details;
        this.imageName = imageName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", details='" + details + '\'' +
                '}';
    }

    public String stringify() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
