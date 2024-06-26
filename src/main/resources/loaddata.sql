LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/geo_data.csv'
INTO TABLE Countries
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, Display_Name, Area_SqKm, Population, Capital, Continent);

LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/currency_data.csv'
INTO TABLE Currencies
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, CurrencyCode, CurrencyName);

LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/region_data.csv'
INTO TABLE CountryRegionDetails
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, Phone, Region_Code, Region_Name, Subregion_Code, Subregion_Name, Intermediate_Region_Code, Intermediate_Region_Name);

LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/development_data.csv'
INTO TABLE CountryDevelopmentStatus
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, Status, Developed_or_Developing, @SIDS, @LLDC, @LDC)
SET
    SIDS = IF(@SIDS = 'TRUE', TRUE, FALSE),
    LLDC = IF(@LLDC = 'TRUE', TRUE, FALSE),
    LDC = IF(@LDC = 'TRUE', TRUE, FALSE);



-- Load data into ASTC_Data table
LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/ASTC_data.csv'
INTO TABLE ASTC_Data
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, Year, Value);

-- Load data into ASTC_Data table
LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/CRDF_data.csv'
INTO TABLE CRDF_Data
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, Indicator, Year, Value);

-- Load data into ASTC_Data table
LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/FC_data.csv'
INTO TABLE FC_Data
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3,  Indicator, Year, Value);

-- Load data into ASTC_Data table
LOAD DATA LOCAL INFILE '/home/evie/IdeaProjects/Adv-topics-db-tech-app/ATT-4008-4739/target/classes/data/LCA_data.csv'
INTO TABLE LCA_Data
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ISO3, Indicator, Influence, Year, Value);