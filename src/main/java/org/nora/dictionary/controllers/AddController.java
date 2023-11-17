package org.nora.dictionary.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.web.HTMLEditor;

public class AddController {
    @FXML
    private TextArea wordField;
    @FXML
    private HTMLEditor explainField;
    @FXML
    private Label warningLabel;
    @FXML
    private Button checkButton;
    @FXML
    private Button addButton;
}
