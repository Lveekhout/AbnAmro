import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbnAmroRegelOmschrijving {
    public String type;
    public String key;
    public boolean vast;

    private static String[] patterns = {
            ".*IBAN: NL09INGB0000435000.*Naam: AEGON NEDERLAND NV.*Omschrijving: Aegon salaris.*",
            ".*IBAN: NL09INGB0000435000.*Naam: AEGON NEDERLAND NV.*Kenmerk: Salaris-over-periode.*",
            ".*IBAN: NL89RABO0341090417.*Naam: R Jansma.*Omschrijving: Kinderalimentatie.*",
            ".*IBAN: NL45RABO0306545241.*Naam: ML van Eekhout.*Omschrijving: Lening auto.*",
            ".*Incassant: NL10ZZZ302086370000.*Naam: Zilveren Kruis Zorgverzeke.*Omschrijving: Premie Zilveren Kruis.*",
            ".*Incassant: NL90223130416110000.*Naam: ESSENT RETAIL ENERG.*Machtiging: 000088118419.*",
            ".*Incassant: NL60ZZZ321410680000.*Naam: INSHARED NEDERLAND B\\.V\\..*Machtiging: INSH121424900001.*",
            ".*Incassant: NL29ZZZ011574870000.*Naam: BUDOVERENIGING RENSHU.*Mndt-131-23-1-2016.*Omschrijving: Contributie.*",
            ".*Incassant: NL29ZZZ011574870000.*Naam: BUDOVERENIGING RENSHU.*Machtiging: Mndt-92-17-8-2015.*Omschrijving: Contributie.*",
            ".*Incassant: NL05ZZZ412014630000.*Naam: STG SAVE THE CHILDREN.*Machtiging: MBOA-2540329-1.*",
            ".*Incassant: NL02ZZZ271084360000.*Naam: OXFAM NOVIB.*Machtiging: 000000023144.*",
            ".*Incassant: NL85ZZZ140522640000.*Naam: Vodafone-Libertel N\\.V\\..*Machtiging: 27913143M.*",
            ".*Incassant: NL89ZIG370267060000.*Naam: ZIGGO B\\.V\\..*Machtiging: 15579571-0412061511.*",
            ".*Incassant: GB96NFXSDDCHAS00000041302392.*Naam: NETFLIX INTERNATIONAL B\\.V\\.Machtiging: DD-93693575450943019 7-2158-160529.*",
            ".*Incassant: NL98ZZZ020086210000.*Naam: NV WATERBEDRIJF GRONINGEN.*Machtiging: 000007439277.*",
            ".*/IBAN/NL45RABO0306545241.*/NAME/ML van Eekhout/REMI/Lening auto.*",
            ".*/IBAN/NL81ABNA0540200735.*/NAME/L J VAN EEKHOUT/REMI/Vakantiegeld / 13de maand.*",
            ".*/IBAN/NL89RABO0341090417.*/NAME/R Jansma/REMI/Kinderalimentatie.*",
            ".*/IBAN/NL09INGB0000435000.*/NAME/AEGON NEDERLAND NV.*/EREF/Salaris-over-periode.*",
            ".*/CSID/NL87ZZZ011718240000/NAME/Gemeente Groningen/MARF/00001880360000122218.*",
            ".*/CSID/NL98ZZZ271112510000/NAME/AEGON NEDERLAND NV/MARF/AEDE20160801H1002847.*",
            ".*/CSID/NL98ZZZ271112510000/NAME/AEGON NEDERLAND NV/MARF/AEDI20160801L61671143.*",
            ".*/CSID/NL60ZZZ321410680000/NAME/INSHARED NEDERLAND B\\.V\\./MARF/INSH121424900001.*",
            ".*/CSID/NL90223130416110000/NAME/ESSENT RETAIL ENERG/MARF/000088118419.*",
            ".*/CSID/NL05ZZZ412014630000/NAME/STG SAVE THE CHILDREN/MARF/MBOA-2540329-1.*",
            ".*/CSID/NL02ZZZ271084360000/NAME/OXFAM NOVIB/MARF/000000023144.*",
            ".*/CSID/NL29ZZZ011574870000/NAME/BUDOVERENIGING RENSHU/MARF/Mndt-92-17-8-2015.*",
            ".*/CSID/NL29ZZZ011574870000/NAME/BUDOVERENIGING RENSHU/MARF/Mndt-131-23-1-2016.*",
            ".*/CSID/NL98ZZZ020086210000/NAME/NV WATERBEDRIJF GRONINGEN/MARF/000007439277.*",
            ".*/CSID/NL30ZZZ333034790000/NAME/ZIGGO SERVICES BV/MARF/001557957100129112016000000660.*",
            ".*/CSID/GB96NFXSDDCHAS00000041302392/NAME/NETFLIX INTERNATIONAL B\\.V\\./MARF/DD-936935754509430197-2158-160529.*",
            ".*/CSID/NL85ZZZ140522640000/NAME/Vodafone-Libertel B\\.V\\./MARF/27913143M.*",
            ".*/CSID/NL10ZZZ302086370000/NAME/Zilveren Kruis Zorgverzekeringen  NV/MARF/1000002286680.*",
            "ABN AMRO Bank.*",
            "PAKKETVERZ\\. POLISNR\\..*412061511.*"
};

    private AbnAmroRegelOmschrijving() {
        super();
    }

    public AbnAmroRegelOmschrijving(String omschrijving) throws ParseException {
        if ("BEA".equals(omschrijving.substring(0,3))) handleBEA(omschrijving);
        else if ("GEA".equals(omschrijving.substring(0,3))) handleBEA(omschrijving);
        else if ("/".equals(omschrijving.substring(0,1))) handleMT940(omschrijving);
        else if ("SEPA ".equals(omschrijving.substring(0,5))) handleSEPA(omschrijving);
        else {
            this.type = "Anders";
            this.key = omschrijving.split(" ")[0];
        }

        for (int i=0;i<patterns.length;i++) {
            this.vast = omschrijving.matches(patterns[i]);
            if (this.vast) break;
        }
    }

    private void handleSEPA(String omschrijving) {
        this.type = "SEPA";
        int start = omschrijving.indexOf("Incassant: ");
        if (start>-1) {
            int eind = omschrijving.indexOf(" ", start+11);
            if (eind>0) this.key = omschrijving.substring(start+11, eind);
        } else {
            start = omschrijving.indexOf("IBAN: ");
            if (start>-1) {
                int eind = omschrijving.indexOf(" ", start+6);
                if (eind>0) this.key = omschrijving.substring(start+6, eind);
            } else this.key = "Geen KEY gevonden (SEPA)";
        }
    }

    private void handleMT940(String omschrijving) {
        String iban = null;
        String[] names = omschrijving.split("/");
        this.type = names[1];
        for (int i=1;i<names.length;i=i+2) {
            if ("CSID".equals(names[i])) this.key = names[i+1];
            else if ("IBAN".equals(names[i])) iban = names[i+1];
        }
        if (this.key==null||this.key.length()==0) {
            if (iban!=null) this.key = iban;
            else this.key = "Geen KEY gevonden (/TRTP)";
        }
    }

    private void handleBEA(String omschrijving) throws ParseException {
        this.type = omschrijving.substring(0, 3);
        this.key  = omschrijving.substring(6, 18).trim();
    }

    private Date parseDatum(String datum, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(datum);
    }
}