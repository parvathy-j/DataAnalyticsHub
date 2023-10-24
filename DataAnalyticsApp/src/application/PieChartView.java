package application;

import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PieChartView {
    private VBox root;
    private UserDatabase userDatabase;

    public PieChartView(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
        root = new VBox();

        // Fetch and categorize the share counts
        List<Integer> shareCounts = userDatabase.fetchShareCounts();
        List<PieChart.Data> categorizedData = userDatabase.categorizeShareCounts(shareCounts);

        // Convert categorizedData to an ObservableList
        ObservableList<PieChart.Data> observableData = FXCollections.observableArrayList(categorizedData);

        // Create the pie chart
        PieChart pieChart = new PieChart(observableData);

        // Add the pie chart to the layout
        root.getChildren().add(pieChart);
    }

    public VBox getView() {
        return root;
    }
}
