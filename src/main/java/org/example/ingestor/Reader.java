package org.example.ingestor;

import org.example.model.RawOrder;
import org.example.utils.CsvUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Reader extends Thread {
    private final File file;
    private final ExecutorService workerPool;
    private final Writer writer;
    private final int chunkSize;

    public Reader(String filePath, ExecutorService workerPool, Writer writer, int chunkSize) {
        this.file = new File(filePath);
        this.workerPool = workerPool;
        this.writer = writer;
        this.chunkSize = chunkSize;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine(); // skip header line
            String line;
            int count = 0;
            List<RawOrder> chunk = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                RawOrder rawOrder = CsvUtils.parseRawOrder(line);
                chunk.add(rawOrder);
                count++;
                if (count == chunkSize) {
                    workerPool.submit(new WorkerTask(new ArrayList<>(chunk), writer));
                    chunk.clear();
                    count = 0;
                }
            }
            if (!chunk.isEmpty()) {
                workerPool.submit(new WorkerTask(new ArrayList<>(chunk), writer));
            }
            System.out.println("Finished reading file: " + file.getName());
            workerPool.shutdown();
            while (!workerPool.awaitTermination(10, TimeUnit.SECONDS)) {
                System.out.println("Waiting for worker pool to finish...");
            }
            writer.shutdownAll();
            System.out.println("All ingestion completed and writer threads shut down.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
