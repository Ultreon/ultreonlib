package dev.ultreon.mods.lib.common.vector;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public class Vec3i implements Externalizable, Cloneable {
    public int x, y, z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i() {

    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return this.z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public static Vec3i mul(Vec3i a, Vec3i b) {
        return new Vec3i(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public static Vec3i div(Vec3i a, Vec3i b) {
        return new Vec3i(a.x / b.x, a.y / b.y, a.z / b.z);
    }

    public static Vec3i add(Vec3i a, Vec3i b) {
        return new Vec3i(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public static Vec3i sub(Vec3i a, Vec3i b) {
        return new Vec3i(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static int dot(Vec3i a, Vec3i b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static Vec3d pow(Vec3i a, Vec3i b) {
        return new Vec3d(Math.pow(a.x, b.x), Math.pow(a.y, b.y), Math.pow(a.z, b.z));
    }

    public int dot(Vec3i vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    public int dot(int x, int y, int z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public int dot(int v) {
        return this.x * v + this.y * v + this.z * v;
    }

    public double dst(Vec3i vec) {
        int a = vec.x - this.x;
        int b = vec.y - this.y;
        int c = vec.z - this.z;
        return Math.sqrt(a * a + b * b + c * c);
    }

    public double dst(int x, int y, int z) {
        int a = x - this.x;
        int b = y - this.y;
        int c = z - this.z;
        return Math.sqrt(a * a + b * b + c * c);
    }

    public Vec3i set(Vec3i vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return this;
    }

    public Vec3i set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3i set(int v) {
        this.x = v;
        this.y = v;
        this.z = v;
        return this;
    }

    public Vec3i add(Vec3i vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;
        return this;
    }

    public Vec3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3i add(int v) {
        this.x += v;
        this.y += v;
        this.z += v;
        return this;
    }

    public Vec3i sub(Vec3i vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;
        return this;
    }

    public Vec3i sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3i sub(int v) {
        this.x -= v;
        this.y -= v;
        this.z -= v;
        return this;
    }

    public Vec3i mul(Vec3i vec) {
        this.x *= vec.x;
        this.y *= vec.y;
        this.z *= vec.z;
        return this;
    }

    public Vec3i mul(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vec3i mul(int v) {
        this.x *= v;
        this.y *= v;
        this.z *= v;
        return this;
    }

    public Vec3i div(Vec3i vec) {
        this.x /= vec.x;
        this.y /= vec.y;
        this.z /= vec.z;
        return this;
    }

    public Vec3i div(int x, int y, int z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vec3i div(int v) {
        this.x /= v;
        this.y /= v;
        this.z /= v;
        return this;
    }

    public Vec3i mod(Vec3i vec) {
        this.x %= vec.x;
        this.y %= vec.y;
        this.z %= vec.z;
        return this;
    }

    public Vec3i mod(int x, int y, int z) {
        this.x %= x;
        this.y %= y;
        this.z %= z;
        return this;
    }

    public Vec3i mod(int v) {
        this.x %= v;
        this.y %= v;
        this.z %= v;
        return this;
    }

    public Vec3i pow(Vec3i vec) {
        this.x = (int) Math.pow(this.x, vec.x);
        this.y = (int) Math.pow(this.y, vec.y);
        this.z = (int) Math.pow(this.z, vec.z);
        return this;
    }

    public Vec3i pow(int x, int y, int z) {
        this.x = (int) Math.pow(this.x, x);
        this.y = (int) Math.pow(this.y, y);
        this.z = (int) Math.pow(this.z, z);
        return this;
    }

    public Vec3i pow(int v) {
        this.x = (int) Math.pow(this.x, v);
        this.y = (int) Math.pow(this.y, v);
        this.z = (int) Math.pow(this.z, v);
        return this;
    }

    public Vec3i neg() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
        return this;
    }

    public Vec3i inc() {
        this.x++;
        this.y++;
        this.z++;
        return this;
    }

    public Vec3i dec() {
        this.x--;
        this.y--;
        this.z--;
        return this;
    }

    public Vec3i abs() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        this.z = Math.abs(this.z);
        return this;
    }

    public Vec3i cpy() {
        return new Vec3i(this.x, this.y, this.z);
    }

    public Vec3d d() {
        return new Vec3d(this.x, this.y, this.z);
    }

    public Vec3f f() {
        return new Vec3f(this.x, this.y, this.z);
    }

    public Vec3i i() {
        return new Vec3i(this.x, this.y, this.z);
    }

    @Override
    public Vec3i clone() {
        try {
            Vec3i clone = (Vec3i) super.clone();

            clone.x = this.x;
            clone.y = this.y;
            clone.z = this.z;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Vec3i vector4i = (Vec3i) o;
        return this.getX() == vector4i.getX() && this.getY() == vector4i.getY() && this.getZ() == vector4i.getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getX(), this.getY(), this.getZ());
    }

    @Override
    public String toString() {
        return String.format("%d, %d, %d", this.x, this.y, this.z);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.x);
        out.writeInt(this.y);
        out.writeInt(this.z);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        this.x = in.readInt();
        this.y = in.readInt();
        this.z = in.readInt();
    }
}
