package com.kerdotnet.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class PasswordValidatorExceptionTest {

    private final static String TEST_DATA_HASH = "pass3124";
    private final int LENGTH_HASH = 60;
    private String password;
    private String generatedHash;

    public PasswordValidatorExceptionTest(String password, String generatedHash) {
        this.password = password;
        this.generatedHash = generatedHash;
    }

    @Parameters
    public static Collection<Object[]> testConditions(){
        Object [][] correctHashProvider = {
                {"abc","$2b$04$a9KMRWPRiJuS8hPMl7WPhefpDXYwq53iQ8Xf2y1ZXbC.6r90isuz2"},
                {"dsfsad", null}
        };
        return Arrays.asList(correctHashProvider);
    }

    @Test
    public void testHashPassword_Type() throws Exception {
        assertTrue(PasswordValidator.hashPassword(TEST_DATA_HASH) instanceof String);
        assertEquals(PasswordValidator.hashPassword(TEST_DATA_HASH).length(), LENGTH_HASH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckPassword_Exception() throws Exception {
        PasswordValidator.checkPassword(password, generatedHash);
    }
}