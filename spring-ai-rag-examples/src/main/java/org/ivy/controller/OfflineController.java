package org.ivy.controller;

import org.ivy.service.OfflineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * RAG 系统的离线部分实现
 */
@RestController
public class OfflineController {

    private final OfflineService offlineService;

    public OfflineController(OfflineService offlineService) {
        this.offlineService = offlineService;
    }
    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile file) {
        return ResponseEntity.ok(offlineService.upload(file));
    }
}
