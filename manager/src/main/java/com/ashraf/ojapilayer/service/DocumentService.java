package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.entity.FileStore;
import org.springframework.web.multipart.MultipartFile;


public interface DocumentService {
    String uploadMultipartFile(FileUploadRequest request);
    FileStore getDocument(String id);
}
