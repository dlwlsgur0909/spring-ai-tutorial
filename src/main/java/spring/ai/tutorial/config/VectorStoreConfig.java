package spring.ai.tutorial.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class VectorStoreConfig {

    @Bean
    public VectorStore pgVectorStore(JdbcTemplate jdbcTemplate,
                                     EmbeddingModel embeddingModel) {

        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .initializeSchema(false) // PGVector에서 사용할 테이블 자동 생성 여부
                .vectorTableName("documents") // 기본 테이블명은 vector_store
                .schemaName("public") // 기본 스키마명은 public
                .dimensions(0) // Embedding Vector의 차원 EmbeddingModel과 반드시 동일한 값을 사용해야 한다
                                // 기본적으로 Spring AI는 EmbeddingModel에서 차원을 자동으로 가져온다
                .distanceType(PgVectorStore.PgDistanceType.COSINE_DISTANCE) // 유사도 계산 방법, 기본값 COSINE_DISTANCE
                .maxDocumentBatchSize(10) // 문서 저장 배치 사이즈 (보통 기본값 10000 사용)
                .indexType(PgVectorStore.PgIndexType.HNSW) // 벡터 전용 인덱스, 기본값 HNSW
                .build();
    }


}
