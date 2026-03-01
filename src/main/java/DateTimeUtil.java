import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.time.temporal.ChronoField;

public class DateTimeUtil {
    // Case-insensitive formatter (accepts "pm", "PM", etc.)
    private static final DateTimeFormatter DATE_TIME_12H = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("yyyy-MM-dd ha")   // 2026-03-05 4pm
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter(Locale.ENGLISH);

    private static final DateTimeFormatter DATE_TIME_12H_MIN = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("yyyy-MM-dd h:mma") // e.g. 2026-03-05 4:30pm
            .toFormatter(Locale.ENGLISH);

    private static final DateTimeFormatter DATE_TIME_24H = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public static LocalDateTime parseDateTime(String s) throws MerciException {

        String text = s.trim();
        try {
            return LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception ignored) {}
        // Try multiple formats (no space-stripping)
        try { return LocalDateTime.parse(text, DATE_TIME_12H); } catch (Exception ignored) {}
        try { return LocalDateTime.parse(text, DATE_TIME_12H_MIN); } catch (Exception ignored) {}
        try { return LocalDateTime.parse(text, DATE_TIME_24H); } catch (Exception ignored) {}

        // Date-only fallback: treat it as end of day
        try {
            LocalDate d = LocalDate.parse(text);
            return d.atTime(23, 59);
        } catch (Exception ignored) {}

        throw new MerciException(
                "Invalid date/time format. Use:\n" +
                        "  yyyy-MM-dd (e.g. 2026-03-05)\n" +
                        "  yyyy-MM-dd 4pm (e.g. 2026-03-05 4pm)\n" +
                        "  yyyy-MM-dd 4:30pm\n" +
                        "  yyyy-MM-dd 16:00"
        );
    }

    public static String formatDateTime(LocalDateTime dt) {
        DateTimeFormatter out = DateTimeFormatter.ofPattern("MMM dd yyyy h:mma", Locale.ENGLISH);
        return dt.format(out);
    }
}