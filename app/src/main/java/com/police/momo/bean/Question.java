package com.police.momo.bean;

import java.io.Serializable;

/**
 * 作者： momo on 2015/10/11.
 * 邮箱：wangzhonglimomo@163.com
 */
public class Question implements Serializable{
    public String type;//问题类型
    public int num;//问题编号
    public String content;//问题内容
    public String answer;//问题答案
    public String updateTime;//问题更新时间
    public int type_cat;//问题类型id
    public String defaultAnswer;//默认答案
    public String parentName;//父类型名字
    public String parentTypeId;//父类型名字ID

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getType_cat() {
        return type_cat;
    }

    public void setType_cat(int type_cat) {
        this.type_cat = type_cat;
    }

    public String getDefaultAnswer() {
        return defaultAnswer;
    }

    public void setDefaultAnswer(String defaultAnswer) {
        this.defaultAnswer = defaultAnswer;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentTypeId() {
        return parentTypeId;
    }

    public void setParentTypeId(String parentTypeId) {
        this.parentTypeId = parentTypeId;
    }
}
