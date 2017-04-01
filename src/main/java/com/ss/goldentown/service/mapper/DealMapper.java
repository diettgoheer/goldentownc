package com.ss.goldentown.service.mapper;

import com.ss.goldentown.domain.*;
import com.ss.goldentown.service.dto.DealDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Deal and its DTO DealDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface DealMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    DealDTO dealToDealDTO(Deal deal);

    List<DealDTO> dealsToDealDTOs(List<Deal> deals);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "agentId", target = "agent")
    @Mapping(source = "productId", target = "product")
    Deal dealDTOToDeal(DealDTO dealDTO);

    List<Deal> dealDTOsToDeals(List<DealDTO> dealDTOs);

    default Agent agentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(id);
        return agent;
    }

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
