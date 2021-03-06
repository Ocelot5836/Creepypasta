package com.ocelot.entity.hostile;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySlenderman extends EntityMob {

	private int targetChangeTime;
	private static final DataParameter<Boolean> LOCKED_INSIDE_FOREST = EntityDataManager.<Boolean>createKey(EntitySlenderman.class, DataSerializers.BOOLEAN);

	public EntitySlenderman(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 2.9F);
		this.stepHeight = 1.0F;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(LOCKED_INSIDE_FOREST, Boolean.valueOf(false));
	}

	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntitySlenderman.AIFindPlayer(this));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80 + rand.nextInt(21));
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6 + rand.nextInt(3));
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	}

	/**
	 * Sets the active target the Task system uses for tracking
	 */
	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
		super.setAttackTarget(entitylivingbaseIn);
		IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (entitylivingbaseIn == null) {
			this.targetChangeTime = 0;
		} else {
			this.targetChangeTime = this.ticksExisted;
		}
	}

	public static void registerFixesSlenderman(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntitySlenderman.class);
	}

	/**
	 * Checks to see if this enderman should be attacking this player
	 */
	private boolean shouldAttackPlayer(EntityPlayer player) {
		return player.getDistanceSqToEntity(this) <= this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
	}

	public float getEyeHeight() {
		return 2.55F;
	}

	/**
	 * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons use this to react to sunlight and start to burn.
	 */
	public void onLivingUpdate() {
		if (ticksExisted == 1) {
			this.setLockedInForest(this.isInForest());
		}
		
		this.isJumping = false;
		super.onLivingUpdate();
	}

	protected void updateAITasks() {
		if (this.ticksExisted >= this.targetChangeTime + 600) {
			float f = this.getBrightness();

			if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
				this.setAttackTarget((EntityLivingBase) null);
				this.teleportRandomly();
			}
		}

		if (!this.isInForest() && isLockedInForest()) {
			this.setAttackTarget((EntityLivingBase) null);
			this.teleportRandomly();
		}

		super.updateAITasks();
	}

	public boolean isInForest(BlockPos pos) {
		Biome biome = world.getBiome(pos);
		return biome == Biomes.TAIGA || biome == Biomes.TAIGA_HILLS || biome == Biomes.MUTATED_TAIGA;
	}

	public boolean isInForest() {
		return this.isInForest(this.getPosition());
	}

	public void setLockedInForest(boolean lockedInForest) {
		this.dataManager.set(LOCKED_INSIDE_FOREST, Boolean.valueOf(lockedInForest));
	}

	public boolean isLockedInForest() {
		return this.dataManager.get(LOCKED_INSIDE_FOREST);
	}

	/**
	 * Teleport the enderman to a random nearby position
	 */
	protected boolean teleportRandomly() {
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = this.posY + (double) (this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		return this.teleportTo(d0, d1, d2);
	}

	/**
	 * Teleport the enderman to another entity
	 */
	protected boolean teleportToEntity(Entity p_70816_1_) {
		Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double) (this.height / 2.0F) - p_70816_1_.posY + (double) p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
		vec3d = vec3d.normalize();
		double d0 = 16.0D;
		double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * 16.0D;
		double d2 = this.posY + (double) (this.rand.nextInt(16) - 8) - vec3d.y * 16.0D;
		double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * 16.0D;
		return this.teleportTo(d1, d2, d3);
	}

	/**
	 * Teleport the enderman
	 */
	private boolean teleportTo(double x, double y, double z) {
		boolean flag = this.attemptTeleport(x, y, z);
		boolean flag1 = isInForest(new BlockPos(x, y, z));

		if (!this.isLockedInForest()) {
			flag1 = true;
		}

		if (flag && flag1) {
			this.world.playSound((EntityPlayer) null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
			this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		}

		return flag && flag1;
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_ENDERMEN_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
		return SoundEvents.ENTITY_ENDERMEN_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_ENDERMEN_DEATH;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_ENDERMAN;
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			boolean flag = super.attackEntityFrom(source, amount);

			if (source.isUnblockable() && this.rand.nextInt(10) != 0) {
				this.teleportRandomly();
			}

			return flag;
		}
	}

	static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
		private final EntitySlenderman enderman;
		/** The player */
		private EntityPlayer player;
		private int aggroTime;
		private int teleportTime;

		public AIFindPlayer(EntitySlenderman p_i45842_1_) {
			super(p_i45842_1_, EntityPlayer.class, false);
			this.enderman = p_i45842_1_;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			double d0 = this.getTargetDistance();
			this.player = this.enderman.world.getNearestAttackablePlayer(this.enderman.posX, this.enderman.posY, this.enderman.posZ, d0, d0, (Function) null, new Predicate<EntityPlayer>() {
				public boolean apply(@Nullable EntityPlayer p_apply_1_) {
					return p_apply_1_ != null && AIFindPlayer.this.enderman.shouldAttackPlayer(p_apply_1_);
				}
			});
			return this.player != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.aggroTime = 5;
			this.teleportTime = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask() {
			this.player = null;
			super.resetTask();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			if (this.player != null) {
				if (!this.enderman.shouldAttackPlayer(this.player)) {
					return false;
				} else {
					this.enderman.faceEntity(this.player, 10.0F, 10.0F);
					return true;
				}
			} else {
				return this.targetEntity != null && ((EntityPlayer) this.targetEntity).isEntityAlive() ? true : super.shouldContinueExecuting();
			}
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {
			if (this.player != null) {
				if (--this.aggroTime <= 0) {
					this.targetEntity = this.player;
					this.player = null;
					super.startExecuting();
				}
			} else {
				if (this.targetEntity != null) {
					if (this.enderman.shouldAttackPlayer((EntityPlayer) this.targetEntity)) {
						if (((EntityPlayer) this.targetEntity).getDistanceSqToEntity(this.enderman) < 16.0D) {
							this.enderman.teleportRandomly();
						}

						this.teleportTime = 0;
					} else if (((EntityPlayer) this.targetEntity).getDistanceSqToEntity(this.enderman) > 256.0D && this.teleportTime++ >= 30 && this.enderman.teleportToEntity(this.targetEntity)) {
						this.teleportTime = 0;
					}
				}

				super.updateTask();
			}
		}
	}
}