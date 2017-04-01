package com.ss.goldentown.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Board entity.
 */
public class BoardDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private ZonedDateTime productionDate;

    private ZonedDateTime purchaseTime;

    private String productionPlace;

    private String purchasePlace;

    private String provider;

    private String process;

    private ZonedDateTime time;

    private String place;

    private String surrounding;

    private String teaPerson;

    private String drinkingPerson;

    private String mood;

    private String teaSet;

    private String teaPic;

    private String packagePic;

    private String storageMethod;

    private String shape;

    private String aroma;

    private String hotAroma;

    private String teaWash;

    private String boilingAroma;

    private String cupAroma;

    private String firstBrewPic;

    private String firstBrewingSoup;

    private String firstBrewAroma;

    private String firstBrewMood;

    private String secondBrewPic;

    private String secondBrewSoup;

    private String secondBrewAroma;

    private String secondBrewMood;

    private String thirdBrewPic;

    private String thirdBrewSoup;

    private String thirdBrewAroma;

    private String thirdBrewMood;

    private String brewTimes;

    private String comment;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ZonedDateTime getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(ZonedDateTime productionDate) {
        this.productionDate = productionDate;
    }
    public ZonedDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(ZonedDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }
    public String getProductionPlace() {
        return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
    }
    public String getPurchasePlace() {
        return purchasePlace;
    }

    public void setPurchasePlace(String purchasePlace) {
        this.purchasePlace = purchasePlace;
    }
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public String getSurrounding() {
        return surrounding;
    }

    public void setSurrounding(String surrounding) {
        this.surrounding = surrounding;
    }
    public String getTeaPerson() {
        return teaPerson;
    }

    public void setTeaPerson(String teaPerson) {
        this.teaPerson = teaPerson;
    }
    public String getDrinkingPerson() {
        return drinkingPerson;
    }

    public void setDrinkingPerson(String drinkingPerson) {
        this.drinkingPerson = drinkingPerson;
    }
    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }
    public String getTeaSet() {
        return teaSet;
    }

    public void setTeaSet(String teaSet) {
        this.teaSet = teaSet;
    }
    public String getTeaPic() {
        return teaPic;
    }

    public void setTeaPic(String teaPic) {
        this.teaPic = teaPic;
    }
    public String getPackagePic() {
        return packagePic;
    }

    public void setPackagePic(String packagePic) {
        this.packagePic = packagePic;
    }
    public String getStorageMethod() {
        return storageMethod;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }
    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }
    public String getHotAroma() {
        return hotAroma;
    }

    public void setHotAroma(String hotAroma) {
        this.hotAroma = hotAroma;
    }
    public String getTeaWash() {
        return teaWash;
    }

    public void setTeaWash(String teaWash) {
        this.teaWash = teaWash;
    }
    public String getBoilingAroma() {
        return boilingAroma;
    }

    public void setBoilingAroma(String boilingAroma) {
        this.boilingAroma = boilingAroma;
    }
    public String getCupAroma() {
        return cupAroma;
    }

    public void setCupAroma(String cupAroma) {
        this.cupAroma = cupAroma;
    }
    public String getFirstBrewPic() {
        return firstBrewPic;
    }

    public void setFirstBrewPic(String firstBrewPic) {
        this.firstBrewPic = firstBrewPic;
    }
    public String getFirstBrewingSoup() {
        return firstBrewingSoup;
    }

    public void setFirstBrewingSoup(String firstBrewingSoup) {
        this.firstBrewingSoup = firstBrewingSoup;
    }
    public String getFirstBrewAroma() {
        return firstBrewAroma;
    }

    public void setFirstBrewAroma(String firstBrewAroma) {
        this.firstBrewAroma = firstBrewAroma;
    }
    public String getFirstBrewMood() {
        return firstBrewMood;
    }

    public void setFirstBrewMood(String firstBrewMood) {
        this.firstBrewMood = firstBrewMood;
    }
    public String getSecondBrewPic() {
        return secondBrewPic;
    }

    public void setSecondBrewPic(String secondBrewPic) {
        this.secondBrewPic = secondBrewPic;
    }
    public String getSecondBrewSoup() {
        return secondBrewSoup;
    }

    public void setSecondBrewSoup(String secondBrewSoup) {
        this.secondBrewSoup = secondBrewSoup;
    }
    public String getSecondBrewAroma() {
        return secondBrewAroma;
    }

    public void setSecondBrewAroma(String secondBrewAroma) {
        this.secondBrewAroma = secondBrewAroma;
    }
    public String getSecondBrewMood() {
        return secondBrewMood;
    }

    public void setSecondBrewMood(String secondBrewMood) {
        this.secondBrewMood = secondBrewMood;
    }
    public String getThirdBrewPic() {
        return thirdBrewPic;
    }

    public void setThirdBrewPic(String thirdBrewPic) {
        this.thirdBrewPic = thirdBrewPic;
    }
    public String getThirdBrewSoup() {
        return thirdBrewSoup;
    }

    public void setThirdBrewSoup(String thirdBrewSoup) {
        this.thirdBrewSoup = thirdBrewSoup;
    }
    public String getThirdBrewAroma() {
        return thirdBrewAroma;
    }

    public void setThirdBrewAroma(String thirdBrewAroma) {
        this.thirdBrewAroma = thirdBrewAroma;
    }
    public String getThirdBrewMood() {
        return thirdBrewMood;
    }

    public void setThirdBrewMood(String thirdBrewMood) {
        this.thirdBrewMood = thirdBrewMood;
    }
    public String getBrewTimes() {
        return brewTimes;
    }

    public void setBrewTimes(String brewTimes) {
        this.brewTimes = brewTimes;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoardDTO boardDTO = (BoardDTO) o;

        if ( ! Objects.equals(id, boardDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BoardDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", productionDate='" + productionDate + "'" +
            ", purchaseTime='" + purchaseTime + "'" +
            ", productionPlace='" + productionPlace + "'" +
            ", purchasePlace='" + purchasePlace + "'" +
            ", provider='" + provider + "'" +
            ", process='" + process + "'" +
            ", time='" + time + "'" +
            ", place='" + place + "'" +
            ", surrounding='" + surrounding + "'" +
            ", teaPerson='" + teaPerson + "'" +
            ", drinkingPerson='" + drinkingPerson + "'" +
            ", mood='" + mood + "'" +
            ", teaSet='" + teaSet + "'" +
            ", teaPic='" + teaPic + "'" +
            ", packagePic='" + packagePic + "'" +
            ", storageMethod='" + storageMethod + "'" +
            ", shape='" + shape + "'" +
            ", aroma='" + aroma + "'" +
            ", hotAroma='" + hotAroma + "'" +
            ", teaWash='" + teaWash + "'" +
            ", boilingAroma='" + boilingAroma + "'" +
            ", cupAroma='" + cupAroma + "'" +
            ", firstBrewPic='" + firstBrewPic + "'" +
            ", firstBrewingSoup='" + firstBrewingSoup + "'" +
            ", firstBrewAroma='" + firstBrewAroma + "'" +
            ", firstBrewMood='" + firstBrewMood + "'" +
            ", secondBrewPic='" + secondBrewPic + "'" +
            ", secondBrewSoup='" + secondBrewSoup + "'" +
            ", secondBrewAroma='" + secondBrewAroma + "'" +
            ", secondBrewMood='" + secondBrewMood + "'" +
            ", thirdBrewPic='" + thirdBrewPic + "'" +
            ", thirdBrewSoup='" + thirdBrewSoup + "'" +
            ", thirdBrewAroma='" + thirdBrewAroma + "'" +
            ", thirdBrewMood='" + thirdBrewMood + "'" +
            ", brewTimes='" + brewTimes + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
