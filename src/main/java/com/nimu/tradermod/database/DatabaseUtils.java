package com.nimu.tradermod.database;

import com.nimu.tradermod.money.Money;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    public static void AddUserMoney(Database database, Money playermoney) throws SQLException {
        if (verifData(database, playermoney)) return;
        String sql = "INSERT INTO money_table (playerUUID, playermoney) VALUES ('" + playermoney.getPlayer().getUuidAsString() +"', " + playermoney.getAmount() + ");";
        Statement statement = database.getConnection().createStatement();
        statement.execute(sql);
    }
    public static boolean verifData(Database database, Money playermoney) throws SQLException {
        String sql = "SELECT * FROM money_table WHERE playerUUID like '" + playermoney.getPlayer().getUuidAsString() +"';" ;
        Statement statement = database.getConnection().createStatement();
        statement.execute(sql);
        return statement.getResultSet().next();
    }
    public static void UpdateMoney(Database database, Money playermoney) throws SQLException{
        if (!verifData(database, playermoney)) return;
        String sql =  "UPDATE money_table SET playermoney = " + playermoney.getAmount() + " WHERE playerUUID = '" + playermoney.getPlayer().getUuidAsString()+ "';";
        Statement statement = database.getConnection().createStatement();
        statement.execute(sql);
    }
    public static int getMoney(Database database, String playerUUID) throws SQLException {
        String sql =  "SELECT playermoney FROM money_table WHERE playerUUID like '" + playerUUID +"'" ;
        Statement statement = database.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sql);
        Object o = rs.getObject(1);
        if (o instanceof Integer) {
            return (int) o;
        }
        return -1;
    }
}
