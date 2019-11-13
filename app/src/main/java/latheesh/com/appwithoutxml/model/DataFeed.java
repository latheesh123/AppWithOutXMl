package latheesh.com.appwithoutxml.model;

public class DataFeed {

    private String title;
    private String imageUrl;
    private String description;
    private String pubDate;
    private String link;


    public DataFeed(String title, String imageUrl, String description, String pubDate, String link) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
