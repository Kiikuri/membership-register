package ht;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author miro
 * @version 27.1.2019
 *
 */
public class Seura {
    private final Jasenet Jasenet = new Jasenet();
    private final Ottelut Ottelut = new Ottelut();
    private final Joukkueet Joukkueet = new Joukkueet();
    private String seuranNimi;
    
    
    /**
     * alustaa tietokannat
     */
    public void alustaSeura() {
        Joukkueet.alustaJoukkueet();
        Ottelut.alustaOttelut();
        Jasenet.alustaJasenet();
        seuranNimi = null;
    }
    /**
     * palauttaa jäsenten lukumäärän
     * @return jäseneten lukumäärä
     */
    public int getJasenet() {
        return Jasenet.getLkm();
    }
    
    /**
     * palauttaa otteluiden lukumäärän
     * @return otteluiden lukumäärä
     */
    public int getOttelut() {
        return Ottelut.getLkmO();
    }
    
    /**
     * palauttaa joukkueiden lukumäärän
     * @return joukkueiden lukumäärä
     */
    public int getJoukkueet() {
        return Joukkueet.getLkm();
    }
    
    /**
     * Poistaa jäsenen
     * @param id = viitenumero
     */
    public void poistaJasen(int id) {
        Jasenet.poista(id);
    }
    
    /**
     * palauttaa jäsenien maksimi lukumäärän
     * @return maksimi lukumäärä
     */
    public int maxJasenet() {
        return Jasenet.maxJasenet();
    }
    
    /**
     * palauttaa joukkueiden maksimi lukumäärän
     * @return maksimi lukumäärä
     */
    public int maxJoukkueet() {
        return Joukkueet.maxJoukkueet();
    }
    /**
     * Asettaa joukkueen jäsenelle
     * @param args = joukkueen nimi
     * @param jasen = muokattava jäsen
     */
    public void asetaJasenenJoukkue(String args, Jasen jasen) {
        if(etsiJ(args) != null) jasen.setJoukkue(etsiJ(args).getJoukkueTunnus());
        else{
            try 
            {
            luoJoukkue(args);
            jasen.setJoukkue(etsiJ(args).getJoukkueTunnus());
            } 
            catch (Virhe e) {
            e.printStackTrace();
            }
        }
    }
    
    /**
     * Poistaa Ottelun
     * @param id = viitenumero
     */
    public void poistaOttelu(int id) {
        Ottelut.poista(id);
    }
    
    /**
     * Poistaa Joukkueen
     * @param id = viitenumero
     */
    public void poistaJoukkue(int id) {
        Joukkueet.poistaJoukkue(id);
    }
    
    /**
     * uuden jäsenen lisääminen
     * @param Jasen = lisättävä jäsen
     * @throws Virhe = virhe
     */
    public void lisaaJ(Jasen Jasen) throws Virhe {
        Jasenet.lisaa(Jasen);
    }
    
    /**
     * Etsii joukkueen. Mikäli joukkuetta ei ole, palauttaa null
     * @param Joukkue = etsittävän joukkueen nimi, tai lyhenne
     * @return etsitty joukkue
     */
    public Joukkue etsiJ(String Joukkue) {
        int i = 0;
        Joukkue JoukkueKohdalla = null;
        while (i < Joukkueet.getLkm())
        {
            JoukkueKohdalla = Joukkueet.anna(i);
            if (Joukkue == JoukkueKohdalla.getJoukkueNimi() | Joukkue == JoukkueKohdalla.getLyhenne()) return JoukkueKohdalla;
            i++;
        }
        return null;
    }
    
    /**
     * Luo uuden joukkueen ja joukkuetunnuksen
     * @param args = joukkueen nimi
     * @throws Virhe = virhe
     */
    public void luoJoukkue(String args) throws Virhe {
        Joukkueet.luoJoukkue(args);
    }
    
    /**
     * uuden joukkueen lisääminen
     * @param Joukkue = lisättävä joukkue
     * @throws Virhe = virhe
     */
    public void lisaaJoukkue(Joukkue Joukkue) throws Virhe {
        Joukkueet.lisaaJ(Joukkue);
    }
    /**
     * Uuden ottelun lisääminen
     * @param ott = lisättävä ottelu
     */
    public void lisaaO(Ottelu ott) {
        Ottelut.lisaa(ott);
    }
    
    
    /**
     * palauttaa tietyn joukkueen kaikki pelaajat
     * @param id = joukkueen tunnus
     * @return tietyn joukkueen kaikki pelaajat
     */
    public ArrayList<Jasen> annaJoukkueJasenet(int id) {
        return Jasenet.annaJoukkueJasenet(id);
    }
    
    /**
     * palauttaa i:nnen jäsenen
     * @param i = indeksi
     * @return haluttu jäsen
     * @throws IndexOutOfBoundsException = i yli rajan
     */
    public Jasen annaJasen(int i) throws IndexOutOfBoundsException {
        return Jasenet.anna(i);
    }

    /**
     * palauttaa kaikki jäsenet taulukossa
     * @return lista jäsenistä
     */
    public ArrayList<Jasen> annaJasenet() {
        return Jasenet.annaJasenet();
    }
    
    /**
     * palauttaa i:nnen ottelun
     * @param i = indeksi
     * @return haluttu ottelu
     * @throws IndexOutOfBoundsException = i yli rajan
     */
    public Ottelu annaOttelu(int i) throws IndexOutOfBoundsException {
        return Ottelut.anna(i);
    }
    
    /**
     * palauttaa i:nnen joukkueen
     * @param i = indeksi
     * @return haluttu joukkue
     * @throws IndexOutOfBoundsException = i yli rajan
     */
    public Joukkue annaJoukkue(int i) throws IndexOutOfBoundsException {
        return Joukkueet.anna(i);
    }
    
    /**
     * palauttaa kaikki ottelut
     * @return ottelut
     */
    public ArrayList<Ottelu> annaOttelut(){
        return Ottelut.annaOttelut();
    }
    
    /**
     * palauttaa kaikki joukkueet
     * @return joukkueet
     */
    public ArrayList<Joukkue> annaJoukkueet(){
        return Joukkueet.annaJoukkueet();
    }
    
    /**
     * lukee seuran tiedot tiedostosta
     * @param polku = nimi
     * @throws Virhe = virhe
     */
    public void lueTiedostosta(String polku) throws Virhe {
        Jasenet.lue(polku);
        Ottelut.lue(polku);
        Joukkueet.lue(polku);
    }
    
    /**
     * tallentaa seuran tiedot
     * @param polku = polku kansioon
     * @throws Virhe = virhe
     */
    public void talleta(String polku) throws Virhe{
        Jasenet.tallenna(polku);
        Ottelut.tallenna(polku);
        Joukkueet.tallenna(polku);
    }
    
    /**
     * pyyhkii tiedoston kaikista tiedoista
     * @param polku = polku tiedostoon
     * @param tiedosto = nollattava tiedosto
     */
    public void nollaaTiedosto(String polku, String tiedosto) {
        String nollattava = polku + tiedosto;
        try(FileWriter fileWriter = new FileWriter(nollattava)) {
            fileWriter.write("");
        } catch (IOException e) {
            //
        }
    }
    /**
     * Testi tietorakenteelle, luodaan 10 joukkuetta
     * @throws Virhe = virhe
     */
    public void seuraJoukkueTesti() throws Virhe {
        Joukkue jeka = new Joukkue(); jeka.UusiJoukkue("Helsingin palloseura", jeka); lisaaJoukkue(jeka);
        Joukkue jeka1 = new Joukkue(); jeka1.UusiJoukkue("Haminan Nuoret", jeka1); lisaaJoukkue(jeka1);
        Joukkue jeka2 = new Joukkue(); jeka2.UusiJoukkue("Tampereen Jalkapalloilijat", jeka2); lisaaJoukkue(jeka2);
        Joukkue jeka3 = new Joukkue(); jeka3.UusiJoukkue("Uudenmaan Frisbeegolf", jeka3); lisaaJoukkue(jeka3);
        Joukkue jeka4 = new Joukkue(); jeka3.UusiJoukkue("Vantaan Kiekko", jeka4); lisaaJoukkue(jeka4);
        Joukkue jeka5 = new Joukkue(); jeka3.UusiJoukkue("Rauman Kiipelijät", jeka5); lisaaJoukkue(jeka5);
        Joukkue jeka6 = new Joukkue(); jeka3.UusiJoukkue("Haminan Reserviupseerikoulu", jeka6); lisaaJoukkue(jeka6);
        Joukkue jeka7 = new Joukkue(); jeka3.UusiJoukkue("Santahaminan Kaupunkijääkärit", jeka7); lisaaJoukkue(jeka7);
        Joukkue jeka8 = new Joukkue(); jeka3.UusiJoukkue("Englannin Arsenal", jeka8); lisaaJoukkue(jeka8);
        Joukkue jeka9 = new Joukkue(); jeka3.UusiJoukkue("Toinen joukkue", jeka9); lisaaJoukkue(jeka9);
    }
    
    /**
     * asettaa seuralle nimen
     * @param args nimi
     */
    public void setSeuraNimi(String args) {
        seuranNimi = args;
    }
    
    /**
     * hakee seuran nimen
     * @return seuran nimi
     */
    public String getSeuraNimi() {
        return seuranNimi;
    }
    
    
    /**
     * Ohjelman testaaminen
     * @param args Ei k�yt�ss�
     * @throws Virhe = virhe
     */
    public static void main(String[] args) throws Virhe {
        Seura Seura = new Seura();
        // seura.lueTiedostosta("seura");
        Jasen matti = new Jasen(), matti2 = new Jasen();
        matti.rekisteroi();
        matti.testaa();
        
        matti2.rekisteroi();
        matti2.testaa();
        
        
        Joukkue jeka = new Joukkue(); jeka.UusiJoukkue("Helsingin palloseura", jeka); Seura.lisaaJoukkue(jeka);
        Joukkue jeka1 = new Joukkue(); jeka1.UusiJoukkue("Haminan Nuoret", jeka1); Seura.lisaaJoukkue(jeka1);
        Joukkue jeka2 = new Joukkue(); jeka2.UusiJoukkue("Tampereen Jalkapalloilijat", jeka2); Seura.lisaaJoukkue(jeka2);
        Joukkue jeka3 = new Joukkue(); jeka3.UusiJoukkue("Uudenmaan Frisbeegolf", jeka3); Seura.lisaaJoukkue(jeka3);
        Joukkue jeka4 = new Joukkue(); jeka3.UusiJoukkue("Vantaan Kiekko", jeka4); Seura.lisaaJoukkue(jeka4);
        Joukkue jeka5 = new Joukkue(); jeka3.UusiJoukkue("Rauman Kiipelijät", jeka5); Seura.lisaaJoukkue(jeka5);
        Joukkue jeka6 = new Joukkue(); jeka3.UusiJoukkue("Haminan Reserviupseerikoulu", jeka6); Seura.lisaaJoukkue(jeka6);
        Joukkue jeka7 = new Joukkue(); jeka3.UusiJoukkue("Santahaminan Kaupunkijääkärit", jeka7); Seura.lisaaJoukkue(jeka7);
        Joukkue jeka8 = new Joukkue(); jeka3.UusiJoukkue("Englannin Arsenal", jeka8); Seura.lisaaJoukkue(jeka8);
        Joukkue jeka9 = new Joukkue(); jeka3.UusiJoukkue("Toinen joukkue", jeka9); Seura.lisaaJoukkue(jeka9);
        
        Seura.lisaaJ(matti);
        Ottelu eka = new Ottelu(); eka.testiO(); eka.rekisteroiO(); Seura.lisaaO(eka);
        Ottelu eka1 = new Ottelu(); eka1.testiO(); eka1.rekisteroiO(); Seura.lisaaO(eka1);
        Ottelu eka2 = new Ottelu(); eka2.testiO(); eka2.rekisteroiO(); Seura.lisaaO(eka2);
        Ottelu eka3 = new Ottelu(); eka3.testiO(); eka3.rekisteroiO(); Seura.lisaaO(eka3);
        
        System.out.println("============= Kerhon testi =================");
        for (int i = 0; i < Seura.getJasenet(); i++) {
            Jasen Jasen = Seura.annaJasen(i);
            System.out.println("Jäsen paikassa: " + i+1);
            Jasen.tulosta(System.out);
            System.out.println("");
            List<Ottelu> loytyneet = Seura.annaOttelut();
            for (Ottelu Ottelu : loytyneet) { 
                Ottelu.tulostaO(System.out);
                System.out.println("");
            }
            
            List<Joukkue> loytyneetj = Seura.annaJoukkueet();
            for (Joukkue J : loytyneetj) { 
                if(J == null) break;
                J.tulostaJ(System.out);
                System.out.println("");
            }
            
            Seura.asetaJasenenJoukkue("Englannin Arsenal", matti2);

            System.out.println("matin joukkue = " + matti2.get9());
        }
    }

    /**
     * palauttaa hakuehdon vastaavien jäsenten viitteet
     * @param ehto = hakuehto
     * @param k = etsittävän kentän indeksi
     * @return = jäsen
     * @throws Virhe = virhe
     */
    public Collection<Jasen> etsi(String ehto, int k) throws Virhe{
        return Jasenet.etsi(ehto, k);
    }
}