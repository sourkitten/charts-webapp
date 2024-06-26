package org.tardis.service;

public interface ScriptWriter {
    void initializeDatabaseSQL();

    void loadDataSQL();
    void writeInitializeDatabase();
    void writeLoadData();

}
