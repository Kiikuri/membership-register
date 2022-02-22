package fxht;


import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.StringGrid.GridRowItem;
import fi.jyu.mit.fxgui.StringGrid.OnGridCell;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import ht.Virhe;
import ht.Seura;
import ht.Joukkue;
import ht.Ottelu;
import ht.Ottelut;
import ht.Seura;
import ht.Testit;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class SeuranNimiGUIController implements ModalControllerInterface<String>,Initializable {
    //pääohjelma
    private String kansio = System.getProperty("user.dir") + File.separator + "data";
    ArrayList<Seura> seurat = new ArrayList<Seura>();

    @FXML
    private ListChooser<Seura> seuratChooser;

    @FXML
    private TextField seuratTeksti;
    
    @FXML
    private Label LabelOhje;

    @FXML
    private Button seuratLuo;

    @FXML
    private Button seuratPoista;

    @FXML
    private Button seuratAvaa;

    @FXML
    void seuratAvaaAction(ActionEvent event) {
        try {
        avaa();
        }
        catch(Exception e) {
            Dialogs.showMessageDialog("Valitse seura listasta");
            return;
        }
        ModalController.closeStage(seuratTeksti);
    }

    @FXML
    void seuratLuoAction(ActionEvent event) {
        uusiSeura();
    }

    @FXML
    void seuratPoistaAction(ActionEvent event) {
        if ( Dialogs.showQuestionDialog("Poista", "Poistetaanko seura: "+ seuraKohdalla.getSeuraNimi() + "? ", "Kyllä", "Ei")) poistaSeura();
    }
    
//===========================================================================================    
  // Aliohjelmat
    private Seura seuraKohdalla;
    private Seura seura;
    private String vastaus = null;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
       Alusta();  
    }
    
      @Override
      public String getResult() {
          return vastaus;
      }
  
      
      @Override
      public void setDefault(String oletus) {
          //
      }
  
      
      /**
       * Mitä tehdään kun dialogi on näytetty
       */
      @Override
      public void handleShown() {
          seuratTeksti.requestFocus();
      }
      
    private void avaa() {
        vastaus = seuraKohdalla.getSeuraNimi();
    }
    
    private void Alusta() {
        seuratChooser.clear();
        seuratChooser.addSelectionListener(e -> naytaSeura());
        File polku = new File(System.getProperty("user.dir") + File.separator + "data");
        File[] kansiot = polku.listFiles();
        int i = 0;
        while(i < kansiot.length) {
            if (kansiot[i].isDirectory()) {
                Seura uusi = new Seura();
                uusi.setSeuraNimi(kansiot[i].getName());
                seurat.add(uusi);
                seuratChooser.add(kansiot[i].getName(), uusi);;
            }
            i++;
        }
    }
    
    private void naytaSeura() {
        if (seuratChooser != null) seuraKohdalla = seuratChooser.getSelectedObject();
        if (seuraKohdalla == null) {
        return;
        }  
    }

    private int etsi(String args) {
        int i = 0;
        while(i < seurat.size()) {
            if(seurat.get(i).getSeuraNimi() == args) return i;
        }
        return -1;
    }
    
    /**
     * poistaa seuran
     */
    public void poistaSeura() {
        String polku = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        polku = polku + seuraKohdalla.getSeuraNimi();
        System.out.println(polku);
        File uusiKerho = new File(polku);
        File poistettava = new File(polku);
        File[] tiedostot = poistettava.listFiles();
        System.out.println(polku);
        int i = 0;
        while(i < tiedostot.length) {
            if (tiedostot[i].isFile()) {
                poistettava = tiedostot[i];
                System.out.println(poistettava.getName());
                try {
                poistettava.delete();
                }
                catch(Exception e) {
                    Dialogs.showMessageDialog("Ongelma tiedoston poistossa");
                }
            }
            i++;
        }
        uusiKerho.delete();
        Alusta();
    }
    /**
     * Uuden seuran luominen
     */
    public void uusiSeura() {
        String polku = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        polku = polku + seuratTeksti.getText().toLowerCase();
        Pattern erikois = Pattern.compile ("[!@#$%&*()+=|<>?{}\\[\\]~]");
        Matcher laiton = erikois.matcher(polku);
        if(seuratTeksti.getText() == null) {
            Dialogs.showMessageDialog("Anna seuralle nimi");
            return;
        }
        if(laiton.find() == true) {
            Dialogs.showMessageDialog("Ei sallittuja kirjaimia");
            return;
        }
        File uusiKerho = new File(polku);
        boolean bool = uusiKerho.mkdirs();
        if(!bool){
            Dialogs.showMessageDialog("On jo olemassa");
            seuratTeksti.clear();
            return;
        }
        Seura uusi = new Seura();
        uusi.setSeuraNimi(seuratTeksti.getText());
        seuratChooser.add(uusi.getSeuraNimi(), uusi);
        seurat.add(uusi);
        seuratTeksti.clear();
        try(FileWriter fileWriter = new FileWriter(polku + File.separator + "Jasenet.txt", true)) {
            fileWriter.write("");
        } catch (IOException e) {
            return;
        }
        try(FileWriter fileWriter = new FileWriter(polku + File.separator + "Ottelut.txt", true)) {
            fileWriter.write("");
        } catch (IOException e) {
            return;
        }
        try(FileWriter fileWriter = new FileWriter(polku + File.separator + "Joukkueet.txt", true)) {
            fileWriter.write("");
        } catch (IOException e) {
            return;
        }
    }
    
          /**
           * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
           * @param modalityStage mille ollaan modaalisia, null = sovellukselle
           * @param oletus mitä nimeä näytetään oletuksena
           * @return null jos painetaan Cancel, muuten kirjoitettu nimi
           */
          public static String kysySeura(Stage modalityStage, String oletus) {
              return ModalController.showModal(
                      SeuranNimiGUIController.class.getResource("HTGUIViewSeuranNimi.fxml"),
                      "Seura",
                      modalityStage, oletus);
     }
}
