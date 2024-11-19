package com.thesearch.mylaptopshop.service.product;

import java.util.List;

import com.thesearch.mylaptopshop.dto.ProductDto;
import com.thesearch.mylaptopshop.model.Product;
import com.thesearch.mylaptopshop.request.AddProductRequest;
import com.thesearch.mylaptopshop.request.ProductUpdateRequest;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndCategory(String brand, String category);
    List<Product> getProductByBrandAndName(String brand, String name);
    Long countProductByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);
    ProductDto convertToDto(Product product);
}
