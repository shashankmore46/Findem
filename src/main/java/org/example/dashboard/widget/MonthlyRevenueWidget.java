package org.example.dashboard.widget;

import org.example.utils.CsvUtils;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.*;
import javax.swing.*;

public class MonthlyRevenueWidget implements DashboardWidget {
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JFreeChart chart;

    @Override
    public void loadData(String filePath) throws Exception {
        var rows = CsvUtils.readCsv(filePath);
        dataset.clear();
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String month = row[0];
            double revenue = Double.parseDouble(row[1]);
            dataset.addValue(revenue, "Revenue", month);
        }
        chart = ChartFactory.createLineChart(
                "Monthly Revenue Trend", "Month", "Revenue", dataset
        );
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
