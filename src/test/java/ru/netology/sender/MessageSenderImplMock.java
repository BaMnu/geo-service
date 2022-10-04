package ru.netology.sender;

import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;

public class MessageSenderImplMock implements MessageSender {

    private final GeoService geoService;
    private final LocalizationService localizationService;

    public MessageSenderImplMock(GeoService geoService, LocalizationService localizationService) {
        this.geoService = geoService;
        this.localizationService = localizationService;
    }

    public String send(Map<String, String> headers) {
        String ipAddress = String.valueOf(headers.get(MessageSenderImpl.IP_ADDRESS_HEADER));
        if (ipAddress != null && !ipAddress.isEmpty()) {
            Location location = geoService.byIp(ipAddress);
            return localizationService.locale(location.getCountry());
        }
        return localizationService.locale(Country.USA);
    }
}