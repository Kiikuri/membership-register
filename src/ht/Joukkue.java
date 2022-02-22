package ht;

import java.io.*;
import java.util.ArrayList;

/**
 * Joukkue luokka, joka huolehtii yksittäisestä joukkueesta.
 * @author miro
 * @version 10.3.2019
 *
 */
public class Joukkue {
    private int JoukkueTunnus;
    private String JoukkueNimi = "";
    private String Lyhenne = "";
    public static int JoukkueSeuraava = 0;
    
    /**
     * alustaa joukkue luokan
     */
    public void JoukkueAlusta() {
        JoukkueTunnus = 0;
        JoukkueNimi = "";
        Lyhenne = "";
        JoukkueSeuraava = 0;
    }
   /**
    * palauttaa joukkueen nimen
    * @return joukkueen nimi
    */
   public String getJoukkueNimi() {
       return JoukkueNimi;
   }
   
   /**
    * palauttaa joukkueen joukkuetunnuksen
    * @return joukkueen joukkuetunnus
    */
   public int getJoukkueTunnus() {
       return JoukkueTunnus;
   }
   
   /**
    * palauttaa joukkueen lyhenteen
    * @return joukkueen lyhenne
    */
    public String getLyhenne() {
       return Lyhenne;
   }
   
    
    /**
     * palauttaa joukkueen tiedot
     * @return tiedot
     */
    public ArrayList<String> annaJoukkueTiedot() {
        ArrayList<String> tiedot = new ArrayList<String>();
        tiedot.add(getJoukkueNimi());
        tiedot.add(getLyhenne());
        return tiedot;
    }
    
    /**
     * miinustaa jäsenen tunnusta yhdellä
     */
    public void miinusJoukkueTunnus() {
        if(JoukkueTunnus > 0 && JoukkueSeuraava > 0)
        JoukkueTunnus--;
    }
    
    /**
     * Lisää lyhenteen ja nimen joukkueelle.
     * @param args = joukkueen nimi;
     * @param uusi = uusi joukkue
     */
    public void UusiJoukkue(String args, Joukkue uusi) {
        uusi.JoukkueNimi = args;
        if (args.length() > 2 && args.contains(" ") == true) {
            StringBuilder build = new StringBuilder();
            build.append(args.substring(0, 2));
            char s = Character.toUpperCase(build.charAt(0));
            build.replace(0, 1, Character.toString(s));
            if(args.indexOf(" ") < args.length()-2) {
                 build.append(args.substring(args.indexOf(" ")+1,args.indexOf(" ")+3));
                 s = Character.toUpperCase(build.charAt(2));
                 build.replace(2, 3, Character.toString(s));
            }
            uusi.Lyhenne = build.toString();
        }
        else uusi.Lyhenne = args;
        uusi.JoukkueTunnus = rekisteroiJouk();
    }
    
    /** asettaa joukkuenumeron
     * @param args = tunnus String muodossa
     */
    public void setJoukkueTunnus(String args) {
        JoukkueTunnus = Integer.parseInt(args);
        JoukkueSeuraava++;          
    }
    
    /**
     * Asettaa joukkueelle uuden nimen
     * @param args Joukkueen uusi nimi
     */
    public void setJoukkueNimi(String args) {
        JoukkueNimi = args;
    }
    
    /**
     * Asettaa joukkueelle uuden lyhenteen
     * @param args Joukkueen uusi lyhenne
     */
    public void setLyhenne(String args) {
        Lyhenne = args;
    }
    
    
    /**
     * Joukkueen tietojen asettaminen silmukassa
     * indeksit:
     * 0 = tunnus
     * 1 = nimi
     * 2 = lyhenne
     * @param args = teksti
     * @param index = tekstikentän indeksi
     */
    public void setJoukkueTiedot(String args, int index) {
        switch(index) {
        case 0:
            JoukkueTunnus = Integer.parseInt(args);
            JoukkueSeuraava++;
            break;
            
        case 1:
            JoukkueNimi = args;
            break;
        case 2:
            Lyhenne = args;
            break;
            
        default:
            System.out.println("Laiton indeksi");
            break;
        }
    }
    
   
   
   /**
    * joukkueen tietojen tulostaminen.
    * @param out tietovirta, johon tulostetaan
    */
   public void tulostaJ(PrintStream out) {
       out.println("Joukkue tunnus: " + String.format("%01d", JoukkueTunnus, 1));
       out.println("Joukkueen nimi: " + JoukkueNimi);
       out.println("Lyhenne:        " + Lyhenne);
   }

   /**
 * @param os = tietovirta, johon tulostetaan
 */
   public void tulostaJ(OutputStream os) {
    tulostaJ(new PrintStream(os));
   }
   
   /**
   * Testaa tiedot
   */
   public void testaaJ() {
       JoukkueNimi = "Helsingin Palloseura";
       Lyhenne = "HePa";
   }

   /** palauttaa joukkuenumeron
    * @return Jäsenen joukkuenumero
    */
   public int rekisteroiJouk() {
       JoukkueTunnus = JoukkueSeuraava;
       JoukkueSeuraava++;
       return JoukkueTunnus;
               
   }

    /**
     * testi
     * @param args = ei käytössä
     */
    public static void main(String[] args) {
        Joukkue hepa = new Joukkue();
        hepa.rekisteroiJouk();
        hepa.testaaJ();
        Joukkue hepa1 = new Joukkue();
        hepa1.UusiJoukkue("helsingin palloseura", hepa1);
        
        hepa.tulostaJ(System.out);
        hepa1.tulostaJ(System.out);
    }

}