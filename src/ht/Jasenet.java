package ht;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * /**
 * Jäsenet luokka, joka huolehtii kaikista jäsenistä.
 * @author miro
 * @version 27.1.2019
 */
public class Jasenet implements Iterable<Jasen> {
    private static final int max_jasenet = 20;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private Jasen alkiot[] = new Jasen[max_jasenet];
    private String tiedostonPerusNimi = "Jasenet.txt";
    
    
    
    /**
     * alustaa jäsenet
     */
    public void alustaJasenet() {
        muutettu = false;
        lkm = 0;
        kokoNimi = "";
        alkiot = null;
        alkiot = new Jasen[max_jasenet];
        tiedostonPerusNimi = "Jasenet.txt";
        Jasen.seuraava = 0;
    }
    /**
     * Oletusmuodostaja
     */
    public Jasenet() {
        //
    }
    
    /**
     * Lisää jäsenen
     * @param Jasen = jäsen
     * @throws Virhe = jos liikaa jäseniä
     */
    public void lisaa(Jasen Jasen)throws Virhe{
        if (lkm >= alkiot.length) throw new Virhe("liikaa jäseniä");
        alkiot[lkm] = Jasen;
        lkm++;
    }
    
    /**
     * palauttaa kaikki tietyn joukkueen jäsenet
     * @param id = Joukkueen tunnus
     * @return joukkueen jäsenet
     */
    public ArrayList<Jasen> annaJoukkueJasenet(int id) {
        ArrayList<Jasen> loydetyt = new ArrayList<Jasen>();
        int i = 0;
        while (i < lkm) {
            if(alkiot[i].get9() == id) {
                loydetyt.add(alkiot[i]);
            }
        }
        return loydetyt;
    }
    
    /**
     * Poistaa jäsenen
     * @param id = viitenumero
     */
    public void poista(int id) {
        if(id >= lkm) {
            System.out.println("Ei sallittu indeksi");
            return;
        }
        Jasen.seuraava = Jasen.seuraava -1;
        int i = id;
        lkm--;
        while(i < lkm) {
            alkiot[i] = alkiot[i+1];
            alkiot[i].miinusTunnus();
            i++;
        }
        alkiot[i] = null;
    }
   
    /**
     * Palauttaa kaikki jäsenet
     * @return lista jäsenistä
     */
    public ArrayList<Jasen> annaJasenet() {
        ArrayList<Jasen> kaikki = new ArrayList<Jasen>();
        int i = 0;
        while(alkiot[i] != null) {
            kaikki.add(alkiot[i]);
            i++;
        }
        return kaikki;
    }
    /**
     * palauttaa jäsenten lukumäärän
     * @return jäsenten lukumäärä
     */
    public int getLkm(){
        return lkm;
    }
    
    /**
     * tallentaa jäsenistön tiedostostoon..
     * @param polku = polku tiedostoon
     * @throws Virhe = jos epäonnistuu.
     */
    public void tallenna(String polku) throws Virhe {
        ArrayList<Jasen> Jasenet = annaJasenet();                       //hakee kaikki jäsenet
        StringBuilder jasenTiedot = new StringBuilder();
        int i = 0;
        int l = 0;
        while(i < Jasenet.size()) {
            String[] tiedot = Jasenet.get(i).annaTiedot();               //hakee tietyn jäsenen tiedot
            jasenTiedot.append(Jasenet.get(i).getTunnus());              //lisää merkkijonoon jäsenen tunnuksen
            l = 0;
            while (l < tiedot.length) {
                jasenTiedot.append("|" +tiedot[l]);                  //lisää kaikki muut tiedot merkkijonoon "|" merkillä erotettuna
                l++;
            }
            try(FileWriter fileWriter = new FileWriter(polku + tiedostonPerusNimi, true)) {
                fileWriter.write(jasenTiedot.toString() + "\n");
            } catch (IOException e) {
                System.out.println("Ei voitu kirjoittaa");  
            }
            jasenTiedot.replace(0, jasenTiedot.length(), "");        //nollaa merkkijonon uutta jäsentä varten
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
        String tiedosto = polku + tiedostonPerusNimi;
        try(FileReader fileReader = new FileReader(tiedosto)) {
            int ch = fileReader.read();
            while(ch != -1) {
                Jasen uusi = new Jasen();
                i = 0;
                while(i < 11) {
                    while(ch != '|' && ch != '\n' && ch != -1) {
                        sana.append((char)ch);
                        ch = fileReader.read();
                    }
                    ch = fileReader.read();
                    uusi.setTiedot(sana.toString(), i);
                    sana.delete(0, sana.length());
                    i++;
                }
                try {
                    lisaa(uusi);
                } catch (Virhe e) {
                    e.printStackTrace();
                    System.out.println("Liikaa jäseniä, ei voida lisätä");
                }
            }
        } catch (FileNotFoundException e) {
            Dialogs.showMessageDialog(virhe);
        } catch (IOException e) {
            //
        }
    }
    
    
    /**
     * @param i jäsenen viite
     * @return viite jäseneen
     * @throws IndexOutOfBoundsException virhe, jos i on laittomalla alueella
     */
    public Jasen anna(int i) throws IndexOutOfBoundsException{
        if (i < 0 && lkm <= i) throw new IndexOutOfBoundsException("laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi = tallennustiedoston nimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    /**
     * palauttaa tallennukseen käytettävän tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    /**
     * palauttaa tallennukseen käytettävän tiedoston nimen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * palauttaa varmuuskopiontitiedoston nimen
     * @return varmuuskopiontitiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    /**
     * luokka jäsenten iterointiin
     * @author miro
     * @version 9.4.2019
     *
     */
    public class JasenetIterator implements Iterator<Jasen>{
        private int kohdalla = 0;
        
        
        /**
         * tarkistaa onko vielä seuraavaa jäsentä
         * @see java.util.Iterator#hasNext()
         * return = true jos on jäseniä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }
        
        
        /**
         * annetaan seuraava jäsen
         * @see java.util.Iterator#next()
         * return = seuraava jäsen
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         */
        @Override
        public Jasen next() throws NoSuchElementException{
            if (!hasNext()) throw new NoSuchElementException("ei ole");
            return anna(kohdalla++);
        }
        
        
        /**
         * tuhoamista ei olla toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException{
            throw new UnsupportedOperationException("ei poisteta");
        }  
    }
    
    
    /**
     * palautetaan iteraattori jäsenistään
     * @return = jäsen iteraattori
     */
    @Override
    public Iterator<Jasen> iterator(){
        return new JasenetIterator();
    }
    
    /**
     * ohjelma testaamista varten
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        System.out.println("luettu");
    }

    /**
     * palauttaa hakuehdon vastaavien jäsenten viitteet
     * @param ehto = hakuehto
     * @param k = etsittävän kentän indeksi
     * @return = jäsen
     */
    public Collection<Jasen> etsi(String ehto, int k) {
        Collection<Jasen> loytyneet = new ArrayList<Jasen>();
        for(Jasen jasen : this) {
            loytyneet.add(jasen);
        }
        return loytyneet;
    }

    /**
     * palauttaa jäsenien maksimi lukumäärän
     * @return jäsenien maksimi määrä
     */
    public int maxJasenet() {
        return max_jasenet;
    }
}