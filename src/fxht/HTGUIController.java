package fxht;


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.StringGrid;
import ht.Virhe;
import ht.Jasen;
import ht.Ottelu;
import ht.Seura;
import ht.Testit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
//import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class HTGUIController implements Initializable {
    //pääohjelma
    private Testit testi = new Testit();
    private String seuranNimi;
    private String kansio;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        try {
            alusta();
        } catch (Virhe e) {
            e.printStackTrace();
        }
    }
    
    @FXML private TextField Hakuehto; // Value injected by FXMLLoader
    @FXML void HakuehtoAction(ActionEvent event) {
        jasenenHaku();
    }
    
    @FXML
    private ComboBoxChooser<String> JasenetLajittelu;
    
    @FXML
    void JasenetLajitteluAction(ActionEvent event) {
        lajittele(JasenetLajittelu.getSelectedObject());
    }
    
    @FXML private ListChooser<Jasen> chooserJasenet; // Value injected by FXMLLoader
    @FXML private TextField Menu0;
    @FXML private TextField Menu1;
    @FXML private TextField Menu2;
    @FXML private TextField Menu3;
    @FXML private TextField Menu4;
    @FXML private TextField Menu5;
    @FXML private TextField Menu6;
    @FXML private TextField Menu7;
    @FXML private TextField Menu8;
    @FXML private TextField Menu9;
    @FXML private ScrollPane PanelJasenet;
    @FXML private StringGrid<Ottelu> OttelutSG; // Value injected by FXMLLoader
    @FXML private TextField MenuJoukkueHaku; // Value injected by FXMLLoader
    
    @FXML void MenuJoukkueHakuAction(ActionEvent event) {
        ottelunHaku();
    }
    @FXML private Button MuokkaaJasen; // Value injected by FXMLLoader
//    Jäsenen tietojen muokkaamis ikkunan avaaminen
    @FXML void MuokkaaJasenAction(ActionEvent event) {
        MuokkaaJasen();
    }
    @FXML private Menu MenubarTiedosto; // Value injected by FXMLLoader
    @FXML private MenuItem TiedostoTulosta;
    @FXML void TiedostoTulostaAction(ActionEvent event) {
        try {
            int rivi = OttelutSG.getRowNr();
            if (rivi < 0) throw new Exception();
            Ottelu valittu = Seura.annaOttelu(rivi);
            if (valittu == null) {
                throw new Exception();
            }
            tulostaO(valittu);
            paivitaOttelut();
        }
        catch(Exception e) {
            System.out.println(jasenKohdalla);
            if(jasenKohdalla == null) {
                Dialogs.showMessageDialog("Valitse tulostettava jäsen tai ottelu");
                return;
            }
            tulostaJ(jasenKohdalla);
            jasenKohdalla = null;
        }
    }
    @FXML private MenuItem TiedostoTallenna;
    @FXML void TiedostoTallennaAction(ActionEvent event) {
        tallenna();
    }
    @FXML private MenuItem TiedostoAvaa; // Value injected by FXMLLoader
    @FXML void TiedostoAvaaAction(ActionEvent event) {
        tallenna();
        try {
            alusta();
        } catch (Virhe e) {
            Dialogs.showMessageDialog("Ongelmia avaamisessa");
        }
    }
    @FXML private MenuItem TiedostoLopeta; // Value injected by FXMLLoader
//    Ohjelman sulkeminen
    @FXML void TiedostoLopetaAction(ActionEvent event) {
        if(voikoSulkea() == true) Platform.exit();
    }
    @FXML private Menu MenubarApua; // Value injected by FXMLLoader
    @FXML private MenuItem ApuaApua; // Value injected by FXMLLoader
    @FXML void ApuaApuaAction(ActionEvent event) throws IOException, URISyntaxException {
        Desktop apua = Desktop.getDesktop();
        apua.browse(new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/rihumwv"));
    }
    @FXML private MenuItem ApuaInfo; // Value injected by FXMLLoader
    @FXML void ApuaInfoAction(ActionEvent event) {
        info();
    }

    @FXML private Button UusiJasen; // Value injected by FXMLLoader
//    Jäsenen lisäämisen ikkunan avaaminen
    @FXML void UusiJasenAction(ActionEvent event) {
        uusiJasen();
    }
    @FXML private Button TallennaMenu; // Value injected by FXMLLoader
    @FXML void TallennaMenuAction(ActionEvent event) {
        tallenna();
    }
    @FXML private Button PoistaJasen; // Value injected by FXMLLoader
//    Jäsenen poistamisen ikkunan avaaminen
    @FXML void PoistaJasenAction(ActionEvent event) {
        poistaJasen();
    }
    @FXML private Button PoistaOttelu; // Value injected by FXMLLoader
//    Ottelun poistamisen ikkunan avaaminen
    @FXML void PoistaOtteluAction(ActionEvent event) {
        poistaOttelu();
    }
    @FXML private Button LisaaOttelu; // Value injected by FXMLLoader
//    Ottelun lisäämisen ikkunan avaaminen
    /**
     * @throws Virhe  = virhe
     */
    @FXML void LisaaOtteluAction(ActionEvent event) throws Virhe {
        uusiOttelu();
    }
    
//===========================================================================================    
  // Aliohjelmat
    private Jasen jasenKohdalla;
    private Seura Seura = new Seura();
    private boolean sulje = false;
    
    /**
     * Tekee tarvittavat alustukset.
     * Alustetaan myös jäsenlistan kuuntelija.
     * @throws Virhe = virhe
     */
    protected void alusta() throws Virhe {
        kansio = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        seuranNimi = null;
        jasenKohdalla = null;
        sulje = false;
        Seura.alustaSeura();
        avaa();
        kansio = kansio+seuranNimi + File.separator;
        chooserJasenet.clear();
        chooserJasenet.addSelectionListener(e -> naytaJasen(null));
        AlustaGrid();
        if(sulje == false)lue(kansio);
        if(Seura.getJoukkueet() < 1) Seura.seuraJoukkueTesti();
        alustaTiedot();
        JasenetLajittelu.clear();
        JasenetLajittelu.add("Tunnus");
        JasenetLajittelu.add("Etunimi");
        JasenetLajittelu.add("Sukunimi");
        JasenetLajittelu.add("Joukkue");
        JasenetLajittelu.add("Postinumero");
        JasenetLajittelu.add("Paikkakunta");
        JasenetLajittelu.add("Liittynyt");
        JasenetLajittelu.add("Jäsenmaksu");
        JasenetLajittelu.getSelectionModel().select(0);
        lajittele("Tunnus");
    }  
    
    /**
     * lajittelee jäsenlistan
     * @param miten = ehto
     * @throws Virhe = virhe
     */
    private void lajittele(String miten) {
        System.out.println(miten);
        int i = 0;
        int j = 0;
        int a = 0;
        int b = 0;
        int jatko = 1;
        Boolean check = false;
        Jasen p;
        ArrayList<Jasen> jasenet = Seura.annaJasenet();
        switch(miten)
        {
        case "Tunnus":
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Etunimi":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    if(check == false) a = jasenet.get(j).get0().charAt(0);
                    if(check == false) b = jasenet.get(j+1).get0().charAt(0);
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                        check = false;
                    }
                    if(a == b && jatko < jasenet.get(j+1).get0().length() && jatko < jasenet.get(j).get0().length()) {
                        a = jasenet.get(j).get0().charAt(jatko);
                        b = jasenet.get(j+1).get0().charAt(jatko);
                        check = true;
                        jatko++;
                    }
                    if(jatko >= jasenet.get(j+1).get0().length() | jatko >= jasenet.get(j).get0().length()) check = false;
                    if(a < b) check = false;
                    if(check == false)j++;
                }
                jatko = 1;
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Sukunimi":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    if(check == false) a = jasenet.get(j).get1().charAt(0);
                    if(check == false) b = jasenet.get(j+1).get1().charAt(0);
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                        check = false;
                    }
                    if(a == b && jatko < jasenet.get(j+1).get1().length() && jatko < jasenet.get(j).get1().length()) {
                        a = jasenet.get(j).get1().charAt(jatko);
                        b = jasenet.get(j+1).get1().charAt(jatko);
                        check = true;
                        jatko++;
                    }
                    if(jatko >= jasenet.get(j+1).get1().length() | jatko >= jasenet.get(j).get1().length()) check = false;
                    if(a < b) check = false;
                    if(check == false)j++;
                }
                jatko = 1;
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Joukkue":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    if(check == false) a = Seura.annaJoukkue(jasenet.get(j).get9()).getJoukkueNimi().charAt(0);
                    if(check == false) b = Seura.annaJoukkue(jasenet.get(j+1).get9()).getJoukkueNimi().charAt(0);
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                        check = false;
                    }
                    if(a == b && jatko < Seura.annaJoukkue(jasenet.get(j+1).get9()).getJoukkueNimi().length() && jatko < Seura.annaJoukkue(jasenet.get(j).get9()).getJoukkueNimi().length()) {
                        a = Seura.annaJoukkue(jasenet.get(j).get9()).getJoukkueNimi().charAt(jatko);
                        b = Seura.annaJoukkue(jasenet.get(j+1).get9()).getJoukkueNimi().charAt(jatko);
                        check = true;
                        jatko++;
                    }
                    if(jatko >= Seura.annaJoukkue(jasenet.get(j+1).get9()).getJoukkueNimi().length() | jatko >= Seura.annaJoukkue(jasenet.get(j).get9()).getJoukkueNimi().length()) check = false;
                    if(a < b) check = false;
                    if(check == false)j++;
                }
                jatko = 1;
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Postinumero":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    a = Integer.parseInt(jasenet.get(j).get4());
                    b = Integer.parseInt(jasenet.get(j+1).get4());
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                    }
                    j++;
                }
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Paikkakunta":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    if(check == false) a = jasenet.get(j).get5().charAt(0);
                    if(check == false) b = jasenet.get(j+1).get5().charAt(0);
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                        check = false;
                    }
                    if(a == b && jatko < jasenet.get(j+1).get5().length() && jatko < jasenet.get(j).get5().length()) {
                        a = jasenet.get(j).get5().charAt(jatko);
                        b = jasenet.get(j+1).get5().charAt(jatko);
                        check = true;
                        jatko++;
                    }
                    if(jatko >= jasenet.get(j+1).get5().length() | jatko >= jasenet.get(j).get5().length()) check = false;
                    if(a < b) check = false;
                    if(check == false)j++;
                }
                jatko = 1;
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Liittynyt":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    a = Integer.parseInt(jasenet.get(j).get8().substring(0,2)) + (Integer.parseInt(jasenet.get(j).get8().substring(3,5)) * 30) + (Integer.parseInt(jasenet.get(j).get8().substring(6,10)) * 365);
                    b = Integer.parseInt(jasenet.get(j+1).get8().substring(0,2)) + (Integer.parseInt(jasenet.get(j+1).get8().substring(3,5)) * 30) + (Integer.parseInt(jasenet.get(j+1).get8().substring(6,10)) * 365);
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                    }
                    j++;
                    a = 0;
                    b = 0;
                }
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        case "Jäsenmaksu":
            while (i < jasenet.size())
            {
                while (j < jasenet.size() - 1)
                {
                    if(jasenet.get(j).get7() == "ok") a = 1;
                    if(jasenet.get(j).get7() == "Myöhässä") a = 0;
                    if(jasenet.get(j+1).get7() == "ok") b = 1;
                    if(jasenet.get(j+1).get7() == "Myöhässä") b = 0;
                    if (a > b)
                    {
                        p = jasenet.get(j+1);
                        jasenet.set(j+1, jasenet.get(j));
                        jasenet.set(j, p);
                    }
                    j++;
                }
                j = 0;
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < jasenet.size()) {
                haeJ(jasenet.get(i));
                i++;
            }
            break;
            
        default:
            Dialogs.showMessageDialog("Ongelmia lajittelussa");
            break;
        }
    }
    /**
     * Jäsenen luonti
     */
    protected void uusiJasen() {
        if(Seura.getJasenet() < 20) 
        {
            try {
                Jasen uusi = new Jasen();
                uusi = MuokkaaGUIController.kysyJasen(null, uusi, Seura.annaJoukkueet());  
                if ( uusi == null ) return;
                int i = 0;
                String[] tiedot = uusi.annaTiedot();
                while(i < tiedot.length) {
                    if(tiedot[i].length() <= 0) return;
                    i++;
                }
                if(testi.hetuSyntaksi(uusi.get2()) == false) return;
                if(testi.postinSyntaksi(uusi.get4()) == false) return;
                if(testi.puhelinSyntaksi(uusi.get6()) == false) return;
                if(testi.PaivaSyntaksi(uusi.get8()) == false) return;
                if (uusi.getTempJoukkue() != 0) {
                    try {
                        Seura.luoJoukkue(uusi.getTempJoukkueNimi()); 
                    }
                    catch (Virhe e) {
                        Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
                        uusi.setJoukkue(0);
                        return;
                    }
                }
                uusi.rekisteroi();
                Seura.lisaaJ(uusi);
                haeJ(uusi);
                naytaJasen(uusi);
            } 
            catch (Virhe e) {
               Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());      
               return;
            }
        }
        else Dialogs.showMessageDialog("Liikaa jäseniä");
    }
    
    
    /**
     * Jäsenen Muokkaus
     */
    protected void MuokkaaJasen() {
        try {
           Jasen uusi = jasenKohdalla;
           Jasen varmuus = new Jasen();
           int i = 0;
           String[] tiedot = uusi.annaTiedot();
           while(i < tiedot.length) {
           varmuus.setTiedot(tiedot[i], i+1);
           i++;
           }
           uusi = MuokkaaGUIController.kysyJasen(null, uusi, Seura.annaJoukkueet());
           if ( uusi == null ) return;
           tiedot = varmuus.annaTiedot();
           i = 0;
           while(i < tiedot.length) {
               if(tiedot[i].length() <= 0) {uusi.palautaTiedot(tiedot); return;}
               i++;
           }
           i = 0;
           if(testi.hetuSyntaksi(uusi.get2()) == false) {uusi.palautaTiedot(tiedot); return;}
           if(testi.postinSyntaksi(uusi.get4()) == false) {uusi.palautaTiedot(tiedot); return;}
           if(testi.puhelinSyntaksi(uusi.get6()) == false){uusi.palautaTiedot(tiedot); return;}
           if(testi.PaivaSyntaksi(uusi.get8()) == false) {uusi.palautaTiedot(tiedot); return;}
           if (uusi.getTempJoukkue() != 0) {
               try {
               Seura.luoJoukkue(uusi.getTempJoukkueNimi()); 
               }
               catch (Virhe e) {
                   Dialogs.showMessageDialog("Ongelmia uuden joukkueen luomisessa " + e.getMessage());
                   uusi.palautaTiedot(tiedot);
                   return;
               }
           }
           paivitaJasenet();
           naytaJasen(uusi);
        }
        catch(Exception e) {
           Dialogs.showMessageDialog("Valitse jäsen listasta");
           return;
        }
    }
        
    /**
     * Uuden ottelun luonti
     */
    protected void uusiOttelu() {
        Ottelu uusi = new Ottelu();
        uusi = LisaaottGUIController.kysyOttelu(null, uusi, Seura.annaJoukkueet());  
        if ( uusi == null ) return;
        ArrayList<String> tiedot = uusi.annaOtteluTiedot();
        int i = 0;
        while(i < tiedot.size()) {
            if(tiedot.get(i).length() <= 0) return;
            i++;
        }
        try {
            try {
                Seura.annaJoukkue(Integer.parseInt(tiedot.get(0))).getLyhenne();
                Seura.annaJoukkue(Integer.parseInt(tiedot.get(1))).getLyhenne();
            }
            catch(Exception e){
                Seura.annaJoukkue(Integer.parseInt(tiedot.get(0))).getLyhenne();
                Seura.annaJoukkue(Integer.parseInt(tiedot.get(1))).getLyhenne();
            }
        }
        catch(Exception e) {
            return;
        }
        if(testi.PaivaSyntaksi(tiedot.get(3)) == false) return;
        if(testi.AikaSyntaksi(tiedot.get(4).substring(0, tiedot.get(4).indexOf('-'))) == false) return;
        if(testi.AikaSyntaksi(tiedot.get(4).substring(tiedot.get(4).indexOf('-')+1, tiedot.get(4).length())) == false) return;
        uusi.rekisteroiO();
        Seura.lisaaO(uusi);
        haeO(uusi);
    }
    
    /**
     * Poistaa valmiiksi luodun jäsenen, vaihetta 5 varten
     */
    protected void poistaJasen() {
        if (chooserJasenet != null) jasenKohdalla = chooserJasenet.getSelectedObject();
        if (jasenKohdalla != null) {    
            jasenKohdalla.getTunnus();
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko jäsen: " + jasenKohdalla.getNimi(), "Kyllä", "Ei") )
        return;
            Seura.poistaJasen(jasenKohdalla.getTunnus());
            paivitaJasenet();
            naytaJasen(null);
        }
    }
    
    /**
     * Poistaa valmiiksi luodun ottelun, vaihetta 5 varten
     */
    protected void poistaOttelu() {
        int rivi = OttelutSG.getRowNr();
        if (rivi < 0) return;
        Ottelu valittu = Seura.annaOttelu(rivi);
        if (valittu == null) {
            return;
        }
        int id = valittu.getOtteluTunnus();
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko ottelu numero: " + (id+1), "Kyllä", "Ei") )
        return;
        Seura.poistaOttelu(id);
        paivitaOttelut();
    }
    
    
    /**
     * Poistaa joukkueen
     */
    protected void poistaJoukkue() {
        int id = 0;
        Seura.poistaJoukkue(id);
    }
    
    /**
     * StringGridin alustaminen
     */
    public void AlustaGrid() {
        OttelutSG.clear();
        OttelutSG.setRivit("Nro |Koti | Vieras | Paikka | Päivä | Aika");
        double numeroLeveys = 29;
        OttelutSG.setColumnWidth(0, numeroLeveys);
        int i = 1;
        while (i < OttelutSG.getColumns().size()) {
        OttelutSG.setColumnWidth(i, (OttelutSG.getPrefWidth()-numeroLeveys)/(OttelutSG.getColumns().size()-1));
        i++;
        }   
    }

    
    /**
     * päivittää ottelut
     */
    public void paivitaOttelut() {
        OttelutSG.clear();
        int i = 0;
        while(i < Seura.getOttelut()) {
            haeO(Seura.annaOttelu(i));
            i++;
        }
    }
    
    /**
     * Alustetaan jäsenistö ja ottelut listaan.
     */
    public void alustaTiedot() {
        ArrayList<Ottelu> ott = Seura.annaOttelut();
        ArrayList<Jasen> jas = Seura.annaJasenet();
        int i = 0;
        while(i < Seura.getJasenet()) {
            haeJ(jas.get(i));
            i++;
        }
        i = 0;
        while(i < Seura.getOttelut()) {
            haeO(ott.get(i));
            i++;
        }
    }
    
    /**
     * Tulostaa jäsenen tiedot
     * @param jasen tulostettava jäsen
     */
    public void tulostaJ(final Jasen jasen) {
        StringBuilder builder = new StringBuilder();
        builder.append("            Jäsenen tiedot" + "\n");
        builder.append("----------------------------------------------" + "\n");
        builder.append(jasen.tulosta(false));
        builder.append("  Joukkue:      " + Seura.annaJoukkue(jasen.get9()).getJoukkueNimi() + " ("+ Seura.annaJoukkue(jasen.get9()).getLyhenne() + ")" + "\n");
        builder.append("----------------------------------------------" + "\n");
        TulostusGUIController.kysyTeksti(null, builder.toString());   
    }
    
    /**
     * Tulostaa ottelun tiedot
     * @param ott tulostettava ottelu
     */
    public void tulostaO(final Ottelu ott) {
        StringBuilder builder = new StringBuilder();
        builder.append("                 Ottelun tiedot" + "\n");
        builder.append("----------------------------------------------" + "\n");
        builder.append("  Koti joukkue:   " + Seura.annaJoukkue(Integer.parseInt(ott.getKotiJ())).getJoukkueNimi() + " ("+ Seura.annaJoukkue(Integer.parseInt(ott.getKotiJ())).getLyhenne() + ")" + "\n");
        builder.append("  Vieras joukkue: " + Seura.annaJoukkue(Integer.parseInt(ott.getVierasJ())).getJoukkueNimi() + " ("+ Seura.annaJoukkue(Integer.parseInt(ott.getVierasJ())).getLyhenne() + ")" + "\n");
        builder.append("  Paikka:         " + ott.getPaikka() + "\n");
        builder.append("  Päivä:          " + ott.getPaiva() + "\n");
        builder.append("  Aika:           " + ott.getAika() + "\n");
        builder.append("----------------------------------------------" + "\n");
        TulostusGUIController.kysyTeksti(null, builder.toString());   
    }
    
    /**
     * Uuden seuran luominen
     */
    public void uusiSeura() {
        Scanner sc = new Scanner(System.in);
        String polku = System.getProperty("user.dir") + File.separator + "data" + File.separator;
        System.out.println("Anna uuden seuran nimi: ");
        polku = polku + sc.next().toLowerCase();
        Pattern erikois = Pattern.compile ("[!@#$%&*()+=|<>?{}\\[\\]~]");
        Matcher laiton = erikois.matcher(polku);
        if(laiton.find() == true) {
            System.out.println("Väärä syntaksi");
            return;
        }
        System.out.println("path = " + polku);
        File uusiKerho = new File(polku);
        boolean bool = uusiKerho.mkdirs();
        if(bool){
           System.out.println("luotiin");
        }else{
           System.out.println("On jo olemassa");
        }
        sc.close();
    }
    
    /**
     * Jäsenen hakeminen hakukentän avulla
     */
    public void jasenenHaku() {
        String hakuehto = Hakuehto.getText();
        int i = 0;
        int l = 0;
        try {
            int numero = Integer.parseInt(hakuehto);
            if(Seura.getJasenet() >= numero) {
                chooserJasenet.clear();
                haeJ(Seura.annaJasen(numero));
            }
            else throw new Exception();
        }
        catch(Exception e)
        {
            ArrayList<Jasen> jasenet = Seura.annaJasenet();
            ArrayList<Jasen> loydetyt = new ArrayList<Jasen>();
            while(i < jasenet.size()) {
                String[] tiedot = jasenet.get(i).annaTiedot();
                String nimi = Integer.toString(jasenet.get(i).getTunnus()) + " ";
                l = 0;
                while(l < tiedot.length -1) {
                    nimi = nimi + tiedot[l] + " ";
                    l++;
                }
                nimi = nimi + Seura.annaJoukkue(Integer.parseInt(tiedot[l])).getJoukkueNimi() + " " + Seura.annaJoukkue(Integer.parseInt(tiedot[l])).getLyhenne();
                if(Pattern.compile(Pattern.quote(hakuehto), Pattern.CASE_INSENSITIVE).matcher(nimi).find() == true) loydetyt.add(jasenet.get(i));
                i++;
            }
            i = 0;
            chooserJasenet.clear();
            while(i < loydetyt.size()) {
                haeJ(loydetyt.get(i));
                i++;
            }
            Hakuehto.clear();
        }
    }
    
    /**
     * ottelun hakeminen hakukentän avulla
     */
    public void ottelunHaku() {
        String hakuehto = MenuJoukkueHaku.getText();
        int i= 0;
        int l = 0;
        try {
            int numero = Integer.parseInt(hakuehto)-1;
            if(Seura.getOttelut() >= numero) {
                OttelutSG.clear();
                haeO(Seura.annaOttelu(numero));
            }
            else throw new Exception();
        }
        catch(Exception e)
        {
        ArrayList<Ottelu> ottelut = Seura.annaOttelut();
        ArrayList<Ottelu> loydetyt = new ArrayList<Ottelu>();
        while(i < ottelut.size()) {
            ArrayList<String> koti = Seura.annaJoukkue(Integer.parseInt(ottelut.get(i).getKotiJ())).annaJoukkueTiedot();
            ArrayList<String> vieras = Seura.annaJoukkue(Integer.parseInt(ottelut.get(i).getVierasJ())).annaJoukkueTiedot();
            ArrayList<String> tiedot = ottelut.get(i).annaOtteluTiedot();
            StringBuilder nimi = new StringBuilder();
            nimi.append(ottelut.get(i).getOtteluTunnus() + " ");
            l = 0;
            while (l<koti.size()) {
                nimi.append(koti.get(l) + " ");
                nimi.append(vieras.get(l) + " ");
                l++;
            }
            l = 2;
            while (l < tiedot.size()) {
                nimi.append(tiedot.get(l) + " ");
                l++;
            }
            if(Pattern.compile(Pattern.quote(hakuehto), Pattern.CASE_INSENSITIVE).matcher(nimi).find() == true) loydetyt.add(ottelut.get(i));
            i++;
        }
        i = 0;
        OttelutSG.clear();
        while(i < loydetyt.size()) {
            haeO(loydetyt.get(i));
            i++;
        }
      }
      MenuJoukkueHaku.clear();
    }
    
    
    /**
     * Näyttää valitun jäsenen, mikäli annettu parametri on null, muuten parametrin jäsen
     * @param jasen = näytettävä jäsen
     */
    protected void naytaJasen(Jasen jasen) {
        paivitaOttelut();
    	if (jasen == null && chooserJasenet != null) jasenKohdalla = chooserJasenet.getSelectedObject();
        if (jasen == null && jasenKohdalla == null) {
        return;
        }  
        if(jasen != null) jasenKohdalla = jasen;
        Object[] menu= {Menu0, Menu1, Menu2, Menu3, Menu4, Menu5, Menu6, Menu7, Menu8, };
        String[] tiedot = jasenKohdalla.annaTiedot();
        int i = 0;
        while (i < 9) {
            ((TextInputControl) menu[i]).setText(tiedot[i]);
            i++;
        }
        Menu9.setText(Seura.annaJoukkue(jasenKohdalla.get9()).getJoukkueNimi());
    }
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
    
    /**
     * Ottelun lisäys listaan ja seuraan
     * @param ott = ottelu
     */
    public void haeO(Ottelu ott) {
        String index = Integer.toString(ott.getOtteluTunnus() + 1);
        String kotiJ = Seura.annaJoukkue(Integer.parseInt(ott.getKotiJ())).getLyhenne();
        String vierasJ = Seura.annaJoukkue(Integer.parseInt(ott.getVierasJ())).getLyhenne();
        Seura.annaJoukkue(Integer.parseInt(ott.getKotiJ()));
        String[] ottS = {index, kotiJ, vierasJ, ott.getPaikka(), ott.getPaiva(), ott.getAika()};
        OttelutSG.add(ottS);
     }
    
    /**
     * Jäsenen lisäys listaan
     * @param jasen = jäsen
     */
    public void haeJ(Jasen jasen) {
        chooserJasenet.add(jasen.get1() + " " + jasen.get0(), jasen);
    }
    
    /**
     * Päivittää jäsenlistan
     */
    public void paivitaJasenet() {
        chooserJasenet.clear();
        int i = 0;
        while (i < Seura.getJasenet()) {
            Jasen jasen = Seura.annaJasen(i);
          chooserJasenet.add(jasen.get1() + " " + jasen.get0(), jasen);  
          i++;
        }
    }
    
    /**
     * lukee tiedot tiedostoista
     * @param polku = polku tiedostoihin
     * @throws Virhe = virhe
     */
    protected void lue(String polku) throws Virhe {
        Seura.lueTiedostosta(polku);
    }
    
    
    /**
     * kysyy nimen, ja lukee sen, jos mahdollista.
     */
    public void avaa() {
        seuranNimi = SeuranNimiGUIController.kysySeura(null, seuranNimi);
        if(seuranNimi == null) {
            sulje = true;
            Platform.exit();
        }
    }

    
    /**
     * Tallentaa tiedot.
     */
    void tallenna() {
        Seura.nollaaTiedosto(kansio, "Jasenet.txt");
        Seura.nollaaTiedosto(kansio, "Ottelut.txt");
        Seura.nollaaTiedosto(kansio, "Joukkueet.txt");
        try {
            Seura.talleta(kansio);
        } catch (Virhe e) {
            e.printStackTrace();
        }
    }
    
    
    
   // /**
   //  * kirjoittaa tekstin kenttiin
   //  * @param jnro jäsen numero
   //  */
   // public void hae(int jnro) {
      //  String ehto = "";
      // if (Hakuehto.getText() != null) ehto = Hakuehto.getText();
       ///if (k > 0 || ehto.length() > 0) naytaVirhe(String.format("Ei osata hakea (kenttä: %d, ehto: %s",k,ehto));
       // else naytaVirhe(null);
        //int index = 0;
      //  Collection<Jasen> jasenet;
      //  try {
            //jasenet = Seura.etsi(ehto, k);
           // int i = 0;
           // for(Jasen jasen:jasenet) { 
            //if (jasen.getTunnus() == jnro) index = i;
            //chooserJasenet.add(jasen.getNimi(), jasen);
           // i++;
           // }
       // } catch (Virhe ex) {
        //    Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage());
      //  }
       // chooserJasenet.setSelectedIndex(index);
    // }

    //private void naytaVirhe(String virhe) {
      //  if (virhe == null || virhe.isEmpty()) {
      //      return;
      //  }
      //  Dialogs.showMessageDialog("Virhe! "+ virhe);
  //  }
   
    
    /**
     * Info
     */
    public void info() {
        try {
        FXMLLoader Info = new FXMLLoader(getClass().getResource("HTGUIViewInfo.fxml"));
        Parent root1 = (Parent) Info.load();
        Stage info = new Stage();
        info.initStyle(StageStyle.UNDECORATED);
        info.setTitle("Info");
        info.setScene(new Scene(root1));;
        info.show();
        } catch (Exception eiToimi) {
            EiToimi();
        }
    }


    /**
     * tallentaa ja sulkee ohjelman.
     * @return = true/false
     */
    boolean voikoSulkea() {
        if ( !Dialogs.showQuestionDialog("Sulje", "Suljetaanko ohjelma? ", "Kyllä", "Ei") )
            return false;
        tallenna();
        return true;
    }

}
