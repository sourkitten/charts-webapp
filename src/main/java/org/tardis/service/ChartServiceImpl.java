package org.tardis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;
import org.tardis.dao.*;
import org.tardis.data.Country;
import org.tardis.data.DataPoint;

import java.util.ArrayList;
import java.util.List;

@Service
@AutoConfiguration
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ASTC_DataDAO ASTCRepository;
    @Autowired
    private CRDF_DataDAO CRDFRepository;
    @Autowired
    private FC_DataDAO FCRepository;
    @Autowired
    private LCA_DataDAO LCARepository;
    @Autowired
    private CountryDAO countryRepository;


    public ArrayList<ArrayList<DataPoint>> getASTCDataPoints(ArrayList<char[]> ISOs) {
        ArrayList<ArrayList<DataPoint>> dataPoints = new ArrayList<ArrayList<DataPoint>>();
        for (int i = 0; i < ISOs.size(); i++) {
            dataPoints.add(ASTCRepository.findAllByISO3OrderByYearAsc(ISOs.get(i)));
        }
        return dataPoints;
    }

    public ArrayList<ArrayList<DataPoint>> getCRDFDataPoints(ArrayList<char[]> isos, List<String> indicators) {
        ArrayList<ArrayList<DataPoint>> dataPoints = new ArrayList<ArrayList<DataPoint>>();
        for (int i = 0; i < isos.size(); i++) {
            for (int j = 0; j < indicators.size(); j++) {
                dataPoints.add(CRDFRepository.findAllByISO3AndIndicatorOrderByYearAsc(isos.get(i), indicators.get(j).toCharArray()));
            }
        }
        return dataPoints;
    }

    public ArrayList<ArrayList<DataPoint>> getFCDataPoints(ArrayList<char[]> isos, List<String> indicators) {
        ArrayList<ArrayList<DataPoint>> dataPoints = new ArrayList<ArrayList<DataPoint>>();
        for (int i = 0; i < isos.size(); i++) {
            for (int j = 0; j < indicators.size(); j++) {
                dataPoints.add(FCRepository.findAllByISO3AndIndicatorOrderByYearAsc(isos.get(i), indicators.get(j).toCharArray()));
            }
        }
        return dataPoints;
    }

    public ArrayList<ArrayList<DataPoint>> getLCADataPoints(ArrayList<char[]> isos, List<String> indicators) {
        ArrayList<ArrayList<DataPoint>> dataPoints = new ArrayList<ArrayList<DataPoint>>();
        for (int i = 0; i < isos.size(); i++) {
            for (int j = 0; j < indicators.size(); j++) {
                dataPoints.add(LCARepository.findAllByISO3AndIndicatorOrderByYearAsc(isos.get(i), indicators.get(j).toCharArray()));
            }
        }
        return dataPoints;
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

}
