package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.entity.BookItemStatus;
import com.kerdotnet.entity.BookItemUser;
import com.kerdotnet.entity.User;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.IBookOperationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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
    @Mock
    private IBookItemDAO bookItemDAO;

    @InjectMocks
    private IBookOperationService operationService = new BookOperationServiceImpl();

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
                .willReturn(new BookItemUser(1, 1, null, BookItemStatus.REQUESTED, true));
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
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(1, BookItemStatus.TAKEN.name()))
                .willReturn(new BookItemUser(1,1,null,BookItemStatus.TAKEN,true));
        given(bookItemUserDAO.update(Mockito.any(BookItemUser.class)))
                .willReturn(true);
        //When
        boolean resultTakeBookItem = operationService.returnBookItemById(1);
        //Then
        assertThat(resultTakeBookItem, is(true));

        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemIdByStatus(
                1, BookItemStatus.TAKEN.name());
        verify(bookItemUserDAO, times(1)).update(Mockito.any(BookItemUser.class));
    }

    @Test
    public void testReturnBookItemById_Fail() throws Exception{
        //Given
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(1, BookItemStatus.TAKEN.name()))
                .willReturn(null);
        given(bookItemUserDAO.update(Mockito.any(BookItemUser.class)))
                .willReturn(false);
        //When
        boolean resultTakeBookItem = operationService.returnBookItemById(1);
        //Then
        assertThat(resultTakeBookItem, is(false));

        verify(bookItemUserDAO, times(1))
                .findActiveEntityByBookItemIdByStatus(1, BookItemStatus.TAKEN.name());
        verify(bookItemUserDAO, never()).update(Mockito.any(BookItemUser.class));
    }

    @Test
    public void testConfirmTakeBookItemById_Success() throws Exception{
        //Given
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(1, BookItemStatus.REQUESTED.name()))
                .willReturn(new BookItemUser(1,1,null,BookItemStatus.REQUESTED,true));
        given(bookItemUserDAO.update(Mockito.any(BookItemUser.class)))
                .willReturn(true);
        //When
        boolean resultTakeBookItem = operationService.confirmTakeBookItemById(1);
        //Then
        assertThat(resultTakeBookItem, is(true));

        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemIdByStatus(
                1, BookItemStatus.REQUESTED.name());
        verify(bookItemUserDAO, times(1)).update(Mockito.any(BookItemUser.class));
    }

    @Test
    public void testConfirmReturnBookItemById_Success() throws Exception{
        //Given
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(1, BookItemStatus.TORETURN.name()))
                .willReturn(new BookItemUser(1,1,null,BookItemStatus.TORETURN,true));
        given(bookItemUserDAO.update(Mockito.any(BookItemUser.class)))
                .willReturn(true);
        //When
        boolean resultTakeBookItem = operationService.confirmReturnBookItemById(1);
        //Then
        assertThat(resultTakeBookItem, is(true));

        verify(bookItemUserDAO, times(1)).findActiveEntityByBookItemIdByStatus(
                1, BookItemStatus.TORETURN.name());
        verify(bookItemUserDAO, times(1)).update(Mockito.any(BookItemUser.class));
    }

    @Test(expected = ServiceException.class)
    public void testReturnBookItemById_Exception() throws Exception{
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(anyInt(), any(String.class)))
                .willThrow(new DAOSystemException());
        //When
        boolean resultTakeBookItem = operationService.returnBookItemById( 1);
    }

    @Test(expected = ServiceException.class)
    public void testConfirmTakeBookItemByIdByUser_Exception() throws Exception{
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(anyInt(), any(String.class)))
                .willThrow(new DAOSystemException());
        //When
        boolean resultTakeBookItem = operationService.confirmTakeBookItemById( 1);
    }

    @Test(expected = ServiceException.class)
    public void testConfirmReturnBookItemByIdByUser_Exception() throws Exception{
        given(bookItemUserDAO.findActiveEntityByBookItemIdByStatus(anyInt(), any(String.class)))
                .willThrow(new DAOSystemException());
        //When
        boolean resultTakeBookItem = operationService.confirmReturnBookItemById( 1);
    }
}