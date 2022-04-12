package com.ashraf.ojapilayer.service;

import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.entity.FileStore;


public interface DocumentService {
    String uploadMultipartFile(FileUploadRequest request);
    FileStore getDocument(String id);
}
