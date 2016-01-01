package com.sixonethree.randomutilities.common.command;

import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.sixonethree.randomutilities.reference.CommandReference.TeleportRequests;

public class CommandTpDeny extends ModCommandBase implements ICommand {
	@Override public void processCommandPlayer(EntityPlayer player, String[] args) {
		if (TeleportRequests.pending(player.getUniqueID())) {
			outputMessage(player, "youdenied", true, true);
			TeleportRequests.remove(player.getUniqueID());
			List<EntityPlayerMP> playerlist = configHandler.playerEntityList;
			for (int i = 0; i < playerlist.size(); ++ i) {
				if (playerlist.get(i).getUniqueID().equals(TeleportRequests.fromWho(player.getUniqueID()))) {
					outputMessage(playerlist.get(i), "gotdenied", true, true);
				}
			}
		} else {
			outputMessage(player, "nonetodeny", true, true);
		}
	}
	
	@Override public boolean canConsoleUseCommand() { return false; }
	@Override public int getUsageType() { return 1; }
	@Override public boolean isOpOnly() { return false; }
	@Override public boolean tabCompletesOnlinePlayers() { return false; }
}