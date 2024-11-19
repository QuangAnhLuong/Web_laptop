package com.thesearch.mylaptopshop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.thesearch.mylaptopshop.dto.ImageDto;
import com.thesearch.mylaptopshop.model.Image;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, long laptopId);
    void updateImage(MultipartFile file, long id);

}
