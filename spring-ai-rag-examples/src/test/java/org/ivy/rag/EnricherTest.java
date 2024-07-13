package org.ivy.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.ai.transformer.SummaryMetadataEnricher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EnricherTest {
    @Resource
    private OllamaChatModel ollamaChatModel;

    @Value("classpath:java.pdf")
    private org.springframework.core.io.Resource testFileResource;

    /**
     * 关键词提取
     */
    @Test
    public void testEnrichKeywordMetadata() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(testFileResource);
        List<Document> documents = reader.get();
        KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(ollamaChatModel, 1000);
        List<Document> enrichers = keywordMetadataEnricher.apply(documents);
        System.out.println(enrichers);
    }

    /**
     * 文档摘要
     */
    @Test
    public void testSummaryMetadataEnricher() {
        PagePdfDocumentReader reader = new PagePdfDocumentReader(testFileResource);
        List<Document> documents = reader.get();
        SummaryMetadataEnricher keywordMetadataEnricher = new SummaryMetadataEnricher(ollamaChatModel,List.of(SummaryMetadataEnricher.SummaryType.CURRENT));
        List<Document> enrichers = keywordMetadataEnricher.apply(documents);
        System.out.println(enrichers);

    }

}
