package vn.duyta.productservice.mapper;

import org.mapstruct.Mapper;
import vn.duyta.productservice.dto.request.CreateProductRequest;
import vn.duyta.productservice.dto.response.CreateProductResponse;
import vn.duyta.productservice.dto.response.UpdateProductResponse;
import vn.duyta.productservice.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    UpdateProductResponse toUpdateProduct(Product product);
    Product toProduct(CreateProductRequest request);
    CreateProductResponse toCreateProductResponse(Product product);
}
