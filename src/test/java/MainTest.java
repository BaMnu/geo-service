import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.*;
import ru.netology.i18n.*;
import ru.netology.sender.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.entity.Country.*;

class MainTest {

    private final String ipRus = "172.1.33.97";
    private final String ipUSA = "96.23.155.132";

    @Test void byIp_Test_ItShouldFindLocationByIp() {
        //given
        GeoServiceImpl geoUnderTest = new GeoServiceImpl();
        String ipLocalhost = "127.1.2.1";

        //when
        Country resultRus = geoUnderTest.byIp(ipRus).getCountry();
        Country resultUSA = geoUnderTest.byIp(ipUSA).getCountry();
        Country resultLocalhost = geoUnderTest.byIp(ipLocalhost).getCountry();

        //then
        Assertions.assertEquals(RUSSIA, resultRus);
        Assertions.assertEquals(USA, resultUSA);
        assertNull(resultLocalhost);
    }

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

    @Test void send_Test_ItShouldSendMessageOnLatin() {
        //given
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        //when
        Mockito.when(geoService.byIp(ipUSA))
                .thenReturn(new Location("New York", Country.USA, "null",  0));
        Country country = geoService.byIp(ipUSA).getCountry();

        Mockito.when(localizationService.locale(country)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImplMock(geoService, localizationService);

        Map<String, String> headersUSA = new HashMap<String, String>();
        headersUSA.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipUSA);

        //then
        Assertions.assertEquals("Welcome", messageSender.send(headersUSA));
        Assertions.assertNotEquals("Добро пожаловать", messageSender.send(headersUSA));
    }

    @Test void send_Test_ItShouldSendMessageOnCyrillic() {
        //given
        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);

        //when
        Mockito.when(geoService.byIp(ipRus)).thenReturn(new Location("Moscow", RUSSIA, "null", 0));
        Country country = geoService.byIp(ipRus).getCountry();

        Mockito.when(localizationService.locale(country)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImplMock(geoService, localizationService);

        Map<String, String> headersRus = new HashMap<String, String>();
        headersRus.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipRus);

        //then
        Assertions.assertEquals("Добро пожаловать", messageSender.send(headersRus));
        Assertions.assertNotEquals("Welcome", messageSender.send(headersRus));
    }
}