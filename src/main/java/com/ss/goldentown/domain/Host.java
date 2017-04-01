package com.ss.goldentown.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.ss.goldentown.domain.enumeration.HostType;

/**
 * A Host.
 */
@Entity
@Table(name = "host")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "host")
public class Host implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "credit")
    private Double credit;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private HostType type;

    @ManyToOne
    private User person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Host name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Host description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public Host birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getTelephone() {
        return telephone;
    }

    public Host telephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Host temperature(Double temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getCredit() {
        return credit;
    }

    public Host credit(Double credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public HostType getType() {
        return type;
    }

    public Host type(HostType type) {
        this.type = type;
        return this;
    }

    public void setType(HostType type) {
        this.type = type;
    }

    public User getPerson() {
        return person;
    }

    public Host person(User user) {
        this.person = user;
        return this;
    }

    public void setPerson(User user) {
        this.person = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Host host = (Host) o;
        if (host.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, host.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Host{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", birthday='" + birthday + "'" +
            ", telephone='" + telephone + "'" +
            ", temperature='" + temperature + "'" +
            ", credit='" + credit + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
