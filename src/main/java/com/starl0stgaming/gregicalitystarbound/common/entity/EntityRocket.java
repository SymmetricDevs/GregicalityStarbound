package com.starl0stgaming.gregicalitystarbound.common.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.starl0stgaming.gregicalitystarbound.client.sound.MovingSoundRocket;

import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class EntityRocket extends EntityLiving implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    protected static final float jerk = 0.0001F;
    private static final DataParameter<Boolean> LAUNCHED = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> COUNTDOWN_STARTED = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> LAUNCH_TIME = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> FLIGHT_TIME = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.VARINT);
    private static final DataParameter<Float> START_POS = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.FLOAT);

    private final int countdownTimer = 0;
    @SideOnly(Side.CLIENT)
    private MovingSoundRocket soundRocket;
    private String name;
    private int id;

    private double realMotionX;
    private double realMotionY;
    private double realMotionZ;

    public EntityRocket(World worldIn) {
        super(worldIn);
    }

    public EntityRocket(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, -90F);
        rideCooldown = -1;
        ignoreFrustumCheck = true;
        this.setEntityBoundingBox(new AxisAlignedBB(x - 1, y + 0.1, z - 1, x + 1, y + 40, z + 1));
    }

    public EntityRocket(World worldIn, BlockPos pos) {
        this(worldIn, (float) pos.getX() + 0.5F, pos.getY(), (float) pos.getZ() + 0.5F);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        if (!this.world.isRemote) {}

        this.dataManager.register(LAUNCHED, false);
        this.dataManager.register(COUNTDOWN_STARTED, false);
        this.dataManager.register(START_POS, 0.F);
        this.dataManager.register(AGE, 0);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setLaunched(compound.getBoolean("Launched"));
        this.setCountdownStarted(compound.getBoolean("CountdownStarted"));
        this.setAge(compound.getInteger("Age"));
        this.setStartPos(compound.getFloat("StartPos"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("Launched", this.isLaunched());
        compound.setBoolean("CountdownStarted", this.isCountdownStarted());
        compound.setInteger("Age", this.getAge());
        compound.setFloat("StartPos", this.getStartPos());
    }

    public void onLaunch() {
        this.setLaunched(true);
        this.isAirBorne = true;

        this.playRocketSound();
    }

    @Override
    public void onUpdate() {
        if (!world.isRemote) {
            this.setAge(getAge() + 1);
        }

        if (this.firstUpdate) {
            this.setStartPos((float) this.posY);
        }

        super.onUpdate();

        this.setRotation(0.0F, -90.0F);

        if (isLaunched()) {
            float startPos = this.getStartPos();

            this.realMotionY += 0.001D;
            velocityChanged = true;
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        motionX = realMotionX;
        motionY = realMotionY;
        motionZ = realMotionZ;
    }

    public void explode() {
        this.world.newExplosion(this, this.posX, this.posY, this.posZ, 24, true, true);
        this.setDead();
    }

    public boolean isLaunched() {
        return this.dataManager.get(LAUNCHED);
    }

    public void setLaunched(boolean launched) {
        this.dataManager.set(LAUNCHED, launched);
    }

    public boolean isCountdownStarted() {
        return this.dataManager.get(COUNTDOWN_STARTED);
    }

    public void setCountdownStarted(boolean countdownStarted) {
        this.dataManager.set(COUNTDOWN_STARTED, countdownStarted);
    }

    public int getAge() {
        return this.dataManager.get(AGE);
    }

    public void setAge(int age) {
        this.dataManager.set(AGE, age);
    }

    public float getStartPos() {
        return this.dataManager.get(START_POS);
    }

    public void setStartPos(Float startPos) {
        this.dataManager.set(START_POS, startPos);
    }

    @SideOnly(Side.CLIENT)
    public void playRocketSound() {
        if (this.world.isRemote) {
            this.soundRocket = new MovingSoundRocket(this);
            Minecraft.getMinecraft().getSoundHandler().playSound(this.soundRocket);
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "animationRocket", 0, (rocket) -> {
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public boolean getIsInvulnerable() {
        return true;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();

        if (flag) {
            return super.processInteract(player, hand);
        }
        if (!this.isBeingRidden()) {
            if (!this.world.isRemote) {
                player.startRiding(this);
                this.onLaunch();
            }
        }
        return true;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            passenger.setPosition(this.posX + getLookVec().x * this.getMountedYOffset(),
                    this.posY + getLookVec().y * (this.getMountedYOffset() + passenger.getYOffset()),
                    this.posZ + getLookVec().z * this.getMountedYOffset());
        }
    }

    @Override
    public double getMountedYOffset() {
        return 0.25;
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("RealMotion", this.newDoubleNBTList(this.realMotionX, this.realMotionY, this.realMotionZ));
        return super.writeToNBT(compound);
    }
}
