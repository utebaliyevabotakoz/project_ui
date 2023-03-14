import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Tag("ui")
public class PageObjects extends TestBase {

    @Test
    @DisplayName("Проверка формы регистрации")
    void successfulRegisterTests() {
        Faker faker = new Faker((new Locale("en")));

        String userFirstName = faker.name().firstName(),
                userLastName = faker.name().lastName(),
                email = faker.internet().emailAddress(),
                number = faker.phoneNumber().subscriberNumber(10),
                day = "17",
                month = "December",
                year = "1991",
                subject = "Maths",
                address = faker.lebowski().quote();

        step("Ввод личных данных в форму", () -> {
            registrationPage.openPage()
                    .setFirstName(userFirstName)
                    .setLastName(userLastName)
                    .setEmail(email)
                    .setGender()
                    .setNumber(number)
                    .setBirthDate(day, month, year)
                    .setSubject(subject)
                    .setHobby()
                    .setPicture()
                    .setAddress(address)
                    .setState()
                    .setCity()
                    .setSubmit();
        });

        step("Проверка открытия модального окна с корректными данными ", () -> {
            registrationPage.verifyResultModalAppear()
                    .verifyResult("Student Name", userFirstName + " " + userLastName)
                    .verifyResult("Student Email", email)
                    .verifyResult("Subjects", subject)
                    .verifyResult("Address", address)
                    .verifyResult("Mobile", number);
        });
    }
}