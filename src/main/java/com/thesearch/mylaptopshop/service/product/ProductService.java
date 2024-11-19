package com.thesearch.mylaptopshop.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.thesearch.mylaptopshop.dto.ImageDto;
import com.thesearch.mylaptopshop.dto.ProductDto;
import com.thesearch.mylaptopshop.exception.AlreadyExistException;
import com.thesearch.mylaptopshop.exception.ResourceNotFoundException;
import com.thesearch.mylaptopshop.model.Category;
import com.thesearch.mylaptopshop.model.Image;
import com.thesearch.mylaptopshop.model.Product;
import com.thesearch.mylaptopshop.repository.CategoryRepository;
import com.thesearch.mylaptopshop.repository.ImageRepository;
import com.thesearch.mylaptopshop.repository.ProductRepository;
import com.thesearch.mylaptopshop.request.AddProductRequest;
import com.thesearch.mylaptopshop.request.ProductUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final  ProductRepository productRepository;
    private final  CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest request){

        if(productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistException(request.getBrand()+" "+request.getName()+" Already Exists!");
        }
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
            .orElseGet(()->{
                Category newCategory = new Category(request.getCategory().getName());
                return categoryRepository.save(newCategory);
        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private boolean  productExists(String name,String brand){
        return productRepository.existsByNameAndBrand(name,brand);
    }
    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getPrice(),
            request.getQuantity(),
            request.getDescription(),
            category
        );
    }
    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }
    @Override
    public void deleteProductById(Long id){
        productRepository.findById(id).
            ifPresentOrElse(productRepository::delete, 
                ()->{throw new ResourceNotFoundException("Product not found!");});
    }
    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId){
        return productRepository.findById(productId)
            .map(existingProduct -> updateExistingProduct(existingProduct, request))
            .map(productRepository :: save)
            .orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
    }
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setDescription(request.getDescription());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct; 
    }
    @Override
    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }
    @Override
    public List<Product> getProductByCategory(String category){
        return productRepository.findByCategoryName(category);
    }
    @Override
    public List<Product> getProductByBrand(String brand){
        return productRepository.findByBrand(brand);
    }
    @Override
    public List<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }
    @Override
    public List<Product> getProductByBrandAndCategory(String brand, String category){
        return productRepository.findByBrandAndCategoryName(brand, category);
    }
    @Override
    public List<Product> getProductByBrandAndName(String brand, String name){
        return productRepository.findByBrandAndName(brand,name);
    }
    @Override
    public Long countProductByBrandAndName(String brand, String name){
        return productRepository.countByBrandAndName(brand,name);
    }
    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
            .map(image -> modelMapper.map(image,ImageDto.class))
            . toList();
        productDto.setImage(imageDtos);
        return productDto;
        }
}
