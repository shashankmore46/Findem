package org.example.dashboard.widget;

import org.example.utils.CsvUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class AnomalyRecordsWidget implements DashboardWidget {
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JFreeChart chart;

    @Override
    public void loadData(String csvPath) throws Exception {
        var rows = CsvUtils.readCsv(csvPath);
        dataset.clear();
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String orderId = row[0];
            String product = row[1];
            double revenue = Double.parseDouble(row[9]);
            dataset.addValue(revenue, "Revenue", orderId+"-"+product);
        }
        chart = ChartFactory.createBarChart(
                "Top 5 Orders By Revenue", "Order", "Revenue", dataset);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6)  // 30°
        );
        domainAxis.setTickLabelFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 10));
        domainAxis.setMaximumCategoryLabelWidthRatio(1.0f);
    }

    @Override
    public JComponent getView() {
        return new ChartPanel(chart);
    }
}
