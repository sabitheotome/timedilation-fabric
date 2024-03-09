package me.sabitheotome.timedilation.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;

import me.sabitheotome.timedilation.Main;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.server.command.ServerCommandSource;
import static net.minecraft.text.Text.literal;

import java.text.DecimalFormat;

public final class TimeDilationCommand {

    public static final String SUBCOMMAND_SET_ARG_NAME = "percentage";
    public static final DecimalFormat STANDARD_DECIMAL_FORMAT = new DecimalFormat("0.#####");

    public static final int getServerTickrateCmd(CommandContext<ServerCommandSource> context) {
        double clientTPSPercent = Main.CLIENT_TICKRATE_MULTIPLIER.get() * 100;
        var manager = context.getSource().getServer().getTickManager();
        float tps = manager.getTickRate();
        float tpsMultiplier = tps / 20;
        float tpsPercent = tpsMultiplier * 100;
        String tpsFormatted = STANDARD_DECIMAL_FORMAT.format(tpsPercent);
        String clientTPSFormatted = STANDARD_DECIMAL_FORMAT.format(clientTPSPercent);
        context.getSource()
                .sendFeedback(() -> literal("§5§b[Time Dilation]§r Target tick rate is set to " + tpsFormatted
                        + "% (server) and " + clientTPSFormatted + "% (client)"), false);
        return Command.SINGLE_SUCCESS;
    }

    public static final int setServerTickrateCmd(CommandContext<ServerCommandSource> context) {
        var manager = context.getSource().getServer().getTickManager();
        float tpsPercent = FloatArgumentType.getFloat(context, SUBCOMMAND_SET_ARG_NAME);
        float tpsMultiplier = Math.max(tpsPercent / 100, 0.05f);
        tpsPercent = tpsMultiplier * 100;
        float tps = Math.max(tpsMultiplier * 20, 1f);
        manager.setTickRate(tps);
        Main.CLIENT_TICKRATE_MULTIPLIER.set(tpsMultiplier);
        String tpsFormatted = STANDARD_DECIMAL_FORMAT.format(tpsPercent);
        context.getSource()
                .sendFeedback(() -> literal("§5§b[Time Dilation]§r Target tick rate changed to " + tpsFormatted + "%"),
                        false);
        return Command.SINGLE_SUCCESS;
    }

    public static final int getClientTickrateCmd(CommandContext<FabricClientCommandSource> context) {
        double tpsPercent = Main.CLIENT_TICKRATE_MULTIPLIER.get() * 100;
        String tpsFormatted = STANDARD_DECIMAL_FORMAT.format(tpsPercent);
        context.getSource()
                .sendFeedback(literal("§5§b[Time Dilation]§r Target tick rate is set to " + tpsFormatted + "%"));
        return Command.SINGLE_SUCCESS;
    }

    public static final int setClientTickrateCmd(CommandContext<FabricClientCommandSource> context) {
        float tpsPercent = FloatArgumentType.getFloat(context, SUBCOMMAND_SET_ARG_NAME);
        float tpsMultiplier = Math.max(tpsPercent / 100, 0.05f);
        tpsPercent = tpsMultiplier * 100;
        Main.CLIENT_TICKRATE_MULTIPLIER.set(tpsMultiplier);
        String tpsFormatted = STANDARD_DECIMAL_FORMAT.format(tpsPercent);
        context.getSource()
                .sendFeedback(
                        literal("§5§b[Time Dilation]§r Target tick rate changed to " + tpsFormatted + "%"));
        return Command.SINGLE_SUCCESS;
    }

}
