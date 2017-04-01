package com.ss.goldentown.service.mapper;

import com.ss.goldentown.domain.*;
import com.ss.goldentown.service.dto.ProductDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface ProductMapper {

    @Mapping(source = "user.id", target = "userId")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    @Mapping(source = "userId", target = "user")
    Product productDTOToProduct(ProductDTO productDTO);

    List<Product> productDTOsToProducts(List<ProductDTO> productDTOs);
}
