package com.kerdotnet.beans;

public class BookItem extends Entity {
    private static final long serialVersionUID = 1L;

    private int bookCatalogId;
    private String description;
    private String bookShelfAdress;
    private boolean enabled;

    public BookItem() {
    }

    public BookItem(int bookCatalogId, String description,
                    String bookShelfAdress, boolean enabled) {
        this.bookCatalogId = bookCatalogId;
        this.description = description;
        this.bookShelfAdress = bookShelfAdress;
        this.enabled = enabled;
    }

    public BookItem(int id, int bookCatalogId, String description,
                    String bookShelfAdress, boolean enabled) {
        this.setId(id);
        this.bookCatalogId = bookCatalogId;
        this.description = description;
        this.bookShelfAdress = bookShelfAdress;
        this.enabled = enabled;
    }

    public int getBookCatalogId() {
        return bookCatalogId;
    }

    public void setBookCatalogId(int bookCatalogId) {
        this.bookCatalogId = bookCatalogId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookShelfAdress() {
        return bookShelfAdress;
    }

    public void setBookShelfAdress(String bookShelfAdress) {
        this.bookShelfAdress = bookShelfAdress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "Id=" + getId() +
                "bookCatalogId=" + bookCatalogId +
                ", description='" + description + '\'' +
                ", bookShelfAdress='" + bookShelfAdress + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
