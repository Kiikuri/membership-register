package ht;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * Jäsen luokka, joka huolehtii yksittäisestä jäsenestä.
 * @author miro
 * @version 10.3.2019
 *
 */
public class Testit {
    
    /**
     * testi
     * @param args = ei käytössä
     */
    public static void main(String[] args) {
        Testit testi = new Testit();
        System.out.println(testi.AikaSyntaksi("21:00"));
        System.out.println(testi.AikaSyntaksi("21.02.2020"));
        System.out.println(testi.PaivaSyntaksi("21:00"));
        System.out.println(testi.PaivaSyntaksi("21.02.2020"));
        System.out.println(testi.onkoKarkausVuosi(2020));
        System.out.println(testi.onkoKarkausVuosi(2019));
    }
    
    
    /**
     * Tarkistaa ajan syntaksin
     * @param text = aika
     * @return = true/false
     */
    public boolean AikaSyntaksi(String text) {
        if(text.length() != 5) return false;
        if(text.contains(":") == false) return false;
        String tunnit = text.substring(0, 2);
        String minuutit = text.substring(3, 5);
        try {
            int tun = Integer.parseInt(tunnit);
            int min = Integer.parseInt(minuutit);
            if(tun > 23 | tun < 0) return false;
            if(min > 59 | min < 0) return false;
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Tarkistaa päivän syntaksin
     * @param text = aika
     * @return true/false
     */
    public boolean PaivaSyntaksi(String text) {
        if(text.length() != 10) return false;
        if(text.charAt(2) != '.') return false;
        if(text.charAt(5) != '.') return false;
        String paiva = text.substring(0, 2);
        String kuukausi = text.substring(3, 5);
        String vuosi = text.substring(6, 10);
        try {
            int pv = Integer.parseInt(paiva);
            int kk = Integer.parseInt(kuukausi);
            int vv = Integer.parseInt(vuosi);
            if(pv > 31 | pv < 0) return false;
            if(kk > 12 | kk < 0) return false;
            if(vv > 2400 | vv < 1900) return false;
            if(kk == 1 && pv > 31) return false;
            if(kk == 2 && pv > 28) 
            {
                if(onkoKarkausVuosi(vv) == true && pv <= 29) return true;
                return false;
            }
            if(kk == 3 && pv > 31) return false;
            if(kk == 4 && pv > 30) return false;
            if(kk == 5 && pv > 31) return false;
            if(kk == 6 && pv > 30) return false;
            if(kk == 7 && pv > 31) return false;
            if(kk == 8 && pv > 31) return false;
            if(kk == 9 && pv > 30) return false;
            if(kk == 10 && pv > 31) return false;
            if(kk == 11 && pv > 30) return false;
            if(kk == 12 && pv > 31) return false;
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * @param yy = vuosi
     * @return true/false
     */
    public boolean onkoKarkausVuosi(int yy){
        if (((yy % 4 == 0) && (yy % 100 != 0)) |(yy % 400 == 0)) return true;
        return false;
    }
    
    /**
     * tarkistaa, ettei lopetus voi olla aikaisemmin kuin aloitus
     * @param aika1 = aloitus
     * @param aika2 = lopetus
     * @return true/false
     */
    public boolean oikeaAika(String aika1, String aika2) {
        int erotus1 = Integer.parseInt(aika1.substring(0, 2));
        int erotus2 = Integer.parseInt(aika2.substring(0, 2));
        if(erotus2 < erotus1 && erotus2 <= 10 && erotus1 >= 16) erotus2 = 24 + erotus2;
        erotus1 = erotus1*60 + Integer.parseInt(aika1.substring(3,5));
        erotus2 = erotus2*60 + Integer.parseInt(aika2.substring(3,5));
        if(erotus1 >= erotus2) {
            return false;
        }
        return true;
    }
    
    /**
     * Tarkistaa postinumeron syntaksin
     * @param text = numero
     * @return true/false
     */
    public boolean postinSyntaksi(String text) {
        if(text.length() != 5) return false;
        if(text.charAt(4) != '0') return false;
        try {
            Integer.parseInt(text);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    /**
     * Tarkistaa puhelinnumeron syntaksin
     * @param text = numero
     * @return true/false
     */
    public boolean puhelinSyntaksi(String text) {
        String numero = text;
        if(numero.length() < 10 | numero.length() > 13) return false;
        if(numero.charAt(0) == '+') {
            StringBuilder sana = new StringBuilder();
            sana.append(numero.substring(4, numero.length()));
            numero = sana.toString();
        }
        try {
            Integer.parseInt(numero);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
    
    
    /**
     * Tarkistaa henkilötunnuksen syntaksin
     * @param text = hetu
     * @return true/false
     */
    public boolean hetuSyntaksi(String text) {
        if(text.length() != 11) return false;
        if(text.charAt(6) == '-' | text.charAt(6) == 'A' | text.charAt(6) == '+');
        else return false;
        String paiva = text.substring(0, 2);
        String kuukausi = text.substring(2, 4);
        String vuosi = text.substring(4, 6);
        String viivanum = text.substring(7, 10);
        String kirj = text.substring(10, 11);
        try {
            int pv = Integer.parseInt(paiva);
            int kk = Integer.parseInt(kuukausi);
            int vv = Integer.parseInt(vuosi);
            int viiv = Integer.parseInt(viivanum);
            try {
                Integer.parseInt(kirj);
            }
            catch(Exception e) {
                Pattern testi = Pattern.compile ("[ABCDEFHJKLMNPRSTUVXYZÅÄÖ]");
                if(testi.matcher(kirj).find() == false) return false; 
            }
            
            if(viiv > 899 | viiv < 2) return false;
            if(pv > 31 | pv < 0) return false;
            if(kk > 12 | kk < 0) return false;
            if(vv > 99 | vv < 0) return false;
            if(kk == 1 && pv > 31) return false;
            if(kk == 2 && pv > 28) 
            {
                if((text.charAt(6) == 'A' && vv == 0 && pv == 29) | (vv % 4 == 0 && pv == 29)) return true;
                return false;
            }
            if(kk == 3 && pv > 31) return false;
            if(kk == 4 && pv > 30) return false;
            if(kk == 5 && pv > 31) return false;
            if(kk == 6 && pv > 30) return false;
            if(kk == 7 && pv > 31) return false;
            if(kk == 8 && pv > 31) return false;
            if(kk == 9 && pv > 30) return false;
            if(kk == 10 && pv > 31) return false;
            if(kk == 11 && pv > 30) return false;
            if(kk == 12 && pv > 31) return false;
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }
}
