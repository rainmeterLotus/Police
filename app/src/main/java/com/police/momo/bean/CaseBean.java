package com.police.momo.bean;

/**
 * 作者： momo on 2015/11/12.
 * 邮箱：wangzhonglimomo@163.com
 */
public class CaseBean {
    private String name;
    private String sourcePpath;//原地址
    private String targetpath;//目标地址

    public CaseBean() {
    }

    public CaseBean(String name, String sourcePpath, String targetpath) {
        this.name = name;
        this.sourcePpath = sourcePpath;
        this.targetpath = targetpath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSourcePpath() {
        return sourcePpath;
    }

    public void setSourcePpath(String sourcePpath) {
        this.sourcePpath = sourcePpath;
    }

    public String getTargetpath() {
        return targetpath;
    }

    public void setTargetpath(String targetpath) {
        this.targetpath = targetpath;
    }
}
