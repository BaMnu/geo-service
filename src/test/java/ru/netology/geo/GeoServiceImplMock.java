package ru.netology.geo;

import ru.netology.entity.Country;
import ru.netology.entity.Location;

public class GeoServiceImplMock implements GeoService {

    public Location byIp(String ip) {
        Location location = new Location("New York", Country.USA, " 10th Avenue", 32);
        return location;
    }

    @Override
    public Location byCoordinates(double latitude, double longitude) {
        return null;
    }

}