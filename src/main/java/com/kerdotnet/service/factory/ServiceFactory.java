package com.kerdotnet.service.factory;

import com.kerdotnet.dao.daofactory.AbstractDAOFactory;
import com.kerdotnet.dao.daofactory.IDAOFactory;
import com.kerdotnet.dao.transaction.TransactionInvocationHandler;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.ILoginService;
import com.kerdotnet.service.IService;
import com.kerdotnet.service.serviceimplementation.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Service Factory
 * Yevhen Ivanov, 2018-05-09
 */
public class ServiceFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFactory.class);
    private static ServiceFactory serviceFactoryInstance;
    private IDAOFactory daoFactory;

    private ServiceFactory() throws ServiceException {
        try {
            daoFactory = AbstractDAOFactory.getDAOFactory();
        } catch (DAOSystemException e) {
            throw new ServiceException("Error in daoFactoryCreating", e);
        }
    }

    public ServiceFactory(IDAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public static synchronized ServiceFactory getInstance() throws ServiceException {
        if (serviceFactoryInstance == null){
            serviceFactoryInstance = new ServiceFactory();
        }
        return serviceFactoryInstance;
    }

    public ILoginService getLoginService() throws DAOSystemException {
        return (ILoginService) getProxy(ILoginService.class,
                new LoginServiceImpl(daoFactory.getUserDAO(), daoFactory.getUserAuthorityDAO(),
                        daoFactory.getAuthorityDAO()));
    }

    private Object getProxy(Class clazz, Object object){
        return Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[] { clazz },
                new TransactionInvocationHandler(object));
    }
}
