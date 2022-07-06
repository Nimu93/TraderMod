package com.nimu.tradermod;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.util.ActionResult;

import java.sql.SQLException;

public class EventsManager {
    public static void registerEvents(){
        onServerStart();
        onPlayerJoin();
    }

    private static ActionResult onServerStart(){
        ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
            Main.PLAYER_MANAGER = server.getPlayerManager();
        });
        return ActionResult.PASS;
    }

    private static ActionResult onPlayerJoin(){
        ServerPlayConnectionEvents.JOIN.register(((handler, sender, server) -> {
            try {
                Main.MONEY_MANAGER.AddPlayerMoney(handler.player);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }));
        return ActionResult.PASS;
    }
}
