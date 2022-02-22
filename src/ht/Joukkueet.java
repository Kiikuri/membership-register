package ht;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * /**
 * Joukkueet luokka, joka huolehtii kaikista joukkueista.
 * @author miro
 * @version 27.1.2019
 */
public class Joukkueet {
    private static final int max_nro = 20;
    private int lkm = 0;
    private String tiedostonNimi = "Joukkueet.txt";
    private Joukkue alkiotJ[] = new Joukkue[max_nro];
    
    
    /**
     * alustaa joukkueet
     */
    public void alustaJoukkueet() {
        lkm = 0;
        tiedostonNimi = "Joukkueet.txt";
        alkiotJ = null;
        alkiotJ = new Joukkue[max_nro];
        Joukkue.JoukkueSeuraava = 0;
    }
    
    
    /**
     * Oletusmuodostaja
     */
    public Joukkueet() {
        //
    }
    
    /**
     * luo uuden joukkueen ja joukkue tunnuksen
     * @param args = joukkueen nimi
     * @throws Virhe = virhe
     */
    public void luoJoukkue(String args) throws Virhe {
        Joukkue uusi = new Joukkue();
        uusi.UusiJoukkue(args, uusi);
        lisaaJ(uusi);
    }
    
    /**
     * @param Joukkue = joukkue
     * @throws Virhe = jos liikaa jäseniä
     */
    public void lisaaJ(Joukkue Joukkue)throws Virhe{
        if (lkm >= alkiotJ.length) throw new Virhe("liikaa joukkueita");
        alkiotJ[lkm] = Joukkue;
        lkm++;
    }
    
    /**
     * Poistaa joukkueen
     * @param id = viitenumero
     */
    public void poistaJoukkue(int id) {
        if(id >= lkm) {
            System.out.println("Ei sallittu indeksi");
            return;
        }
        Joukkue.JoukkueSeuraava = Joukkue.JoukkueSeuraava -1;
        int i = id;
        lkm--;
        while(i < lkm) {
            alkiotJ[i] = alkiotJ[i+1];
            alkiotJ[i].miinusJoukkueTunnus();
            i++;
        }
        alkiotJ[i] = null;
    }
    
    /**
     * palauttaa joukkueiden lukumäärän
     * @return joukkueiden lukumäärä
     */
    public int getLkm(){
        return lkm;
    }
    
    
    /**
     * tallentaa joukkueet tiedostostoon. ei vielä osata.
     * @param polku = polku tiedostoon
     * @throws Virhe = jos epäonnistuu.
     */
    public void tallenna(String polku) throws Virhe {
        ArrayList<Joukkue> Joukkueet = annaJoukkueet();     //hakee kaikki joukkueet
        StringBuilder joukkueTiedot = new StringBuilder();      
        int i = 0;
        while(i < Joukkueet.size()) {
            ArrayList<String> joukTiedot = Joukkueet.get(i).annaJoukkueTiedot(); //hakee tietyn joukkueen tiedot
            joukkueTiedot.append(Joukkueet.get(i).getJoukkueTunnus());           //lisää merkkijonoon joukkueen tunnuksen
            joukkueTiedot.append("|" + joukTiedot.get(0));                       //lisää kaikki muut tiedot merkkijonoon "|" merkillä erotettuna
            joukkueTiedot.append("|" + joukTiedot.get(1));
            try(FileWriter fileWriter = new FileWriter(polku + tiedostonNimi, true)) {
                fileWriter.write(joukkueTiedot.toString() + "\n");
            } catch (IOException e) {
                System.out.println("Ei voitu kirjoittaa");  
            }
            joukkueTiedot.replace(0, joukkueTiedot.length(), "");        //nollaa merkkijonon uutta jäsentä varten
            i++;
        }
    }
    
    /**
     * Lukee tiedoston
     * @param polku = luettavan tiedoston sijainti
     */
    protected void lue(String polku) {
        String virhe = "Tiedostoa ei löytynyt";
        int i = 0;
        StringBuilder sana = new StringBuilder();
        String tiedosto = polku + tiedostonNimi;
        try(FileReader fileReader = new FileReader(tiedosto)) {
            int ch = fileReader.read();
            while(ch != -1) {
                Joukkue uusi = new Joukkue();
                i = 0;
                while(i < 3) {
                    while(ch != '|' && ch != '\n' && ch != -1) {
                        sana.append((char)ch);
                        ch = fileReader.read();
                    }
                    ch = fileReader.read();
                    uusi.setJoukkueTiedot(sana.toString(), i);
                    sana.delete(0, sana.length());
                    i++;
                }
                try {
                    lisaaJ(uusi);
                } catch (Virhe e) {
                    e.printStackTrace();
                    System.out.println("Ei voitu lisätä, liikaa joukkueita");
                }
            }
        } catch (FileNotFoundException e) {
            Dialogs.showMessageDialog(virhe);
        } catch (IOException e) {
            //
        }
    }
    
    /**
     * Palauttaa kaikki joukkueet
     * @return kaikki joukkueet
     */
    public ArrayList<Joukkue> annaJoukkueet(){
        ArrayList<Joukkue> loydetyt = new ArrayList<Joukkue>();
        int i = 0;
        while(i < max_nro && alkiotJ[i] != null) {
        loydetyt.add(alkiotJ[i]);
        i++;
        }
        return loydetyt;
    }
    
    /**
     * palauttaa tietyn joukkueen
     * @param i joukkueen viite
     * @return viite joukkueeseen
     * @throws IndexOutOfBoundsException virhe, jos i on laittomalla alueella
     */
    public Joukkue anna(int i) throws IndexOutOfBoundsException{
        if (i < 0 && lkm <= i)
            throw new IndexOutOfBoundsException("laiton indeksi: " + i);
        return alkiotJ[i];
    }
    
   
    
    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        Joukkueet Joukkueet = new Joukkueet();
        Joukkue hepa = new Joukkue(), hepa2 = new Joukkue(), hepa3 = new Joukkue(), hepa4 = new Joukkue(), hepa5 = new Joukkue();     
        try {
            hepa.rekisteroiJouk();
            hepa.testaaJ();
            hepa.setJoukkueNimi("tosiaan0");
            Joukkueet.lisaaJ(hepa);
            
            hepa2.rekisteroiJouk();
            hepa2.testaaJ();
            hepa2.setJoukkueNimi("tosiaan1");
            Joukkueet.lisaaJ(hepa2);
            
            hepa3.rekisteroiJouk();
            hepa3.testaaJ();
            hepa3.setJoukkueNimi("tosiaan2");
            Joukkueet.lisaaJ(hepa3);
            
            Joukkueet.poistaJoukkue(hepa2.getJoukkueTunnus());
            
            hepa4.rekisteroiJouk();
            hepa4.testaaJ();
            hepa4.setJoukkueNimi("tosiaan3");
            Joukkueet.lisaaJ(hepa4);
            
            hepa5.rekisteroiJouk();
            hepa5.testaaJ();
            hepa5.setJoukkueNimi("tosiaan4");
            Joukkueet.lisaaJ(hepa5);
            System.out.println("=================== joukkue testi ===================");
            
            int i= 0;
            while (i<Joukkueet.getLkm()) {
                Joukkue Joukkue = Joukkueet.anna(i);
                System.out.println("");
                Joukkue.tulostaJ(System.out);
                i++;
            }
            } catch (Virhe ex) {
                System.out.println(ex.getMessage());
        }
        
    }

    /**
     * palauttaa joukkueiden maksimi lukumäärän
     * @return maksimi lukumäärä
     */
    public int maxJoukkueet() {
        return max_nro;
    }
}