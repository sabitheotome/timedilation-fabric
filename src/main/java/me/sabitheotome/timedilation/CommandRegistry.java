package me.sabitheotome.timedilation;

import com.mojang.brigadier.CommandDispatcher;

import me.sabitheotome.timedilation.commands.TimeDilationCommand;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.*;
import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;

public final class CommandRegistry {

	public static final void registerTickrateCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
		/*
		 * /timedilate [<get>/<set>]
		 */
		var get = literal("get").executes(TimeDilationCommand::getServerTickrateCmd);
		var set = literal("set").then(
				argument(TimeDilationCommand.SUBCOMMAND_SET_ARG_NAME, floatArg())
						.executes(TimeDilationCommand::setServerTickrateCmd));
		var cmd = literal("timedilate")
				.then(get).then(set).requires(source -> source.hasPermissionLevel(2));
		dispatcher.register(cmd);
	}

	public static final void registerTickrateClientCommand(CommandDispatcher<FabricClientCommandSource> dispatcher) {
		/*
		 * /timedilate-client [<get>/<set>]
		 */
		var get = ClientCommandManager.literal("get").executes(TimeDilationCommand::getClientTickrateCmd);
		var set = ClientCommandManager.literal("set").then(
				ClientCommandManager.argument(TimeDilationCommand.SUBCOMMAND_SET_ARG_NAME, floatArg())
						.executes(TimeDilationCommand::setClientTickrateCmd));
		var cmd = ClientCommandManager.literal("timedilate-client")
				.then(get).then(set);
		dispatcher.register(cmd);
	}

	public static final void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			registerTickrateCommand(dispatcher);
		});
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			registerTickrateClientCommand(dispatcher);
		});
	}

}
