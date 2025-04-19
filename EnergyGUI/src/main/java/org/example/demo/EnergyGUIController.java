package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class EnergyGUIController {

    @FXML private TextField communityPoolField;
    @FXML private TextField gridPortionField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TextField producedField;
    @FXML private TextField usedField;
    @FXML private TextField gridUsedField;
    @FXML private ImageView infoImage;

    public void handleRefresh() {
        // Placeholder for API call to fetch current percentage
        System.out.println("Refresh clicked");
    }

    public void handleShowData() {
        // Placeholder for API call to fetch historical data
        System.out.println("Show Data clicked");
    }
}