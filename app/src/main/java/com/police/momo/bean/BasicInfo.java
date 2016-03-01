package com.police.momo.bean;

import java.io.Serializable;

/**
 * 作者： momo on 2015/9/27.
 * 邮箱：wangzhonglimomo@163.com
 */
public class BasicInfo implements Serializable {
    private int count = 0;
    private String category = "";
    private String suspicion = "";
    private String type = "";
    private String startTime = "";
    private String endTime = "";
    private String addrass = "";
    private String idCard = "";
    private String name = "";
    private String oldName = "";
    private String sex = "";
    private String birthday = "";
    private String education = "";
    private int age = 0;
    private String nation = "";
    private String politics = "";
    private String censusAdd = "";
    private String nowAdd = "";
    private String companyAdd = "";
    private String tel = "";

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSuspicion() {
        return suspicion;
    }

    public void setSuspicion(String suspicion) {
        this.suspicion = suspicion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddrass() {
        return addrass;
    }

    public void setAddrass(String addrass) {
        this.addrass = addrass;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPolitics() {
        return politics;
    }

    public void setPolitics(String politics) {
        this.politics = politics;
    }

    public String getCensusAdd() {
        return censusAdd;
    }

    public void setCensusAdd(String censusAdd) {
        this.censusAdd = censusAdd;
    }

    public String getNowAdd() {
        return nowAdd;
    }

    public void setNowAdd(String nowAdd) {
        this.nowAdd = nowAdd;
    }

    public String getCompanyAdd() {
        return companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
