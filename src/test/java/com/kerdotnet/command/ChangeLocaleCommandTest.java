package com.kerdotnet.command;

import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import org.junit.Test;

import javax.servlet.ServletException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;

public class ChangeLocaleCommandTest {
    private SessionRequestContent sessionRequestContent;

    @Test
    public void testExecute_SuccessMain() throws ServletException {
        //Given
        sessionRequestContent = mock(SessionRequestContent.class);
        given(sessionRequestContent.getRequestParameter(anyString()))
                .willReturn("en_US");
        given(sessionRequestContent.getSessionAttribute("user"))
                .willReturn("kerdotnet");
        //When
        ChangeLocaleCommand changeLocaleCommand = new ChangeLocaleCommand();
        String result = changeLocaleCommand.execute(sessionRequestContent);
        //Then
        assertThat(result, is(ConfigurationManager.getProperty("path.page.main")));
    }

    @Test
    public void testExecute_SuccessLogin() throws ServletException {
        //Given
        sessionRequestContent = mock(SessionRequestContent.class);
        given(sessionRequestContent.getRequestParameter(anyString()))
                .willReturn("en_US");
        given(sessionRequestContent.getSessionAttribute("user"))
                .willReturn(null);
        //When
        ChangeLocaleCommand changeLocaleCommand = new ChangeLocaleCommand();
        String result = changeLocaleCommand.execute(sessionRequestContent);
        //Then
        assertThat(result, is(ConfigurationManager.getProperty("path.page.login")));
    }
}