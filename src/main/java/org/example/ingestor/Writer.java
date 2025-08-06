package org.example.ingestor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Writer {
    private final Map<String, ExecutorService> writerPools;

    public Writer() {
        this.writerPools = new HashMap<>();
    }

    public void write(String tableName, Runnable dataWriter) {
        ExecutorService pool = writerPools.get(tableName);
        if (pool == null) {
            throw new IllegalArgumentException("No writer pool for table: " + tableName);
        }
        pool.submit(dataWriter);
    }

    public void createTableWriter(String tableName) {
        writerPools.putIfAbsent(tableName, Executors.newSingleThreadExecutor());
    }

    public void shutdownAll() {
        for (ExecutorService pool : writerPools.values()) {
            pool.shutdown();
        }
    }
}
