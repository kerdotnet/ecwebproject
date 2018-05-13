package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.IBookOperationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class BookOperationServiceImplTest {
    @Mock
    private IUserDAO userDAOMock;
    @Mock
    private IBookItemUserDAO bookItemUserDAO;

    @InjectMocks
    private IBookOperationService operationService = new BookOperationServiceImpl();

    @Before
    public void initData() throws Exception{

    }

    @Test
    public void testTakeBookItemByIdByUser_Success() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User(1, "kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        given(bookItemUserDAO.findActiveEntityByBookItemId(1))
                .willReturn(null);
        given(bookItemUserDAO.create(Mockito.any(BookItemUser.class)))
                .willReturn(true);
        //When
        boolean resultTakeBookItem = operationService.takeBookItemByIdByUser("kerdotnet", 1);
        //Then
        assertThat(resultTakeBookItem, is(true));

        verify(userDAOMock, times(1)).findUserByUserName("kerdotnet");
        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemId(1);
        verify(bookItemUserDAO, times(1)).create(Mockito.any(BookItemUser.class));
    }

    @Test
    public void testTakeBookItemByIdByUser_Fail() throws Exception{
        //Given
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willReturn(new User(1, "kerdotnet",
                        "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                        "", "", "", "", true));
        given(bookItemUserDAO.findActiveEntityByBookItemId(1))
                .willReturn(new BookItemUser(1, 1, null, true));
        given(bookItemUserDAO.create(Mockito.any(BookItemUser.class)))
                .willReturn(true);
        //When
        boolean resultTakeBookItem = operationService.takeBookItemByIdByUser("kerdotnet", 1);
        //Then
        assertThat(resultTakeBookItem, is(false));

        verify(userDAOMock, times(1)).findUserByUserName("kerdotnet");
        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemId(1);
        verify(bookItemUserDAO, never()).create(Mockito.any(BookItemUser.class));
    }

    @Test(expected = ServiceException.class)
    public void testTakeBookItemByIdByUser_Exception() throws Exception{
        given(userDAOMock.findUserByUserName("kerdotnet"))
                .willThrow(new DAOSystemException());
        //When
        boolean resultTakeBookItem = operationService.takeBookItemByIdByUser("kerdotnet", 1);
    }

    @Test
    public void testReturnBookItemById_Success() throws Exception{
        //Given
        given(bookItemUserDAO.findActiveEntityByBookItemId(1))
                .willReturn(new BookItemUser());
        given(bookItemUserDAO.update(Mockito.any(BookItemUser.class)))
                .willReturn(true);
        //When
        boolean resultTakeBookItem = operationService.returnBookItemById(1);
        //Then
        assertThat(resultTakeBookItem, is(true));

        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemId(1);
        verify(bookItemUserDAO, times(1)).update(Mockito.any(BookItemUser.class));
    }

    @Test
    public void testReturnBookItemById_Fail() throws Exception{
        //Given
        given(bookItemUserDAO.findActiveEntityByBookItemId(1))
                .willReturn(null);
        given(bookItemUserDAO.update(Mockito.any(BookItemUser.class)))
                .willReturn(false);
        //When
        boolean resultTakeBookItem = operationService.returnBookItemById(1);
        //Then
        assertThat(resultTakeBookItem, is(false));

        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemId(1);
        verify(bookItemUserDAO, never()).update(Mockito.any(BookItemUser.class));
    }

    @Test(expected = ServiceException.class)
    public void testReturnBookItemById_Exception() throws Exception{
        given(bookItemUserDAO.findActiveEntityByBookItemId(anyInt()))
                .willThrow(new DAOSystemException());
        //When
        boolean resultTakeBookItem = operationService.returnBookItemById( 1);
    }
}