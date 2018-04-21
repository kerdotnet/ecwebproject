package com.kerdotnet.beans;

import java.io.Serializable;

/**
 * Abstract class Entity
 * every Entity in inherited from this class
 * Yevhen Ivanov, 2018-04-07
 */

public abstract class Entity implements Serializable, Cloneable{
    private int id;

    public Entity(){
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return id == entity.id;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id;
        return result;
    }
}
