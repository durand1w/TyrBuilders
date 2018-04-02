package com.tyr.builders;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TyrBuildersSounds 
{
	public static final SoundEvent chains_rattle = new SoundEvent(new ResourceLocation(TyrBuilders.MODID, "chains_rattle")).setRegistryName("chains_rattle");
	public static final SoundEvent beehive = new SoundEvent(new ResourceLocation(TyrBuilders.MODID, "beehive")).setRegistryName("beehive");
	
	@SubscribeEvent
	public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) 
	{
		event.getRegistry().registerAll
		(
				chains_rattle,
				beehive
		);
	}
}
