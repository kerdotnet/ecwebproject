package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.beans.BookCatalog;
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

import static org.mockito.BDDMockito.given;

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

    @Before
    public void setUp() throws Exception{
        bookCatalogList = Arrays.asList(
                new BookCatalog(1, "Test1", "","","", true),
                new BookCatalog(2, "Test2", "","","", true),
                new BookCatalog(3, "Test3", "","","", true),
                new BookCatalog(4, "Test4", "","","", true)
        );
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