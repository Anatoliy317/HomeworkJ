package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import page.RegistrationPage;

import java.util.Map;

import static com.codeborne.selenide.logevents.SelenideLogger.step;

public class RegistrationFormTests {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
//        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
//        Configuration.holdBrowserOpen = true;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();

    }

    RegistrationPage registrationPage = new RegistrationPage();

    @Test
    @Tag("Smoke")
    @DisplayName("Заполнение обязательных полей")
    void minimalRegistrationTest() {
        step("Открытие формы регистрации",()->{
            registrationPage.openPage();
        });
        step("Заполнение полей",()->{
            registrationPage.setFirstName("Тест")
                    .setLastName("Тестов")
                    .setGender("Other")
                    .setNumber("89012345678");
        });
        step("Проверка",()->{
            registrationPage.submit()
                    .checkSubmitResult("Student Name", "Тест Тестович")
                    .checkSubmitResult("Gender", "Other")
                    .checkSubmitResult("Mobile", "89012345678");
        });
    }

    @Test
    @Tag("Regress")
    @DisplayName("Заполнение всех полей регистрации")
    void registration() {
        step("Открытие формы регистрации",()->{
            registrationPage.openPage();
        });
        step("Заполнение полей",()->{
            registrationPage.setFirstName("Ivan")
                    .setLastName("Ivanov")
                    .setEmail("ivan@mail.ru")
                    .setGender("Other")
                    .setNumber("8901234567")
                    .setDateOfBirth("20", "January", "2001")
                    .setSubject("Economics")
                    .setHobbies("Reading").setHobbies("Sports")
                    .setPicture("homework.jpg")
                    .setAddress("Москва 1")
                    .setState("NCR")
                    .setCity("Delhi");
        });
        step("Проверка",()->{
            registrationPage.submit()
                    .checkSubmitResult("Student Name", "Ivan" + " " + "Ivanov")
                    .checkSubmitResult("Student Email", "ivan@mail.ru")
                    .checkSubmitResult("Gender", "Other")
                    .checkSubmitResult("Mobile", "8901234567")
                    .checkSubmitResult("Date of Birth", "20 January,2001")
                    .checkSubmitResult("Subjects", "Economics")
                    .checkSubmitResult("Hobbies", "Reading, Sports")
                    .checkSubmitResult("Picture", "homework.jpg")
                    .checkSubmitResult("Address", "Москва 1")
                    .checkSubmitResult("State and City", "NCR Delhi");
        });
    }

    @Test
    @Tag("Regress")
    @DisplayName("Негативный тест")
    void negativeTest() {
        step("Открытие формы регистрации",()->{
            registrationPage.openPage();
        });
        step("Заполнение полей",()->{
            registrationPage.setGender("Other")
                    .setNumber("1234567890");
        });
        step("Проверка",()->{
            registrationPage.submit()
                    .checkBorderColor();
        });
    }
}