package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.api.requestmodels.FileUploadRequest;
import com.ashraf.ojapilayer.entity.FileStore;
import com.ashraf.ojapilayer.repository.FileStoreRepository;
import com.ashraf.ojapilayer.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class DocumentServiceImpl implements DocumentService {
    private final FileStoreRepository fileStoreRepository;

    @Override
    public String uploadMultipartFile(FileUploadRequest fileUploadRequest) {
        try {
            FileStore fileStore = FileStore.builder().data(fileUploadRequest.getMultipartFile().getBytes())
                    .fileType(fileUploadRequest.getFileType()).build();
            return fileStoreRepository.save(fileStore).getId();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed");
        }
    }

    @Override
    public FileStore getDocument(String id) {
        return fileStoreRepository.findById(id).orElse(null);
    }
}
