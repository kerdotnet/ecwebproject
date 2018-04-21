package com.kerdotnet.beans;

/**
 * BookCatalog java DTO class
 *  Yevhen Ivanov, 2018-04-21
 */

public class BookCatalog extends Entity {
    private static final long serialVersionUID = 1L;

    private String name;
    private String fullName;
    private String description;
    private String keywords;
    private boolean enabled;

    public BookCatalog() {
    }

    public BookCatalog(String name, String fullName,
                       String description, String keywords, boolean enabled) {
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.keywords = keywords;
        this.enabled = enabled;
    }

    public BookCatalog(int id, String name, String fullName,
                       String description, String keywords, boolean enabled) {
        this.setId(id);
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.keywords = keywords;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "BookCatalog{" +
                "Id=" + getId() +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
