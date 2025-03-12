package org.nano.nstitleskills.data.register;

import org.nano.nstitleskills.data.db.DBFile;

public class FileRegistry {
    public static FileRegistry instance;
    public static FileRegistry getInstance(){
        if (instance == null) {
            instance = new FileRegistry();
        }
        return instance;
    }
    private final DBFile dbFile;

    public FileRegistry() {
        dbFile = new DBFile();
        dbFile.loadAll();
    }

    public DBFile getDbFile() {
        return dbFile;
    }
}
