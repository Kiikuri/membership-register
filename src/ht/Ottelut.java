package ht;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class Ottelut implements Iterable<Ottelu> {
    
    private String tiedostonNimi="Ottelut.txt";
    
    private final ArrayList<Ottelu> alkiot = new ArrayList<Ottelu>();

    
    
    /**
     * alustaa ottelut
     */
    public void alustaOttelut() {
        tiedostonNimi="Ottelut.txt";
        alkiot.clear();
        Ottelu.OtteluSeuraava = 0;
    }
    /**
     * otteluiden alustaminen
     */
    public Ottelut() {
        //
    }
    
    /**
     * lisää ottelun tietorakenteeseen
     * @param ott lisättävä ottelu
     */
    public void lisaa(Ottelu ott) {
        alkiot.add(ott);
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
                Ottelu uusi = new Ottelu();
                i = 0;
                while(i < 6) {
                    while(ch != '|' && ch != '\n' && ch != -1) {
                        sana.append((char)ch);
                        ch = fileReader.read();
                    }
                    ch = fileReader.read();
                    uusi.setOtteluTiedot(sana.toString(), i);
                    sana.delete(0, sana.length());
                    i++;
                }
                lisaa(uusi);
            }
        } catch (FileNotFoundException e) {
            Dialogs.showMessageDialog(virhe);
        } catch (IOException e) {
            //
        }
    }
    
    /**
     * tallentaa ottelut tiedostostoon. ei vielä osata.
     * @param polku = polku tiedostoon
     * @throws Virhe = jos epäonnistuu.
     */
    public void tallenna(String polku) throws Virhe {
        ArrayList<Ottelu> Ottelut = annaOttelut();                     //hakee kaikki ottelut
        StringBuilder otteluTiedot = new StringBuilder();
        int i = 0;
        int l = 0;
        while (i < Ottelut.size()) {
            ArrayList<String> ottTiedot = Ottelut.get(i).annaOtteluTiedot(); //hakee tietyn ottelun tiedot
            otteluTiedot.append(Ottelut.get(i).getOtteluTunnus());           //lisää merkkijonoon ottelun tunnuksen
            l = 0;
            while (l < ottTiedot.size()) {
            otteluTiedot.append("|" + ottTiedot.get(l));                     //lisää kaikki muut tiedot merkkijonoon "|" merkillä erotettuna
            l++;
            }
            try(FileWriter fileWriter = new FileWriter(polku + tiedostonNimi, true)) {
                fileWriter.write(otteluTiedot.toString() + "\n");
            } catch (IOException e) {
                System.out.println("Ei voitu kirjoittaa");  
            }
            otteluTiedot.replace(0, otteluTiedot.length(), "");              //nollaa merkkijonon uutta jäsentä varten
            i++;
        }
    }
    /**
     * palauttaa otteluiden lukumäärän
     * @return otteluiden lkm
     */
    public int getLkmO() {
        return alkiot.size();
    }
    
    
    /**
     * @param i = ottelun viite
     * @return haluttu ottelu
     * @throws IndexOutOfBoundsException virhe, jos i on laittomalla alueella
     */
    public Ottelu anna(int i) throws IndexOutOfBoundsException{
        if (i < 0)
            throw new IndexOutOfBoundsException("laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    /**
     * Iteraattori otteluiden läpikäymiseen
     * @return Otteluiteraattori
     */
    @Override
    public Iterator<Ottelu> iterator() {
        return alkiot.iterator();
    }
    
    /**
     * Etsii kaikki ottelut
     * @return = ottelut
     */
    public ArrayList<Ottelu> annaOttelut(){
        ArrayList<Ottelu> loydetyt = new ArrayList<Ottelu>();
        for(Ottelu ott : alkiot) loydetyt.add(ott);
        return loydetyt;
    }
    
    
    /**
     * Tulostaa kaikki ottelut
     * @param lista = lista otteluista
     * @throws Virhe = virhe
     */
    public static void TulostaOttelut(List<Ottelu> lista) throws Virhe {
        System.out.println("");
        for(Ottelu ott : lista) {
            ott.tulostaO(System.out);
            System.out.println("");
        }
    }
    /**
     * Ohjelman testaamista
     * @param args = ei käytössä
     * @throws Virhe =
     */
    public static void main(String[] args) throws Virhe {
        Ottelut Ottelut = new Ottelut();
        Ottelu eka = new Ottelu();
        eka.testaaO();
        Ottelut.lisaa(eka);
        Ottelu eka1 = new Ottelu();
        eka1.testaaO();
        Ottelut.lisaa(eka1);
        
        System.out.println("============= ottelut testi =================");
        List<Ottelu> ottelut2 = Ottelut.annaOttelut();
        TulostaOttelut(ottelut2);
    }


    /**
     * poistaa ottelun
     * @param id = viitenumero
     */
    public void poista(int id) {
        int lkm = alkiot.size();
        if(id >= lkm) {
            System.out.println("Ei sallittu indeksi");
            return;
        }
        Ottelu.OtteluSeuraava--;
        int i = id;
        lkm--;
        while(i < lkm) {
            alkiot.set(i, alkiot.get(i + 1));
            alkiot.get(i).miinusTunnus();
            i++;
        }
        alkiot.remove(i);
    }
}