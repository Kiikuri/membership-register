package ht;

/**
 * Tietorakenteen virheluokka.
 * @author miro
 * @version 27.1.2019
 *
 */
public class Virhe extends Exception{
    private static final long serialVersionUID = 1L;
    /**
     * Virheen muodostaja.
     * @param msg Virheen viesti
     */
    public Virhe(String msg) {
    super(msg);
    }
}