import com.skillbox.airport.Airport;
import com.skillbox.airport.Flight;
import com.skillbox.airport.Terminal;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SimpleDateFormat format = new SimpleDateFormat("|dd.MM.yyyy|HH:mm|");
        LocalDateTime dateNow = LocalDateTime.now();

        Airport airport = Airport.getInstance();
        List<Terminal> terminals = airport.getTerminals();

        terminals.stream()
                .map(Terminal::getFlights)
                .flatMap(List::stream)
                .filter(f -> f.getType().equals(Flight.Type.DEPARTURE) &&
                        converterLocalDateTime(f).isAfter(dateNow) &&
                        converterLocalDateTime(f).isBefore(dateNow.plusHours(2)))
                .forEach(f -> System.out.println("Время вылета:\n" +
                        "--------------------------\n" +
                        format.format(f.getDate()) +
                        "\n" +
                        "--------------------------\n" +
                        "Модель самолета:\n" +
                        "--------------------------\n" +
                        f.getAircraft().getModel() +
                        "\n" +
                        "--------------------------\n"));
    }

    private static LocalDateTime converterLocalDateTime(Flight flight)
    {
        return LocalDateTime.ofInstant(flight.getDate().toInstant(), ZoneId.systemDefault());
    }
}
