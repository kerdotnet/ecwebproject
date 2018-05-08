package com.kerdotnet.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PasswordValidatorParametrizedTest {

    private boolean expectedResult;
    private String password;
    private String generatedHash;

    private final static String [][] EXCEPTIONS_HASH_PROVIDER = {
            {"abc","$2b$04$a9KMRWPRiJuS8hPMl7WPhefpDXYwq53iQ8Xf2y1ZXbC.6r90isuz2"},
            {"dsfsad", null}
    };

    public PasswordValidatorParametrizedTest(boolean result, String password, String generatedHash) {
        this.expectedResult = result;
        this.password = password;
        this.generatedHash = generatedHash;
    }

    @Parameters
    public static Collection<Object[]> testConditions(){
        Object [][] correctHashProvider = {
                {true, "abc","$2a$04$mpyxgQmXoCKlQ88rLZQopeMK0UTQ89RjUHmUZpmimXe1QWZYZDC.y"}
                ,{true, "ert__123","$2a$04$Ccdbv3AOPiy3zPctF/eGB.Y6JWhdzvv1A.TPICDPgl6MgXlP7ef2e"}
                ,{true, "jbkfkjsladfn32r2+'","$2a$04$pNA3TqZ.cuUxJzcacQ43XOuGbARs6bIzQdj2lbiaAmq5ajXqhpZZG"}
                ,{false, "abc","$2a$04$a9KMRWPRiJuS8hPMl7WPhefpDXYwq53iQ8Xf2y1ZXbC.6r90isuz2"}
                ,{false, "ert__123","$2a$04$bwh0VzFsl5yT7XG/bbqTD.9ycZv0gGVM8beCiYHc8VuTRXdujArC2"}
                ,{false, "jbkfkjsladfn32r2+'","$2a$04$STy46rOLPM0oOy5Pnskmju5jCYDhMn.M1DKaL8/SC7CAqLN6ZhRBa"}
        };
        return Arrays.asList(correctHashProvider);
    }

    @Test
    public void testCheckPassword() throws Exception{
            assertEquals(expectedResult, PasswordValidator.checkPassword(password,
                            generatedHash));
    }
}