This project demonstrates data cleaning, aggregation, and visualization for sales order data using Java and CSV files, with a focus on realistic “dirty” data and robust annotation-driven transformation.

# Approach
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
