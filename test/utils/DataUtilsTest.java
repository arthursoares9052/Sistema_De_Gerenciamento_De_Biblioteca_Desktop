package utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static org.junit.Assert.*;

public class DataUtilsTest {

    private DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                             .withResolverStyle(ResolverStyle.STRICT);

    @Test
    public void testParseDataValida() {
        String dataStr = "23/12/2023";
        LocalDate data = LocalDate.parse(dataStr, formatter);

        assertEquals(23, data.getDayOfMonth());
        assertEquals(12, data.getMonthValue());
        assertEquals(2023, data.getYear());
    }

    @Test(expected = DateTimeParseException.class)
    public void testParseDataInvalida() {
        String dataStr = "31/02/2023";
        LocalDate.parse(dataStr, formatter);
    }
}
