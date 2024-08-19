package com.starl0stgaming.gregicalitystarbound.common.entity;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.configuration.GCSBForgeConfig;
import com.starl0stgaming.gregicalitystarbound.api.space.timeline.Timeline;
import com.starl0stgaming.gregicalitystarbound.client.particle.SusyParticleFlame;
import com.starl0stgaming.gregicalitystarbound.client.particle.SusyParticleSmoke;
import com.starl0stgaming.gregicalitystarbound.common.entity.timeline.ChangeMotionEvent;
import com.starl0stgaming.gregicalitystarbound.common.entity.timeline.SwapDimensionEvent;
import gregtech.api.GTValues;
import mezz.jei.network.PacketHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
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
    private static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> FLIGHT_TIME = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.VARINT);
    private static final DataParameter<Float> START_POS = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Integer> TARGET_DIMENSION = EntityDataManager.createKey(EntityRocket.class,
            DataSerializers.VARINT);


    private final int countdownTimer = 0;
    @SideOnly(Side.CLIENT)
    private MovingSoundRocket soundRocket;
    private String name;
    private int id;

    private Timeline<EntityRocket> timeline;

    private double realMotionX;
    public double realMotionY;
    private double realMotionZ;

    public double accelerationY;

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
        if (!this.world.isRemote) {
        }

        this.dataManager.register(FLIGHT_TIME, -1);
        this.dataManager.register(START_POS, 0.F);
        this.dataManager.register(AGE, 0);
        this.dataManager.register(TARGET_DIMENSION, 29);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        this.setAge(compound.getInteger("Age"));
        this.setStartPos(compound.getFloat("StartPos"));
        if (compound.hasKey("FlightTime")) {
            this.setFlightTime(compound.getInteger("FlightTime"));
        } else {
            this.setFlightTime(-1);
        }
        if (compound.hasKey("RealMotion")) {
            NBTTagList nbttaglist = compound.getTagList("RealMotion", 6);
            this.realMotionX = nbttaglist.getDoubleAt(0);
            this.realMotionY = nbttaglist.getDoubleAt(1);
            this.realMotionZ = nbttaglist.getDoubleAt(2);
        }
        if (compound.hasKey("AccelerationY")) {
            this.accelerationY = compound.getDouble("AccelerationY");
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("Age", this.getAge());
        compound.setInteger("FlightTime", this.getFlightTime());
        compound.setFloat("StartPos", this.getStartPos());
        compound.setTag("RealMotion", this.newDoubleNBTList(this.realMotionX, this.realMotionY, this.realMotionZ));
        compound.setDouble("AccelerationY", accelerationY);
    }

    public void onLaunch() {
        if (this.isLaunched()) return;
        prepareLaunch();
        this.setFlightTime(0);
        if (world.isRemote) {
            PacketHandler.sendToServer(new PacketEntity(this));
        }
        this.isAirBorne = true;
        this.playRocketSound();
    }

    protected void prepareLaunch() {
        timeline = new Timeline<>();
        timeline.registerEvent(100, new ChangeMotionEvent(0, 0.001F));
        timeline.registerEvent(399, new ChangeMotionEvent(0, 0));
        timeline.registerEvent(400, new SwapDimensionEvent(GCSBForgeConfig.spaceDimensionID, false));
        timeline.registerEvent(699, new ChangeMotionEvent(-0.5F, 0.001F));
        timeline.registerEvent(700, new SwapDimensionEvent(29, true));
        timeline.registerEvent(1200, new ChangeMotionEvent(Double.NaN, 0.F));
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
            if (world.isRemote) {
                if (soundRocket == null) {
                    playRocketSound();
                }
                if (this.realMotionY != 0) {
                    spawnFlightParticles(this.realMotionY >= 0);
                }
            }
            if (timeline == null) {
                prepareLaunch();
            }
            setFlightTime(getFlightTime() + 1);

            timeline.run(this, getFlightTime());
            this.realMotionY += this.accelerationY;
            velocityChanged = true;
        }
        if (!isLaunched()) {
            if (this.world.isRemote && soundRocket != null) {
                soundRocket.startFading();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void spawnFlightParticles(boolean goingUp) {
        if (this.isDead) {
            return;
        }

        double offset = goingUp ? 0.2D : 0.5D;
        SusyParticleFlame flame1 = new SusyParticleFlame(
                this.world,
                this.posX + 0.8D,
                this.posY + 0.9D + offset,
                this.posZ + 0.2D,
                1.5 * (GTValues.RNG.nextFloat() + 0.2) * 0.08,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.08);
        SusyParticleFlame flame2 = new SusyParticleFlame(
                this.world,
                this.posX + 0.8D,
                this.posY + 0.9D + offset,
                this.posZ - 0.2D,
                1.5 * (GTValues.RNG.nextFloat() + 0.2) * 0.08,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.08);
        SusyParticleFlame flame3 = new SusyParticleFlame(
                this.world,
                this.posX - 0.8D,
                this.posY + 0.9D + offset,
                this.posZ + 0.2D,
                1.5 * (GTValues.RNG.nextFloat() - 1.2) * 0.08,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.08);
        SusyParticleFlame flame4 = new SusyParticleFlame(
                this.world,
                this.posX - 0.8D,
                this.posY + 0.9D + offset,
                this.posZ - 0.2D,
                1.5 * (GTValues.RNG.nextFloat() - 1.2) * 0.08,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.08);

        SusyParticleSmoke smoke1 = new SusyParticleSmoke(
                this.world,
                this.posX + 0.8D,
                this.posY + 0.9D + offset,
                this.posZ + 0.2D,
                1.5 * (GTValues.RNG.nextFloat() + 0.2) * 0.16,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.16);
        SusyParticleSmoke smoke2 = new SusyParticleSmoke(
                this.world,
                this.posX + 0.8D,
                this.posY + 0.9D + offset,
                this.posZ - 0.2D,
                1.5 * (GTValues.RNG.nextFloat() + 0.2) * 0.16,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.16);
        SusyParticleSmoke smoke3 = new SusyParticleSmoke(
                this.world,
                this.posX - 0.8D,
                this.posY + 0.9D + offset,
                this.posZ + 0.2D,
                1.5 * (GTValues.RNG.nextFloat() - 1.2) * 0.16,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.16);
        SusyParticleSmoke smoke4 = new SusyParticleSmoke(
                this.world,
                this.posX - 0.8D,
                this.posY + 0.9D + offset,
                this.posZ - 0.2D,
                1.5 * (GTValues.RNG.nextFloat() - 1.2) * 0.16,
                -1.5,
                1.5 * (GTValues.RNG.nextFloat() - 0.5) * 0.16);

        Minecraft.getMinecraft().effectRenderer.addEffect(smoke1);
        Minecraft.getMinecraft().effectRenderer.addEffect(smoke2);
        Minecraft.getMinecraft().effectRenderer.addEffect(smoke3);
        Minecraft.getMinecraft().effectRenderer.addEffect(smoke4);

        Minecraft.getMinecraft().effectRenderer.addEffect(flame1);
        Minecraft.getMinecraft().effectRenderer.addEffect(flame2);
        Minecraft.getMinecraft().effectRenderer.addEffect(flame3);
        Minecraft.getMinecraft().effectRenderer.addEffect(flame4);
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
        return this.dataManager.get(FLIGHT_TIME) >= 0;
    }

    public void setLaunched(boolean launched) {
        if (launched) {
            this.dataManager.set(FLIGHT_TIME, 0);
        } else {
            this.dataManager.set(FLIGHT_TIME, -1);
        }
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

    public int getFlightTime() {
        return this.dataManager.get(FLIGHT_TIME);
    }

    public void setFlightTime(int flightTime) {
        this.dataManager.set(FLIGHT_TIME, flightTime);
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
        compound.setTag("Acceleration", this.newDoubleNBTList(0, this.accelerationY, 0));

        return super.writeToNBT(compound);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {

    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
        super.updateFallState(y, onGroundIn, state, pos);
        if (this.realMotionY < 0.0D) {
            this.setLaunched(false);
        }
    }
}
