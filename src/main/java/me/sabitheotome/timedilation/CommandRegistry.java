package me.sabitheotome.timedilation;

import com.mojang.brigadier.CommandDispatcher;

import me.sabitheotome.timedilation.commands.TimeDilationCommand;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.*;
import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;

public final class CommandRegistry {

	public static final void registerTickrateCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
		/*
		 * /tickrate get
		 */
		var get = literal("get").executes(TimeDilationCommand::get);
		/*
		 * /tickrate set [<tps>]
		 */
		var set = literal("set").then(
				argument(TimeDilationCommand.SUBCOMMAND_SET_ARG_NAME, floatArg())
						.executes(TimeDilationCommand::set));

		var cmd = literal("timedilation").then(get).then(set);
		dispatcher.register(cmd);
	}

	public static final void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			registerTickrateCommand(dispatcher);
		});
	}

}
