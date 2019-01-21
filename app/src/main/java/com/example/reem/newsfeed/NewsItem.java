package com.example.reem.newsfeed;

/**
 * Created by Reem on 20,Jan,2019
 * {@link NewsItem} represents a single NewsItem.
 * Each object has 5 properties: title, section, authorName, publicationDate and url .
 */
public class NewsItem {

    /**
     * Title of NewsItem object
     */
    private String title;

    /**
     * Section of NewsItem object
     */
    private String section;

    /**
     * Author of NewsItem object
     */
    private String authorName;

    /**
     * Publication date of NewsItem object
     */
    private String publicationDate;

    /**
     * URL of NewsItem object
     */
    private String newsItemUrl;

    /**
     * Create a new NewsItem object.
     *
     * @param title           is the NewsItem object title.
     * @param section         is the NewsItem object section.
     * @param authorName      is the NewsItem object author name.
     * @param publicationDate is the NewsItem object publication date.
     * @param newsItemUrl     is the NewsItem object URL.
     */
    public NewsItem(String title, String section, String authorName, String publicationDate, String newsItemUrl) {
        this.title = title;
        this.section = section;
        this.authorName = authorName;
        this.publicationDate = publicationDate;
        this.newsItemUrl = newsItemUrl;
    }

    /**
     * Get the NewsItem object Title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the NewsItem object Section.
     */
    public String getSection() {
        return section;
    }

    /**
     * Get the NewsItem object Author name.
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Get the NewsItem object publication date.
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    /**
     * Get the NewsItem object URL.
     */
    public String getNewsItemUrl() {
        return newsItemUrl;
    }
}
