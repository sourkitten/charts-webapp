package org.tardis.backend;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class CSVManager {

    private static String dataDir = "./classes/data/"; // from target dir

    public static void copyColumns(String inputFile, String outputFile, String[] columnsToCopy) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String headerLine = reader.readLine();
            if (headerLine == null) {
                throw new IOException("Input file is empty.");
            }
            String[] headers = TextHelper.parseCSVLine(headerLine); // Parsing header line

            Map<String, Integer> headerMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                headerMap.put(headers[i], i);
            }

            StringBuilder newHeaderLine = new StringBuilder();
            for (String column : columnsToCopy) {
                if (headerMap.containsKey(column)) {
                    newHeaderLine.append(column).append(",");
                } else {
                    throw new IllegalArgumentException("Column '" + column + "' not found in the input CSV file.");
                }
            }
            // Remove the last comma
            if (!newHeaderLine.isEmpty()) {
                newHeaderLine.deleteCharAt(newHeaderLine.length() - 1);
            }

            writer.write(newHeaderLine.toString());
            writer.newLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = TextHelper.parseCSVLine(line);

                StringBuilder newLine = new StringBuilder();
                for (String column : columnsToCopy) {
                    int index = headerMap.get(column);
                    String value = columns[index];
                    if (value.contains(",") || value.contains("\"")) {
                        value = "\"" + value.replace("\"", "\"\"") + "\"";
                    }
                    newLine.append(value).append(",");
                }
                // Remove the last comma
                if (!newLine.isEmpty()) {
                    newLine.deleteCharAt(newLine.length() - 1);
                }

                writer.write(newLine.toString());
                writer.newLine();
            }
        }
    }





    public static void processCRDF(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            // Write header line
            writer.write("ISO3,Indicator,Year,Value\n");

            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line (header)
                }

                // Split the line manually
                String[] columns = TextHelper.parseCSVLine(line);

                // Extract ISO3 and Indicator
                String iso3 = columns[0].trim(); // Assuming ISO3 is at index 0
                String indicator = columns[1].trim(); // Assuming Indicator is at index 1
                String indicatorValue = TextHelper.extractIndicatorValue(indicator); // Extract value after the colon

                // Process F columns
                for (int i = 2; i < columns.length; i++) {
                    String year ="" + (1980 + i - 2); // Extracting year from column index
                    String value = columns[i].trim();

                    if (!value.isEmpty()) {
                        writer.write(iso3 + "," + indicatorValue + "," + year + "," + value + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processCSV(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            // Write header line
            writer.write("ISO3,Year,Value\n");

            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line (header)
                }

                String[] columns = line.split(",");

                // Extract ISO3
                String iso3 = columns[0].trim(); // Assuming ISO3 is at index 0

                // Process F columns
                for (int i = 1; i < columns.length; i++) {
                    String year = Integer.toString(1961 + i -1); // Extracting year from column index
                    String value = columns[i].trim();

                    if (!value.isEmpty()) {
                        writer.write(iso3 + "," + year + "," + value + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processFC(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            // Write header line
            writer.write("ISO3,Indicator,Year,Value\n");

            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line (header)
                }

                // Split the line manually
                String[] columns = TextHelper.parseCSVLine(line);

                // Extract ISO3 and Indicator
                String iso3 = columns[0].trim(); // Assuming ISO3 is at index 0
                String indicator = columns[1].trim(); // Assuming Indicator is at index 1

                // Process F columns
                for (int i = 2; i < columns.length; i++) {
                    String year = "" + (1992 + i - 2); // Extracting year from column index
                    String value = columns[i].trim();

                    if (!value.isEmpty()) {
                        writer.write(iso3 + "," + indicator + "," + year + "," + value + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void processLCA(String inputFile, String outputFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            // Write header line
            writer.write("ISO3,Indicator,Year,Value\n");

            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip the first line (header)
                }

                // Split the line manually
                String[] columns = TextHelper.parseCSVLine(line);

                // Extract ISO3 and Indicator
                String iso3 = columns[0].trim(); // Assuming ISO3 is at index 0
                String indicator = columns[1].trim(); // Assuming Indicator is at index 1
                String altering = columns[2].trim(); // Assuming altering is at index 2

                // Process F columns
                for (int i = 3; i < columns.length; i++) {
                    String year = "" + (1992 + i - 2); // Extracting year from column index
                    String value = columns[i].trim();

                    if (!value.isEmpty()) {
                        writer.write(iso3 + "," + TextHelper.quoteIfNeeded(indicator) + "," + altering + "," + year + "," + value + "\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main() {
        String inputFile = dataDir + "countries.csv"; // Path to input CSV file

        String[] outputFile = {dataDir + "/geo_data.csv", dataDir + "/currency_data.csv", dataDir + "/development_data.csv", dataDir + "/region_data.csv"}; // Paths to output CSV files
        String[][] columnsToCopy = {{"ISO3", "Display_Name", "Area_SqKm", "Population", "Capital", "Continent"}, {"ISO3", "CurrencyCode", "CurrencyName"}, {"ISO3", "Status","Developed or Developing","Small Island Developing States (SIDS)","Land Locked Developing Countries (LLDC)","Least Developed Countries (LDC)"}, {"ISO3", "Phone","Region Code","Region Name","Sub-region Code","Sub-region Name","Intermediate Region Code","Intermediate Region Name"}}; // Indices of columns to copy (0-based)
        for(int i = 0; i < outputFile.length; i++)
        {
            try {
                copyColumns(inputFile, outputFile[i], columnsToCopy[i]);
                System.out.println("Columns copied successfully!");
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        try {
            copyColumns(dataDir + "/Annual_Surface_Temperature_Change.csv", dataDir + "/ASTC_idoc.csv", new String[] {"ISO3","F1961","F1962","F1963","F1964","F1965","F1966","F1967","F1968","F1969","F1970","F1971","F1972","F1973","F1974","F1975","F1976","F1977","F1978","F1979","F1980","F1981","F1982","F1983","F1984","F1985","F1986","F1987","F1988","F1989","F1990","F1991","F1992","F1993","F1994","F1995","F1996","F1997","F1998","F1999","F2000","F2001","F2002","F2003","F2004","F2005","F2006","F2007","F2008","F2009","F2010","F2011","F2012","F2013","F2014","F2015","F2016","F2017","F2018","F2019","F2020","F2021","F2022"});
            System.out.println("Columns copied successfully!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        processCSV(dataDir + "/ASTC_idoc.csv", dataDir + "/ASTC_data.csv");

        try {
            copyColumns(dataDir + "/Climate-related_Disasters_Frequency.csv", dataDir + "/CRDF_idoc.csv", new String[] {"ISO3","Indicator","F1980","F1981","F1982","F1983","F1984","F1985","F1986","F1987","F1988","F1989","F1990","F1991","F1992","F1993","F1994","F1995","F1996","F1997","F1998","F1999","F2000","F2001","F2002","F2003","F2004","F2005","F2006","F2007","F2008","F2009","F2010","F2011","F2012","F2013","F2014","F2015","F2016","F2017","F2018","F2019","F2020","F2021","F2022"});
            System.out.println("Columns copied successfully!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        processCRDF(dataDir + "/CRDF_idoc.csv", dataDir + "/CRDF_data.csv");

        try {
            copyColumns(dataDir + "/Land_Cover_Accounts.csv", dataDir + "/LCA_idoc.csv", new String[] {"ISO3","Indicator","Climate_Influence","F1992","F1993","F1994","F1995","F1996","F1997","F1998","F1999","F2000","F2001","F2002","F2003","F2004","F2005","F2006","F2007","F2008","F2009","F2010","F2011","F2012","F2013","F2014","F2015","F2016","F2017","F2018","F2019","F2020"});
            System.out.println("Columns copied successfully!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        processLCA(dataDir + "/LCA_idoc.csv", dataDir + "/LCA_data.csv");

        try {
            copyColumns(dataDir + "/Forest_and_Carbon.csv", dataDir + "/FC_idoc.csv", new String[] {"ISO3","Indicator","F1992","F1993","F1994","F1995","F1996","F1997","F1998","F1999","F2000","F2001","F2002","F2003","F2004","F2005","F2006","F2007","F2008","F2009","F2010","F2011","F2012","F2013","F2014","F2015","F2016","F2017","F2018","F2019","F2020"});
            System.out.println("Columns copied successfully!");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        processFC(dataDir + "/FC_idoc.csv", dataDir + "/FC_data.csv");

    }

}