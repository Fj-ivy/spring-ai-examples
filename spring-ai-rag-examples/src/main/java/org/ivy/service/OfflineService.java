package org.ivy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.DocumentTransformer;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfflineService {

    private final VectorStore vectorStore;
    private final DocumentTransformer transformer;

    /**
     * 上传文件，并拆分文档，向量化到数据库
     *
     * @param file 文件
     * @return 上传结果
     */
    public String upload(MultipartFile file) {
        Resource resource = file.getResource();
        TikaDocumentReader reader = new TikaDocumentReader(resource);
        // 读取文档
        List<Document> documents = reader.get();
        // 拆分文档
        List<Document> transform = transformer.transform(documents);
        // 向量化到数据库
        vectorStore.accept(transform);
        return "ok";
    }
}
