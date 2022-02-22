package fxht;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author miro
 * @version 27.1.2019
 * pääohjelma ohjelman käynnistämiseen
 */
public class HT extends Application {
    @Override
    public void start(Stage primaryStage) {  
        try {
        FXMLLoader ldr = new FXMLLoader(getClass().getResource("HTGUIView.fxml"));
        final Pane root = ldr.load();
        final HTGUIController HTCtrl = (HTGUIController) ldr.getController();
        
        final Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("HT.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("HT");
        
        primaryStage.setOnCloseRequest((event) -> {
            if(!HTCtrl.voikoSulkea()) event.consume();
        });
        
        primaryStage.show();
        
    } catch(Exception e) {
        e.printStackTrace();
    }
}
    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}