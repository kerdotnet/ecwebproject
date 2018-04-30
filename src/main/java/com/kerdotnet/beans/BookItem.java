package com.kerdotnet.beans;

/**
 * java DTO class for Book items entities
 *  Yevhen Ivanov, 2018-04-21
 */
public class BookItem extends Entity {
    private static final long serialVersionUID = 1L;

    private int bookCatalogId;
    private String description;
    private String bookShelfAddress;
    private boolean enabled;
    private BookItemUser bookItemUser;

    public BookItem() {
    }

    public BookItem(int bookCatalogId, String description,
                    String bookShelfAddress, boolean enabled) {
        this.bookCatalogId = bookCatalogId;
        this.description = description;
        this.bookShelfAddress = bookShelfAddress;
        this.enabled = enabled;
    }

    public BookItem(int id, int bookCatalogId, String description,
                    String bookShelfAddress, boolean enabled) {
        this.setId(id);
        this.bookCatalogId = bookCatalogId;
        this.description = description;
        this.bookShelfAddress = bookShelfAddress;
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

    public String getBookShelfAddress() {
        return bookShelfAddress;
    }

    public void setBookShelfAddress(String bookShelfAddress) {
        this.bookShelfAddress = bookShelfAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public BookItemUser getBookItemUser() {
        return bookItemUser;
    }

    public void setBookItemUser(BookItemUser bookItemUser) {
        this.bookItemUser = bookItemUser;
    }

    public boolean isTakenByUser(){
        return (bookItemUser != null);
    }

    @Override
    public String toString() {
        return "BookItem{" +
                "Id=" + getId() +
                "bookCatalogId=" + bookCatalogId +
                ", description='" + description + '\'' +
                ", bookShelfAddress='" + bookShelfAddress + '\'' +
                ", enabled=" + enabled +
                ", bookItemUser=" + bookItemUser +
                '}';
    }
}
