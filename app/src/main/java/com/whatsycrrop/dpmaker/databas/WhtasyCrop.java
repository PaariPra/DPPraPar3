package com.whatsycrrop.dpmaker.databas;

public class WhtasyCrop {
    int status;
    int count;
   int  nat;

    public WhtasyCrop(int status, int count, int nat) {
        this.status = status;
        this.count = count;
        this.nat = nat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNat() {
        return nat;
    }

    public void setNat(int nat) {
        this.nat = nat;
    }
}
