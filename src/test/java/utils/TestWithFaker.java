package utils;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import entities.RegistrationInfo;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TestWithFaker {
    private static Faker faker;
    RegistrationInfo info = DataGenerator
            .Registration
            .generateInfo("ru");

    LocalDate localDate = LocalDate.now().plusDays(3);
    DateTimeFormatter data = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String strData = localDate.format(data);

    LocalDate localDate1 = LocalDate.now().plusDays(6);
    DateTimeFormatter data1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String strData1 = localDate1.format(data1);

    @Test
    void happyPathAfterChangeDate() {

        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").val(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(strData);
        $("[data-test-id='name'] input").val(info.getName());
        $("[data-test-id='phone'] input").val(info.getPhone());
        $("[data-test-id='agreement']").click();
        $("div .button").click();
        $("[data-test-id='success-notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + strData), Duration.ofSeconds(15));

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(strData1);
        $("div .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"), Duration.ofSeconds(15));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + strData1), Duration.ofSeconds(15));

    }

}

