package org.ivy.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class PgVectorServiceTest {
    @Resource
    private VectorStore vectorStore;

    @Test
    public void testAdd() {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!! Spring AI rocks!!", Map.of("year", "2021", "user", "zhang")),
                new Document("The World is Big and Salvation Lurks Around the Corner", Map.of("year", "2019", "user", "li")),
                new Document("You walk forward facing the past and you turn back toward the future.", Map.of("year", "2024", "user", "wang")));

        vectorStore.accept(documents);
    }


    /**
     * 测试相似查询，仅设置query参数
     */
    @Test
    public void testSimilaritySearch() {
        List<Document> documents = vectorStore.similaritySearch("Spring AI rocks!!");
        Assertions.assertEquals(documents.size(), 3);
    }

    /**
     * 相似度查询，设置相似度阈值和返回结果数
     */
    @Test
    public void testSimilaritySearchWithRequest() {
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest
                        .query("Spring AI rocks!!")
                        .withTopK(2)
                        .withSimilarityThreshold(0.8));

        Assertions.assertEquals(documents.size(), 1);
    }


    /**
     * 查询相似度，并且是zhang的数据
     */
    @Test
    public void testSimilaritySearchWithRequestAndFilter() {
        FilterExpressionBuilder b = new FilterExpressionBuilder();

        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest
                        .query("Spring AI rocks!!")
                        .withFilterExpression(b.eq("user", "zhang").build())
        );

        Assertions.assertEquals(documents.size(), 1);

        // 另外一种写法
        List<Document> docs = vectorStore.similaritySearch(
                SearchRequest
                        .query("Spring AI rocks!!")
                        .withFilterExpression("user == 'zhang'")
        );

        Assertions.assertEquals(docs.size(), 1);

    }

}
