package vn.duyta.productservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.duyta.productservice.dto.request.CreateProductRequest;
import vn.duyta.productservice.dto.request.UpdateProductRequest;
import vn.duyta.productservice.dto.response.CreateProductResponse;
import vn.duyta.productservice.dto.response.ResultPaginationDTO;
import vn.duyta.productservice.dto.response.UpdateProductResponse;
import vn.duyta.productservice.mapper.ProductMapper;
import vn.duyta.productservice.model.Product;
import vn.duyta.productservice.repository.ProductRepository;
import vn.duyta.productservice.util.error.IdInvalidException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CreateProductResponse handleCreateProduct(CreateProductRequest request){
        Product product = productMapper.toProduct(request);
        return productMapper.toCreateProductResponse(this.productRepository.save(product));
    }

    public UpdateProductResponse handleUpdateProduct(long id, UpdateProductRequest request) throws IdInvalidException {
        Product currentProduct = productRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Product not found"));

        currentProduct.setName(request.getName());
        currentProduct.setDescription(request.getDescription());
        currentProduct.setPrice(request.getPrice());
        currentProduct.setQuantity(request.getQuantity());
        currentProduct.setImage(request.getImages());

        Product updatedProduct = productRepository.save(currentProduct);
        return productMapper.toUpdateProduct(updatedProduct);
    }

    public ResultPaginationDTO handleGetProduct(Specification<Product> spec, Pageable pageable){
        Page<Product> pageProduct = this.productRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());

        mt.setPages(pageProduct.getTotalPages());
        mt.setTotal(pageProduct.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageProduct.getContent());

        return rs;
    }

    public Product fetchProductById(long id) throws IdInvalidException {
        Optional<Product> optionalProduct = this.productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public void deleteProduct(long id) throws IdInvalidException{
        Product product = this.productRepository.findById(id)
                .orElseThrow(()-> new IdInvalidException("Product not found"));
        productRepository.delete(product);
    }
}
