package org.tardis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tardis.data.DataPoint;
import org.tardis.data.LCADP;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface LCA_DataDAO extends JpaRepository<LCADP, char[]> {
    ArrayList<DataPoint> findAllByISO3AndIndicatorOrderByYearAsc(char[] ISO3, char[] Indicator); // returns list of currencies
}
