package me.sabitheotome.timedilation.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;

import me.sabitheotome.timedilation.Main;
import net.minecraft.server.command.ServerCommandSource;
import static net.minecraft.text.Text.literal;

import java.text.DecimalFormat;

public final class TimeDilationCommand {

    public static final String SUBCOMMAND_SET_ARG_NAME = "percentage";
    public static final DecimalFormat STANDARD_DECIMAL_FORMAT = new DecimalFormat("0.#####");

    public static final int get(CommandContext<ServerCommandSource> context) {
        double tpsPercent = Main.TICKRATE_MULTIPLIER.get() * 100;
        String tpsFormatted = STANDARD_DECIMAL_FORMAT.format(tpsPercent);
        context.getSource()
                .sendMessage(literal("§5§b[Time Dilation]§r Target tick rate is set to " + tpsFormatted + "%"));
        return Command.SINGLE_SUCCESS;
    }

    public static final int set(CommandContext<ServerCommandSource> context) {
        var manager = context.getSource().getServer().getTickManager();
        float tpsPercent = FloatArgumentType.getFloat(context, SUBCOMMAND_SET_ARG_NAME);
        float tpsMultiplier = tpsPercent / 100;
        float tps = tpsMultiplier * 20;
        Main.TICKRATE_MULTIPLIER.set(tpsMultiplier);
        manager.setTickRate(tps);
        String tpsFormatted = STANDARD_DECIMAL_FORMAT.format(tpsPercent);
        context.getSource()
                .sendMessage(literal("§5§b[Time Dilation]§r Target tick rate changed to " + tpsFormatted + "%"));
        return Command.SINGLE_SUCCESS;
    }

}
