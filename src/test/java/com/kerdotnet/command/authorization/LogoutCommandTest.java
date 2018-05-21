package com.kerdotnet.command.authorization;

import com.kerdotnet.controller.SessionRequestContent;
import com.kerdotnet.resource.ConfigurationManager;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LogoutCommandTest {
    private SessionRequestContent sessionRequestContent;

    @Test
    public void testExecute() throws Exception {
        sessionRequestContent = mock(SessionRequestContent.class);

        LogoutCommand logoutCommand = new LogoutCommand();
        String result = logoutCommand.execute(sessionRequestContent);

        assertThat(result, is(ConfigurationManager.getProperty("path.page.login")));

        verify(sessionRequestContent, times(1)).invalidateSession();
    }

}