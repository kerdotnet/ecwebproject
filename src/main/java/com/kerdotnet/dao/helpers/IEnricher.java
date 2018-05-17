package com.kerdotnet.dao.helpers;

import com.kerdotnet.entity.Entity;
import com.kerdotnet.exceptions.DAOSystemException;

import javax.ejb.NoSuchEntityException;

/**
 * Helper class abstract IEnricher
 * enrich IDAO with sub IDAO
 * Yevhen Ivanov, 2018-04-12
 */

public interface IEnricher<T extends Entity> {
    IEnricher NULL = (record)->{};
            
    void enrich(T record) throws DAOSystemException, NoSuchEntityException;
}
