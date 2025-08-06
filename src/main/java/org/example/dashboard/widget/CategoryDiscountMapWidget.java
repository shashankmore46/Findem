package org.example.dashboard.widget;

import org.example.utils.CsvUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/** Widget for "Category Discount Map". Plots average discount per category as a bar chart. */
public class CategoryDiscountMapWidget implements DashboardWidget {
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JFreeChart chart;

    @Override
    public void loadData(String csvPath) throws Exception {
        List<String[]> rows = CsvUtils.readCsv(csvPath); // header: category, avg_discount, order_count
        dataset.clear();
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String category = row[0];
            double avgDiscount = Double.parseDouble(row[1]);
            dataset.addValue(avgDiscount, "Avg Discount", category);
        }
        chart = ChartFactory.createBarChart(
                "Category Discount Map", "Category", "Average Discount %", dataset
        );
        // Make all category names readable
        CategoryPlot plot = chart.getCategoryPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6) // 30°
        );
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.PLAIN, 11));
        domainAxis.setMaximumCategoryLabelWidthRatio(1.0f);
    }

    @Override
    public JComponent getView() {
        return new ChartPanel(chart);
    }
}

