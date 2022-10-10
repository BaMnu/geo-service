package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.*;
import static ru.netology.entity.Country.BRAZIL;

class LocalizationServiceImplTest {

    @Test void locate_Test_ItShouldSendTextAccordingToLocation() {
        //given
        LocalizationServiceImpl localUnderTest = new LocalizationServiceImpl();
        String expectedResultRus = "Добро пожаловать";
        String expectedResultEng = "Welcome";

        //when
        String resultRus = localUnderTest.locale(RUSSIA);
        String resultUSA = localUnderTest.locale(USA);
        String resultGer = localUnderTest.locale(GERMANY);
        String resultBra = localUnderTest.locale(BRAZIL);

        //then
        Assertions.assertEquals(expectedResultRus, resultRus);
        Assertions.assertEquals(expectedResultEng, resultUSA);
        Assertions.assertEquals(expectedResultEng, resultGer);
        Assertions.assertEquals(expectedResultEng, resultBra);
    }
}