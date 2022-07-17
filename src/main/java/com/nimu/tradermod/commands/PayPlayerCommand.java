package com.nimu.tradermod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.nimu.tradermod.Main;
import com.nimu.tradermod.money.Money;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.sql.SQLException;

public class PayPlayerCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playertarget =  EntityArgumentType.getPlayer(context, "playerToPay");
        int quantity = IntegerArgumentType.getInteger(context, "quantity");
        if (playertarget.isDisconnected()){
            context.getSource().getPlayer().sendMessage(new LiteralText("Player is not connected"), false);
            return 0;
        }
        try {
            Money player = Main.MONEY_MANAGER.getMoneyForPlayer((PlayerEntity) context.getSource().getPlayer());
            player.Translate(Main.MONEY_MANAGER.getMoneyForPlayer((PlayerEntity) playertarget), quantity);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public CommandNode getNode() {
        return CommandManager.literal("pay").then(
                CommandManager.argument("playerToPay", EntityArgumentType.player()).then(
                        CommandManager.argument("quantity", IntegerArgumentType.integer()).executes(this::run))
        ).build();
    }
}
