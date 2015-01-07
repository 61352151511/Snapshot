package com.sixonethree.randomutilities.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandHeal extends ModCommandBase implements ICommand {
	
	@Override public int getUsageType() { return 0; }

	@Override public boolean canConsoleUseCommand() { return true; }
	@Override public boolean isOpOnly() { return true; }
	@Override public boolean TabCompletesOnlinePlayers() { return true; }
	
	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayer player = getPlayer(sender, args[0]);
			player.setHealth(player.getMaxHealth());
			outputMessage(player, "healed", true, true);
			outputMessage(sender, "healedplayer", true, true, ColorPlayer(player));
		} else {
			if (sender instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) sender;
				player.setHealth(player.getMaxHealth());
				outputMessage(player, "healed", true, true);
			}
		}
	}
}