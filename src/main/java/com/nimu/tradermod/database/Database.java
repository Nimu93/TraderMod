package com.nimu.tradermod.database;

import com.nimu.tradermod.Main;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public Connection connection;
    public String NameDB;
    public File DBfile;

    public Database(String path){
        NameDB = "TraderMod";
        setupConnection(path);
    }
    private void setupConnection(String path){
        DBfile = new File(path + File.separator + NameDB + ".db");
        try {
            Class.forName("org.sqlite.JDBC");

            connection = DriverManager.getConnection("jdbc:sqlite:" + DBfile.getAbsolutePath());
        } catch (SQLException | ClassNotFoundException throwables) {
            Main.LOGGER.error(throwables.getMessage());
        }
    }

    public void Finish(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            Main.LOGGER.error(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }


    //connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
}
