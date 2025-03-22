package main.java.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CenterLabelController extends ParentController {
    @FXML private Label center_text_label;

    public void setText(String string) {
        center_text_label.setText(string);
    }
}
