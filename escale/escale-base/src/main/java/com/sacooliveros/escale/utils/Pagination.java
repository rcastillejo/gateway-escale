package com.sacooliveros.escale.utils;

/**
 * Created by rcastillejo on 02/06/2016.
 */
public final class Pagination {
    private int totalSize;
    private int blockSize;
    private int blockCount;
    private int currentBlockSize;
    private int currentBlockCount;


    public Pagination(int blockSize) {
        this.blockSize = blockSize;
    }

    public Pagination(int totalSize, int blockSize) {
        this.totalSize = totalSize;
        this.blockSize = blockSize;
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
        validate();
        blockCount = totalSize / blockSize;
        if (totalSize % blockSize > 0) {
            blockCount++;
        }
        return blockCount;
    }


    public void nextBlock() {
        currentBlockSize += blockSize;
        currentBlockCount++;
    }

    public boolean hasMore() {
        return currentBlockCount < blockCount;
    }

    public void setTotalSizeAndCalculate(int totalSize) {
        this.totalSize = totalSize;
        calcutate();
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

    @Override
    public String toString() {
        return "Pagination{" +
                "totalSize=" + totalSize +
                ", blockSize=" + blockSize +
                ", blockCount=" + blockCount +
                ", currentBlockSize=" + currentBlockSize +
                ", currentBlockCount=" + currentBlockCount +
                '}';
    }
}
