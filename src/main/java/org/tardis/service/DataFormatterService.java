package org.tardis.service;

import org.tardis.data.DataPoint;

import java.util.List;

public class DataFormatterService {
    public static String formatDataSeries(List<DataPoint> dataPoints) {
        String data = "[\n";

        for (DataPoint dataPoint : dataPoints) {
            data += dataPoint.toString() + "\n";
        }

        data += "]\n";
        return data;
    }
}
