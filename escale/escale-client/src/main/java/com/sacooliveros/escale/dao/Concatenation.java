package com.sacooliveros.escale.dao;

/**
 * Created by rcastillejo on 02/06/2016.
 */
public final class Concatenation {
    private int totalSize;
    private int blockSize;
    private int blockCount;
    private int currentBlockSize;
    private int currentBlockCount;

    public Concatenation(int totalSize, int blockSize) {
        this.totalSize = totalSize;
        this.blockSize = blockSize;
        validate();
        calcutate();
    }

    private void validate() {
        if (totalSize <= 0) {
            throw new RuntimeException("Total Size [" + totalSize + "] should be greater than zero");
        }
        if (blockSize <= 0) {
            throw new RuntimeException("Block Size [" + blockSize + "] should be greater than zero");
        }
        if (blockSize > totalSize) {
            throw new RuntimeException("Block Size [" + blockSize + "] should be less or equals than Total Size [" + totalSize + "]");
        }
    }

    private int calcutate() {
        blockCount = totalSize / blockSize;
        if (totalSize % blockSize > 0) {
            blockCount++;
        }
        return blockCount;
    }


    public void concatenate() {
        currentBlockSize += blockSize;
        currentBlockCount++;
    }

    public boolean hasMore() {
        return currentBlockCount < blockCount;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getBlockSize() {
        return blockSize;
    }

    public int getCurrentBlockSize() {
        return currentBlockSize;
    }

    public int getBlockCount() {
        return blockCount;
    }

    public int getCurrentBlockCount() {
        return currentBlockCount;
    }
}
