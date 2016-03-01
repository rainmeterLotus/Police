package com.police.momo.bean;

import java.io.Serializable;

/**
 * 作者： momo on 2015/10/25.
 * 邮箱：wangzhonglimomo@163.com
 */
public class QuestionParent implements Serializable{
    private String Name;//名字
    private String catId; //ID

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }
}
