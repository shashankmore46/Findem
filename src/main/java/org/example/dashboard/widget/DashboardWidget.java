package org.example.dashboard.widget;

import javax.swing.*;

public interface DashboardWidget {
    /**
     * Load data from a CSV file.
     * @param csvPath The path to the CSV file.
     * @throws Exception on load failure.
     */
    void loadData(String csvPath) throws Exception;

    /**
     * Get the Swing component for display.
     * @return JComponent to be embedded in the dashboard.
     */
    JComponent getView();
}
