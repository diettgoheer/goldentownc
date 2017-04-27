package com.ss.goldentown.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A World.
 */
@Entity
@Table(name = "world")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "world")
public class World implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "time")
    private ZonedDateTime time;

    @Column(name = "person_count")
    private Double personCount;

    @Column(name = "birth_count")
    private Double birthCount;

    @Column(name = "death_count")
    private Double deathCount;

    @Column(name = "max_generation")
    private Double maxGeneration;

    @Column(name = "average_value")
    private Double averageValue;

    @Column(name = "average_age")
    private Double averageAge;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "max_age")
    private Double maxAge;

    @Column(name = "world_value")
    private Double worldValue;

    @Column(name = "world_age")
    private Double worldAge;

    @Column(name = "mid_age")
    private Double midAge;

    @Column(name = "base_value")
    private Double baseValue;

    @Column(name = "grow_rate")
    private Double growRate;

    @Column(name = "legacy_rate")
    private Double legacyRate;

    @Column(name = "breed_rate")
    private Double breedRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public World time(ZonedDateTime time) {
        this.time = time;
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public Double getPersonCount() {
        return personCount;
    }

    public World personCount(Double personCount) {
        this.personCount = personCount;
        return this;
    }

    public void setPersonCount(Double personCount) {
        this.personCount = personCount;
    }

    public Double getBirthCount() {
        return birthCount;
    }

    public World birthCount(Double birthCount) {
        this.birthCount = birthCount;
        return this;
    }

    public void setBirthCount(Double birthCount) {
        this.birthCount = birthCount;
    }

    public Double getDeathCount() {
        return deathCount;
    }

    public World deathCount(Double deathCount) {
        this.deathCount = deathCount;
        return this;
    }

    public void setDeathCount(Double deathCount) {
        this.deathCount = deathCount;
    }

    public Double getMaxGeneration() {
        return maxGeneration;
    }

    public World maxGeneration(Double maxGeneration) {
        this.maxGeneration = maxGeneration;
        return this;
    }

    public void setMaxGeneration(Double maxGeneration) {
        this.maxGeneration = maxGeneration;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public World averageValue(Double averageValue) {
        this.averageValue = averageValue;
        return this;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Double getAverageAge() {
        return averageAge;
    }

    public World averageAge(Double averageAge) {
        this.averageAge = averageAge;
        return this;
    }

    public void setAverageAge(Double averageAge) {
        this.averageAge = averageAge;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public World maxValue(Double maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Double getMaxAge() {
        return maxAge;
    }

    public World maxAge(Double maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public void setMaxAge(Double maxAge) {
        this.maxAge = maxAge;
    }

    public Double getWorldValue() {
        return worldValue;
    }

    public World worldValue(Double worldValue) {
        this.worldValue = worldValue;
        return this;
    }

    public void setWorldValue(Double worldValue) {
        this.worldValue = worldValue;
    }

    public Double getWorldAge() {
        return worldAge;
    }

    public World worldAge(Double worldAge) {
        this.worldAge = worldAge;
        return this;
    }

    public void setWorldAge(Double worldAge) {
        this.worldAge = worldAge;
    }

    public Double getMidAge() {
        return midAge;
    }

    public World midAge(Double midAge) {
        this.midAge = midAge;
        return this;
    }

    public void setMidAge(Double midAge) {
        this.midAge = midAge;
    }

    public Double getBaseValue() {
        return baseValue;
    }

    public World baseValue(Double baseValue) {
        this.baseValue = baseValue;
        return this;
    }

    public void setBaseValue(Double baseValue) {
        this.baseValue = baseValue;
    }

    public Double getGrowRate() {
        return growRate;
    }

    public World growRate(Double growRate) {
        this.growRate = growRate;
        return this;
    }

    public void setGrowRate(Double growRate) {
        this.growRate = growRate;
    }

    public Double getLegacyRate() {
        return legacyRate;
    }

    public World legacyRate(Double legacyRate) {
        this.legacyRate = legacyRate;
        return this;
    }

    public void setLegacyRate(Double legacyRate) {
        this.legacyRate = legacyRate;
    }

    public Double getBreedRate() {
        return breedRate;
    }

    public World breedRate(Double breedRate) {
        this.breedRate = breedRate;
        return this;
    }

    public void setBreedRate(Double breedRate) {
        this.breedRate = breedRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        World world = (World) o;
        if (world.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, world.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "World{" +
            "id=" + id +
            ", time='" + time + "'" +
            ", personCount='" + personCount + "'" +
            ", birthCount='" + birthCount + "'" +
            ", deathCount='" + deathCount + "'" +
            ", maxGeneration='" + maxGeneration + "'" +
            ", averageValue='" + averageValue + "'" +
            ", averageAge='" + averageAge + "'" +
            ", maxValue='" + maxValue + "'" +
            ", maxAge='" + maxAge + "'" +
            ", worldValue='" + worldValue + "'" +
            ", worldAge='" + worldAge + "'" +
            ", midAge='" + midAge + "'" +
            ", baseValue='" + baseValue + "'" +
            ", growRate='" + growRate + "'" +
            ", legacyRate='" + legacyRate + "'" +
            ", breedRate='" + breedRate + "'" +
            '}';
    }
}
