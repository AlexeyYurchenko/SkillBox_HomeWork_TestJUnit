import core.Line;
import core.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteCalculatorTest {

    List<Station> route;
    List<Station> stationsOnSameLine;
    List<Station> routeOneTransferMetroStations;
    List<Station> routeTwoTransferMetroStations;
    StationIndex stationIndex;
    RouteCalculator routeCalculator;

    @BeforeEach
    protected void setUp() throws Exception {
        stationIndex = new StationIndex();
        route = new ArrayList<>();
        Line lineRed = new Line(1,"Кировско-Выборгская");
        Station station1r = new Station("Выборгская",lineRed);
        Station station2r = new Station("Площадь Ленина",lineRed);
        Station station3r = new Station("Чернышевская",lineRed);
        Station station4r = new Station("Площадь Восстания",lineRed);

        Line lineBlue = new Line(2,"Московско-Петроградская");
        Station station1b = new Station("Невский проспект",lineBlue);
        Station station2b = new Station("Сенная площадь",lineBlue);
        Station station3b = new Station("Технологический институт",lineBlue);
        Station station4b = new Station("Фрунзенская",lineBlue);

        Line lineGreen = new Line(3,"Невско-Василеостровская");
        Station station1g = new Station("Василеостровская",lineGreen);
        Station station2g = new Station("Гостиный двор",lineGreen);
        Station station3g = new Station("Маяковская",lineGreen);
        Station station4g = new Station("Площадь Александра Невского",lineGreen);

        lineRed.addStation(station1r);
        lineRed.addStation(station2r);
        lineRed.addStation(station3r);
        lineRed.addStation(station4r);
        lineBlue.addStation(station1b);
        lineBlue.addStation(station2b);
        lineBlue.addStation(station3b);
        lineBlue.addStation(station4b);
        lineGreen.addStation(station1g);
        lineGreen.addStation(station2g);
        lineGreen.addStation(station3g);
        lineGreen.addStation(station4g);

        //добавляем линии в метро
        stationIndex.addLine(lineRed);
        stationIndex.addLine(lineBlue);
        stationIndex.addLine(lineGreen);

        //добавляем станции в метро
        // линия Red
        stationIndex.addStation(station1r);
        stationIndex.addStation(station2r);
        stationIndex.addStation(station3r);
        stationIndex.addStation(station4r);

        // линия Blue
        stationIndex.addStation(station1b);
        stationIndex.addStation(station2b);
        stationIndex.addStation(station3b);
        stationIndex.addStation(station4b);

        // линия Green
        stationIndex.addStation(station1g);
        stationIndex.addStation(station2g);
        stationIndex.addStation(station3g);
        stationIndex.addStation(station4g);

        //добавляем пересадки в метро
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(station1b, station2g)));
        stationIndex.addConnection(new ArrayList<>(Arrays.asList(station3g, station4r)));

        routeCalculator = new RouteCalculator(stationIndex);
    }
    @Test
    @DisplayName("Тесты на метод CalculateDuration")
    void testCalculateDuration()  {
        route = new ArrayList<>();
        route.add(stationIndex.getStation("Технологический институт"));
        route.add(stationIndex.getStation("Сенная площадь"));
        route.add(stationIndex.getStation("Невский проспект"));
        route.add(stationIndex.getStation("Гостиный двор"));
        route.add(stationIndex.getStation("Маяковская"));
        double actual = RouteCalculator.calculateDuration(route);
        double expected = 11;
        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Маршрут по одной линии")
    void testGetRouteOnTheLine()  {
        stationsOnSameLine = new ArrayList<>();
        stationsOnSameLine.add(stationIndex.getStation("Выборгская"));
        stationsOnSameLine.add(stationIndex.getStation("Площадь Ленина"));
        stationsOnSameLine.add(stationIndex.getStation("Чернышевская"));
        stationsOnSameLine.add(stationIndex.getStation("Площадь Восстания"));
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Выборгская"),
                stationIndex.getStation("Площадь Восстания"));
        assertEquals(stationsOnSameLine, actual);
    }
    @Test
    @DisplayName("Маршрут c одной пересадкой")
    void testOneTransferMetroStations() {
        routeOneTransferMetroStations = new ArrayList<>();
        routeOneTransferMetroStations.add(stationIndex.getStation("Технологический институт"));
        routeOneTransferMetroStations.add(stationIndex.getStation("Сенная площадь"));
        routeOneTransferMetroStations.add(stationIndex.getStation("Невский проспект"));
        routeOneTransferMetroStations.add(stationIndex.getStation("Гостиный двор"));
        routeOneTransferMetroStations.add(stationIndex.getStation("Маяковская"));
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Технологический институт"),
                stationIndex.getStation("Маяковская"));
        assertEquals(routeOneTransferMetroStations, actual);
    }
    @Test
    @DisplayName("Маршрут c двумя пересадками")
    void testTwoTransferMetroStations() {
        routeTwoTransferMetroStations = new ArrayList<>();
        routeTwoTransferMetroStations.add(stationIndex.getStation("Технологический институт"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Сенная площадь"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Невский проспект"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Гостиный двор"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Маяковская"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Площадь Восстания"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Чернышевская"));
        routeTwoTransferMetroStations.add(stationIndex.getStation("Площадь Ленина"));
        List<Station> actual = routeCalculator.getShortestRoute(stationIndex.getStation("Технологический институт"),
                stationIndex.getStation("Площадь Ленина"));
        assertEquals(routeTwoTransferMetroStations, actual);
    }


}
