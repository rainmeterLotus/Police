package com.police.momo.bean;

import java.io.Serializable;

/**
 * Created by rain on 2015/11/6.
 */
public class BasicChunk implements Serializable {
    private String chunkKey;
    private String chunkValue;
    private int ratio;

    public BasicChunk(String chunkKey, String chunkValue, int ratio) {
        this.chunkKey = chunkKey;
        this.chunkValue = chunkValue;
        this.ratio = ratio;
    }

    public String getChunkKey() {
        return chunkKey;
    }

    public void setChunkKey(String chunkKey) {
        this.chunkKey = chunkKey;
    }

    public String getChunkValue() {
        return chunkValue;
    }

    public void setChunkValue(String chunkValue) {
        this.chunkValue = chunkValue;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
