package pazienti;

import java.util.Scanner;

public class Pazienti {
    static Scanner input = new Scanner(System.in);
    static void SOP(String s) {System.out.println(s);}
    static int leggiInt() { return input.nextInt(); }
    static double leggiDouble() { return input.nextDouble(); }

    static int chiediIntPos(String richiesta)
    {
        int n;
        do
        {
            SOP(richiesta);
            n = leggiInt();
        }while(n<=0);
        return n;
    }
    
    static void stampaMenu()
    {
        SOP("0.    Uscita");
        SOP("1.    Inserimento di un nuovo utente (fino ad un massimo prestabilito);");
        SOP("2.    Stampa dei valori di tutti gli utenti ordinati in ordine decrescente di peso;");
        SOP("3.    Ricerca e stampa delle informazioni relative ad un paziente, letto in input il suo codice;");
        SOP("4.    Stampa dell’altezza media;");
        SOP("5.    Stampa dei codici di tutti i pazienti la cui altezza supera il valore medio;");
        SOP("6.    Stampa del codice corrispondente al paziente più leggero.");
    }
    
    static void stampa(int[] c, int[] h, int [] w, int n)
    {
        for(int i=0;i<n;i++)
            SOP(c[i]+" "+h[i]+" "+w[i]);        
    }
    static boolean isPresente(int[] v, int n, int valore)
    {
        for(int i=0;i<n;i++)
            if(v[i]==valore)
                return true;
        return false;
    }
    static void inserisci(int[] c, int[] h, int [] w, int pos)
    {
        do
        {
            c[pos] = chiediIntPos("codice?");
        }while(isPresente(c,pos,c[pos]));        
        h[pos] = chiediIntPos("altezza?");
        w[pos] = chiediIntPos("peso?");
    }
    
    public static void main(String[] args) {
        int massimoNumeroDiPazienti;
        massimoNumeroDiPazienti = chiediIntPos("Quanti pazienti vuoi gestire al massimo?");
        int[] codici, pesi, altezze;
        codici = new int[massimoNumeroDiPazienti];
        pesi = new int[massimoNumeroDiPazienti];
        altezze = new int[massimoNumeroDiPazienti];
        
        int posUltimoInserito = 0;
        
        int scelta;
        do
        {
            stampaMenu();
            scelta = leggiInt();
            switch(scelta)
            {
                case 0:
                    SOP("0.    Uscita");
                    break;
                case 1:
                    SOP("1.    Inserimento di un nuovo utente (fino ad un massimo prestabilito);");
                    if(posUltimoInserito<massimoNumeroDiPazienti)
                    {
                        inserisci(codici, altezze, pesi, posUltimoInserito);
                        posUltimoInserito++;
                    }
                    else
                        SOP("Vettori pieni");
                    break;
                case 2:
                    SOP("2.    Stampa dei valori di tutti gli utenti ordinati in ordine decrescente di peso;");
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                default:
                    SOP("Offesa a piacere");
            }
            stampa(codici,altezze,pesi,posUltimoInserito);
        }while(scelta!=0);
    }
    
}
