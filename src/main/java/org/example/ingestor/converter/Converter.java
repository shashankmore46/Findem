package org.example.ingestor.converter;

public interface Converter<F,T> {
    T convert(F from);
}
