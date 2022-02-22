package fxht;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import ht.Jasen;
import ht.Joukkue;
import ht.Testit;
import javafx.event.ActionEvent;
//import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class MuokkaaGUIController implements ModalControllerInterface<Jasen>,Initializable  {
    private Jasen jasenKohdalla;
    private Testit testi = new Testit();
    private TextField edits[];
    private static ArrayList<Joukkue> Joukkueet = new ArrayList<Joukkue>();

    @FXML private GridPane JasenPane;
    @FXML private Label LabelVirhe;

    @FXML private Button MuokkaaValmis;
    @FXML void MuokkaaValmisAction(ActionEvent event) {
        if ( jasenKohdalla != null) {
            int i = 0;
            while (i < edits.length) {
                if (edits[i].getText().length() == 0) {
                    naytaVirhe("Älä jätä tyhjiä kohtia");
                    return;
                }
                i++;
            }
            if(testi.PaivaSyntaksi(edits[8].getText()) == false){
                naytaVirhe("Korjaa päivämäärä");
                return;
            }
            if(testi.hetuSyntaksi(edits[2].getText()) == false){
                naytaVirhe("Korjaa henkilötunnus");
                return;
            }
            if(testi.puhelinSyntaksi(edits[6].getText()) == false){
                naytaVirhe("Korjaa puhelinnumero");
                return;
            }
            if(testi.postinSyntaksi(edits[4].getText()) == false){
                naytaVirhe("Korjaa postinumero");
                return;
            }
        }
        ModalController.closeStage(LabelVirhe);
    }
    @FXML private Button MuokkaaPeruuta;
    @FXML void MuokkaaPeruutaAction(ActionEvent event) {
        jasenKohdalla = null;
        Stage Peruuta = (Stage) MuokkaaPeruuta.getScene().getWindow();
        Peruuta.close();
    }
    @FXML private TextField MuokkaaEtu;
    @FXML void MuokkaaEtuAction(ActionEvent event) {
       //
    }
    @FXML private TextField MuokkaaSuku;
    @FXML void MuokkaaSukuAction(ActionEvent event) {
       //
    }
    @FXML private TextField MuokkaaHetu;
    @FXML void MuokkaaHetuAction(ActionEvent event) {
       //
    }
    @FXML private TextField MuokkaaOsoite;
    @FXML void MuokkaaOsoiteAction(ActionEvent event) {
       //
    }
    @FXML private TextField MuokkaaPostiN;
    @FXML void MuokkaaPostiNAction(ActionEvent event) {
      //
    }

    @FXML private TextField MuokkaaPostiO;
    @FXML void MuokkaaPostiOAction(ActionEvent event) {
        //
    }
    @FXML private TextField MuokkaaPuhelin;
    @FXML void MuokkaaPuhelinAction(ActionEvent event) {
        //
    }
    @FXML private TextField MuokkaaMaksu;

    @FXML private TextField MuokkaaPaiva;
    @FXML void MuokkaaPaivaAction(ActionEvent event) {
       //
    }
    @FXML private TextField MuokkaaJoukkue;
    @FXML void MuokkaaJoukkueAction(ActionEvent event) {
       //
    }

	        
//      Aliohjelmat
//=====================================================================================================	        

    
        @Override
        public void initialize(URL url, ResourceBundle bundle) {
           alusta();  
        }

 
        private void alusta() {
           edits = new TextField[]{MuokkaaEtu, MuokkaaSuku, MuokkaaHetu, MuokkaaOsoite, MuokkaaPostiN, MuokkaaPostiO, MuokkaaPuhelin, MuokkaaMaksu, MuokkaaPaiva, MuokkaaJoukkue};
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

        private int etsiJoukkue() {
            int i = 0;
            while(i < Joukkueet.size()) {
                if(Pattern.compile(Pattern.quote(MuokkaaJoukkue.getText()), Pattern.CASE_INSENSITIVE).matcher(Joukkueet.get(i).getJoukkueNimi()).find() == true) {
                    return Joukkueet.get(i).getJoukkueTunnus();
                }
                if(Pattern.compile(Pattern.quote(MuokkaaJoukkue.getText()), Pattern.CASE_INSENSITIVE).matcher(Joukkueet.get(i).getLyhenne()).find() == true) {
                    return Joukkueet.get(i).getJoukkueTunnus();
                }
                i++;
            }
            jasenKohdalla.setTempJoukkueNimi(MuokkaaJoukkue.getText());
            jasenKohdalla.setTempJoukkue(i);
            return i;
        }
        
        /**
         * Käsitellään jäseneen tullut muutos
         * @param edit muuttunut kenttä
         */
        private void kasitteleMuutosJaseneen(int k, TextField edit) {
            if (jasenKohdalla == null) return;
            String s = edit.getText();
            StringBuilder sana = new StringBuilder();
            String virhe = null;
            switch (k) {
               case 1 : if(s.length() > 1) sana.append(s.substring(0, 1).toUpperCase()); sana.append(s.substring(1, s.length())); virhe = jasenKohdalla.set0(sana.toString()); break;
               case 2 : if(s.length() > 1) sana.append(s.substring(0, 1).toUpperCase()); sana.append(s.substring(1, s.length())); virhe = jasenKohdalla.set1(sana.toString()); break;
               case 3 : virhe = jasenKohdalla.set2(s); break;
               case 4 : if(s.length() > 1) sana.append(s.substring(0, 1).toUpperCase()); sana.append(s.substring(1, s.length())); virhe = jasenKohdalla.set3(sana.toString()); break;
               case 5 : virhe = jasenKohdalla.set4(s); break;
               case 6 : if(s.length() > 1) sana.append(s.substring(0, 1).toUpperCase()); sana.append(s.substring(1, s.length())); virhe = jasenKohdalla.set5(sana.toString()); break;
               case 7 : virhe = jasenKohdalla.set6(s); break;
               case 8 : virhe = jasenKohdalla.set7(s); break;
               case 9 : virhe = jasenKohdalla.set8(s); break;
               case 10 : virhe = jasenKohdalla.setJoukkue(etsiJoukkue()); break;
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
	    
	    /**
	     * Näytetään jäsenen tiedot TextField komponentteihin
	     * @param edits taulukko jossa tekstikenttiä
	     * @param jasen näytettävä jäsen
	     */
	    public static void naytaJasen(TextField[] edits, Jasen jasen) {
	        if (jasen == null) return;
	        edits[0].setText(jasen.get0());
	        edits[1].setText(jasen.get1());
	        edits[2].setText(jasen.get2());
	        edits[3].setText(jasen.get3());
	        edits[4].setText(jasen.get4());
	        edits[5].setText(jasen.get5());
	        edits[6].setText(jasen.get6());
	        edits[7].setText(jasen.get7());
	        edits[8].setText(jasen.get8());
	        edits[9].setText(annaJoukkue(jasen.get9()));
	    }
	    
        private static String annaJoukkue(int index) {
            int i = 0;
            while (i < Joukkueet.size()) {
               if(Joukkueet.get(i).getJoukkueTunnus() == index) return Joukkueet.get(i).getJoukkueNimi();
               i++;
            }
            return null;
        }

        @Override
        public Jasen getResult() {
            return jasenKohdalla;
        }
        
        /**
         * mitä tehdään kun handle on näytetty
         */
        @Override
        public void handleShown() {
            MuokkaaEtu.requestFocus();
            
        }

        @Override
        public void setDefault(Jasen oletus) {
            jasenKohdalla = oletus;
            jasenKohdalla.set7("ok");
            naytaJasen(edits, jasenKohdalla);
            
        }
	    
        
       /**
        * Luodaan jäsenen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
        * @param modalityStage mille ollaan modaalisia, null = sovellukselle
        * @param oletus mitä dataan näytetään oletuksena
        * @param joukkueet = joukkueet
        * @return null jos painetaan Cancel, muuten täytetty tietue
        */
        public static Jasen kysyJasen(Stage modalityStage, Jasen oletus, ArrayList<Joukkue> joukkueet) {
            Joukkueet = joukkueet;
            return ModalController.<Jasen, MuokkaaGUIController>showModal(
                  MuokkaaGUIController.class.getResource("HTGUIViewMuokkaa.fxml"),
                  "Seura",
                  modalityStage, oletus, null 
              );
        }
}
