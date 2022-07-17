package com.nimu.tradermod.manager;

import com.nimu.tradermod.Main;
import com.nimu.tradermod.database.Database;
import com.nimu.tradermod.database.DatabaseUtils;
import com.nimu.tradermod.money.Money;
import net.minecraft.entity.player.PlayerEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MoneyManager implements IManager<MoneyManager>{

    public Database database;
   public MoneyManager(Database database){
       this.database = database;
       try {
           ResultSet resultSet = this.database.getConnection().getMetaData().getTables(null, null, "money_table", null);
           if (!resultSet.next()){
               String sql = "CREATE TABLE money_table (playerUUID, playermoney);";
               Statement statement = database.getConnection().createStatement();
               statement.execute(sql);
           }
       } catch (SQLException ex){
           Main.LOGGER.error(ex);
       }
   }

   public Money getMoneyForPlayer(PlayerEntity playerEntity) throws SQLException {
       int i = DatabaseUtils.getMoney(Main.DATABASE, playerEntity.getUuidAsString());
     if (i == -1){

         return null;
     }
       return new Money(playerEntity, i);
   }

   public void AddPlayerMoney(PlayerEntity playerEntity) throws SQLException {
       DatabaseUtils.AddUserMoney(Main.DATABASE, new Money(playerEntity, 500));
   }
}
