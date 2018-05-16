package com.kerdotnet.dao.transactionmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Invocation Handler used for transactions
 * if a method is marked with Annotation InTransaction
 * Then this method is done with transactionmanager
 * Yevhen Ivanov, 2018-05-09
 */
public class TransactionInvocationHandler implements InvocationHandler{
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionInvocationHandler.class);

    private Object object;

    public TransactionInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ITransactionManager txManager = new TransactionManagerImpl();
        if (isTransactionAnnotationPresent(proxy, method)) {
            LOGGER.debug("TransactionInvocationHandler: Do in transaction!");
            return txManager.doInTransaction(() -> method.invoke(object, args));
        }

        LOGGER.debug("TransactionInvocationHandler: Do without transaction!");
        return txManager.doWithoutTransaction(() -> method.invoke(object, args));
    }

    private boolean isTransactionAnnotationPresent(Object proxy, Method method){
        try {
            Method invokedMethod = object.getClass().getMethod(
                    method.getName(), method.getParameterTypes());
            invokedMethod.getDeclaredAnnotations();
            return invokedMethod.isAnnotationPresent(InTransaction.class);
        } catch (NoSuchMethodException e) {
            LOGGER.debug("Error in the TransactionInvocationHandler, no such method inside TransactionAnnotationPresent");
            return false;
        }
    }
}
