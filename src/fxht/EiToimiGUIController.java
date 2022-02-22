package fxht;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class EiToimiGUIController {
	        
	        
	        
//      Aliohjelmat
//=====================================================================================================	        

    @FXML private Button EiToimiNappi; // Value injected by FXMLLoader
//  Ei toimi ikkunan sulkeminen
  @FXML void EiToimiOk(ActionEvent event) {
      Stage ok = (Stage) EiToimiNappi.getScene().getWindow();
      ok.close();
  }
	
}
