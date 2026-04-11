package com.tfg.configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ApplicationConfigTest {

    @Test
    void passwordCorrecto() {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = "1234";
        String encoded = encoder.encode(password);

        assertNotEquals(password, encoded);
        assertTrue(encoder.matches(password, encoded));
    }

    @Test
    void passwordIncorrecto() {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "1234";
        String encoded = encoder.encode(password);

        assertFalse(encoder.matches("1111", encoded));
    }
}