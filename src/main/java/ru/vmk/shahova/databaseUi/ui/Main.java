package ru.vmk.shahova.databaseUi.ui;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import ru.vmk.shahova.databaseUi.ui.page.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import ru.vmk.shahova.databaseUi.ui.page.ErrorController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);

        Parent root = FXMLLoader.load(getClass().getResource("/table.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("TableView");
        stage.setWidth(2500);
        stage.setHeight(2000);

        stage.show();

    }
    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in "+t);

        }
    }

    private static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/error.fxml"));
        try {
            Parent root = loader.load();
            ((ErrorController)loader.getController()).setErrorText("Введи нормально");
            dialog.setScene(new Scene(root, 250, 250));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}