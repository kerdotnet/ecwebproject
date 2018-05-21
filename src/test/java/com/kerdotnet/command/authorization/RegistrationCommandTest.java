package com.kerdotnet.command.authorization;

import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import org.junit.Test;

import javax.servlet.ServletException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RegistrationCommandTest {
    private SessionRequestContent sessionRequestContent;

    @Test
    public void testExecute_Success() throws ServletException {
        sessionRequestContent = mock(SessionRequestContent.class);

        //When
        RegistrationCommand changeLocaleCommand = new RegistrationCommand();
        String result = changeLocaleCommand.execute(sessionRequestContent);
        //Then
        assertThat(result, is(ConfigurationManager.getProperty("path.page.adduser")));
    }
}