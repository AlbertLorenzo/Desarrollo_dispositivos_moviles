package es.umh.dadm.category;

public class Category {
    private String id;
    private String shortDesc;
    private String longDesc;
    private String details;

    public Category(String id, String shortDesc, String longDesc, String details) {
        this.id = id;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.details = details;
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
}
