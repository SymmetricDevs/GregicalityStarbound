package com.starl0stgaming.gregicalitystarbound.api.space.rocketry;

import com.starl0stgaming.gregicalitystarbound.api.GCSBLog;
import com.starl0stgaming.gregicalitystarbound.api.sound.GCSBSounds;
import com.starl0stgaming.gregicalitystarbound.api.sound.MovingSoundRocket;
import com.starl0stgaming.gregicalitystarbound.api.space.rocketry.guidance.GuidanceComputer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class RocketEntity extends Entity {

    protected static final float jerk = 0.0001F;

    private int countdownTimer = 0;

    @SideOnly(Side.CLIENT)
    private MovingSoundRocket soundRocket;

    private GuidanceComputer guidanceComputer;
    private String name;
    private int id;


    private static final DataParameter<Boolean> LAUNCHED = EntityDataManager.<Boolean>createKey(RocketEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> COUNTDOWN_STARTED = EntityDataManager.<Boolean>createKey(RocketEntity.class, DataSerializers.BOOLEAN);

    private static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(RocketEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> LAUNCH_TIME = EntityDataManager.<Integer>createKey(RocketEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FLIGHT_TIME = EntityDataManager.<Integer>createKey(RocketEntity.class, DataSerializers.VARINT);

    private static final DataParameter<Float> START_POS = EntityDataManager.<Float>createKey(RocketEntity.class, DataSerializers.FLOAT);



    public RocketEntity(World worldIn) {
        super(worldIn);
        this.setSize(3F, 31F);
    }

    public RocketEntity(World worldIn, double x, double y, double z) {
        super(worldIn);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, 180.0F);
        this.setSize(3F, 31F);
        rideCooldown = -1;
        ignoreFrustumCheck = true;
        this.setEntityBoundingBox(new AxisAlignedBB(x - 1, y + 0.1, z - 1, x + 1, y + 40, z + 1));
    }

    public RocketEntity(World worldIn, BlockPos pos) {
        this(worldIn, (float) pos.getX() + 0.5F, pos.getY(), (float) pos.getZ() + 0.5F);
    }

    @Override
    protected void entityInit() {
        this.guidanceComputer = new GuidanceComputer();
        this.guidanceComputer.init();

        this.dataManager.register(LAUNCHED, false);
        this.dataManager.register(COUNTDOWN_STARTED, false);
        this.dataManager.register(START_POS, 0.F);
        this.dataManager.register(AGE, 0);
        this.dataManager.register(LAUNCH_TIME, 0);
        this.dataManager.register(FLIGHT_TIME, 0);



    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.setLaunched(compound.getBoolean("Launched"));
        this.setCountdownStarted(compound.getBoolean("CountdownStarted"));
        this.setAge(compound.getInteger("Age"));
        this.setLaunchTime(compound.getInteger("LaunchTime"));
        this.setFlightTime(compound.getInteger("FlightTime"));
        this.setStartPos(compound.getFloat("StartPos"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setBoolean("Launched", this.isLaunched());
        compound.setBoolean("CountdownStarted", this.isCountdownStarted());
        compound.setInteger("Age", this.getAge());
        compound.setInteger("LaunchTime", this.getLaunchTime());
        compound.setInteger("FlightTime", this.getFlightTime());
        compound.setFloat("StartPos", this.getStartPos());
    }




    public void Launch(){
        this.setLaunched(true);
        this.isAirBorne = true;
    }



    @Override
    public void onUpdate() {
        this.guidanceComputer.onUpdate();

        if(this.firstUpdate) {
            this.setCountdownStarted(true);
            this.setStartPos((float)this.posY);
            this.setLaunchTime(this.getAge() + 200);
            this.playRocketSound();
        }

        if(!world.isRemote) {
            if(countdownTimer % 20 == 0) {
                GCSBLog.LOGGER.info("gaming " + countdownTimer / 20);


            }

        }

        super.onUpdate();

        if (this.isCountdownStarted() && !isLaunched() && this.getAge() >= this.getLaunchTime()) {
            this.Launch();
        }


        this.setRotation(0.0F, 90.0F);

        if(isLaunched()) {
            int flightTime = getFlightTime();
            float startPos = this.getStartPos();
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY = jerk * Math.pow(getFlightTime(), 2) / 2;
            this.setPosition(this.posX, startPos + jerk * Math.pow(flightTime, 3) / 6, this.posZ);
            this.setFlightTime(flightTime + 1);
        }

        this.setAge(getAge() + 1);
        this.countdownTimer += 1;
    }

    public void explode() {
        this.world.newExplosion(this, this.posX, this.posY, this.posZ, 24, true, true);
        this.setDead();
    }

    public boolean isLaunched(){
        return this.dataManager.get(LAUNCHED);
    }

    public void setLaunched(boolean launched){
        this.dataManager.set(LAUNCHED, launched);
    }

    public boolean isCountdownStarted(){
        return this.dataManager.get(COUNTDOWN_STARTED);
    }

    public void setCountdownStarted(boolean countdownStarted){
        this.dataManager.set(COUNTDOWN_STARTED, countdownStarted);
    }

    public int getAge(){
        return this.dataManager.get(AGE);
    }

    public void setAge(Integer age){
        this.dataManager.set(AGE, age);
    }

    public int getFlightTime(){
        return this.dataManager.get(FLIGHT_TIME);
    }

    public void setFlightTime(Integer flightTime){
        this.dataManager.set(FLIGHT_TIME, flightTime);
    }

    public int getLaunchTime(){
        return this.dataManager.get(LAUNCH_TIME);
    }

    public void setLaunchTime(Integer launchTime){
        this.dataManager.set(LAUNCH_TIME, launchTime);
    }

    public float getStartPos(){
        return this.dataManager.get(START_POS);
    }

    public void setStartPos(Float startPos){
        this.dataManager.set(START_POS, startPos);
    }

    @SideOnly(Side.CLIENT)
    public void playRocketSound() {
        if(this.world.isRemote) {
            this.soundRocket = new MovingSoundRocket(this);
            Minecraft.getMinecraft().getSoundHandler().playSound(this.soundRocket);
        }
    }

}
