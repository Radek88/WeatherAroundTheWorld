package Controler;

import com.aquafx_project.AquaFx;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.autosize();
        primaryStage.setTitle("WeatherAroundTheWorld");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        AquaFx.style();
        AquaFx.styleStage(primaryStage, StageStyle.UTILITY);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
