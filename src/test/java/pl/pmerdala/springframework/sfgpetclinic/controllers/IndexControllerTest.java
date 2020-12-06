package pl.pmerdala.springframework.sfgpetclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class IndexControllerTest {

    IndexController controller;

    @BeforeEach
    void setUp() {
        controller = new IndexController();
    }

    @DisplayName("Test proper View name is  returned  for index page")
    @Test
    void index() {
        assertEquals("index", controller.index());
    }

    @DisplayName("Test proper View name is returned for error handle page")
    @Test
    void oopsHandler() {
        assertThrows(ValueNotFoundException.class, () -> controller.oopsHandler());
    }

    @Disabled("Only For Timeout Test")
    @Test
    void testTimeOut() {
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("After 2s sleep");
        });
    }

    @Disabled("Only For Timeout Test")
    @Test
    void testTimeOutPreemptively() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(2000);
            System.out.println("Preemptively After 2s sleep");
        });
    }

    @Test
    void testAssumeTrue() {
        assumeTrue("PAWEŁ".equalsIgnoreCase(System.getenv("USER_NAME")));
    }

    @Test
    void testAssumeTrueAssumptionIsTrue() {
        assumeTrue("PAWEŁ".equalsIgnoreCase(System.getProperty("USER_NAME","PAWEŁ")));
    }

    @EnabledOnOs(OS.MAC)
    @Test
    void testOnMac() {
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    void testOnWindows() {
    }

    @EnabledOnJre(JRE.JAVA_8)
    @Test
    void testForJava8() {
    }

    @EnabledOnJre(JRE.JAVA_11)
    @Test
    void testForJava11() {
    }

    @EnabledIfEnvironmentVariable(named="USERNAME",matches="merdala")
    @Test
    void testIfUserPM() {
    }

    @EnabledIfEnvironmentVariable(named="USERNAME",matches="Fred")
    @Test
    void testIfUserFred() {

    }
}