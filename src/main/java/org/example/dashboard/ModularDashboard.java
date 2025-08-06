package org.example.dashboard;

import org.example.dashboard.widget.DashboardWidget;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ModularDashboard extends JFrame {
    public ModularDashboard(List<DashboardWidget> widgets, List<String> csvPaths) throws Exception {
        setTitle("Sales Analytics Dashboard");
        setLayout(new GridLayout(3, 2));
        for (int i = 0; i < widgets.size(); i++) {
            DashboardWidget widget = widgets.get(i);
            widget.loadData(csvPaths.get(i));
            add(widget.getView());
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setVisible(true);
    }
}
