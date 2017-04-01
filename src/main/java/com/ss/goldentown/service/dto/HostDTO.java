package com.ss.goldentown.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ss.goldentown.domain.enumeration.HostType;

/**
 * A DTO for the Host entity.
 */
public class HostDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private ZonedDateTime birthday;

    private String telephone;

    private Double temperature;

    private Double credit;

    private HostType type;


    private Long personId;
    
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public ZonedDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }
    public HostType getType() {
        return type;
    }

    public void setType(HostType type) {
        this.type = type;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long userId) {
        this.personId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HostDTO hostDTO = (HostDTO) o;

        if ( ! Objects.equals(id, hostDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HostDTO{" +
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
