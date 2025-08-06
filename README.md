This project demonstrates data cleaning, aggregation, and visualization for sales order data using Java and CSV files, with a focus on realistic “dirty” data and robust annotation-driven transformation.

## Instructions
1. Clone the master branch.
2. Install Java 8 or higher.
3. Run java -version to confirm installation.
4. Open the project in any Java IDE (e.g., IntelliJ, Eclipse, VS Code).
5. Navigate to org.example.Main::main (the main method in Main.java).
6. Now make sure to follow all the steps from 7 to 10 as it is.
7. Generate sample data:
   - Uncomment ONLY line 22.
   - Run main().
   - This will create a raw orders CSV file containing 1 million raw orders.
   - Comment line 22 back.
8. Ingest data into DB:
   - Uncomment ONLY line 23.
   - Run main().
   - This will ingest the created raw orders CSV into the database.
   - Comment line 23 back.
9. Analyze:
   - Uncomment ONLY line 24.
   - Run main().
   - This will create the 6 analytical tables in the database.
   - Comment line 24 back.
10. Create dashboard:
   - Uncomment ONLY line 25.
   - Run main().
   - This will create and open the dashboard in a new tab.
   - Comment line 25 back.

# Approaches
This project can be adapted to different ways of reading and processing data, each with unique benefits and drawbacks:

## 1. Direct Read the Whole File
Description:
Read the entire data file into memory at once, process it as needed.

Pros:

Simple to implement and debug

Fast for small to medium datasets that fit in RAM

No complex threading or chunk logic

Cons:

Not scalable for very large datasets (can cause OutOfMemoryError)

High memory consumption

## 2. Read File in Chunks Using Threads
Description:
Read and process the file in chunks (e.g., lines or blocks), possibly in parallel threads.

Pros:

Allows much larger files to be processed (constant memory usage)

Can improve speed via multi-threading, especially on multi-core systems

Good for streaming or partial processing scenarios

Cons:

More complex code (threading, synchronization, error handling)

Order of output may be non-deterministic if not carefully managed

Potential for race conditions or thread-safety issues

## 3. Process Cleaned Data In-Memory
Description:
After cleaning, load and process all data in Java collections/structures in memory.

Pros:

Immediate, high-speed analytics and filtering on small datasets

Flexibility in further in-memory computations after cleaning

Cons:

Limited by available JVM memory; not suitable for very large datasets

Entire pipeline must finish before results are available

## 4. Store Cleaned Data in OLAP DB, Then Process for Analytical Tables
Description:
Output cleaned records into an OLAP database (e.g., DuckDB), then use SQL to produce pre-aggregated analytical tables.

Pros:

Scalable: can process much larger data using disk-based DB

Enables powerful SQL/OLAP analytics, advanced aggregations, window functions, etc.

Analytical tables can be exported to CSV for downstream processing or visualizations

Cons:

Requires DB knowledge and extra setup (install, connect, etc.)

ETL pipeline slightly more complex (write, then query from DB)

Some latency from intermediate persistence before analysis

# Used Approach
## 1. Raw Data Simulation & Real-World Error
Raw order data is randomly generated with intentional typos, missing values, and variable date formats to simulate real-world “messy” business data.

Data is written to a pipe-delimited CSV file, with occasional misspellings and format errors in categorical and date fields.

## 2. Annotation-Driven Cleaning & Validation
A modular design uses Java annotations (@Cleaner, @Validator) to declare, on each field of a RawOrder object, what kind of cleaning/standardization and validation logic should be applied.

Example:

Product names like T.V., Television, tv are mapped to TV

“nort” → “North”; “elec”, “gadget” → “Electronics”; various date formats unified to yyyy-MM-dd

AnnotationCleaner and AnnotationValidator utilities use reflection to apply these transformations generically and extensibly.

## 3. Data Warehousing (OLAP) and Analytical Table Generation
Cleaned and validated records are aggregated using SQL (DuckDB) or plain Java for:

Monthly sales summary

Top products by revenue

Regional sales performance

Top revenue outliers (“anomaly” records)

Category discount map

Analytical tables are exported as CSV for lightweight dashboarding.

## 4. Modular Dashboard (Java Swing & JFreeChart)
Each dashboard widget (chart/table visualization) implements a standard DashboardWidget interface with loadData() and getView() methods.

Modular widgets for:

Monthly revenue trend (line chart)

Top 10 products (bar chart)

Sales by region (bar chart)

Category discount map (bar chart)

Anomaly records (JTable)

Dashboard assembles these widgets in a grid, loading from pre-aggregated CSV output.

Full month/category labels are displayed, and charts auto-rotate/join labels to maximize readability.

## 5. Thorough Automated Testing
JUnit tests for all annotation-based cleaning and validation logic.

Both positive (“can clean/validate correctly”) and negative (“identifies invalid/malformed data”) cases are covered.

Workflow Overview
Raw Data Generation:
Dirty, semi-random sales data created using RawOrderDataGenerator.

Cleaning & Validation:
Annotation-driven, field-level pipeline standardizes and checks all fields.

Aggregation:
Clean data is grouped and summarized by SQL or Java.

CSV Export:
Analytical tables exported for portable downstream usage.

Dashboard:
Modular, extensible widgets visualize each analytic using Java+JFreeChart, loading directly from CSV files for maximum reproducibility.

Testing:
All transformation and validation logic is tested for reliability, including edge and “bad data” cases.

# Why This Approach?
Realism: Simulates the messiness of real sales data, not perfect “lab” data.

Maintainability: Cleaning and validation logic are not hard-coded but declared via annotations, so evolving business logic is easy to update.

Modularity: Every chart/table is a self-contained widget, easy to swap, test, or extend for new analytics.

Lightweight & Portable: No web server required; pure Java and CSV for maximum run-anywhere simplicity.

Testability: Comprehensive unit tests ensure that critical data transformation logic is robust to evolving requirements and new kinds of data errors.

# Local Test Results
1. Machine details:
   - Processor: 12th Gen Intel(R) Core(TM) i7-12650H (16 CPUs), ~2.7GHz
   - Memory: 16384MB RAM
   - Disk type: SSD
2. Sample data size: 1M
   - Ingestion time: 1 mins 50 seconds
   - Analyze time: 2 seconds
   - dashboard creation time: 3 seconds
