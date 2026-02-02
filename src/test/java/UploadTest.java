import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UploadTest {

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
     void uploadTestPositive()
    {
        //Шаг 1. Открываем сайт
        open("https://the-internet.herokuapp.com/");
        System.out.println("1. Сайт Открыт");

        //Шаг 2. Находим ссылку на File Upload
        assertTrue ($("a[href='/upload']").exists());

        //Шаг 3. Переходим на страницу выгрузки файла
        $(By.linkText("File Upload")).click();
        System.out.println("2. Открыта страница File Uploader");
        sleep(1000);

        //Шаг 4. Находим кнопку "Выберите файл" и нажимаем на неё
        $("input[type='file']").shouldBe(visible);
        System.out.println("3. Видим кнопку 'Выберите файл' и надпись 'Файл не выбран'");

        $("input[id ='file-upload']").uploadFromClasspath("files/TestPicture.jpg");
        System.out.println("4. Выбираем и загружаем файл в загрузчик...");
        sleep(3000);

        //Шаг 5. Находим кнопку "Upload" и нажимаем на неё
        $("input[class ='button']").click();
        System.out.println("5. Загружаем картинку на сайт");
        sleep(3000);

        //Шаг 6. Проверяем, что картинка загрузилась
        assertTrue (  $(byText("File Uploaded!")).isDisplayed() );
        System.out.println("6. Появилось уведомление о том, что файл загружен");
        sleep(3000);
        // Проверяем название файла
        $("#uploaded-files").shouldHave(text("TestPicture.jpg"));
        System.out.println("7. Отображается корректное название файла: TestPicture.jpg");
    }
    @Test
    void uploadTestNegative()
    {
        //Шаг 1. Открываем сайт
        open("https://the-internet.herokuapp.com/");
        System.out.println("1. Сайт Открыт");

        //Шаг 2. Находим ссылку на File Upload
        assertTrue ($("a[href='/upload']").exists());

        //Шаг 3. Переходим на страницу выгрузки файла
        $(By.linkText("File Upload")).click();
        System.out.println("2. Открыта страница File Uploader");
        sleep(1000);

        //Шаг 4. Находим кнопку "Выберите файл" и нажимаем на неё
        $("input[type='file']").shouldBe(visible);
        System.out.println("3. Видим кнопку 'Выберите файл' и надпись 'Файл не выбран'");

        //Шаг 5. Находим кнопку "Upload" и нажимаем на неё без выбора файла
        $("input[class ='button']").click();
        System.out.println("5. Ничего не загружаем на сайт");
        sleep(2000);

        //Шаг 6. Проверяем, что мы остались на странице загрузки
        $(byText("File Uploader")).shouldBe(visible);

        // Дополнительная проверка: нет ли сообщения об успехе?
        $(byText("File Uploaded!")).shouldNotBe(visible);

        System.out.println("6. Появилась ошибка, что файл не загружен");
        // Проверяем элементы формы
        sleep(2000);

          }
}
