package ht;

import java.io.*;
import java.util.ArrayList;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Jäsen luokka, joka huolehtii yksittäisestä jäsenestä.
 * @author miro
 * @version 10.3.2019
 *
 */
public class Jasen {
    private int tunnus;
    private String etunimi = "";
    private String sukunimi = "";
    private String hetu = "";
    private String katuosoite = "";
    private String postinumero = "";
    private String postiosoite = "";
    private String puhelin = "";
    private String liittynyt = "";
    private String maksu = "";
    private int joukkue;
    private int TempJoukkue;
    private String TempJoukkueNimi;
    
    /**
     * seuraava tunnus
     */
    public static int seuraava = 0;
    
    
    
    
    /**
     * alustetaan jäsen luokka
     */
    public void JasenAlusta() {
        tunnus = 0;
        etunimi = "";
        sukunimi = "";
        hetu = "";
        katuosoite = "";
        postinumero = "";
        postiosoite = "";
        puhelin = "";
        liittynyt = "";
        maksu = "";
        joukkue = 0;
        TempJoukkue = 0;
        TempJoukkueNimi = "";
        seuraava = 0;
    }
    
    
    /**
     * palauttaa kenttien lukumäärän
     * @return lkm
     */
    public int getKenttia() {
        return 11;
    }
    
    /**
     * palauttaa ensimmäisen käytettävän kentän
     * @return indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
   /**
    * @return jäsenen nimi
    */
   public String getNimi() {
       return etunimi + " " + sukunimi;
   }
   
   
   /**
    * @return jäsenen jäsentunnus
    */
   public int getTunnus() {
       return tunnus;
   }
   
   
   /**
    * palauttaa kentän tekstin
    * 0: tunnus
    * 1: Etunimi
    * 2: Sukunimi
    * 3: Henkilötunnus
    * 4: Katuosoite
    * 5: Postinumero
    * 6: Postiosoite
    * 7: Puhelin
    * 8: Jäsenmaksu
    * 9: Päivä
    * 10: Joukkue
 * @param indeksi = indeksi
 * @return kenttä
 */
public String mikaKentta(int indeksi) {
       switch(indeksi)
       {
       case 0:
           return "tunnus";
           
       case 1:
           return "Etunimi";
           
       case 2:
           return "Sukunimi";
           
       case 3:
           return "Hetu";
           
       case 4:
           return "Osoite";
           
       case 5:
           return "Postinumero";
           
       case 6:
           return "Postiosoite";
           
       case 7:
           return "Puhelin";
           
       case 8:
           return "Maksu";
           
       case 9:
           return "Päivä";
           
       case 10:
           return "Joukkue";
    
       default:
           return "laiton indeksi";
       }
   }
   
   /**
    * Palauttaa jäsenen etunimen
    * @return jäsenen etunimi
    */
   public String[] annaTiedot() {
       String[] lista = {etunimi, sukunimi, hetu, katuosoite, postinumero, postiosoite, puhelin, maksu, liittynyt, Integer.toString(joukkue) };
       return lista;
   }
   
   /**
    * Palauttaa jäsenen sukunimen
    * @return jäsenen sukunimi
    */
   public String get1() {
       return sukunimi;
   }
   
   /**
    * Palauttaa jäsenen etunimen
    * @return jäsenen etunimi
    */
   public String get0() {
       return etunimi;
   }
   
   /**
    * Palauttaa jäsenen henkilötunnuksen
    * @return jäsenen henkilötunnus
    */
   public String get2() {
       return hetu;
   }
   
   
   /**
    * Palauttaa jäsenen katuosoitteen
    * @return jäsenen katuosoite
    */
   public String get3() {
       return katuosoite;
   }
   
   /**
    * Palauttaa jäsenen postinumeron
    * @return jäsenen postinumero
    */
   public String get4() {
       return postinumero;
   }
   
   /**
    * Palauttaa jäsenen postiosoitteen
    * @return jäsenen postiosoite
    */
   public String get5() {
       return postiosoite;
   }
   
   /**
    * Palauttaa jäsenen puhelinnumeron
    * @return jäsenen puhelinnumero
    */
   public String get6() {
       return puhelin;
   }
   
   /**
    * Palauttaa jäsenen jäsenmaksun statuksen
    * @return jäsenen jäsenmaksun status
    */
   public String get7() {
       return maksu;
   }
   
   /**
    * Palauttaa jäsenen liittymispäivän
    * @return jäsenen liittymispäivä
    */
   public String get8() {
       return liittynyt;
   }

   /**
    * Palauttaa jäsenen joukkueen
    * @return jäsenen joukkue
    */
   public int get9() {
       return joukkue;
   }
   
   /**
    * Palauttaa jäsenen väliaikaisen joukkueen
    * @return jäsenen joukkue
    */
   public String getTempJoukkueNimi() {
       return TempJoukkueNimi;
   }
   
   /**
    * Palauttaa jäsenen väliaikaisen joukkueen
    * @return jäsenen joukkue
    */
   public int getTempJoukkue() {
       return TempJoukkue;
   }
   
   /**
    * tietojen testaaminen
    */
   public void testi() {
       etunimi = "Matti" + seuraava;
       sukunimi = "Vanhanen";
       hetu = "041155-152A";
       katuosoite = "Mannerheimintie 2A";
       postinumero= "00100";
       postiosoite= "Helsinki";
       puhelin= "0400781827";
       liittynyt= "01.01.1960";
       maksu = "ok";
       joukkue = 6;          
   }
   
   
   /**
    * henkilön tietojen tulostaminen.
    * @param joukkuekko tulostetaanko joukkuekkin?
    * @return tiedot
    */
   public String tulosta(Boolean joukkuekko) {
       StringBuilder builder = new StringBuilder();
       builder.append("  Jäsen tunnus: " + String.format("%03d", tunnus, 3) + "\n");
       builder.append("  Etunimi:      " + etunimi+ "\n");
       builder.append("  Sukunimi:     " + sukunimi+ "\n");
       builder.append("  Hetu:         " + hetu+ "\n");
       builder.append("  Katuosoite:   " + katuosoite+ "\n");
       builder.append("  Postinumero:  " + postinumero+ "\n");
       builder.append("  Postiosoite:  " + postiosoite+ "\n");
       builder.append("  Puhelin:      " + puhelin+ "\n");
       builder.append("  Liittynyt:    " + liittynyt+ "\n");
       builder.append("  Jäsenmaksu:   " + maksu+ "\n");
       if(joukkuekko == true) builder.append("Joukkue:      " + String.format("%01d", joukkue, 1)+ "\n");
       return builder.toString();

   }

   
   /**
 * @param os = tietovirta, johon tulostetaan
 */
   public void tulosta(OutputStream os) {
    tulosta(new PrintStream(os));
   }
   
   
   /**
   * Apumetodi testille
   */
   public void testaa() {
       testi();
   }

   
   /** palauttaa jäsennumeron
    * @return Jäsenen jäsennumero
    */
   public int rekisteroi() {
       tunnus = seuraava;
       seuraava++;
       return tunnus;
   }
   
   
   /** Asettaa jäsenelle jäsennumeron ja varmistaa että seuraava on aina yhden suurempi.
    * @param nro asetettava jäsennumero
    */
   public void setTunnus(int nro) {
       tunnus = nro;
       if (tunnus >= seuraava) seuraava = tunnus +1;
   }
    
   
    /**
     * selvittää jäsenen tiedot | erotellusta merkkijonosta
     * pitää huolen että seuraava > tunnus
     * @param rivi = rivi josta jäsenen tiedot otetaan
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnus(Mjonot.erota(sb, '|', getTunnus()));
        etunimi = Mjonot.erota(sb, '|', etunimi);
        sukunimi = Mjonot.erota(sb, '|', sukunimi);
        hetu = Mjonot.erota(sb, '|', hetu);
        katuosoite = Mjonot.erota(sb, '|', katuosoite);
        postinumero = Mjonot.erota(sb, '|', postinumero);
        postiosoite = Mjonot.erota(sb, '|', postiosoite);
        puhelin = Mjonot.erota(sb, '|', puhelin);
        liittynyt = Mjonot.erota(sb, '|', liittynyt);
        maksu = Mjonot.erota(sb, '|', maksu);
        joukkue = Mjonot.erota(sb, '|', joukkue);
    }
    
    
    @Override
    public boolean equals(Object jasen) {
        if (jasen == null) return false;
        return this.toString().contentEquals(jasen.toString());
    }
    
    
    @Override
    public int hashCode() {
        return tunnus;
    }
    
    /**
     * miinustaa jäsenen tunnusta yhdellä
     */
    public void miinusTunnus() {
        if(tunnus > 0 && seuraava > 0) tunnus--;
    }
    
    /**
     * Jäsenen tietojen asettaminen silmukassa
     * indeksit:
     * 0 = nimi
     * 1 = sukunimi
     * 2 = henkilötunnus
     * 3 = osoite
     * 4 = postinumero
     * 5 = postiosoite
     * 6 = puhelin
     * 7 = maksu
     * 8 = päivä
     * @param args = teksti
     * @param index = tekstikentän indeksi
     */
    public void setTiedot(String args, int index) {
        switch(index) {
        case 0:
            tunnus = Integer.parseInt(args);
            seuraava++;
            break;
            
        case 1:
            etunimi = args;
            break;
        case 2:
            sukunimi = args;
            break;
            
        case 3:
            hetu = args;
            break;
            
        case 4:
            katuosoite = args;
            break;
            
        case 5:
            postinumero = args;
            break;
            
        case 6:
            postiosoite = args;
            break;
            
        case 7:
            puhelin = args;
            break;
            
        case 8:
            maksu = args;
            break;
            
        case 9:
            liittynyt = args;
            break;
            
        case 10:
            joukkue = Integer.parseInt(args);
            break;
            
        default:
            System.out.println("Laiton indeksi");
            break;
        }
    }
    
    
    /**
     * Palauttaa jäsenen väliaikaisen joukkueen
     * @param args = joukkueen nimi
     */
    public void setTempJoukkueNimi(String args) {
        TempJoukkueNimi = args;
    }
    
    /**
     * Palauttaa jäsenen väliaikaisen joukkueen
     * @param args = joukkueen tunnus
     */
    public void setTempJoukkue(int args) {
        TempJoukkue = args;
    }
    
    
    /**
     * Asettaa tiedot jäsenelle taulukosta
     * @param tiedot = palautettavat tiedot
     */
    public void palautaTiedot(String[] tiedot) {
        int i = 0;
        while(i < tiedot.length) {
            setTiedot(tiedot[i], i+1);
            i++;
        }
    }
    /**
     * Asettaa jäsenen nimen
     * @param args = nimi
     * @return null
     */
    public String set0(String args) {
        etunimi = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen sukunimen
     * @param args = sukunimi
     * @return null
     */
    public String set1(String args) {
        sukunimi = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen henkilötunnuksen
     * @param args = henkilötunnus
     * @return null
     */
    public String set2(String args) {
        hetu = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen osoitteen
     * @param args = osoite
     * @return null
     */
    public String set3(String args) {
        katuosoite = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen postinumeron
     * @param args = postinumero
     * @return null
     */
    public String set4(String args) {
        postinumero = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen postiosoitteen
     * @param args = postiosoite
     * @return null
     */
    public String set5(String args) {
         postiosoite = args;
         return null;
    }
    
    /**
     * Asettaa jäsenen puhelinnumeron
     * @param args = puhelinnumero
     * @return null
     */
    public String set6(String args) {
        puhelin = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen jäsenmaksun tilan
     * @param args = maksun tila
     * @return null
     */
    public String set7(String args) {
        maksu = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen liittymispäivän
     * @param args = päiväys
     * @return null
     */
    public String set8(String args) {
        liittynyt = args;
        return null;
    }
    
    /**
     * Asettaa jäsenen joukkueen
     * @param args joukkuen tunnus
     * @return null
     */
    public String setJoukkue(int args) {
        joukkue = args;
        return null;
        }
    
    /**
     * testi
     * @param args = ei käytössä
     */
    public static void main(String[] args) {
        Jasen matti = new Jasen(), matti2 = new Jasen(), matti3 = new Jasen();
        matti.rekisteroi();
        matti.testaa();
        matti.tulosta(System.out);
        
        System.out.println("");
        matti2.rekisteroi();
        matti2.testaa();
        matti2.tulosta(System.out);
        
        System.out.println("");
        matti3.rekisteroi();
        matti3.testaa();
        matti3.tulosta(System.out);
    }
}