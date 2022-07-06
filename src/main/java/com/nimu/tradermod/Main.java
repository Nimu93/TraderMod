package com.nimu.tradermod;

import com.nimu.tradermod.database.Database;
import com.nimu.tradermod.manager.MoneyManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.sql.SQLException;

public class Main implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("tradermod");
	public static final String PATH = FabricLoader.getInstance().getGameDir().resolve("tradermod").toString();
	public static Database DATABASE;
	public static PlayerManager PLAYER_MANAGER;
	public static MoneyManager MONEY_MANAGER;
	@Override
	public void onInitialize() {
		DATABASE = new Database(PATH);
		MONEY_MANAGER = new MoneyManager(DATABASE);
		EventsManager.registerEvents();
	}



}
