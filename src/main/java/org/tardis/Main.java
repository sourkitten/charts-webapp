package org.tardis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.tardis.backend.CSVManager;
import org.tardis.service.ScriptWriter;
import org.tardis.service.ScriptWriterImpl;


@ComponentScan("org.tardis")
@SpringBootApplication
public class Main {
    private static ScriptWriter sw = new ScriptWriterImpl();
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}