package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        fxmlLoader.setResources(ResourceBundle.getBundle("controller.Locale", new Locale("ru")));

        Parent root = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

        primaryStage.setTitle(fxmlLoader.getResources().getString("adress_book"));
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
