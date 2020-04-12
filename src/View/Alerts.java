package View;

import javafx.scene.control.Alert;

public class Alerts{

    public static void cityIsNotAvailable() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wrong city name");
        alert.setHeaderText("Sorry but weather forecast for this city is not available.");
        alert.setContentText("You can also choice interested city from list on right panel.");
        alert.showAndWait();
    }
}
