package vn.duyta.productservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.duyta.productservice.dto.request.CreateProductRequest;
import vn.duyta.productservice.dto.response.CreateProductResponse;
import vn.duyta.productservice.dto.response.ProductResponse;
import vn.duyta.productservice.dto.response.UpdateProductResponse;
import vn.duyta.productservice.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    UpdateProductResponse toUpdateProduct(Product product);

    @Mapping(target = "image", source = "image")
    Product toProduct(CreateProductRequest request);

    CreateProductResponse toCreateProductResponse(Product product);
    ProductResponse toProductResponse(Product product);
}
