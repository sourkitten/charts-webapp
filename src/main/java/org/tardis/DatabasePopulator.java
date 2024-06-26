package org.tardis;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.tardis.backend.CSVManager;
import org.tardis.service.ScriptWriterImpl;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DatabasePopulator {

    private static final ScriptWriterImpl sw = new ScriptWriterImpl();

    public static void main(String[] args) throws SQLException, IOException {
        CSVManager.main(); // initialize files that will be loaded
        // initialize script files
        sw.writeInitializeDatabase();
        sw.writeLoadData();
        // run script files
        ScriptRunner sr = getScriptRunner();
        executeSQLScript(sr, "initializedatabase.sql");
        System.out.println("Initialized database schema!");
        executeSQLScript(sr, "loaddata.sql");
        System.out.println("Loaded data!");
    }

    private static ScriptRunner getScriptRunner() throws SQLException, IOException {
        //Registering the Driver
        DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
        //Getting the connection
        String mysqlUrl = "jdbc:mariadb://localhost:3306/";
        Scanner sc = new Scanner(System.in);
        System.out.print("Mariadb user: ");
        String user = sc.nextLine();
        System.out.print("Password for the " + user + " user of database: ");
        String password = sc.nextLine();
        sc.close();
        Connection con = DriverManager.getConnection(mysqlUrl, user, password);
        System.out.println("Connection established......");
        //Initialize the script runner
        return new ScriptRunner(con);
    }

    private static void executeSQLScript(ScriptRunner sr, String file) throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader("./classes/" + file));
        //Running the script
        sr.runScript(reader);
    }
}
