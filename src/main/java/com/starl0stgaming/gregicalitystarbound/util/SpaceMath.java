package com.starl0stgaming.gregicalitystarbound.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3i;

public class SpaceMath {
    public static int dotI(Vec3i a, Vec3i b) {
        return a.getX()*b.getX()+a.getY()*b.getY()+a.getZ()*b.getZ();
    }
    public static Vec3i multV3I(Vec3i a, double times) {
        return new Vec3i(Math.round(a.getX()*times), Math.round(a.getY()*times), Math.round(a.getZ()*times));
    }
    public static Vec3i addV3I(Vec3i... a) {
        int X = 0;
        int Y = 0;
        int Z = 0;
        for (Vec3i vec: a) {
            X += vec.getX();
            Y += vec.getY();
            Z += vec.getZ();
        }
        return new Vec3i(X, Y, Z);
    }
    public static Pair<Vec3i,Vec3i> getBounds(AxisAlignedBB aabb) {
        return new Pair<Vec3i,Vec3i>(new Vec3i(aabb.minX,aabb.minY,aabb.minZ),
                new Vec3i(aabb.maxX,aabb.maxY,aabb.maxZ));
    }
}
