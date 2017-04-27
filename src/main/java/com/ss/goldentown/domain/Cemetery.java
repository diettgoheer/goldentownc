package com.ss.goldentown.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Cemetery.
 */
@Entity
@Table(name = "cemetery")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cemetery")
public class Cemetery implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "person")
    private String person;

    @Column(name = "age")
    private Double age;

    @Column(name = "generation")
    private Double generation;

    @Column(name = "value")
    private Double value;

    @Column(name = "last_value")
    private Double lastValue;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @Column(name = "deathday")
    private ZonedDateTime deathday;

    @Column(name = "is_dead")
    private Boolean isDead;

    @ManyToOne
    private Cemetery father;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public Cemetery person(String person) {
        this.person = person;
        return this;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Double getAge() {
        return age;
    }

    public Cemetery age(Double age) {
        this.age = age;
        return this;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getGeneration() {
        return generation;
    }

    public Cemetery generation(Double generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(Double generation) {
        this.generation = generation;
    }

    public Double getValue() {
        return value;
    }

    public Cemetery value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getLastValue() {
        return lastValue;
    }

    public Cemetery lastValue(Double lastValue) {
        this.lastValue = lastValue;
        return this;
    }

    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Cemetery birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public ZonedDateTime getDeathday() {
        return deathday;
    }

    public Cemetery deathday(ZonedDateTime deathday) {
        this.deathday = deathday;
        return this;
    }

    public void setDeathday(ZonedDateTime deathday) {
        this.deathday = deathday;
    }

    public Boolean isIsDead() {
        return isDead;
    }

    public Cemetery isDead(Boolean isDead) {
        this.isDead = isDead;
        return this;
    }

    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    public Cemetery getFather() {
        return father;
    }

    public Cemetery father(Cemetery cemetery) {
        this.father = cemetery;
        return this;
    }

    public void setFather(Cemetery cemetery) {
        this.father = cemetery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cemetery cemetery = (Cemetery) o;
        if (cemetery.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, cemetery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Cemetery{" +
            "id=" + id +
            ", person='" + person + "'" +
            ", age='" + age + "'" +
            ", generation='" + generation + "'" +
            ", value='" + value + "'" +
            ", lastValue='" + lastValue + "'" +
            ", birthday='" + birthday + "'" +
            ", deathday='" + deathday + "'" +
            ", isDead='" + isDead + "'" +
            '}';
    }
}
