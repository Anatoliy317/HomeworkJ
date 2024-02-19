package tests;

import org.junit.jupiter.api.Test;
import page.TextBoxPage;

public class TextBoxTests extends TestBase {

    TextBoxPage textBoxPage = new TextBoxPage();

    @Test
    void fillFormTest() {
        textBoxPage.openPage()
                .setUserName("Тестов")
                .setUserEmail("test@aaa.com")
                .setCurrentAddress("Address")
                .setPermanentAddress("Moskow 1")
                .submit()
                .checkOutput("name", "Тестов")
                .checkOutput("email", "test@aaa.com")
                .checkOutput("currentAddress", "Address")
                .checkOutput("permanentAddress", "Moskow 1");
    }

}