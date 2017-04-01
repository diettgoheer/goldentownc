package com.ss.goldentown.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Agent entity.
 */
public class AgentDTO implements Serializable {

    private Long id;


    private ProductDTO product;
    
    private Long userId;
    
    private Long fatherId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long agentId) {
        this.fatherId = agentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentDTO agentDTO = (AgentDTO) o;

        if ( ! Objects.equals(id, agentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
            "id=" + id +
            '}';
    }
}
