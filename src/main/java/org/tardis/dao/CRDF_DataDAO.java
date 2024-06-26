package org.tardis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tardis.data.CRDFDP;
import org.tardis.data.DataPoint;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface CRDF_DataDAO extends JpaRepository<CRDFDP, char[]> {
    ArrayList<DataPoint> findAllByISO3AndIndicatorOrderByYearAsc(char[] ISO3, char[] Indicator); // returns list of currencies
}
