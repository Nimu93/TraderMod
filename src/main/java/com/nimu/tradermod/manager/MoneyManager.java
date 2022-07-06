package com.nimu.tradermod.manager;

import com.nimu.tradermod.Main;
import com.nimu.tradermod.database.Database;
import com.nimu.tradermod.database.DatabaseUtils;
import com.nimu.tradermod.money.Money;
import net.minecraft.entity.player.PlayerEntity;

import java.sql.SQLException;

public class MoneyManager implements IManager<MoneyManager>{

    public Database database;
   public MoneyManager(Database database){
       this.database = database;
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
