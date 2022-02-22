package fxht;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import ht.Jasen;
import ht.Joukkue;
import ht.Ottelu;
import ht.Ottelut;
import ht.Seura;
import ht.Testit;
import ht.Virhe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import fxht.HTGUIController;

/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class LisaaottGUIController implements ModalControllerInterface<Ottelu>,Initializable {
    
	private Testit testi = new Testit();        
    @FXML private Label LabelVirhe;
    @FXML private TextField OttelunPaiva; // Value injected by FXMLLoader
    @FXML void OttelunPaivaAction(ActionEvent event) {
        //
    }

    @FXML private TextField OttelunAikaAloitus;// Value injected by FXMLLoader
    @FXML void OtelunAikaAloitusAction(ActionEvent event) {
        //
    }
    
    @FXML private TextField OttelunAikaLopetus; // Value injected by FXMLLoader
    @FXML void OtelunAikaLopetusAction(ActionEvent event) {
        //
    }
    

    @FXML private TextField OttelunPaikka; 
    @FXML private TextField Kotijoukkue;
    @FXML private TextField Vierasjoukkue;
    
    @FXML private Button OttelunLisays; // Value injected by FXMLLoader
    /**
     * @throws Virhe = virhe
     */
    @FXML void OttelunLisaysAction(ActionEvent event) throws Virhe {
        int i = 0;
        if ( otteluKohdalla != null) {
            while (i < edits.length) {
                if (edits[i].getText().length() == 0) {
                    naytaVirhe("Älä jätä tyhjiä kohtia");
                    return;
                }
                i++;
            }
            if(testi.PaivaSyntaksi(edits[0].getText()) == false){
                naytaVirhe("Korjaa päivämäärä");
                return;
            }
            if(testi.AikaSyntaksi(edits[1].getText()) == false){
                naytaVirhe("Korjaa aloitusaika");
                return;
            }
            if(testi.AikaSyntaksi(edits[2].getText()) == false){
                naytaVirhe(" Korjaa lopetusaika");
                return;
            }
            if(testi.oikeaAika(edits[1].getText(), edits[2].getText()) == false) {
                naytaVirhe(" Korjaa lopetusaika");
                return;
            }
            if(tarkistaJoukkue(edits[4].getText()) == -1){
                naytaVirhe("Kotijoukkuetta ei löydy");
                return;
            }
            if(tarkistaJoukkue(edits[5].getText()) == -1){
                naytaVirhe("Vierasjoukkuetta ei löydy");
                return;
            }
        }
        ModalController.closeStage(LabelVirhe);
    }

    @FXML private Button OttelunPeruuta; // Value injected by FXMLLoader
    @FXML void OttelunPeruutaAction(ActionEvent event) {
        Stage Peruuta = (Stage) OttelunPeruuta.getScene().getWindow();
        otteluKohdalla = null;
        Peruuta.close();
    }
    @FXML private MenuItem ApuaOtteluApua; // Value injected by FXMLLoader
    @FXML void ApuaOtteluApuaAction(ActionEvent event) {
        //
    }
    
	        
//      Aliohjelmat
//=====================================================================================================	        
    
    private Ottelu otteluKohdalla;
    private TextField edits[];
    private static ArrayList<Joukkue> Joukkueet = new ArrayList<Joukkue>();
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();  
    }

    private void alusta() {
       edits = new TextField[]{OttelunPaiva, OttelunAikaAloitus, OttelunAikaLopetus, OttelunPaikka, Kotijoukkue, Vierasjoukkue};
       int i = 0;
       for (TextField edit : edits) {
         final int k = ++i;
         edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
       }
    }
    
    /**
     * Tyhjentään tekstikentät 
     * @param edits tauluko jossa tyhjennettäviä tektsikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            edit.setText("");
    }

    /**
     * Etsii joukkueista tiettyä joukkuetta. Jos ei löydy, palauttaa -1
     * @param nimi = joukkue
     */
    private int tarkistaJoukkue(String nimi) {
        int i = 0;
        while(i < Joukkueet.size()) {
            if(Pattern.compile(Pattern.quote(nimi), Pattern.CASE_INSENSITIVE).matcher(Joukkueet.get(i).getJoukkueNimi()).find() == true) {
                return Joukkueet.get(i).getJoukkueTunnus();
            }
            if(Pattern.compile(Pattern.quote(nimi), Pattern.CASE_INSENSITIVE).matcher(Joukkueet.get(i).getLyhenne()).find() == true) {
                return Joukkueet.get(i).getJoukkueTunnus();
            }
            i++;
        }
        return -1;
    }
    
    /**
     * Käsitellään jäseneen tullut muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosJaseneen(int k, TextField edit) {
        if (otteluKohdalla == null) return;
        String s = edit.getText();
        StringBuilder sana = new StringBuilder();
        String virhe = null;
        switch (k) {
           case 1 : virhe = otteluKohdalla.setOtteluTiedot(s,4); break;
           case 2 : String aika1 = s + "-" + OttelunAikaLopetus.getText(); virhe = otteluKohdalla.setOtteluTiedot(aika1,5); break;
           case 3 : String aika2 = OttelunAikaAloitus.getText() + "-" + s; virhe = otteluKohdalla.setOtteluTiedot(aika2,5); break;
           case 4 : if(s.length() > 0)sana.append(s.substring(0, 1).toUpperCase()); sana.append(s.substring(1, s.length())); virhe = otteluKohdalla.setOtteluTiedot(sana.toString(),3); break;
           case 5 : virhe = otteluKohdalla.setOtteluTiedot(Integer.toString(tarkistaJoukkue(s)),1); break;
           case 6 : virhe = otteluKohdalla.setOtteluTiedot(Integer.toString(tarkistaJoukkue(s)),2); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit,"");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit,virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }

    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            LabelVirhe.setText("");
            LabelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        LabelVirhe.setText(virhe);
        LabelVirhe.getStyleClass().add("virhe");
    }

    @Override
    public Ottelu getResult() {
        return otteluKohdalla;
    }
    
    /**
     * mitä tehdään kun handle on näytetty
     */
    @Override
    public void handleShown() {
        OttelunPaikka.requestFocus();
        
    }

    @Override
    public void setDefault(Ottelu oletus) {
        otteluKohdalla = oletus;      
    }
    
    
   /**
    * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
    * @param modalityStage mille ollaan modaalisia, null = sovellukselle
    * @param oletus mitä dataan näytetään oletuksena
    * @param joukkueet = joukkueet
    * @return null jos painetaan Cancel, muuten täytetty tietue
    */
    public static Ottelu kysyOttelu(Stage modalityStage, Ottelu oletus, ArrayList<Joukkue> joukkueet) {
        Joukkueet = joukkueet;
        return ModalController.<Ottelu, LisaaottGUIController>showModal(
              LisaaottGUIController.class.getResource("HTGUIViewLisaaott.fxml"),
              "Seura",
              modalityStage, oletus, null 
          );
    }
}
