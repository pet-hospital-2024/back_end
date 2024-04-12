package com.example.pet_hospital.Util;

public class BloomFilter {
    private static final int DEFAULT_SEED=1;
    private static final int DEFAULT_HASH_COUNT = 1;

    private final int[] hashFunctions;
    private final int size;
    private final long[] bits;

    public BloomFilter(int size, int hashCount){
        this.size=size;
        hashFunctions=new int[hashCount];
        for (int i=0;i<hashCount;i++){
            hashFunctions[i]=DEFAULT_SEED+i;
        }
        bits=new long[size/64];
    }

    public void add(String value){
        for (int hash:hashFunctions){
            int index=(hash^value.hashCode())%size;
            bits[index/64]|=(1L<<(index%64));
        }
    }

    public boolean contains(String value) {
        for (int hash : hashFunctions) {
            int index = (hash ^ value.hashCode()) % size;
            if ((bits[index / 64] & (1L << (index % 64))) == 0) {
                return false;
            }
        }
        return true;
    }
}
