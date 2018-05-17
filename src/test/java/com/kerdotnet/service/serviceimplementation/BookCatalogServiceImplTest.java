package com.kerdotnet.service.serviceimplementation;

import com.kerdotnet.entity.*;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
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
    private List<BookItem> bookItemList;
    private List<BookItemUser> bookItemUserList;
    private BookCatalog bookCatalogEntity;

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
        bookItemList = Arrays.asList(
                new BookItem(1, 1,"","",true,
                        null),
                new BookItem(2, 1, "", "", true,
                        null),
                new BookItem(3, 1, "", "", true,
                        null)
        );

        bookItemUserList = Arrays.asList(
                new BookItemUser(1,1, 1, LocalDateTime.now().minusMonths(3), true),
                new BookItemUser(2,2, 1, LocalDateTime.now().minusMonths(2), true),
                new BookItemUser(3,3, 1, LocalDateTime.now().minusDays(5), true)
        );

        bookCatalogEntity = new BookCatalog(
                1, "name", "descritpion", "", "", true);
        bookCatalogEntity.setAuthors(authorsList);
    }

    @Test
    public void testGetBookCatalogById_Success() throws Exception{
        //Given
        given(bookCatalogDAO.findEntity(1))
                .willReturn(bookCatalogEntity);
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
        given(bookCatalogAuthorDAO.findAllByBookCatalogId(anyInt()))
                .willReturn(bookCatalogAuthorsList);
        given(authorDAO.findEntity(1))
                .willReturn(authorsList.get(0));
        given(authorDAO.findEntity(2))
                .willReturn(authorsList.get(1));
        given(authorDAO.findEntity(3))
                .willReturn(authorsList.get(2));

        //When
        List <BookCatalog> bookCatalogListResult = bookCatalogService.getAllBookCatalogByPage(0,10);
        //Then
        assertThat(bookCatalogListResult.size(), is(4));

        verify(bookCatalogDAO, times(1)).findAllByPage(0, 10);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(1);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(2);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(3);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(4);
        verify(authorDAO, times(12)).findEntity(anyInt());
    }

    @Test
    public void testGetAllBookCatalogQuantity() throws Exception{
        //Given
        given(bookCatalogDAO.findQuantity())
                .willReturn(15);

        //When
        int quantity = bookCatalogService.getAllBookCatalogQuantity();
        //Then
        assertThat(quantity, is(15));

        verify(bookCatalogDAO, times(1)).findQuantity();
    }

    @Test
    public void testGetAllBookCatalogBySearchRequestFullTextByPage() throws Exception{
        //Given
        given(bookCatalogDAO.findByKeywordsOrNameOrAuthorByPage(
                "test search request", 0, 10))
                .willReturn(bookCatalogList);
        given(bookCatalogAuthorDAO.findAllByBookCatalogId(anyInt()))
                .willReturn(bookCatalogAuthorsList);
        given(authorDAO.findEntity(1))
                .willReturn(authorsList.get(0));
        given(authorDAO.findEntity(2))
                .willReturn(authorsList.get(1));
        given(authorDAO.findEntity(3))
                .willReturn(authorsList.get(2));

        //When
        List <BookCatalog> bookCatalogListResult = bookCatalogService.getAllBookCatalogBySearchRequestFullTextByPage(
                "test search request", 0, 10);
        //Then
        assertThat(bookCatalogListResult.size(), is(4));

        verify(bookCatalogDAO, times(1))
                .findByKeywordsOrNameOrAuthorByPage("test search request",0, 10);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(1);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(2);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(3);
        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(4);
        verify(authorDAO, times(12)).findEntity(anyInt());
    }

    @Test
    public void testGetAllBookCatalogBySearchRequestFullTextQuantity() throws Exception{
        //Given
        given(bookCatalogDAO.findQuantityByKeywordsOrNameOrAuthor("Search request"))
                .willReturn(15);

        //When
        int quantity = bookCatalogService.getAllBookCatalogBySearchRequestFullTextQuantity("Search request");
        //Then
        assertThat(quantity, is(15));

        verify(bookCatalogDAO, times(1)).findQuantityByKeywordsOrNameOrAuthor("Search request");
    }

    @Test
    public void testDeleteBookCatalogById() throws Exception{
        //Given
        given(bookCatalogAuthorDAO.findAllByBookCatalogId(1))
                .willReturn(bookCatalogAuthorsList);
        given(bookCatalogAuthorDAO.delete(any(BookCatalogAuthor.class)))
                .willReturn(true);

        given(bookItemDAO.findByBookCatalogId(1))
                .willReturn(bookItemList);
        given(bookItemUserDAO.findAllByBookItemId(anyInt()))
                .willReturn(bookItemUserList);
        given(bookItemUserDAO.delete(any(BookItemUser.class)))
                .willReturn(true);
        given(bookItemDAO.delete(any(BookItem.class)))
                .willReturn(true);

        given(bookCatalogDAO.delete(bookCatalogEntity))
                .willReturn(true);
        given(bookCatalogDAO.findEntity(1))
                .willReturn(bookCatalogEntity);
        //When
        boolean result = bookCatalogService.deleteBookCatalogById(1);
        //Then
        assertThat(result, is(true));

        verify(bookItemDAO, times(1)).findByBookCatalogId(1);
        verify(bookItemUserDAO, times(3)).findAllByBookItemId(anyInt());
        verify(bookItemUserDAO, times(9)).delete(any(BookItemUser.class));
        verify(bookItemDAO, times(3)).delete(any(BookItem.class));

        verify(bookCatalogAuthorDAO, times(1)).findAllByBookCatalogId(1);
        verify(bookCatalogAuthorDAO, times(3)).delete(any(BookCatalogAuthor.class));

        verify(bookCatalogDAO, times(1)).findEntity(1);
        verify(bookCatalogDAO, times(1)).delete(bookCatalogEntity);
    }

    @Test
    public void testSaveBookCatalogEntity_Create() throws Exception{
        //Given
        given(bookCatalogDAO.findEntity(1))
                .willReturn(null);
        given(bookCatalogDAO.update(bookCatalogEntity))
                .willReturn(true);
        given(bookCatalogDAO.create(bookCatalogEntity))
                .willReturn(true);

        given(bookCatalogAuthorDAO.deleteByBookCatalogId(anyInt()))
                .willReturn(true);

        given(bookCatalogAuthorDAO.create(any(BookCatalogAuthor.class)))
                .willReturn(true);

        //When
        boolean result = bookCatalogService.saveBookCatalogEntity(bookCatalogEntity);
        //Then
        assertThat(result, is(true));

        verify(bookCatalogDAO, times(1)).findEntity(1);
        verify(bookCatalogDAO, times(1)).create(bookCatalogEntity);
        verify(bookCatalogDAO, never()).update(bookCatalogEntity);

        verify(bookCatalogAuthorDAO, times(1)).deleteByBookCatalogId(1);
        verify(bookCatalogAuthorDAO, times(3)).create(any(BookCatalogAuthor.class));
    }

    @Test
    public void testSaveBookCatalogEntity_Update() throws Exception{
        //Given
        given(bookCatalogDAO.findEntity(1))
                .willReturn(bookCatalogEntity);
        given(bookCatalogDAO.update(bookCatalogEntity))
                .willReturn(true);
        given(bookCatalogDAO.create(bookCatalogEntity))
                .willReturn(true);

        given(bookCatalogAuthorDAO.deleteByBookCatalogId(anyInt()))
                .willReturn(true);

        given(bookCatalogAuthorDAO.create(any(BookCatalogAuthor.class)))
                .willReturn(true);

        //When
        boolean result = bookCatalogService.saveBookCatalogEntity(bookCatalogEntity);
        //Then
        assertThat(result, is(true));

        verify(bookCatalogDAO, times(1)).findEntity(1);
        verify(bookCatalogDAO, never()).create(bookCatalogEntity);
        verify(bookCatalogDAO, times(1)).update(bookCatalogEntity);

        verify(bookCatalogAuthorDAO, times(1)).deleteByBookCatalogId(1);
        verify(bookCatalogAuthorDAO, times(3)).create(any(BookCatalogAuthor.class));

    }

    @Test
    public void testGetAllAuthors() throws Exception{
        //Given
        given(authorDAO.findAll())
                .willReturn(authorsList);

        //When
        List<Author> authorsResult = bookCatalogService.getAllAuthors();
        //Then
        assertThat(authorsResult.size(), is(3));

        verify(authorDAO, times(1)).findAll();
    }

    @Test
    public void testFindAuthorById() throws Exception{
        //Given
        Author authorEntity = new Author(1,"Name", "Description", true);
        given(authorDAO.findEntity(1))
                .willReturn(authorEntity);

        //When
        Author authorResult = bookCatalogService.findAuthorById(1);
        //Then
        assertThat(authorResult, is(authorEntity));

        verify(authorDAO, times(1)).findEntity(1);
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