package com.ss.goldentown.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Board.
 */
@Entity
@Table(name = "board")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "board")
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "production_date")
    private ZonedDateTime productionDate;

    @Column(name = "purchase_time")
    private ZonedDateTime purchaseTime;

    @Column(name = "production_place")
    private String productionPlace;

    @Column(name = "purchase_place")
    private String purchasePlace;

    @Column(name = "provider")
    private String provider;

    @Column(name = "process")
    private String process;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "place")
    private String place;

    @Column(name = "surrounding")
    private String surrounding;

    @Column(name = "tea_person")
    private String teaPerson;

    @Column(name = "drinking_person")
    private String drinkingPerson;

    @Column(name = "mood")
    private String mood;

    @Column(name = "tea_set")
    private String teaSet;

    @Column(name = "tea_pic")
    private String teaPic;

    @Column(name = "package_pic")
    private String packagePic;

    @Column(name = "storage_method")
    private String storageMethod;

    @Column(name = "shape")
    private String shape;

    @Column(name = "aroma")
    private String aroma;

    @Column(name = "hot_aroma")
    private String hotAroma;

    @Column(name = "tea_wash")
    private String teaWash;

    @Column(name = "boiling_aroma")
    private String boilingAroma;

    @Column(name = "cup_aroma")
    private String cupAroma;

    @Column(name = "first_brew_pic")
    private String firstBrewPic;

    @Column(name = "first_brewing_soup")
    private String firstBrewingSoup;

    @Column(name = "first_brew_aroma")
    private String firstBrewAroma;

    @Column(name = "first_brew_mood")
    private String firstBrewMood;

    @Column(name = "second_brew_pic")
    private String secondBrewPic;

    @Column(name = "second_brew_soup")
    private String secondBrewSoup;

    @Column(name = "second_brew_aroma")
    private String secondBrewAroma;

    @Column(name = "second_brew_mood")
    private String secondBrewMood;

    @Column(name = "third_brew_pic")
    private String thirdBrewPic;

    @Column(name = "third_brew_soup")
    private String thirdBrewSoup;

    @Column(name = "third_brew_aroma")
    private String thirdBrewAroma;

    @Column(name = "third_brew_mood")
    private String thirdBrewMood;

    @Column(name = "brew_times")
    private String brewTimes;

    @Column(name = "comment")
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

    public Board name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Board type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZonedDateTime getProductionDate() {
        return productionDate;
    }

    public Board productionDate(ZonedDateTime productionDate) {
        this.productionDate = productionDate;
        return this;
    }

    public void setProductionDate(ZonedDateTime productionDate) {
        this.productionDate = productionDate;
    }

    public ZonedDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public Board purchaseTime(ZonedDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
        return this;
    }

    public void setPurchaseTime(ZonedDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getProductionPlace() {
        return productionPlace;
    }

    public Board productionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
        return this;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
    }

    public String getPurchasePlace() {
        return purchasePlace;
    }

    public Board purchasePlace(String purchasePlace) {
        this.purchasePlace = purchasePlace;
        return this;
    }

    public void setPurchasePlace(String purchasePlace) {
        this.purchasePlace = purchasePlace;
    }

    public String getProvider() {
        return provider;
    }

    public Board provider(String provider) {
        this.provider = provider;
        return this;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProcess() {
        return process;
    }

    public Board process(String process) {
        this.process = process;
        return this;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public Board time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public Board place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSurrounding() {
        return surrounding;
    }

    public Board surrounding(String surrounding) {
        this.surrounding = surrounding;
        return this;
    }

    public void setSurrounding(String surrounding) {
        this.surrounding = surrounding;
    }

    public String getTeaPerson() {
        return teaPerson;
    }

    public Board teaPerson(String teaPerson) {
        this.teaPerson = teaPerson;
        return this;
    }

    public void setTeaPerson(String teaPerson) {
        this.teaPerson = teaPerson;
    }

    public String getDrinkingPerson() {
        return drinkingPerson;
    }

    public Board drinkingPerson(String drinkingPerson) {
        this.drinkingPerson = drinkingPerson;
        return this;
    }

    public void setDrinkingPerson(String drinkingPerson) {
        this.drinkingPerson = drinkingPerson;
    }

    public String getMood() {
        return mood;
    }

    public Board mood(String mood) {
        this.mood = mood;
        return this;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getTeaSet() {
        return teaSet;
    }

    public Board teaSet(String teaSet) {
        this.teaSet = teaSet;
        return this;
    }

    public void setTeaSet(String teaSet) {
        this.teaSet = teaSet;
    }

    public String getTeaPic() {
        return teaPic;
    }

    public Board teaPic(String teaPic) {
        this.teaPic = teaPic;
        return this;
    }

    public void setTeaPic(String teaPic) {
        this.teaPic = teaPic;
    }

    public String getPackagePic() {
        return packagePic;
    }

    public Board packagePic(String packagePic) {
        this.packagePic = packagePic;
        return this;
    }

    public void setPackagePic(String packagePic) {
        this.packagePic = packagePic;
    }

    public String getStorageMethod() {
        return storageMethod;
    }

    public Board storageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
        return this;
    }

    public void setStorageMethod(String storageMethod) {
        this.storageMethod = storageMethod;
    }

    public String getShape() {
        return shape;
    }

    public Board shape(String shape) {
        this.shape = shape;
        return this;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getAroma() {
        return aroma;
    }

    public Board aroma(String aroma) {
        this.aroma = aroma;
        return this;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public String getHotAroma() {
        return hotAroma;
    }

    public Board hotAroma(String hotAroma) {
        this.hotAroma = hotAroma;
        return this;
    }

    public void setHotAroma(String hotAroma) {
        this.hotAroma = hotAroma;
    }

    public String getTeaWash() {
        return teaWash;
    }

    public Board teaWash(String teaWash) {
        this.teaWash = teaWash;
        return this;
    }

    public void setTeaWash(String teaWash) {
        this.teaWash = teaWash;
    }

    public String getBoilingAroma() {
        return boilingAroma;
    }

    public Board boilingAroma(String boilingAroma) {
        this.boilingAroma = boilingAroma;
        return this;
    }

    public void setBoilingAroma(String boilingAroma) {
        this.boilingAroma = boilingAroma;
    }

    public String getCupAroma() {
        return cupAroma;
    }

    public Board cupAroma(String cupAroma) {
        this.cupAroma = cupAroma;
        return this;
    }

    public void setCupAroma(String cupAroma) {
        this.cupAroma = cupAroma;
    }

    public String getFirstBrewPic() {
        return firstBrewPic;
    }

    public Board firstBrewPic(String firstBrewPic) {
        this.firstBrewPic = firstBrewPic;
        return this;
    }

    public void setFirstBrewPic(String firstBrewPic) {
        this.firstBrewPic = firstBrewPic;
    }

    public String getFirstBrewingSoup() {
        return firstBrewingSoup;
    }

    public Board firstBrewingSoup(String firstBrewingSoup) {
        this.firstBrewingSoup = firstBrewingSoup;
        return this;
    }

    public void setFirstBrewingSoup(String firstBrewingSoup) {
        this.firstBrewingSoup = firstBrewingSoup;
    }

    public String getFirstBrewAroma() {
        return firstBrewAroma;
    }

    public Board firstBrewAroma(String firstBrewAroma) {
        this.firstBrewAroma = firstBrewAroma;
        return this;
    }

    public void setFirstBrewAroma(String firstBrewAroma) {
        this.firstBrewAroma = firstBrewAroma;
    }

    public String getFirstBrewMood() {
        return firstBrewMood;
    }

    public Board firstBrewMood(String firstBrewMood) {
        this.firstBrewMood = firstBrewMood;
        return this;
    }

    public void setFirstBrewMood(String firstBrewMood) {
        this.firstBrewMood = firstBrewMood;
    }

    public String getSecondBrewPic() {
        return secondBrewPic;
    }

    public Board secondBrewPic(String secondBrewPic) {
        this.secondBrewPic = secondBrewPic;
        return this;
    }

    public void setSecondBrewPic(String secondBrewPic) {
        this.secondBrewPic = secondBrewPic;
    }

    public String getSecondBrewSoup() {
        return secondBrewSoup;
    }

    public Board secondBrewSoup(String secondBrewSoup) {
        this.secondBrewSoup = secondBrewSoup;
        return this;
    }

    public void setSecondBrewSoup(String secondBrewSoup) {
        this.secondBrewSoup = secondBrewSoup;
    }

    public String getSecondBrewAroma() {
        return secondBrewAroma;
    }

    public Board secondBrewAroma(String secondBrewAroma) {
        this.secondBrewAroma = secondBrewAroma;
        return this;
    }

    public void setSecondBrewAroma(String secondBrewAroma) {
        this.secondBrewAroma = secondBrewAroma;
    }

    public String getSecondBrewMood() {
        return secondBrewMood;
    }

    public Board secondBrewMood(String secondBrewMood) {
        this.secondBrewMood = secondBrewMood;
        return this;
    }

    public void setSecondBrewMood(String secondBrewMood) {
        this.secondBrewMood = secondBrewMood;
    }

    public String getThirdBrewPic() {
        return thirdBrewPic;
    }

    public Board thirdBrewPic(String thirdBrewPic) {
        this.thirdBrewPic = thirdBrewPic;
        return this;
    }

    public void setThirdBrewPic(String thirdBrewPic) {
        this.thirdBrewPic = thirdBrewPic;
    }

    public String getThirdBrewSoup() {
        return thirdBrewSoup;
    }

    public Board thirdBrewSoup(String thirdBrewSoup) {
        this.thirdBrewSoup = thirdBrewSoup;
        return this;
    }

    public void setThirdBrewSoup(String thirdBrewSoup) {
        this.thirdBrewSoup = thirdBrewSoup;
    }

    public String getThirdBrewAroma() {
        return thirdBrewAroma;
    }

    public Board thirdBrewAroma(String thirdBrewAroma) {
        this.thirdBrewAroma = thirdBrewAroma;
        return this;
    }

    public void setThirdBrewAroma(String thirdBrewAroma) {
        this.thirdBrewAroma = thirdBrewAroma;
    }

    public String getThirdBrewMood() {
        return thirdBrewMood;
    }

    public Board thirdBrewMood(String thirdBrewMood) {
        this.thirdBrewMood = thirdBrewMood;
        return this;
    }

    public void setThirdBrewMood(String thirdBrewMood) {
        this.thirdBrewMood = thirdBrewMood;
    }

    public String getBrewTimes() {
        return brewTimes;
    }

    public Board brewTimes(String brewTimes) {
        this.brewTimes = brewTimes;
        return this;
    }

    public void setBrewTimes(String brewTimes) {
        this.brewTimes = brewTimes;
    }

    public String getComment() {
        return comment;
    }

    public Board comment(String comment) {
        this.comment = comment;
        return this;
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
        Board board = (Board) o;
        if (board.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Board{" +
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
