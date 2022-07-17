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

public class RetrieveMoneyCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playertarget =  EntityArgumentType.getPlayer(context, "player");
        int quantity = IntegerArgumentType.getInteger(context, "quantity");
        if (playertarget.isDisconnected()){
            context.getSource().getPlayer().sendMessage(new LiteralText("Player is not connected"), false);
            return 0;
        }
        if (!context.getSource().getPlayer().hasPermissionLevel(2)){
            context.getSource().getPlayer().sendMessage(new LiteralText("You don't have enough permission"), false);
            return 0;
        }
        try {
            Money player = Main.MONEY_MANAGER.getMoneyForPlayer((PlayerEntity) playertarget);
            if (player == null){
                context.getSource().getPlayer().sendMessage(new LiteralText("CRITICAL"), false);
                return 1;
            }
            player.RemoveMoney(quantity);
            context.getSource().getPlayer().sendMessage(new LiteralText("The player have now " + player.getAmount() + "$"), false);
            return 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public CommandNode getNode() {
        return CommandManager.literal("remove").then(
                CommandManager.argument("player", EntityArgumentType.player()).then(
                        CommandManager.argument("quantity", IntegerArgumentType.integer()).executes(this::run))
        ).build();
    }
}
