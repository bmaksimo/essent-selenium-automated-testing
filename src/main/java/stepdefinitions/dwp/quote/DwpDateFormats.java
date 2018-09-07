package stepdefinitions.dwp.quote;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public enum DwpDateFormats {
    TIMESTAMP("yyMMddHHmmssSSS"),
    DWP_TODAY("dd/MM/yyyy");

    private DateTimeFormatter formatter;

    DwpDateFormats(String format) {
        this.formatter  = DateTimeFormat.forPattern(format);
    }

    public String print() {
        return this.print(new DateTime());
    }

    public String print(final DateTime dateTime) {
        return formatter.print(dateTime);
    }
}
