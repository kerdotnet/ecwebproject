package com.kerdotnet.dao.helpers;

import com.kerdotnet.beans.Entity;
import com.kerdotnet.exceptions.DAOSystemException;

import javax.ejb.NoSuchEntityException;

/**
 * Helper class abstract Enricher
 * Yevhen Ivanov, 2018-04-12
 */

public interface Enricher<T extends Entity> {
    Enricher NULL = (record)->{};
            
    void enrich(T record) throws DAOSystemException, NoSuchEntityException;
}
