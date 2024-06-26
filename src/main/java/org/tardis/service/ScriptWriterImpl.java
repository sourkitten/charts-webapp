package org.tardis.service;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.MessageFormat;

@Service
@AutoConfiguration
public class ScriptWriterImpl implements ScriptWriter {

    @Autowired
    static JdbcTemplate jdbcTemplate;

    static String dataDir = "/classes/"; // from target dir
    static String dataDir2 = "./classes/"; // from target dir


    public void initializeDatabaseSQL() {
        ScriptWriterImpl.jdbcTemplate.execute("SOURCE " + ScriptWriterImpl.dataDir2 + "initializeDatabase.sql");
    }

    public void loadDataSQL() {
        ScriptWriterImpl.jdbcTemplate.execute("SOURCE " + ScriptWriterImpl.dataDir2 + "loaddata.sql");
    }

    public void writeInitializeDatabase() {
        String outputFile = dataDir2 + "initializedatabase.sql";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(generateInitData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLoadData() {
        String outputFile = dataDir2 + "loaddata.sql";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(generateLoadData());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull String generateLoadData() {
        String cwd = System.getProperty("user.dir");
        return String.format(
                "LOAD DATA LOCAL INFILE '%sdata/geo_data.csv'\n" +
                "INTO TABLE Countries\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Display_Name, Area_SqKm, Population, Capital, Continent);\n" +
                "\n" +
                "LOAD DATA LOCAL INFILE '%sdata/currency_data.csv'\n" +
                "INTO TABLE Currencies\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, CurrencyCode, CurrencyName);\n" +
                "\n" +
                "LOAD DATA LOCAL INFILE '%sdata/region_data.csv'\n" +
                "INTO TABLE CountryRegionDetails\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Phone, Region_Code, Region_Name, Subregion_Code, Subregion_Name, Intermediate_Region_Code, Intermediate_Region_Name);\n" +
                "\n" +
                "LOAD DATA LOCAL INFILE '%sdata/development_data.csv'\n" +
                "INTO TABLE CountryDevelopmentStatus\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Status, Developed_or_Developing, @SIDS, @LLDC, @LDC)\n" +
                "SET\n" +
                "    SIDS = IF(@SIDS = 'TRUE', TRUE, FALSE),\n" +
                "    LLDC = IF(@LLDC = 'TRUE', TRUE, FALSE),\n" +
                "    LDC = IF(@LDC = 'TRUE', TRUE, FALSE);\n" +
                "\n" +
                "-- Load data into ASTC_Data table\n" +
                "LOAD DATA LOCAL INFILE '%sdata/ASTC_data.csv'\n" +
                "INTO TABLE ASTC_Data\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Year, Value);\n" +
                "\n" +
                "-- Load data into CRDF_Data table\n" +
                "LOAD DATA LOCAL INFILE '%sdata/CRDF_data.csv'\n" +
                "INTO TABLE CRDF_Data\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Indicator, Year, Value);\n" +
                "\n" +
                "-- Load data into FC_Data table\n" +
                "LOAD DATA LOCAL INFILE '%sdata/FC_data.csv'\n" +
                "INTO TABLE FC_Data\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Indicator, Year, Value);\n" +
                "\n" +
                "-- Load data into LCA_Data table\n" +
                "LOAD DATA LOCAL INFILE '%sdata/LCA_data.csv'\n" +
                "INTO TABLE LCA_Data\n" +
                "FIELDS TERMINATED BY ','\n" +
                "ENCLOSED BY '\"'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "IGNORE 1 ROWS\n" +
                "(ISO3, Indicator, Influence, Year, Value);\n",
        cwd + dataDir, cwd + dataDir, cwd + dataDir, cwd + dataDir, cwd + dataDir, cwd + dataDir, cwd + dataDir, cwd + dataDir
        );
    }

    @Contract(pure = true)
    private static @NotNull String generateInitData() {
        return """
                -- Drop database if it exists
                  DROP DATABASE IF EXISTS CountryDatabase;
                  
                  -- Create the database
                  CREATE DATABASE IF NOT EXISTS CountryDatabase;
                  
                  -- Use the newly created database
                  USE CountryDatabase;
                  
                  DROP TABLE IF EXISTS Countries;
                  
                  -- Create Countries table
                  CREATE TABLE IF NOT EXISTS Countries (
                      ISO3 CHAR(15) PRIMARY KEY,
                      Display_Name VARCHAR(255),
                      Area_SqKm DECIMAL(18, 2),
                      Population INT,
                      Capital VARCHAR(255),
                      Continent VARCHAR(255)
                  );
                  
                  DROP TABLE IF EXISTS Currencies;
                  
                  -- Create Currencies table
                  CREATE TABLE IF NOT EXISTS Currencies (
                      ISO3 CHAR(15),
                      CurrencyCode VARCHAR(3),
                      CurrencyName VARCHAR(255),
                      PRIMARY KEY(ISO3),
                      FOREIGN KEY(ISO3) REFERENCES Countries(ISO3)
                  );
                  
                  DROP TABLE IF EXISTS CountryRegionDetails;
                  
                  -- Create CountryDetails table
                  CREATE TABLE IF NOT EXISTS CountryRegionDetails (
                      ISO3 CHAR(15) PRIMARY KEY,
                      Phone VARCHAR(20),
                      Region_Code VARCHAR(10),
                      Region_Name VARCHAR(255),
                      Subregion_Code VARCHAR(10),
                      Subregion_Name VARCHAR(255),
                      Intermediate_Region_Code VARCHAR(10),
                      Intermediate_Region_Name VARCHAR(255),
                      FOREIGN KEY(ISO3) REFERENCES Countries(ISO3)
                  );
                  
                  DROP TABLE IF EXISTS CountryDevelopmentStatus;
                  
                  -- Create CountryDevelopmentStatus table
                  CREATE TABLE IF NOT EXISTS CountryDevelopmentStatus (
                      ISO3 CHAR(15) PRIMARY KEY,
                      Status VARCHAR(50),
                      Developed_or_Developing VARCHAR(20),
                      SIDS BOOLEAN not null default FALSE,
                      LLDC BOOLEAN not null default FALSE,
                      LDC BOOLEAN not null default FALSE,
                      FOREIGN KEY(ISO3) REFERENCES Countries(ISO3)
                  );
                  
                  DROP TABLE IF EXISTS ASTC_Data;
                  
                  -- Create CountryData table
                  CREATE TABLE IF NOT EXISTS ASTC_Data (
                      ISO3 CHAR(15),
                      Year INT,
                      Value DOUBLE,
                      PRIMARY KEY (ISO3, Year),
                      FOREIGN KEY(ISO3) REFERENCES Countries(ISO3)
                  );
                  
                  DROP TABLE IF EXISTS CRDF_Data;
                  
                  -- Create CountryData table
                  CREATE TABLE IF NOT EXISTS CRDF_Data (
                      ISO3 CHAR(15),
                      Indicator CHAR(20),
                      Year INT,
                      Value INT,
                      PRIMARY KEY (ISO3, Indicator, Year)
                  );
                  
                  DROP TABLE IF EXISTS FC_Data;
                  
                  -- Create CountryData table
                  CREATE TABLE IF NOT EXISTS FC_Data (
                      ISO3 CHAR(15),
                      Indicator CHAR(35),
                      Year INT,
                      Value DOUBLE,
                      PRIMARY KEY (ISO3, Indicator, Year)
                  );
                  
                  DROP TABLE IF EXISTS LCA_Data;
                  
                  -- Create CountryData table
                  CREATE TABLE IF NOT EXISTS LCA_Data (
                      ISO3 CHAR(15),
                      Indicator CHAR(70),
                      Influence CHAR(20),
                      Year INT,
                      Value DOUBLE,
                      PRIMARY KEY (ISO3, Indicator, Influence, Year)
                  );
                """;
    }

}
