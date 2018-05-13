package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.beans.BookItem;
import com.kerdotnet.beans.BookItemUser;
import com.kerdotnet.beans.User;
import com.kerdotnet.dao.IBookItemDAO;
import com.kerdotnet.dao.IBookItemUserDAO;
import com.kerdotnet.dao.IUserDAO;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.IBookItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookItemServiceImplTest {
    @Mock
    private IBookItemUserDAO bookItemUserDAO;
    @Mock
    private IBookItemDAO bookItemDAO;
    @Mock
    private IUserDAO userDAO;

    @InjectMocks
    private IBookItemService bookItemService = new BookItemServiceImpl();

    private List<BookItem> bookItemList;
    private List<BookItemUser> bookItemUserList;

    @Before
    public void setUp() throws Exception{
        bookItemList = Arrays.asList(
                new BookItem(1, 1,"","",true,
                        new BookItemUser(1,1, 1, LocalDateTime.now().minusMonths(3), true)),
                new BookItem(2, 1, "", "", true,
                        new BookItemUser(2,2, 1, LocalDateTime.now().minusMonths(2), true)),
                new BookItem(3, 1, "", "", true,
                        new BookItemUser(3,3, 1, LocalDateTime.now().minusDays(5), true))
        );
        bookItemUserList = Arrays.asList(
                new BookItemUser(1,1, 1, LocalDateTime.now().minusMonths(3), true),
                new BookItemUser(2,2, 1, LocalDateTime.now().minusMonths(2), true),
                new BookItemUser(3,3, 1, LocalDateTime.now().minusDays(5), true)
        );
    }

    @Test
    public void testGetBookItemsByBookCatalogIdOnShelves_Success() throws Exception{
        //Given
        given(bookItemDAO.findByBookCatalogIdOnShelves(1))
                .willReturn(bookItemList);
        //When
        List<BookItem> bookItems = bookItemService.getBookItemsByBookCatalogIdOnShelves(1);
        //Then
        assertThat(bookItems.size(), is(3));

        verify(bookItemDAO, times(1)).findByBookCatalogIdOnShelves(1);
    }

    @Test
    public void testGetOverdueBookItemsTakenByUsers_Success() throws Exception{
        //Given
        given(bookItemDAO.findAllBookItemsTakenByUsers())
                .willReturn(bookItemList);
        //When
        List<BookItem> bookItems = bookItemService.getOverdueBookItemsTakenByUsers();
        //Then
        assertThat(bookItems.size(), is(2));

        verify(bookItemDAO, times(1)).findAllBookItemsTakenByUsers();
    }

    @Test
    public void testGetAllBookItemsTakenByConcreteUser_Success() throws Exception{
        //Given
        given(bookItemUserDAO.findAllByUserId(1))
                .willReturn(bookItemUserList);
        given(userDAO.findUserByUserName("kerdotnet"))
                .willReturn(new User(1, "kerdotnet",
                "$2a$12$A.rhSBlyO8U.cqEXbTmi..ascUBBPciDZUlZZZ./JkjlFoHyDQUQG",
                "", "", "", "", true));

        given(bookItemDAO.findEntity(1))
                .willReturn(new BookItem());
        given(bookItemDAO.findEntity(2))
                .willReturn(new BookItem());
        given(bookItemDAO.findEntity(3))
                .willReturn(new BookItem());
        //When
        List<BookItem> bookItems = bookItemService.getAllBookItemsTakenByConcreteUser("kerdotnet");
        //Then
        assertThat(bookItems.size(), is(3));

        verify(userDAO, times(1)).findUserByUserName("kerdotnet");
        verify(bookItemUserDAO, times(1)).findAllByUserId(1);
        verify(bookItemDAO, times(3)).findEntity(anyInt());
    }

    @Test
    public void testGetAllBookItemsTakenByUsers_Success() throws Exception{
        //Given
        given(bookItemDAO.findAllBookItemsTakenByUsers())
                .willReturn(bookItemList);
        //When
        List<BookItem> bookItems = bookItemService.getAllBookItemsTakenByUsers();
        //Then
        assertThat(bookItems.size(), is(3));

        verify(bookItemDAO, times(1)).findAllBookItemsTakenByUsers();
    }

    @Test
    public void testSaveBookItemEntity_SuccessUpdate() throws Exception{
        //Given
        given(bookItemDAO.findEntity(1))
                .willReturn(new BookItem());
        given(bookItemDAO.update(Mockito.any(BookItem.class)))
                .willReturn(true);
        //When
        BookItem bookItem = new BookItem(1,1,"","",true);
        boolean resultSave = bookItemService.saveBookItemEntity(
                bookItem);
        //Then
        assertThat(resultSave, is(true));

        verify(bookItemDAO, times(1)).findEntity(1);
        verify(bookItemDAO, times(1)).update(bookItem);
        verify(bookItemDAO, never()).create(Mockito.any(BookItem.class));
    }

    @Test
    public void testSaveBookItemEntity_SuccessCreate() throws Exception {
        //Given
        given(bookItemDAO.findEntity(1))
                .willReturn(null);
        given(bookItemDAO.create(Mockito.any(BookItem.class)))
                .willReturn(true);
        //When
        BookItem bookItem = new BookItem(1,1,"","",true);
        boolean resultSave = bookItemService.saveBookItemEntity(
                bookItem);
        //Then
        assertThat(resultSave, is(true));

        verify(bookItemDAO, times(1)).findEntity(1);
        verify(bookItemDAO, never()).update(Mockito.any(BookItem.class));
        verify(bookItemDAO, times(1)).create(bookItem);
    }

    @Test
    public void testDeleteBookItemById_Success() throws Exception {
        //Given
        BookItem bookItem = new BookItem(1,1,"","",true);
        given(bookItemDAO.findEntity(1))
                .willReturn(bookItem);
        given(bookItemDAO.delete(Mockito.any(BookItem.class)))
                .willReturn(true);
        //When
        boolean resultDelete = bookItemService.deleteBookItemById(
                1);
        //Then
        assertThat(resultDelete, is(true));
        verify(bookItemDAO, times(1)).findEntity(1);
        verify(bookItemDAO, times(1)).delete(bookItem);
    }

    @Test(expected = ServiceException.class)
    public void testGetBookItemsByBookCatalogIdOnShelves_Exception() throws Exception{
        given(bookItemDAO.findByBookCatalogIdOnShelves(anyInt()))
                .willThrow(new DAOSystemException());
        //When
        bookItemService.getBookItemsByBookCatalogIdOnShelves(1);
    }

    @Test(expected = ServiceException.class)
    public void testGetAllBookItemsTakenByConcreteUser_Exception() throws Exception{
        given(userDAO.findUserByUserName("kerdotnet"))
                .willThrow(new DAOSystemException());
        //When
        bookItemService.getAllBookItemsTakenByConcreteUser("kerdotnet");
    }

    @Test(expected = ServiceException.class)
    public void testGetAllBookItemsTakenByUsers_Exception() throws Exception{
        given(bookItemDAO.findAllBookItemsTakenByUsers())
                .willThrow(new DAOSystemException());
        //When
        bookItemService.getAllBookItemsTakenByUsers();
    }

    @Test(expected = ServiceException.class)
    public void testSaveBookItemEntity_Exception() throws Exception{
        given(bookItemDAO.findEntity(1))
                .willThrow(new DAOSystemException());
        //When
        bookItemService.saveBookItemEntity(new BookItem(1,1, "","",true));
    }

    @Test(expected = ServiceException.class)
    public void testDeleteBookItemById_Exception() throws Exception{
        given(bookItemDAO.findEntity(1))
                .willThrow(new DAOSystemException());
        //When
        bookItemService.deleteBookItemById(1);
    }
}