package br.cedup.ada_com;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {
    private static Scene scene;
    private static Scene originalScene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login-view"));
        stage.setMaximized(false);
        stage.setTitle("ADA/Com");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();

        // Adicionar o manipulador de eventos aqui
        stage.addEventHandler(WindowEvent.WINDOW_SHOWN, new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                // Forçar a atualização da interface de usuário
                ((Pane) scene.getRoot()).requestLayout();
            }
        });
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
    public static void setRoot(String fxml)throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void showModal(String fxml) throws IOException {
        // Obtém a tela atual
        Window primaryStage = scene.getRoot().getScene().getWindow();

        // Armazena a cena original
        originalScene = scene;

        // Carrega a nova tela
        scene = new Scene(loadFXML(fxml));

        // Abre o modal
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.setScene(scene);
        dialog.setResizable(false);
        dialog.showAndWait();

        // Restaura a cena original
        scene = originalScene;
    }

     //Fecha a janela atual
    public static void closeCurrentWindow() {
        ((Stage) scene.getRoot().getScene().getWindow()).close();
    }


}
