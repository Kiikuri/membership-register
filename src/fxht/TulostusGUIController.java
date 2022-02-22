package fxht;


import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
//import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;


 
  /**
   * Tulostuksen hoitava luokka
   * 
   * @author vesal
   * @version 4.1.2016
   */
  public class TulostusGUIController implements ModalControllerInterface<String> {
      @FXML TextArea tulostusAlue;      
      @FXML private void handleOK() {
          ModalController.closeStage(tulostusAlue);
      }
  
      
      @FXML private void handleTulosta() {
          PrinterJob job = PrinterJob.createPrinterJob();
          if ( job != null && job.showPrintDialog(null) ) {
              WebEngine webEngine = new WebEngine();
              webEngine.loadContent("<pre>" + tulostusAlue.getText() + "</pre>");
              webEngine.print(job);
              job.endJob();
          }
      }
      
      @Override
      public String getResult() {
          return null;
      } 
  
      
      @Override
      public void setDefault(String oletus) {
          if ( oletus == null ) return;
          tulostusAlue.setText(oletus);
          System.out.println("1");
      }
  
      
      /**
       * Mitä tehdään kun dialogi on näytetty
       */
      @Override
      public void handleShown() {
          //
      }
      
      
      /**
       * @return alue johon tulostetaan
       */
      public TextArea getTextArea() {
          return tulostusAlue;
      }
      
      /**
       * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
       * @param modalityStage mille ollaan modaalisia, null = sovellukselle
       * @param oletus mitä dataan näytetään oletuksena
     * @return s
       */
       public static String kysyTeksti(Stage modalityStage, String oletus) {
           return ModalController.<String, TulostusGUIController>showModal(
                 TulostusGUIController.class.getResource("HTGUIViewTulostus.fxml"),
                 "Seura",
                 modalityStage, oletus, null 
             );
       }
  
  }