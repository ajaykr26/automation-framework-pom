package automation.library.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class DateHelper {
    private DateHelper() {
    }

    public static LocalDateTime formateAsDate(Object dateToFormat) {
        DateTimeFormatter formatter;
        Map<String, String> dateFormat = getDateTimeFormat(dateToFormat);
        if (dateFormat.containsKey("DATE")) {
            formatter = DateTimeFormatter.ofPattern(dateFormat.get("DATE")).withLocale(Locale.ENGLISH);
            return LocalDate.parse(dateFormat.toString(), formatter).atStartOfDay();
        } else {
            formatter = DateTimeFormatter.ofPattern(dateFormat.get("DATE-TIME")).withLocale(Locale.ENGLISH);
            return LocalDateTime.parse(dateFormat.toString(), formatter);
        }
    }

    public static Map<String, String> getDateTimeFormat(Object dateToFormat) {
        Map<String, String> dateFormatMap = getDateFormatMap();
        Map<String, String> dateTimeFormatMap = getDateTimeFormatMap();

        List<String> matchingFormate = dateFormatMap.entrySet().stream()
                .filter(entry -> dateToFormat.toString().matches(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        if (!matchingFormate.isEmpty()) {
            return Collections.singletonMap("DATE", matchingFormate.get(0));
        } else {
            matchingFormate = dateTimeFormatMap.entrySet().stream()
                    .filter(entry -> dateToFormat.toString().matches(entry.getKey()))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            if (!matchingFormate.isEmpty()) {
                return Collections.singletonMap("DATE-TIME", matchingFormate.get(0));
            } else {
                throw new IllegalArgumentException(String.format("Unknown date format: '%s", dateToFormat));
            }
        }
    }

    private static Map<String, String> getDateFormatMap() {
        Map<String, String> dateFormatMap = new HashMap<>();
        dateFormatMap.put("^\\d{2} ([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9][3][0-1])$", "yyMMdd");
        dateFormatMap.put("^\\([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9][3][0-1])\\d{2}$", "MMddyy");

        return dateFormatMap;
    }

    public static Map<String, String> getDateTimeFormatMap() {
        Map<String, String> dateTimeFormatMap = new HashMap<>();

        return dateTimeFormatMap;
    }

}
