package com.ocelot.init;

import com.ocelot.Creepypasta;
import com.ocelot.Reference;
import com.ocelot.client.render.entity.RenderCrow;
import com.ocelot.client.render.entity.RenderSlenderman;
import com.ocelot.client.render.entity.RenderSmileDog;
import com.ocelot.client.render.entity.RenderSmileMan;
import com.ocelot.client.render.entity.RenderTiccy;
import com.ocelot.client.render.tileentity.RenderItemRecolorer;
import com.ocelot.client.render.tileentity.RenderSafe;
import com.ocelot.entity.hostile.EntitySlenderman;
import com.ocelot.entity.hostile.EntityTiccy;
import com.ocelot.entity.passive.EntityCrow;
import com.ocelot.entity.passive.EntitySmileDog;
import com.ocelot.entity.passive.EntitySmileMan;
import com.ocelot.tileentity.TileEntityBattery;
import com.ocelot.tileentity.TileEntityItemRecolorer;
import com.ocelot.tileentity.TileEntityReserveGenerator;
import com.ocelot.tileentity.TileEntitySafe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author Ocelot5836
 */
public class ModEntities {

	/** The current entity id */
	private static int entityID = 0;

	public static void preInit() {
		registerTileEntities();
	}

	public static void init() {
		registerEntities();
		registerSpawns();
	}

	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySafe.class, Reference.MOD_ID + ":TileEntitySafe");
		GameRegistry.registerTileEntity(TileEntityItemRecolorer.class, Reference.MOD_ID + ":TileEntityItemRecolorer");
		GameRegistry.registerTileEntity(TileEntityBattery.class, Reference.MOD_ID + ":TileEntityBattery");
		GameRegistry.registerTileEntity(TileEntityReserveGenerator.class, Reference.MOD_ID + ":TileEntityReserveGenerator");

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySafe.class, new RenderSafe());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemRecolorer.class, new RenderItemRecolorer());
	}

	/**
	 * Registers the specified entities.
	 */
	private static void registerEntities() {
		registerEntity(EntitySmileDog.class, "smile_dog", 32, 1, true, 0x639fff, 0x62ffe5);
		registerEntity(EntitySmileMan.class, "smile_man", 32, 1, true, 0x639fff, 0x62ffe5);
		registerEntity(EntityCrow.class, "crow", 32, 1, true, 0x639fff, 0x62ffe5);

		registerEntity(EntityTiccy.class, "ticcy", 32, 1, true, 0x639fff, 0x62ffe5);
		registerEntity(EntitySlenderman.class, "slenderman", 32, 1, true, 0x639fff, 0x62ffe5);
	}

	/**
	 * Adds the entity's spawn location.
	 */
	public static void registerSpawns() {
		EntityRegistry.addSpawn(EntitySlenderman.class, 10, 0, 1, EnumCreatureType.MONSTER, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.MUTATED_TAIGA);
	}

	/**
	 * Registers the entity renders
	 */
	public static void registerEntityRenders() {
		Minecraft mc = Minecraft.getMinecraft();

		RenderingRegistry.registerEntityRenderingHandler(EntitySmileDog.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderSmileDog(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntitySmileMan.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderSmileMan(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityCrow.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderCrow(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntityTiccy.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderTiccy(manager);
			}
		});

		RenderingRegistry.registerEntityRenderingHandler(EntitySlenderman.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				return new RenderSlenderman(manager);
			}
		});
	}

	/**
	 * Registers an entity and an egg.
	 * 
	 * @param entityClass
	 *            The entity class
	 * @param entityName
	 *            A unique name for the entity
	 * @param id
	 *            A mod specific ID for the entity
	 * @param mod
	 *            The mod
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 * @param primaryEggColor
	 *            The color of the main part of the spawn egg
	 * @param secondaryEggColor
	 *            The color of the secondary part of the spawn egg
	 */
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryEggColor, int secondaryEggColor) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, entityName), entityClass, entityName, entityID++, Creepypasta.instance, trackingRange, updateFrequency, sendsVelocityUpdates, primaryEggColor, secondaryEggColor);
	}

	/**
	 * Registers an entity.
	 * 
	 * @param entityClass
	 *            The entity class
	 * @param entityName
	 *            A unique name for the entity
	 * @param id
	 *            A mod specific ID for the entity
	 * @param mod
	 *            The mod
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 */
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(new ResourceLocation(Reference.MOD_ID, entityName), entityClass, entityName, entityID++, Creepypasta.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}