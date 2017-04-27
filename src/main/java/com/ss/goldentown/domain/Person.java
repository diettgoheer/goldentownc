package com.ss.goldentown.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "person")
    private String person;

    @Column(name = "surname")
    private String surname;

    @Column(name = "name")
    private String name;

    @Column(name = "father_name")
    private String fatherName;

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
    private Boolean isDead = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public Person person(String person) {
        this.person = person;
        return this;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getSurname() {
        return surname;
    }

    public Person surname(String surname) {
        this.surname = surname;
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public Person fatherName(String fatherName) {
        this.fatherName = fatherName;
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Double getAge() {
        return age;
    }

    public Person age(Double age) {
        this.age = age;
        return this;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getGeneration() {
        return generation;
    }

    public Person generation(Double generation) {
        this.generation = generation;
        return this;
    }

    public void setGeneration(Double generation) {
        this.generation = generation;
    }

    public Double getValue() {
        return value;
    }

    public Person value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getLastValue() {
        return lastValue;
    }

    public Person lastValue(Double lastValue) {
        this.lastValue = lastValue;
        return this;
    }

    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Person birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public ZonedDateTime getDeathday() {
        return deathday;
    }

    public Person deathday(ZonedDateTime deathday) {
        this.deathday = deathday;
        return this;
    }

    public void setDeathday(ZonedDateTime deathday) {
        this.deathday = deathday;
    }

    public Boolean isIsDead() {
        return isDead;
    }

    public Person isDead(Boolean isDead) {
        this.isDead = isDead;
        return this;
    }

    public void setIsDead(Boolean isDead) {
        this.isDead = isDead;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        if (person.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", person='" + person + "'" +
            ", surname='" + surname + "'" +
            ", name='" + name + "'" +
            ", fatherName='" + fatherName + "'" +
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
