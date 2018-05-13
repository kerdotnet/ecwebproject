package com.kerdotnet.beans;

/**
 * java DTO class for Authors entities
 *  Yevhen Ivanov, 2018-04-21
 */

public class Author extends Entity{
    private static final long serialVersionUID = 1L;

    private String name;
    private String descritpion;
    private boolean enabled;

    public Author() {
    }

    public Author(String name, String descritpion, boolean enabled) {
        this.name = name;
        this.descritpion = descritpion;
        this.enabled = enabled;
    }

    public Author(int id, String name, String descritpion, boolean enabled) {
        this.setId(id);
        this.name = name;
        this.descritpion = descritpion;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return getId() == author.getId();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "Id=" + getId() +
                "name='" + name + '\'' +
                ", descritpion='" + descritpion + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
