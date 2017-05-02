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

    @Column(name = "mother_name")
    private String motherName="";

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "age")
    private Double age;

    @Column(name = "gene_1")
    private Double gene1;

    @Column(name = "gene_2")
    private Double gene2;

    @Column(name = "gene_3")
    private Double gene3;

    @Column(name = "gene_4")
    private Double gene4;

    @Column(name = "gene_5")
    private Double gene5;

    @Column(name = "gene_6")
    private Double gene6;

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

    public String getMotherName() {
        return motherName;
    }

    public Person motherName(String motherName) {
        this.motherName = motherName;
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Integer getGender() {
        return gender;
    }

    public Person gender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
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

    public Double getGene1() {
        return gene1;
    }

    public Person gene1(Double gene1) {
        this.gene1 = gene1;
        return this;
    }

    public void setGene1(Double gene1) {
        this.gene1 = gene1;
    }

    public Double getGene2() {
        return gene2;
    }

    public Person gene2(Double gene2) {
        this.gene2 = gene2;
        return this;
    }

    public void setGene2(Double gene2) {
        this.gene2 = gene2;
    }

    public Double getGene3() {
        return gene3;
    }

    public Person gene3(Double gene3) {
        this.gene3 = gene3;
        return this;
    }

    public void setGene3(Double gene3) {
        this.gene3 = gene3;
    }

    public Double getGene4() {
        return gene4;
    }

    public Person gene4(Double gene4) {
        this.gene4 = gene4;
        return this;
    }

    public void setGene4(Double gene4) {
        this.gene4 = gene4;
    }

    public Double getGene5() {
        return gene5;
    }

    public Person gene5(Double gene5) {
        this.gene5 = gene5;
        return this;
    }

    public void setGene5(Double gene5) {
        this.gene5 = gene5;
    }

    public Double getGene6() {
        return gene6;
    }

    public Person gene6(Double gene6) {
        this.gene6 = gene6;
        return this;
    }

    public void setGene6(Double gene6) {
        this.gene6 = gene6;
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
            ", motherName='" + motherName + "'" +
            ", gender='" + gender + "'" +
            ", age='" + age + "'" +
            ", gene1='" + gene1 + "'" +
            ", gene2='" + gene2 + "'" +
            ", gene3='" + gene3 + "'" +
            ", gene4='" + gene4 + "'" +
            ", gene5='" + gene5 + "'" +
            ", gene6='" + gene6 + "'" +
            ", generation='" + generation + "'" +
            ", value='" + value + "'" +
            ", lastValue='" + lastValue + "'" +
            ", birthday='" + birthday + "'" +
            ", deathday='" + deathday + "'" +
            ", isDead='" + isDead + "'" +
            '}';
    }
}
