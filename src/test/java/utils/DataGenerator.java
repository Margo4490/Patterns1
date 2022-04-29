package utils;

import com.github.javafaker.Faker;
import entities.RegistrationInfo;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
@Getter
@UtilityClass

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));


    public static class RegistrationInfo {

        public RegistrationInfo(String city, String name, String numberPhone) {
        }
    }

    public static String generateDate(int days) {

        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String getCity() {
        String city = faker.address().city();
        return city;
    }

    public static String getName() {
        String name = faker.name().name();
        return name;
    }

    public static String getNumberPhone() {
        String phoneNumber = faker.phoneNumber().phoneNumber();
        return phoneNumber;

    }

    public static RegistrationInfo getUser() {
        RegistrationInfo user = new RegistrationInfo(getCity(), getName(), getNumberPhone());
        return user;
    }
}