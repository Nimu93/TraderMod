package com.nimu.tradermod.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
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
import net.minecraft.text.Text;

import java.sql.SQLException;

public class ShowMoneyCommand implements Command<ServerCommandSource> {
    @Override
    public int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
        ServerPlayerEntity playertarget = context.getSource().getPlayer();
        if (target != null) {
            playertarget = target;
        }
        if (playertarget.isDisconnected()){
            context.getSource().getPlayer().sendMessage(new LiteralText("Player is not connected"), false);
            return 0;
        }
        try {
            Money player = Main.MONEY_MANAGER.getMoneyForPlayer((PlayerEntity) playertarget);
            if (player == null){
                context.getSource().getPlayer().sendMessage(new LiteralText("CRITICAL"), false);
                return 1;
            }
            context.getSource().getPlayer().sendMessage(new LiteralText("The money of " + playertarget.getEntityName() + " is " + player.getAmount() + "$"), false);
            return 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public int runsolo(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity playertarget = context.getSource().getPlayer();
        if (playertarget.isDisconnected()){
            context.getSource().getPlayer().sendMessage(new LiteralText("Player is not connected"), false);
            return 0;
        }
        try {
            Money player = Main.MONEY_MANAGER.getMoneyForPlayer((PlayerEntity) playertarget);
            if (player == null){
                context.getSource().getPlayer().sendMessage(new LiteralText("CRITICAL"), false);
                return 1;
            }
            context.getSource().getPlayer().sendMessage(new LiteralText("You have " + player.getAmount() + "$"), false);
            return 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
    public CommandNode getNode() {
        return CommandManager.literal("show").then(
                CommandManager.argument("player", EntityArgumentType.player()).executes(this::run)
        ).executes(this::runsolo).build();
    }

}
