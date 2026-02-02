import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;



public class RetrievePasswordButtonTest {

    @BeforeAll
    public static void SetUp() {
        //Настройка: Открываем браузер на весь экран
        Configuration.browserSize = "1920x1080";
        //Configuration.browser = "firefox";
        //Configuration.browser = "safari";

    }

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver(); //Закрывает браузер после каждого теста
    }

    @Test
    void retrievePasswordButtonTestExists() throws InterruptedException {
        //   Шаг 1. Открываем сайт
        open("https://the-internet.herokuapp.com/");
        System.out.println("1. Открыт сайт https://the-internet.herokuapp.com/");
        sleep(1000);

        //   Шаг 2. Находим ссылку Forgot Password и переходим по ней
        $(By.linkText("Forgot Password")).click();
        System.out.println("2. Открыта страница восстановления пароля");
        sleep(1000);

        // Шаг 3. Проверяем существование кнопки Retrieve password

        try {

            // Проверяем существование
            $(By.id("form_submit")).should(exist);  // ← вот эта строка заставит тест упасть
            System.out.println("3. Кнопка восстановления пароля найдена на странице");

            // Проверяем видимость
            $(By.id("form_submit")).shouldBe(visible);
            System.out.println("4. Кнопка восстановления пароля видима на странице");

        }
        catch (ElementNotFound e) {
            System.err.println("5. ОШИБКА! Кнопка восстановления пароля не найдена на странице");
            throw e; // Перебрасываем исключение, чтобы тест упал
        }
    }
}


