package com.ss.goldentown.service.mapper;

import com.ss.goldentown.domain.*;
import com.ss.goldentown.service.dto.BillingDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Billing and its DTO BillingDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, UserMapper.class, })
public interface BillingMapper {

    @Mapping(source = "deal.id", target = "dealId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "src.id", target = "srcId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    BillingDTO billingToBillingDTO(Billing billing);

    List<BillingDTO> billingsToBillingDTOs(List<Billing> billings);

    @Mapping(source = "dealId", target = "deal")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "srcId", target = "src")
    @Mapping(source = "productId", target = "product")
    Billing billingDTOToBilling(BillingDTO billingDTO);

    List<Billing> billingDTOsToBillings(List<BillingDTO> billingDTOs);

    default Deal dealFromId(Long id) {
        if (id == null) {
            return null;
        }
        Deal deal = new Deal();
        deal.setId(id);
        return deal;
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
