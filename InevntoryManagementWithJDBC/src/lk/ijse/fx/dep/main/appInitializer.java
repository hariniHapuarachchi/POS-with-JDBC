package lk.ijse.fx.dep.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ijse.fx.dep.dbClass.dbConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class appInitializer extends Application {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        launch(args);
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryInfo", "root", "root");
        System.out.println("Connected to database");
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
