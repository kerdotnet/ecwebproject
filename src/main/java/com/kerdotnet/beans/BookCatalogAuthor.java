package com.kerdotnet.beans;

/**
 * User Authority java DTO class for links beetwen Book Catalog and Authors
 *  Yevhen Ivanov, 2018-04-21
 */
public class BookCatalogAuthor extends Entity{
    private static final long serialVersionUID = 1L;

    private int bookCatalogId;
    private int authorId;

    public BookCatalogAuthor() {
    }

    public BookCatalogAuthor(int bookCatalogId, int authorId) {
        this.bookCatalogId = bookCatalogId;
        this.authorId = authorId;
    }

    public BookCatalogAuthor(int id, int bookCatalogId,
                             int authorId) {
        this.setId(id);
        this.bookCatalogId = bookCatalogId;
        this.authorId = authorId;
    }

    public int getBookCatalogId() {
        return bookCatalogId;
    }

    public void setBookCatalogId(int bookCatalogId) {
        this.bookCatalogId = bookCatalogId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "BookCatalogAuthor{" +
                "Id=" + getId() +
                "bookCatalogId=" + bookCatalogId +
                ", authorId=" + authorId +
                '}';
    }
}
