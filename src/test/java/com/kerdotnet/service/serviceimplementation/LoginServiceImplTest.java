package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.entity.Authority;
import com.kerdotnet.entity.User;
import com.kerdotnet.entity.UserAuthority;
import com.kerdotnet.dao.IAuthorityDAO;
import com.kerdotnet.dao.IUserAuthorityDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.NotUniqueUserEmailException;
import com.kerdotnet.exceptions.NotUniqueUserLoginException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.ILoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceImplTest {

    @Mock
    private IUserDAO userDAOMock;
    @Mock
    private IUserAuthorityDAO userAuthorityDAOMock;
    @Mock
    private IAuthorityDAO authorityDAOMock;

    @InjectMocks
    private ILoginService loginService = new LoginServiceImpl();

    private List<UserAuthority> userAuthorityListWithAdmin;
    private List<UserAuthority> userAuthorityListWithoutAdmin;
    private List<Authority> authorityList;

    //@Captor
    //ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setUp() throws Exception{
        userAuthorityListWithAdmin = Arrays.asList(
                new UserAuthority(1,1),
                new UserAuthority(1,2)
        );
        userAuthorityListWithoutAdmin = Arrays.asList(
                new UserAuthority(1,1)
        );
        authorityList = Arrays.asList(
                new Authority(1, "USER", true, false),
                new Authority(2, "ADMINISTRATOR", false, true));
        given(authorityDAOMock.findEntity(1))
                .willReturn(new Authority(2, "USER", true, false));
        given(authorityDAOMock.findEntity(2))
                .willReturn(new Authority(2, "ADMINISTRATOR", false, true));
    }

    @Test
    public void testCheckLogin_CorrectUser() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User("kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        //When
        boolean resultLogin = loginService.checkLogin("kerdotnet", "disappear");
        //Then
        assertThat(resultLogin, is(true));

        verify(userDAOMock, times(1)).findUserByUserName("kerdotnet");
    }

    @Test
    public void testCheckLogin_NotCorrectUser() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User("kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        //When
        boolean resultLogin = loginService.checkLogin("kerdotnet", "ewqfdwfdfew");
        //Then
        assertThat(resultLogin, is(false));

        verify(userDAOMock, times(1)).findUserByUserName("kerdotnet");
    }

    @Test(expected = ServiceException.class)
    public void testCheckLogin_Exception() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willThrow(new DAOSystemException("Mock exception"));
        //When
        boolean resultLogin = loginService.checkLogin("kerdotnet", "ewqfdwfdfew");
    }

    @Test
    public void testCheckAdministratorRole_Administrator() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User(1, "kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        given(userAuthorityDAOMock.findAllByUserId(1))
                .willReturn(userAuthorityListWithAdmin);

        //When
        boolean resultAdmin = loginService.checkAdministratorRole("kerdotnet");
        //Then
        assertThat(resultAdmin, is(true));

        verify(userDAOMock, times(1)).findUserByUserName("kerdotnet");
        verify(userAuthorityDAOMock, times(1)).findAllByUserId(1);
        verify(authorityDAOMock, times(2)).findEntity(anyInt());
    }

    @Test
    public void testCheckAdministratorRole_User() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User(1, "kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        given(userAuthorityDAOMock.findAllByUserId(1))
                .willReturn(userAuthorityListWithoutAdmin);
        //When
        boolean resultAdmin = loginService.checkAdministratorRole("kerdotnet");
        //Then
        assertThat(resultAdmin, is(false));

        verify(userDAOMock, times(1)).findUserByUserName("kerdotnet");
        verify(userAuthorityDAOMock, times(1)).findAllByUserId(1);
        verify(authorityDAOMock, times(1)).findEntity(anyInt());
    }

    @Test(expected = ServiceException.class)
    public void testCheckAdministratorRole_Exception() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User(1, "kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        given(userAuthorityDAOMock.findAllByUserId(1))
                .willThrow(new DAOSystemException("Mock exception"));
        //When
        boolean resultAdmin = loginService.checkAdministratorRole("kerdotnet");
    }

    @Test
    public void testAddUser_Success() throws Exception{
        //Given
        User user = new User("kerdotnet",
                "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                "", "", "", "", true);
        given(userDAOMock.create(user))
                .willReturn(true);
        //When
        boolean resultAddUser = loginService.addUser(user);
        //Then
        assertThat(resultAddUser, is(true));

        verify(userDAOMock, times(1)).create(user);
    }

    @Test(expected = NotUniqueUserLoginException.class)
    public void testAddUser_NotUniqueLogin() throws Exception{
        //Given
        User user = new User("kerdotnet",
                "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                "", "", "", "", true);
        given(userDAOMock.create(user))
                .willThrow(new NotUniqueUserLoginException());
        //When
        boolean resultAddUser = loginService.addUser(user);
    }

    @Test(expected = NotUniqueUserEmailException.class)
    public void testAddUser_NotUniqueEmail() throws Exception{
        //Given
        User user = new User("kerdotnet",
                "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                "", "", "", "", true);
        given(userDAOMock.create(user))
                .willThrow(new NotUniqueUserEmailException());
        //When
        boolean resultAddUser = loginService.addUser(user);
    }

    @Test(expected = ServiceException.class)
    public void testAddUser_DAOException() throws Exception{
        //Given
        User user = new User("kerdotnet",
                "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                "", "", "", "", true);
        given(userDAOMock.create(user))
                .willThrow(new DAOSystemException());
        //When
        boolean resultAddUser = loginService.addUser(user);
    }
}