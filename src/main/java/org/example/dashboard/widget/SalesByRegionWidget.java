package org.example.dashboard.widget;

import org.example.utils.CsvUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SalesByRegionWidget implements DashboardWidget {
    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    private JFreeChart chart;

    @Override
    public void loadData(String csvPath) throws Exception {
        List<String[]> rows = CsvUtils.readCsv(csvPath);
        dataset.clear();
        for (int i = 1; i < rows.size(); i++) {
            String[] row = rows.get(i);
            String region = row[0];
            double revenue = Double.parseDouble(row[1]);
            dataset.addValue(revenue, "Revenue", region);
        }
        chart = ChartFactory.createBarChart(
                "Sales by Region", "Region", "Revenue", dataset
        );
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

