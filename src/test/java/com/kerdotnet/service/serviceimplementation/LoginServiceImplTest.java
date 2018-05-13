package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.*;

public class LoginServiceImplTest {
    private LoginServiceImpl loginService;
    private IUserDAO userDAOMock;
    private IUserAuthorityDAO userAuthorityDAOMock;
    private IAuthorityDAO authorityDAOMock;

    @Before
    public void setUp() throws Exception {
        userDAOMock = mock(IUserDAO.class);
        userAuthorityDAOMock = mock(IUserAuthorityDAO.class);
        authorityDAOMock = mock(IAuthorityDAO.class);

        loginService = new LoginServiceImpl(userDAOMock,
                userAuthorityDAOMock, authorityDAOMock);
    }

    @Test
    public void testCheckLogin(){

    }

    @Test
    public void testCheckAdministratorRole(){

    }

    @Test
    public void testAddUser(){

    }
}