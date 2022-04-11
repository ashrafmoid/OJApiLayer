package com.ashraf.ojapilayer.controller;

import com.ashraf.ojapilayer.entity.FileStore;
import com.ashraf.ojapilayer.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/document/{documentId}")
    public ResponseEntity<?> getDocument(@PathVariable("documentId") String documentId) {
        FileStore fileStore = documentService.getDocument(documentId);
        if(Objects.isNull(fileStore)) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok()
                .contentType(getMediaType(fileStore.getFileType()))
                .body(new ByteArrayResource(fileStore.getData()));
    }

    private MediaType getMediaType(String fileType) {
        if("PDF".equalsIgnoreCase(fileType)) {
            return MediaType.APPLICATION_PDF;
        } else if("txt".equalsIgnoreCase(fileType)) {
            return MediaType.TEXT_PLAIN;
        }
        return null;
    }
}
