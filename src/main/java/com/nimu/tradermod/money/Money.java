package com.nimu.tradermod.money;

import com.nimu.tradermod.Main;
import com.nimu.tradermod.database.DatabaseUtils;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.sql.SQLException;

public class Money {

  int amount;
  PlayerEntity player;

  public Money(PlayerEntity player, int amount){
    this.amount = amount;
    this.player = player;
  }

  public int AddMoney(int quantity) throws SQLException {
      amount += quantity;
      DatabaseUtils.UpdateMoney(Main.DATABASE, this);
      return amount;
  }
    public int RemoveMoney(int quantity) throws SQLException {
        amount -= quantity;
        DatabaseUtils.UpdateMoney(Main.DATABASE, this);
        return amount;
    }
  public void Translate(Money otherPlayer, int quantity) throws SQLException {
      if (otherPlayer.player.isAlive() && Main.PLAYER_MANAGER.getPlayer(otherPlayer.player.getUuid()) != null){
          if (amount - quantity > 0){
            otherPlayer.AddMoney(quantity);
            RemoveMoney(quantity);
        }
      else {
          player.sendMessage(new LiteralText("Not enough money"), false);
      }
      }else {
          player.sendMessage(new LiteralText("Player " + otherPlayer.player.getEntityName() + " is not connected !"), false);
      }
      player.sendMessage(new LiteralText("You sent " + quantity + " to " + otherPlayer.player.getEntityName()), false);
  }

  public int getAmount(){
      return amount;
  }

  public PlayerEntity getPlayer(){
      return player;
  }

}
