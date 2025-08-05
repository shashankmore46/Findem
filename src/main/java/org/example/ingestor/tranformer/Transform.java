package org.example.ingestor.tranformer;

public interface Transform<F,T> {
    T transform(F from);
}
