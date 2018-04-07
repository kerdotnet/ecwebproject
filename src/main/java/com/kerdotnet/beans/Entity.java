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
}
