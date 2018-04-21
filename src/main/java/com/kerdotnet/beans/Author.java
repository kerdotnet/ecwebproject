package com.kerdotnet.beans;

public class Author extends Entity{
    private static final long serialVersionUID = 1L;

    private String name;
    private String descritpion;

    public Author() {
    }

    public Author(String name, String descritpion) {
        this.name = name;
        this.descritpion = descritpion;
    }

    public Author(int id, String name, String descritpion) {
        this.setId(id);
        this.name = name;
        this.descritpion = descritpion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }
}
