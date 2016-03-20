package com.sixonethree.randomutilities.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.sixonethree.randomutilities.client.gui.GuiManager;
import com.sixonethree.randomutilities.client.gui.GuiManager.GUI;
import com.sixonethree.randomutilities.client.model.ModelHelper;
import com.sixonethree.randomutilities.client.render.DisplayTableRenderer;
import com.sixonethree.randomutilities.client.render.MagicChestRenderer;
import com.sixonethree.randomutilities.client.render.ModeledBlockInventoryRenderer;
import com.sixonethree.randomutilities.common.block.tile.TileEntityDisplayTable;
import com.sixonethree.randomutilities.common.block.tile.TileEntityMagicChest;
import com.sixonethree.randomutilities.common.init.ModBlocks;
import com.sixonethree.randomutilities.common.init.ModItems;

public class ClientProxy extends ServerProxy {
	@Override public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override public void init(FMLInitializationEvent event) {
		super.init(event);
		
		this.registerRenderers();
	}
	
	@Override public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te == null) return null;
		if (ID == 0) return GuiManager.GUI.buildGUI(GUI.MAGIC, player.inventory, (TileEntityMagicChest) te);
		if (ID == 1) return GuiManager.GUI.buildGUI(GUI.DISPLAYTABLE, player.inventory, (TileEntityDisplayTable) te);
		return null;
	}
	
	@Override public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
	
	public void registerRenderers() {
		TileEntitySpecialRenderer<TileEntityDisplayTable> dtr = new DisplayTableRenderer(Minecraft.getMinecraft().getRenderManager());
		TileEntitySpecialRenderer<TileEntityMagicChest> mcr = new MagicChestRenderer(Minecraft.getMinecraft().getRenderManager());
		ModelHelper.removeBlockState(ModBlocks.displayTable);
		ModelHelper.removeBlockState(ModBlocks.magicChest);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayTable.class, dtr);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMagicChest.class, mcr);
		
		ModelHelper.registerBlock(ModBlocks.magicChest);
		ModelHelper.registerBlock(ModBlocks.displayTable);
		ModelHelper.registerItem(ModItems.lunchbox, new int[] {0, 1});
		ModelHelper.registerItem(ModItems.heartCanister, new int[] {0, 1, 2, 3});
		ModelHelper.registerItem(ModItems.combined);
		ModelHelper.registerItem(ModItems.magicCard);
		
		TileEntityItemStackRenderer.instance = new ModeledBlockInventoryRenderer(TileEntityItemStackRenderer.instance);
	}
}