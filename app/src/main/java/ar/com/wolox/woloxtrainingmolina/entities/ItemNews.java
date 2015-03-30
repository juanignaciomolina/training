package ar.com.wolox.woloxtrainingmolina.entities;

public class ItemNews {
    private String title;
    private int imageUrl;

    public ItemNews (String title,int imageUrl) {

        this.title = title;
        this.imageUrl = imageUrl;
    }

    // getters & setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }
}
