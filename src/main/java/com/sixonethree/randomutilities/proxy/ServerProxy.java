package com.sixonethree.randomutilities.proxy;

import net.minecraftforge.common.MinecraftForge;

import com.sixonethree.randomutilities.common.event.ChatEvents;
import com.sixonethree.randomutilities.common.event.DeathEvents;

public class ServerProxy extends CommonProxy {
	@Override public void init() {
		super.init();
		MinecraftForge.EVENT_BUS.register(new ChatEvents());
		MinecraftForge.EVENT_BUS.register(new DeathEvents());
	}
}