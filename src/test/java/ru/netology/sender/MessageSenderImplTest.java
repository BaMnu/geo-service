package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

class MessageSenderImplTest {

    @Test
    void send_Test_ItShouldSendMessageOnLatin() {
        //given
        final String ipUSA = "96.23.155.132";
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Map<String, String> headersUSA = new HashMap<String, String>();

        //when
        Mockito.when(geoService.byIp(ipUSA))
                .thenReturn(new Location("New York", Country.USA, "null",  0));
        Country country = geoService.byIp(ipUSA).getCountry();

        Mockito.when(localizationService.locale(country))
                .thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        headersUSA.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipUSA);

        //then
        Assertions.assertEquals("Welcome", messageSender.send(headersUSA));
    }

    @Test
    void send_Test_ItShouldSendMessageOnCyrillic() {
        //given
        final String ipRus = "172.1.33.97";
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Map<String, String> headersRus = new HashMap<String, String>();

        //when
        Mockito.when(geoService.byIp(ipRus))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "null", 0));
        Country country = geoService.byIp(ipRus).getCountry();

        Mockito.when(localizationService.locale(country)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        headersRus.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipRus);

        //then
        Assertions.assertEquals("Добро пожаловать", messageSender.send(headersRus));
    }


}