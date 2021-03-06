/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sacooliveros.escale.utils;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Ricardo
 */
public class PaginationTest {

    private static final Logger log = LoggerFactory.getLogger(PaginationTest.class);



    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCalculate10x10(){
        int totalSize = 10;
        int blockSize = 10;
        Pagination pagination = new Pagination(totalSize, blockSize);
        assertThat(pagination.getBlockCount(), Is.is(1));
    }

    @Test
    public void testCalculate10x5(){
        int totalSize = 10;
        int blockSize = 5;
        Pagination pagination = new Pagination(totalSize, blockSize);
        assertThat(pagination.getBlockCount(), Is.is(2));
    }

    @Test
    public void testCalculate10x3(){
        int totalSize = 10;
        int blockSize = 3;
        Pagination pagination = new Pagination(totalSize, blockSize);
        assertThat(pagination.getBlockCount(), Is.is(4));
    }

    @Test
    public void testHasMore(){
        int totalSize = 10;
        int blockSize = 5;
        Pagination pagination = new Pagination(totalSize, blockSize);

        assertThat(pagination.hasMore(), Is.is(Boolean.TRUE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(0));
        assertThat(pagination.getCurrentBlockSize(), Is.is(0));
    }

    @Test
    public void testHasMoreFirstConcatenate(){
        int totalSize = 10;
        int blockSize = 5;
        Pagination pagination = new Pagination(totalSize, blockSize);

        pagination.nextBlock();

        assertThat(pagination.hasMore(), Is.is(Boolean.TRUE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(1));
        assertThat(pagination.getCurrentBlockSize(), Is.is(5));
    }

    @Test
    public void testHasMoreSecondConcatenate(){
        int totalSize = 10;
        int blockSize = 5;
        Pagination pagination = new Pagination(totalSize, blockSize);

        pagination.nextBlock();
        pagination.nextBlock();

        assertThat(pagination.hasMore(), Is.is(Boolean.FALSE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(2));
        assertThat(pagination.getCurrentBlockSize(), Is.is(10));
    }

    @Test
    public void testHasMoreThirdConcatenate(){
        int totalSize = 10;
        int blockSize = 5;
        Pagination pagination = new Pagination(totalSize, blockSize);

        pagination.nextBlock();
        pagination.nextBlock();
        pagination.nextBlock();

        assertThat(pagination.hasMore(), Is.is(Boolean.FALSE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(3));
        assertThat(pagination.getCurrentBlockSize(), Is.is(15));
    }

    @Test
    public void testHasMore10x10(){
        int totalSize = 10;
        int blockSize = 10;
        Pagination pagination = new Pagination(totalSize, blockSize);

        assertThat(pagination.hasMore(), Is.is(Boolean.TRUE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(0));
        assertThat(pagination.getCurrentBlockSize(), Is.is(0));
    }

    @Test
    public void testHasMore10x10FirstConcatenate(){
        int totalSize = 10;
        int blockSize = 10;
        Pagination pagination = new Pagination(totalSize, blockSize);

        pagination.nextBlock();

        assertThat(pagination.hasMore(), Is.is(Boolean.FALSE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(1));
        assertThat(pagination.getCurrentBlockSize(), Is.is(10));
    }

    @Test
    public void testHasMore10x3(){
        int totalSize = 10;
        int blockSize = 3;
        Pagination pagination = new Pagination(totalSize, blockSize);

        assertThat(pagination.hasMore(), Is.is(Boolean.TRUE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(0));
        assertThat(pagination.getCurrentBlockSize(), Is.is(0));
    }

    @Test
    public void testHasMore10x3FirstConcatenate(){
        int totalSize = 10;
        int blockSize = 3;
        Pagination pagination = new Pagination(totalSize, blockSize);

        pagination.nextBlock();

        assertThat(pagination.hasMore(), Is.is(Boolean.TRUE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(1));
        assertThat(pagination.getCurrentBlockSize(), Is.is(3));
    }

    @Test
    public void testHasMore10x3FourthConcatenate(){
        int totalSize = 10;
        int blockSize = 3;
        Pagination pagination = new Pagination(totalSize, blockSize);

        pagination.nextBlock();
        pagination.nextBlock();
        pagination.nextBlock();
        pagination.nextBlock();

        assertThat(pagination.hasMore(), Is.is(Boolean.FALSE));
        assertThat(pagination.getCurrentBlockCount(), Is.is(4));
        assertThat(pagination.getCurrentBlockSize(), Is.is(12));
    }
}
