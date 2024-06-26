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
