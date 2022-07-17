package com.nimu.tradermod;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.nimu.tradermod.commands.AddMoneyCommand;
import com.nimu.tradermod.commands.PayPlayerCommand;
import com.nimu.tradermod.commands.RetrieveMoneyCommand;
import com.nimu.tradermod.commands.ShowMoneyCommand;
import com.nimu.tradermod.database.Database;
import com.nimu.tradermod.manager.MoneyManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger("tradermod");
	public static final String PATH = FabricLoader.getInstance().getGameDir().resolve("tradermod").toString();
	public static Database DATABASE;
	public static PlayerManager PLAYER_MANAGER;
	public static MoneyManager MONEY_MANAGER;
	@Override
	public void onInitialize() {
		try {
			DATABASE = new Database(PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MONEY_MANAGER = new MoneyManager(DATABASE);
		EventsManager.registerEvents();

		CommandRegistrationCallback.EVENT.register(this::registerCommands);
	}

	private void registerCommands(CommandDispatcher<ServerCommandSource> serverCommandSourceCommandDispatcher, boolean b) {
		LiteralCommandNode<ServerCommandSource> trader = CommandManager.literal("trader").build();
		LiteralCommandNode<ServerCommandSource> trader2 = CommandManager.literal("t").build();

		serverCommandSourceCommandDispatcher.getRoot().addChild(trader);
		serverCommandSourceCommandDispatcher.getRoot().addChild(trader2);
		CommandNode showmoney = (new ShowMoneyCommand()).getNode();
		CommandNode addmoney = (new AddMoneyCommand()).getNode();
		CommandNode removemoney = (new RetrieveMoneyCommand()).getNode();
		CommandNode payplayer = (new PayPlayerCommand()).getNode();

		trader.addChild(payplayer);
		trader2.addChild(payplayer);
		trader.addChild(showmoney);
		trader2.addChild(showmoney);
		trader.addChild(addmoney);
		trader2.addChild(addmoney);
		trader.addChild(removemoney);
		trader2.addChild(removemoney);
	}



}
