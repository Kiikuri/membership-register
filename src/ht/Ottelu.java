package ht;

import java.io.*;
import java.util.ArrayList;

/**
 * Ottelu luokka, joka huolehtii yksittäisestä otteluista.
 * @author miro
 * @version 27.1.2019
 *
 */
public class Ottelu {
    private int OtteluTunnus;
    private int KotiJ;
    private int VierasJ;
    private String OtteluPaikka= "";
    private String OtteluPaiva = "";
    private String OtteluAika = "";
    private String tiedostonNimi = "";
    private String hakemisto = "";
    
    @SuppressWarnings("javadoc")
    public static int OtteluSeuraava = 0;
    
    
    
    /**
     * alustetaan ottelu luokka
     */
    public void OtteluAlusta() {
        OtteluTunnus = 0;
        KotiJ = 0;
        VierasJ = 0;
        OtteluPaikka= "";
        OtteluPaiva = "";
        OtteluAika = "";
        tiedostonNimi = "";
        hakemisto = "";
        OtteluSeuraava = 0;
    }
    
    
   /**
    * haetaan koti joukkueen lyhenne tiedostosta numeron perusteella
 * @return koti joukkueen nimi
 */
   public String getKotiJ() {
       return Integer.toString(KotiJ);
   }
   
   /**
 * @return ottelun Ottelutunnus
 */
   public int getOtteluTunnus() {
       return OtteluTunnus;
   }
   
   /**
    * haetaan vieras joukkueen lyhenne tiedostosta numeron perusteella
 * @return vieras joukkueen nimi
 */
   public String getVierasJ(){
       return Integer.toString(VierasJ);
   }
   
   /**
    * palauttaa ottelun paikan
 * @return = paikka
 */
   public String getPaikka(){
       return OtteluPaikka;
   }
   
   /**
    * palauttaa ottelun paikan
 * @return = paikka
 */
   public String getAika(){
       return OtteluAika;
   }
   
   /**
    * palauttaa ottelun paikan
 * @return = paikka
 */
   public String getPaiva(){
       return OtteluPaiva;
   }
   
   
   /**
    * Asetetaan ottelulle silmukassa tiedot
    * indeksit:
    * 0 = tunnus
    * 1 = koti joukkue
    * 2 = vieras joukkue
    * 3 = paikka
    * 4 = päivä
    * 5 = aika
    * @param args = teksti
    * @param index = indeksi
    * @return null
    */
   public String setOtteluTiedot(String args, int index) {
       switch(index) {
       case 0:
           OtteluTunnus = Integer.parseInt(args);
           OtteluSeuraava++;
           break;
           
       case 1:
           KotiJ = Integer.parseInt(args);
           break;
       case 2:
           VierasJ = Integer.parseInt(args);
           break;
           
       case 3:
           OtteluPaikka = args;
           break;
           
       case 4:
           OtteluPaiva = args;
           break;
           
       case 5:
           OtteluAika = args;
           break;
       default:
           System.out.println("Laiton indeksi");
           break;
       }
    return null;
   }
   
   /**
    * palauttaa ottelun tiedot
 * @return tiedot
 */
public ArrayList<String> annaOtteluTiedot(){
       ArrayList<String> tiedot = new ArrayList<String>();
       tiedot.add(getKotiJ());
       tiedot.add(getVierasJ());
       tiedot.add(getPaikka());
       tiedot.add(getPaiva());
       tiedot.add(getAika());
       return tiedot;
   }
   /**
    * tietojen testaaminen
    */
   public void testiO() {
       KotiJ = 1;
       VierasJ = 2;
       OtteluPaikka = "Hartwall Areena";
       OtteluPaiva = "01.02.2011";
       OtteluAika = "18:00-21:00";
       tulostaO(System.out);
   }
   
   /**
    * ottelun tietojen tulostaminen.
    * @param out tietovirta, johon tulostetaan
    */
   public void tulostaO(PrintStream out) {
       out.println("Tunnus:         " + String.format("%03d", OtteluTunnus, 3));
       out.println("Koti Joukkue:   " + getKotiJ());
       out.println("Vieras Joukkue: " + getVierasJ());
       out.println("Ottelun Paikka: " + OtteluPaikka);
       out.println("Ottelun Päivä   " + OtteluPaiva);
       out.println("Ottelun Aika    " + OtteluAika);
   }

   /**
 * @param os = tietovirta, johon tulostetaan
 * @throws Virhe = virhe
 */
   public void tulostaO(OutputStream os) throws Virhe {
    tulostaO(new PrintStream(os));
   }
   
   /**
   * Apumetodi testille
 * @throws Virhe = 0;
   */
   public void testaaO() throws Virhe {
       rekisteroiO();
       testiO();
   }

   /** palauttaa ottelun tunnuksen
    * @return ottelun tuunus
    */
   public int rekisteroiO() {
       OtteluTunnus = OtteluSeuraava;
       OtteluSeuraava++;
       return OtteluTunnus;
               
   }

    /**
     * testi
     * @param args = ei käytössä
     * @throws Virhe = virhe
     */
    public static void main(String[] args) throws Virhe {
        Ottelu ot1 = new Ottelu();
        ot1.testaaO();
        ot1.testaaO();
        
    }

    /**
     * miinustaa ottelun tunnusta yhdellä
     */
    public void miinusTunnus() {
        if(OtteluTunnus > 0 && OtteluSeuraava > 0) OtteluTunnus--;
    }
}