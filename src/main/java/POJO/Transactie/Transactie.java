package POJO.Transactie;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transactie {
    public String rekeningnummer;
    public String valuta;
    public Date datum;
    public Saldo saldo;
    public Date boekdatum;
    public BigDecimal bedrag;
    public String omschrijving;
    public Custom custom;

    final private DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    public Transactie(String transactieregel) throws ParseException {
        String[] transacties = transactieregel.split("\t");
        this.rekeningnummer = transacties[0];
        this.valuta = transacties[1];
        this.datum = formatter.parse(transacties[2]);
        this.saldo = new Saldo(transacties[3], transacties[4]);
        this.boekdatum = formatter.parse(transacties[5]);
        this.bedrag = new BigDecimal(transacties[6].replace(",", "."));
        this.omschrijving = transacties[7];
        this.custom = new Custom(this.omschrijving);
    }
}