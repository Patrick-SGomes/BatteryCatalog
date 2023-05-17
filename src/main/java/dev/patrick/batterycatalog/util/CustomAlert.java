package dev.patrick.batterycatalog.util;

import javafx.scene.control.Alert;

public class CustomAlert {
    public static Alert alert(String title, String header, String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(text);
        a.showAndWait();
        return a;
    }
}
