package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.RUSSIA;
import static ru.netology.entity.Country.USA;

class GeoServiceImplTest {

    @Test void byIp_Test_ItShouldFindLocationByIp() {
        //given
        GeoServiceImpl geoUnderTest = new GeoServiceImpl();
        final String ipRus = "172.1.33.97";
        final String ipUSA = "96.23.155.132";

        //when
        Country resultRus = geoUnderTest.byIp(ipRus).getCountry();
        Country resultUSA = geoUnderTest.byIp(ipUSA).getCountry();

        //then
        assertEquals(RUSSIA, resultRus);
        assertEquals(USA, resultUSA);
    }
}