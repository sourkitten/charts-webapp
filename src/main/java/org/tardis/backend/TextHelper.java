package org.tardis.backend;

import java.util.ArrayList;
import java.util.List;

public class TextHelper {

    // Custom CSV line parser to handle quoted values containing commas
    public static String[] parseCSVLine(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder currentColumn = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                columns.add(currentColumn.toString());
                currentColumn.setLength(0);
            } else {
                currentColumn.append(c);
            }
        }

        columns.add(currentColumn.toString());
        return columns.toArray(new String[0]);
    }

    public static String extractIndicatorValue(String indicator) {
        int colonIndex = indicator.lastIndexOf(':');
        if (colonIndex != -1 && colonIndex + 1 < indicator.length()) {
            return indicator.substring(colonIndex + 1).trim();
        }
        return indicator; // Return the whole string if no colon is found
    }

    public static String quoteIfNeeded(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\""); // Escape quotes by doubling them
            return "\"" + value + "\"";
        }
        return value;
    }
}
