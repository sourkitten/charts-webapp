package org.tardis.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tardis.data.DataPoint;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataBean {

    @Bean
    public List<List<DataPoint>> list(){
        return new ArrayList<List<DataPoint>>();
    }
}
