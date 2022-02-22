package fxht;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class InfoGUIController {
	        
    @FXML private Button InfoValmis;

    @FXML void InfoValmisAction(ActionEvent event) {
        Stage Peruuta = (Stage) InfoValmis.getScene().getWindow();
        Peruuta.close();
    }
	        
//      Aliohjelmat
//=====================================================================================================	        
    /**
     * Ei toimi viesti
     */
public void EiToimi() {
    try {
    FXMLLoader ikkuna = new FXMLLoader(getClass().getResource("HTGUIViewEiToimi.fxml"));
    Parent root1 = (Parent) ikkuna.load();
    Stage EiToimi = new Stage();
    EiToimi.initStyle(StageStyle.UNDECORATED);
    EiToimi.setTitle("Ei Toimi");
    EiToimi.setScene(new Scene(root1));;
    EiToimi.show();
    } catch (Exception eiToimi) {
    	eiToimi.printStackTrace();
    }
}
}
