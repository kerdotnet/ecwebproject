package com.kerdotnet.utility;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void testValidatePasswordsOnEquality(){
        assertTrue(Validator.validatePasswordsOnEquality("abv", "abv"));
    }

    @Test
    public void testValidatePasswordsOnEquality_NegativeScenario(){
        assertFalse(Validator.validatePasswordsOnEquality("", "abv"));
        assertFalse(Validator.validatePasswordsOnEquality(null, "abv"));
        assertFalse(Validator.validatePasswordsOnEquality("afs", ""));
        assertFalse(Validator.validatePasswordsOnEquality("rfdf", null));
        assertFalse(Validator.validatePasswordsOnEquality("", null));
        assertFalse(Validator.validatePasswordsOnEquality(null, null));
    }

    @Test
    public void testValidateLogin(){
        assertTrue(Validator.validateLogin("login"));
        assertTrue(Validator.validateLogin("kerdotnet"));
        assertTrue(Validator.validateLogin("kerdot_net23"));
    }

    @Test
    public void testValidateLogin_NegativeScenario(){
        assertFalse(Validator.validateLogin("logi  n"));
        assertFalse(Validator.validateLogin("kerdo#$tnet"));
        assertFalse(Validator.validateLogin("ker@#dot_ne +*t23"));
    }

    @Test
    public void testValidatePassword(){
        assertTrue(Validator.validatePassword("zxasQW123_!@"));
        assertTrue(Validator.validatePassword("asQW12!@"));
        assertTrue(Validator.validatePassword("t67g8DF^&"));
    }

    @Test
    public void testValidatePassword_NegativeScenario(){
        assertFalse(Validator.validatePassword("login"));
        assertFalse(Validator.validatePassword("kerdo tnet"));
        assertFalse(Validator.validatePassword("ker*dot_net23"));
    }

    @Test
    public void testValidateName(){
        assertTrue(Validator.validateName("Антонио Антон"));
        assertTrue(Validator.validateName("Мария Анна"));
        assertTrue(Validator.validateName("Yuriy"));
    }

    @Test
    public void testValidateName_NegativeScenario(){
        assertFalse(Validator.validateName("Ferry *"));
    }

    @Test
    public void testValidateEmail(){
        assertTrue(Validator.validateEmail("evg.ivanov@gmail.com"));
        assertTrue(Validator.validateEmail("marta@ya.ru"));
        assertTrue(Validator.validateEmail("test_test@test.com"));
    }

    @Test
    public void testValidateEmail_NegativeScenario(){
        assertFalse(Validator.validateEmail("prt ew@gm.com"));
        assertFalse(Validator.validateEmail("evgen@mail"));
        assertFalse(Validator.validateEmail("fde#g@just.com"));
    }
    @Test
    public void testValidateMobile(){
        assertTrue(Validator.validateMobile("+380952221133"));
        assertTrue(Validator.validateMobile("+38-095-222-1133"));
        assertTrue(Validator.validateMobile("095-222-1133"));
    }

    @Test
    public void testValidateMobile_NegativeScenario(){
        assertFalse(Validator.validateMobile("1133"));
        assertFalse(Validator.validateMobile("3809522211aa"));
        assertFalse(Validator.validateMobile("+380952133"));

    }
}