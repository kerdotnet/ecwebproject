package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.beans.Author;
import com.kerdotnet.beans.BookCatalog;
import com.kerdotnet.beans.BookCatalogAuthor;
import com.kerdotnet.dao.*;
import com.kerdotnet.exceptions.DAOSystemException;
import com.kerdotnet.exceptions.ServiceException;
import com.kerdotnet.service.IBookCatalogService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BookCatalogServiceImplTest {
    @Mock
    private IBookCatalogDAO bookCatalogDAO;
    @Mock
    private IBookCatalogAuthorDAO bookCatalogAuthorDAO;
    @Mock
    private IAuthorDAO authorDAO;
    @Mock
    private IBookItemDAO bookItemDAO;
    @Mock
    private IBookItemUserDAO bookItemUserDAO;

    @InjectMocks
    private IBookCatalogService bookCatalogService = new BookCatalogServiceImpl();

    private List<BookCatalog> bookCatalogList;
    private List<BookCatalogAuthor> bookCatalogAuthorsList;
    private List<Author> authorsList;

    @Before
    public void setUp() throws Exception{
        bookCatalogList = Arrays.asList(
                new BookCatalog(1, "Test1", "","","", true),
                new BookCatalog(2, "Test2", "","","", true),
                new BookCatalog(3, "Test3", "","","", true),
                new BookCatalog(4, "Test4", "","","", true)
        );
        bookCatalogAuthorsList = Arrays.asList(
                new BookCatalogAuthor(1,1,1),
                new BookCatalogAuthor(1,2,2),
                new BookCatalogAuthor(1,3,3)
        );
        authorsList = Arrays.asList(
                new Author(1, "Author 1", "",true),
                new Author(2, "Author 2", "",true),
                new Author(3, "Author 3", "",true)
        );
    }

    @Test
    public void testGetBookCatalogById() throws Exception{
        //Given
        BookCatalog bookCatalogTest = new BookCatalog(1, "name", "descritpion", "", "", true);

        given(bookCatalogDAO.findEntity(1))
                .willReturn(bookCatalogTest);
        given(bookCatalogAuthorDAO.findAllByBookCatalogId(1))
                .willReturn(bookCatalogAuthorsList);
        given(authorDAO.findEntity(1))
                .willReturn(authorsList.get(0));
        given(authorDAO.findEntity(2))
                .willReturn(authorsList.get(1));
        given(authorDAO.findEntity(3))
                .willReturn(authorsList.get(2));
        //When
        BookCatalog bookCatalogResult = bookCatalogService.getBookCatalogById(1);
        //Then
        assertThat(bookCatalogResult.getId(), is(1));
        assertThat(bookCatalogResult.getAuthors().size(), is(3));

        verify(bookCatalogDAO, times(1)).findEntity(1);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(1);
        verify(authorDAO, times(3)).findEntity(anyInt());
    }

    @Test
    public void testGetAllBookCatalogByPage() throws Exception{
        //Given
        given(bookCatalogDAO.findAllByPage(0, 10))
                .willReturn(bookCatalogList);

        //When
        List <BookCatalog> bookCatalogListResult = bookCatalogService.getAllBookCatalogByPage(0,10);
        //Then
        assertThat(bookCatalogListResult.size(), is(4));

        verify(bookCatalogDAO, times(1)).findAllByPage(0, 10);
    }

    @Test(expected = ServiceException.class)
    public void testGetBookCatalogById_Exception() throws Exception{
        given(bookCatalogDAO.findEntity(1))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.getBookCatalogById(1);
    }

    @Test(expected = ServiceException.class)
    public void testGetAllBookCatalogByPage_Exception() throws Exception{
        given(bookCatalogDAO.findAllByPage(1, 10))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.getAllBookCatalogByPage(1, 10);
    }

    @Test(expected = ServiceException.class)
    public void testGetAllBookCatalogQuantity_Exception() throws Exception{
        given(bookCatalogDAO.findQuantity())
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.getAllBookCatalogQuantity();
    }

    @Test(expected = ServiceException.class)
    public void testGetAllBookCatalogBySearchRequestFullTextByPage_Exception() throws Exception{
        given(bookCatalogDAO.findByKeywordsOrNameOrAuthorByPage(
                "TestSearchRequest", 1,10))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.getAllBookCatalogBySearchRequestFullTextByPage(
                "TestSearchRequest", 1,10);
    }

    @Test(expected = ServiceException.class)
    public void testGetAllBookCatalogBySearchRequestFullTextQuantity_Exception() throws Exception{
        given(bookCatalogDAO.findQuantityByKeywordsOrNameOrAuthor("TestSearchRequest"))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.getAllBookCatalogBySearchRequestFullTextQuantity(
                "TestSearchRequest");
    }

    @Test(expected = ServiceException.class)
    public void testDeleteBookCatalogById_Exception() throws Exception{
        given(bookCatalogDAO.findEntity(1))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.deleteBookCatalogById(1);
    }

    @Test(expected = ServiceException.class)
    public void testSaveBookCatalogEntity_Exception() throws Exception{
        given(bookCatalogDAO.findEntity(1))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.saveBookCatalogEntity(
                new BookCatalog(1, "test","test","","",true));
    }

    @Test(expected = ServiceException.class)
    public void testGetAllAuthors_Exception() throws Exception{
        given(authorDAO.findAll())
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.getAllAuthors();
    }

    @Test(expected = ServiceException.class)
    public void testFindAuthorById_Exception() throws Exception{
        given(authorDAO.findEntity(1))
                .willThrow(new DAOSystemException());
        //When
        bookCatalogService.findAuthorById(1);
    }
}