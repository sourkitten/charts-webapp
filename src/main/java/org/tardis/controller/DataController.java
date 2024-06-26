package org.tardis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tardis.service.ScriptWriter;
import org.tardis.service.ScriptWriterImpl;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@AutoConfiguration
public class DataController {
    private static final String BASE_PATH = "./classes/data";
    private static final String[] files = {
        "Forest_and_Carbon.csv",
        "Land_Cover_Accounts.csv",
        "countries.csv",
        "Climate-related_Disasters_Frequency.csv",
        "Annual_Surface_Temperature_Change.csv"
    };

    @Autowired
    private ScriptWriter scriptWriter;

    @GetMapping("/data")
    public String listFiles(Model model) {
        model.addAttribute("fileStatuses", checkFilesExistence());        return "data";
    }

    private List<FileStatus> checkFilesExistence() {
        List<FileStatus> statuses = new ArrayList<>();
        for (String file : files) {
            Path path = Paths.get(BASE_PATH, file);
            statuses.add(new FileStatus(file, Files.exists(path)));
        }
        return statuses;
    }

    @PostMapping("/data/{filename}")
    @ResponseBody // Indicates that the return type should be written straight to the HTTP response body
    public ResponseEntity<String> uploadFile(@PathVariable String filename,
                                             @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                Path targetPath = Paths.get(BASE_PATH).resolve(filename).normalize();
                System.out.println(targetPath.toString());
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                return ResponseEntity.ok("File '" + filename + "' uploaded successfully!");
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to upload file '" + filename + "': " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Please select a file to upload.");
        }

    }

    @PostMapping("/data/init-data")
    public String initData() {
        scriptWriter.writeInitializeDatabase();
        scriptWriter.initializeDatabaseSQL();
        return "Successfully initialized database!";
    }

    @PostMapping("/data/load-data")
    public String loadData() {
        scriptWriter.writeLoadData();
        scriptWriter.loadDataSQL();
        return "Successfully loaded data to database!";
    }

    public static class FileStatus {
        private String name;
        private boolean exists;

        public FileStatus(String name, boolean exists) {
            this.name = name;
            this.exists = exists;
        }

        public String getName() {
            return name;
        }

        public boolean isExists() {
            return exists;
        }
    }

}

