CREATE TABLE document_chunks
(
    id         BIGSERIAL PRIMARY KEY,
    content    TEXT         NOT NULL,
    embedding  VECTOR(1024) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_document_chunks_embedding_hnsw
    ON document_chunks
    USING hnsw (embedding vector_cosine_ops);
