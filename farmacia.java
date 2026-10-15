// ============================================================================
// FILE: Prodotto.java
// ============================================================================

package com.mycompany.mavenproject2;

public abstract class Prodotto {

    private final int id;
    private final String nome;
    private final String produttore;
    private final String tipologia;
    private final double costo;

    public Prodotto(int id, String nome, String produttore,
                    String tipologia, double costo) {
        this.id = id;
        this.nome = nome;
        this.produttore = produttore;
        this.tipologia = tipologia;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getProduttore() {
        return produttore;
    }

    public String getTipologia() {
        return tipologia;
    }

    public double getCosto() {
        return costo;
    }

    /*
     * La chiave primaria è composta da nome e produttore.
     * Usiamo una String per rappresentare entrambi i valori nella HashMap.
     */
    public String getChiavePrimaria() {
        return creaChiave(nome, produttore);
    }

    public static String creaChiave(String nome, String produttore) {
        return (nome + "|" + produttore).toLowerCase();
    }

    @Override
    public String toString() {
        return "ID=" + id + ", nome=" + nome
                + ", produttore=" + produttore
                + ", tipologia=" + tipologia
                + ", costo=" + costo;
    }
}


// ============================================================================
// FILE: Farmaco.java
// ============================================================================

package com.mycompany.mavenproject2;

/*
 * Tutti i farmaci sono prodotti.
 * Farmaco è la classe padre di Allopatico e Omeopatico.
 */
public abstract class Farmaco extends Prodotto {

    public Farmaco(int id, String nome, String produttore,
                   String tipologia, double costo) {
        super(id, nome, produttore, tipologia, costo);
    }
}


// ============================================================================
// FILE: Curativo.java
// ============================================================================

package com.mycompany.mavenproject2;

public interface Curativo {

    // Riceve la patologia da verificare e restituisce true o false.
    boolean verificaCura(String patologia);

    // Restituisce il dosaggio già memorizzato nel prodotto.
    double getDosaggioConsigliato();
}


// ============================================================================
// FILE: Benefico.java
// ============================================================================

package com.mycompany.mavenproject2;

public interface Benefico {

    boolean supportaBeneficio(String beneficio);
}


// ============================================================================
// FILE: Scontabile.java
// ============================================================================

package com.mycompany.mavenproject2;

public interface Scontabile {

    void setSconto(double sconto);

    double getSconto();
}


// ============================================================================
// FILE: Allopatico.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;

/*
 * Tutti i farmaci allopatici sono curativi.
 * Per questo Allopatico implementa l'interfaccia Curativo.
 */
public class Allopatico extends Farmaco implements Curativo {

    private final String principioAttivo;
    private final ArrayList<String> eccipienti;
    private final ArrayList<String> patologie;
    private final double dosaggio;
    private final int etaMinima;

    public Allopatico(int id, String nome, String produttore,
                      String tipologia, double costo,
                      String principioAttivo,
                      ArrayList<String> eccipienti,
                      ArrayList<String> patologie,
                      double dosaggio, int etaMinima) {

        super(id, nome, produttore, tipologia, costo);

        this.principioAttivo = principioAttivo;
        this.eccipienti = eccipienti;
        this.patologie = patologie;
        this.dosaggio = dosaggio;
        this.etaMinima = etaMinima;
    }

    public String getPrincipioAttivo() {
        return principioAttivo;
    }

    public ArrayList<String> getEccipienti() {
        return eccipienti;
    }

    public ArrayList<String> getPatologie() {
        return patologie;
    }

    public int getEtaMinima() {
        return etaMinima;
    }

    @Override
    public double getDosaggioConsigliato() {
        return dosaggio;
    }

    /*
     * La traccia chiede di verificare se una patologia è curata.
     * Scorriamo la lista e restituiamo true appena troviamo una corrispondenza.
     */
    @Override
    public boolean verificaCura(String patologia) {
        for (String p : patologie) {
            if (p.equalsIgnoreCase(patologia)) {
                return true;
            }
        }
        return false;
    }
}


// ============================================================================
// FILE: Omeopatico.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;

/*
 * Gli omeopatici hanno componenti propri e possono essere scontati.
 * La distinzione tra omeopatici curativi e benefici viene fatta dalle
 * due classi figlie.
 */
public abstract class Omeopatico extends Farmaco implements Scontabile {

    private final String componentePrimario;
    private final ArrayList<String> altriComponenti;
    private double sconto;

    public Omeopatico(int id, String nome, String produttore,
                      String tipologia, double costo,
                      String componentePrimario,
                      ArrayList<String> altriComponenti) {

        super(id, nome, produttore, tipologia, costo);

        this.componentePrimario = componentePrimario;
        this.altriComponenti = altriComponenti;
        this.sconto = 0;
    }

    public String getComponentePrimario() {
        return componentePrimario;
    }

    public ArrayList<String> getAltriComponenti() {
        return altriComponenti;
    }

    @Override
    public void setSconto(double sconto) {
        this.sconto = sconto;
    }

    @Override
    public double getSconto() {
        return sconto;
    }
}


// ============================================================================
// FILE: OmeopaticoCurativo.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;

/*
 * Questa classe rappresenta un omeopatico che è curativo.
 * Eredita i componenti e lo sconto da Omeopatico.
 */
public class OmeopaticoCurativo extends Omeopatico implements Curativo {

    private final ArrayList<String> patologie;
    private final double dosaggio;

    public OmeopaticoCurativo(int id, String nome, String produttore,
                              String tipologia, double costo,
                              String componentePrimario,
                              ArrayList<String> altriComponenti,
                              ArrayList<String> patologie,
                              double dosaggio) {

        super(id, nome, produttore, tipologia, costo,
                componentePrimario, altriComponenti);

        this.patologie = patologie;
        this.dosaggio = dosaggio;
    }

    @Override
    public boolean verificaCura(String patologia) {
        for (String p : patologie) {
            if (p.equalsIgnoreCase(patologia)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double getDosaggioConsigliato() {
        return dosaggio;
    }
}


// ============================================================================
// FILE: OmeopaticoBenefico.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;

/*
 * Questa classe rappresenta un omeopatico che dichiara benefici.
 */
public class OmeopaticoBenefico extends Omeopatico implements Benefico {

    private final ArrayList<String> benefici;

    public OmeopaticoBenefico(int id, String nome, String produttore,
                              String tipologia, double costo,
                              String componentePrimario,
                              ArrayList<String> altriComponenti,
                              ArrayList<String> benefici) {

        super(id, nome, produttore, tipologia, costo,
                componentePrimario, altriComponenti);

        this.benefici = benefici;
    }

    @Override
    public boolean supportaBeneficio(String beneficio) {
        for (String b : benefici) {
            if (b.equalsIgnoreCase(beneficio)) {
                return true;
            }
        }
        return false;
    }
}


// ============================================================================
// FILE: Integratore.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;

/*
 * L'integratore è un Prodotto, ma non un Farmaco.
 * È Benefico e Scontabile, come stabilito dalla traccia.
 */
public class Integratore extends Prodotto implements Benefico, Scontabile {

    private final String componentePrincipale;
    private final ArrayList<String> benefici;
    private final String dosaggioGiornaliero;
    private double sconto;

    public Integratore(int id, String nome, String produttore,
                       String tipologia, double costo,
                       String componentePrincipale,
                       ArrayList<String> benefici,
                       String dosaggioGiornaliero) {

        super(id, nome, produttore, tipologia, costo);

        this.componentePrincipale = componentePrincipale;
        this.benefici = benefici;
        this.dosaggioGiornaliero = dosaggioGiornaliero;
        this.sconto = 0;
    }

    public String getComponentePrincipale() {
        return componentePrincipale;
    }

    public ArrayList<String> getBenefici() {
        return benefici;
    }

    public String getDosaggioGiornaliero() {
        return dosaggioGiornaliero;
    }

    @Override
    public boolean supportaBeneficio(String beneficio) {
        for (String b : benefici) {
            if (b.equalsIgnoreCase(beneficio)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setSconto(double sconto) {
        this.sconto = sconto;
    }

    @Override
    public double getSconto() {
        return sconto;
    }
}


// ============================================================================
// FILE: Ricetta.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;

/*
 * La ricetta contiene i dati richiesti dalla traccia per il controllo
 * di compatibilità con un farmaco prescrivibile.
 */
public class Ricetta {

    private final String nomeFarmaco;
    private final String nomeProduttore;
    private final String patologia;
    private final int etaPaziente;
    private final ArrayList<String> componentiRischiosi;

    public Ricetta(String nomeFarmaco, String nomeProduttore,
                   String patologia, int etaPaziente,
                   ArrayList<String> componentiRischiosi) {

        this.nomeFarmaco = nomeFarmaco;
        this.nomeProduttore = nomeProduttore;
        this.patologia = patologia;
        this.etaPaziente = etaPaziente;
        this.componentiRischiosi = componentiRischiosi;
    }

    public String getNomeFarmaco() {
        return nomeFarmaco;
    }

    public String getNomeProduttore() {
        return nomeProduttore;
    }

    public String getPatologia() {
        return patologia;
    }

    public int getEtaPaziente() {
        return etaPaziente;
    }

    public ArrayList<String> getComponentiRischiosi() {
        return componentiRischiosi;
    }
}


// ============================================================================
// FILE: RicettaNonCompatibileException.java
// ============================================================================

package com.mycompany.mavenproject2;

/*
 * Errore se la ricetta non corrisponde al farmaco o se l'età del paziente
 * è inferiore a quella minima prevista.
 */
public class RicettaNonCompatibileException extends Exception {

    public RicettaNonCompatibileException(String messaggio) {
        super(messaggio);
    }
}


// ============================================================================
// FILE: PatologiaNonCurataException.java
// ============================================================================

package com.mycompany.mavenproject2;

public class PatologiaNonCurataException extends Exception {

    public PatologiaNonCurataException(String messaggio) {
        super(messaggio);
    }
}


// ============================================================================
// FILE: ComponenteRischiosoException.java
// ============================================================================

package com.mycompany.mavenproject2;

public class ComponenteRischiosoException extends Exception {

    public ComponenteRischiosoException(String messaggio) {
        super(messaggio);
    }
}


// ============================================================================
// FILE: EsitoRicetta.java
// ============================================================================

package com.mycompany.mavenproject2;

/*
 * Il metodo della ricetta deve restituire due risultati:
 * dosaggio e percentuale da pagare.
 *
 * Java restituisce un solo oggetto per chiamata, quindi questa classe
 * contiene entrambi i risultati.
 */
public class EsitoRicetta {

    private final double dosaggio;
    private final int percentualeDaPagare;

    public EsitoRicetta(double dosaggio, int percentualeDaPagare) {
        this.dosaggio = dosaggio;
        this.percentualeDaPagare = percentualeDaPagare;
    }

    public double getDosaggio() {
        return dosaggio;
    }

    public int getPercentualeDaPagare() {
        return percentualeDaPagare;
    }
}


// ============================================================================
// FILE: FarmacoPrescrivibile.java
// ============================================================================

package com.mycompany.mavenproject2;

/*
 * FarmacoPrescrivibile è una sottocategoria di Allopatico.
 */
public class FarmacoPrescrivibile extends Allopatico {

    public FarmacoPrescrivibile(int id, String nome, String produttore,
                                String tipologia, double costo,
                                String principioAttivo,
                                java.util.ArrayList<String> eccipienti,
                                java.util.ArrayList<String> patologie,
                                double dosaggio, int etaMinima) {

        super(id, nome, produttore, tipologia, costo,
                principioAttivo, eccipienti, patologie,
                dosaggio, etaMinima);
    }

    /*
     * Se la ricetta non è compatibile, il metodo solleva un'eccezione.
     * Altrimenti restituisce dosaggio e percentuale del costo da pagare.
     */
    public EsitoRicetta valutaRicetta(Ricetta ricetta)
            throws RicettaNonCompatibileException,
                   PatologiaNonCurataException,
                   ComponenteRischiosoException {

        if (!getNome().equalsIgnoreCase(ricetta.getNomeFarmaco())
                || !getProduttore().equalsIgnoreCase(
                        ricetta.getNomeProduttore())) {

            throw new RicettaNonCompatibileException(
                    "La ricetta non corrisponde al farmaco.");
        }

        if (ricetta.getEtaPaziente() < getEtaMinima()) {
            throw new RicettaNonCompatibileException(
                    "Il paziente non raggiunge l'età minima.");
        }

        if (!verificaCura(ricetta.getPatologia())) {
            throw new PatologiaNonCurataException(
                    "La patologia non è curata da questo farmaco.");
        }

        /*
         * Si controllano tutti i componenti rischiosi.
         * Se anche uno coincide con il principio attivo o un eccipiente,
         * viene sollevata l'eccezione.
         */
        for (String componente : ricetta.getComponentiRischiosi()) {

            if (getPrincipioAttivo().equalsIgnoreCase(componente)) {
                throw new ComponenteRischiosoException(
                        "Componente rischioso presente nel principio attivo.");
            }

            for (String eccipiente : getEccipienti()) {
                if (eccipiente.equalsIgnoreCase(componente)) {
                    throw new ComponenteRischiosoException(
                            "Componente rischioso presente negli eccipienti.");
                }
            }
        }

        // Math.random() genera una percentuale intera tra 50 e 100 inclusi.
        int percentuale = 50 + (int) (Math.random() * 51);

        return new EsitoRicetta(getDosaggioConsigliato(), percentuale);
    }
}


// ============================================================================
// FILE: ProdottoInDeposito.java
// ============================================================================

package com.mycompany.mavenproject2;

/*
 * Il valore della HashMap contiene il prodotto e il numero di campioni
 * disponibili in deposito.
 */
public class ProdottoInDeposito {

    private final Prodotto prodotto;
    private int quantita;

    public ProdottoInDeposito(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void aggiungiCampioni(int quantita) {
        this.quantita += quantita;
    }

    public void vendiUnCampione() {
        if (quantita <= 0) {
            throw new IllegalStateException("Campioni esauriti.");
        }

        quantita--;
    }
}


// ============================================================================
// FILE: Farmacia.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Farmacia {

    /*
     * Chiave: nome + produttore.
     * Valore: prodotto e quantità in deposito.
     */
    private final HashMap<String, ProdottoInDeposito> catalogo;

    public Farmacia() {
        catalogo = new HashMap<>();
    }

    /*
     * Verifica se il codice numerico è già usato da un altro prodotto.
     * La traccia richiede che l'ID sia univoco.
     */
    private boolean idGiaPresente(int id) {
        for (ProdottoInDeposito deposito : catalogo.values()) {
            if (deposito.getProdotto().getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {

        String chiave = prodotto.getChiavePrimaria();

        /*
         * Se la chiave esiste già, aggiungiamo i campioni alla voce esistente.
         * Altrimenti inseriamo una nuova voce nel catalogo.
         */
        if (catalogo.containsKey(chiave)) {
            catalogo.get(chiave).aggiungiCampioni(quantita);
        } else {
            if (idGiaPresente(prodotto.getId())) {
                throw new IllegalArgumentException("ID già presente nel catalogo.");
            }

            catalogo.put(chiave,
                    new ProdottoInDeposito(prodotto, quantita));
        }
    }

    /*
     * Ricerca per nome e produttore: sono i due componenti della chiave.
     * Se non esiste, restituiamo null.
     */
    public Prodotto cercaPerNomeProduttore(String nome, String produttore) {

        String chiave = Prodotto.creaChiave(nome, produttore);

        ProdottoInDeposito deposito = catalogo.get(chiave);

        if (deposito == null) {
            return null;
        }

        return deposito.getProdotto();
    }

    /*
     * Stampa numero complessivo di campioni e costo totale per categoria.
     * Il costo totale è il costo unitario moltiplicato per la quantità.
     */
    public void stampaTotaleFarmaci(String categoria) {

        int numeroFarmaci = 0;
        double costoTotale = 0;

        for (ProdottoInDeposito deposito : catalogo.values()) {

            Prodotto prodotto = deposito.getProdotto();
            boolean categoriaCorretta = false;

            if (categoria.equalsIgnoreCase("allopatico")
                    && prodotto instanceof Allopatico) {
                categoriaCorretta = true;
            }

            if (categoria.equalsIgnoreCase("omeopatico")
                    && prodotto instanceof Omeopatico) {
                categoriaCorretta = true;
            }

            if (categoriaCorretta) {
                numeroFarmaci += deposito.getQuantita();
                costoTotale += prodotto.getCosto() * deposito.getQuantita();
            }
        }

        System.out.println("Categoria: " + categoria);
        System.out.println("Numero totale di farmaci: " + numeroFarmaci);
        System.out.println("Costo complessivo: " + costoTotale);
    }

    /*
     * Ricerca curativi per patologia, limitando la ricerca alla categoria
     * allopatica oppure omeopatica richiesta.
     */
    public ArrayList<Prodotto> cercaCurativi(String patologia, String categoria) {

        ArrayList<Prodotto> risultati = new ArrayList<>();

        for (ProdottoInDeposito deposito : catalogo.values()) {

            Prodotto prodotto = deposito.getProdotto();
            boolean categoriaCorretta = false;

            if (categoria.equalsIgnoreCase("allopatico")
                    && prodotto instanceof Allopatico) {
                categoriaCorretta = true;
            }

            if (categoria.equalsIgnoreCase("omeopatico")
                    && prodotto instanceof Omeopatico) {
                categoriaCorretta = true;
            }

            /*
             * instanceof controlla il tipo reale dell'oggetto.
             * Un Prodotto può implementare Curativo oppure no.
             *
             * Dopo il controllo effettuiamo il cast per usare il metodo
             * dell'interfaccia Curativo.
             */
            if (categoriaCorretta && prodotto instanceof Curativo) {

                Curativo curativo = (Curativo) prodotto;

                if (curativo.verificaCura(patologia)) {
                    risultati.add(prodotto);
                }
            }
        }

        return risultati;
    }

    /*
     * Restituisce tutti gli allopatici che hanno il principio attivo richiesto.
     * FarmacoPrescrivibile viene incluso perché estende Allopatico.
     */
    public ArrayList<Allopatico> cercaAllopaticiPerPrincipio(String principio) {

        ArrayList<Allopatico> risultati = new ArrayList<>();

        for (ProdottoInDeposito deposito : catalogo.values()) {

            Prodotto prodotto = deposito.getProdotto();

            if (prodotto instanceof Allopatico) {

                Allopatico allopatico = (Allopatico) prodotto;

                if (allopatico.getPrincipioAttivo().equalsIgnoreCase(principio)) {
                    risultati.add(allopatico);
                }
            }
        }

        return risultati;
    }

    /*
     * La farmacia vende un campione soltanto se il prodotto esiste
     * e la quantità disponibile è maggiore di zero.
     */
    public void vendi(String nome, String produttore) {

        String chiave = Prodotto.creaChiave(nome, produttore);

        ProdottoInDeposito deposito = catalogo.get(chiave);

        if (deposito == null) {
            throw new IllegalArgumentException("Prodotto non presente nel catalogo.");
        }

        deposito.vendiUnCampione();
    }

    /*
     * Stampa le voci del catalogo.
     */
    public void stampaCatalogo() {
        for (Map.Entry<String, ProdottoInDeposito> voce
                : catalogo.entrySet()) {
            System.out.println(voce.getKey() + " -> "
                    + voce.getValue().getProdotto()
                    + ", quantità=" + voce.getValue().getQuantita());
        }
    }
}


// ============================================================================
// FILE: Main.java
// ============================================================================

package com.mycompany.mavenproject2;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    /*
     * Metodo di supporto per creare liste rapidamente nel Main.
     * Evita di ripetere ogni volta la stessa istruzione.
     */
    private static ArrayList<String> lista(String... valori) {
        return new ArrayList<>(Arrays.asList(valori));
    }

    public static void main(String[] args) {

        Farmacia farmacia = new Farmacia();

        // Creiamo prodotti di tutte le categorie principali.

        Allopatico paracetamolo = new Allopatico(
                1, "Paracetamolo", "Produttore A",
                "Compresse", 5.0,
                "Paracetamolo",
                lista("Amido", "Cellulosa"),
                lista("Febbre", "Dolore"),
                500, 12
        );

        FarmacoPrescrivibile amoxicillina = new FarmacoPrescrivibile(
                2, "Amoxicillina", "Produttore B",
                "Capsule", 12.0,
                "Amoxicillina",
                lista("Amido", "Cellulosa"),
                lista("Infezione batterica"),
                500, 12
        );

        OmeopaticoCurativo arnica = new OmeopaticoCurativo(
                3, "Arnica", "Produttore C",
                "Granuli", 8.0,
                "Arnica montana",
                lista("Saccarosio"),
                lista("Contusioni"),
                3
        );

        OmeopaticoBenefico relax = new OmeopaticoBenefico(
                4, "Relax", "Produttore C",
                "Granuli", 10.0,
                "Miscela vegetale",
                lista("Saccarosio"),
                lista("Rilassamento", "Benessere")
        );

        Integratore vitaminaC = new Integratore(
                5, "Vitamina C", "Produttore D",
                "Compresse", 9.0,
                "Vitamina C",
                lista("Difese immunitarie", "Benessere"),
                "Una compressa al giorno"
        );

        // Soltanto omeopatici e integratori sono scontabili.
        relax.setSconto(10);
        vitaminaC.setSconto(15);

        // Inserimento nel catalogo con quantità disponibili.
        farmacia.aggiungiProdotto(paracetamolo, 10);
        farmacia.aggiungiProdotto(amoxicillina, 8);
        farmacia.aggiungiProdotto(arnica, 5);
        farmacia.aggiungiProdotto(relax, 3);
        farmacia.aggiungiProdotto(vitaminaC, 6);

        System.out.println("=== CATALOGO ===");
        farmacia.stampaCatalogo();

        // Statistiche sulle due categorie di farmaci.
        System.out.println("\n=== STATISTICHE ===");
        farmacia.stampaTotaleFarmaci("allopatico");
        farmacia.stampaTotaleFarmaci("omeopatico");

        // Ricerca per nome e produttore.
        System.out.println("\n=== RICERCA PER NOME E PRODUTTORE ===");

        Prodotto trovato = farmacia.cercaPerNomeProduttore(
                "Vitamina C", "Produttore D");

        System.out.println(trovato);

        // Ricerca per patologia e categoria.
        System.out.println("\n=== CURATIVI ALLOPATICI PER FEBBRE ===");

        ArrayList<Prodotto> curativi =
                farmacia.cercaCurativi("Febbre", "allopatico");

        for (Prodotto p : curativi) {
            System.out.println(p);
        }

        System.out.println("\n=== CURATIVI OMEOPATICI PER CONTUSIONI ===");

        ArrayList<Prodotto> omeopatici =
                farmacia.cercaCurativi("Contusioni", "omeopatico");

        for (Prodotto p : omeopatici) {
            System.out.println(p);
        }

        // Ricerca di allopatici per principio attivo.
        System.out.println("\n=== RICERCA PER PRINCIPIO ATTIVO ===");

        ArrayList<Allopatico> trovati =
                farmacia.cercaAllopaticiPerPrincipio("Amoxicillina");

        for (Allopatico a : trovati) {
            System.out.println(a.getNome());
        }

        // Valutazione di una ricetta compatibile.
        System.out.println("\n=== RICETTA VALIDA ===");

        Ricetta ricetta = new Ricetta(
                "Amoxicillina",
                "Produttore B",
                "Infezione batterica",
                30,
                lista("Colorante blu")
        );

        try {
            EsitoRicetta esito = amoxicillina.valutaRicetta(ricetta);

            System.out.println("Dosaggio: " + esito.getDosaggio());
            System.out.println("Percentuale da pagare: "
                    + esito.getPercentualeDaPagare() + "%");

            double costoDaPagare = amoxicillina.getCosto()
                    * esito.getPercentualeDaPagare() / 100.0;

            System.out.println("Costo da pagare: " + costoDaPagare);

        } catch (RicettaNonCompatibileException e) {
            System.out.println(e.getMessage());

        } catch (PatologiaNonCurataException e) {
            System.out.println(e.getMessage());

        } catch (ComponenteRischiosoException e) {
            System.out.println(e.getMessage());
        }

        // Proviamo una ricetta incompatibile per patologia.
        System.out.println("\n=== RICETTA CON PATOLOGIA ERRATA ===");

        Ricetta ricettaErrata = new Ricetta(
                "Amoxicillina",
                "Produttore B",
                "Influenza",
                30,
                lista()
        );

        try {
            amoxicillina.valutaRicetta(ricettaErrata);

        } catch (RicettaNonCompatibileException e) {
            System.out.println(e.getMessage());

        } catch (PatologiaNonCurataException e) {
            System.out.println(e.getMessage());

        } catch (ComponenteRischiosoException e) {
            System.out.println(e.getMessage());
        }

        // Proviamo una ricetta con un componente rischioso.
        System.out.println("\n=== RICETTA CON COMPONENTE RISCHIOSO ===");

        Ricetta ricettaRischiosa = new Ricetta(
                "Amoxicillina",
                "Produttore B",
                "Infezione batterica",
                30,
                lista("Amoxicillina")
        );

        try {
            amoxicillina.valutaRicetta(ricettaRischiosa);

        } catch (RicettaNonCompatibileException e) {
            System.out.println(e.getMessage());

        } catch (PatologiaNonCurataException e) {
            System.out.println(e.getMessage());

        } catch (ComponenteRischiosoException e) {
            System.out.println(e.getMessage());
        }

        // Vendita: ogni vendita diminuisce di uno la quantità in deposito.
        System.out.println("\n=== VENDITA ===");

        try {
            farmacia.vendi("Vitamina C", "Produttore D");
            System.out.println("Vendita effettuata.");

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
