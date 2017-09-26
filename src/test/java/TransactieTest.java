import POJO.ConfigElement;
import POJO.Transactie.Transactie;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leekhout on 26-9-2017.
 * class TransactieTest
 * http://www.rexegg.com/regex-quickstart.html
 */
public class TransactieTest {
    @Test
    public void constructorTest() throws ParseException, IOException {
        Set<String> regels = new HashSet<>();
        for (File file : new File(".").listFiles(new AbnAmroFilenameFilter())) {
            String regel = null;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((regel = reader.readLine()) != null) regels.add(regel);
        }

        for (String regel : regels) {
            Transactie t = new Transactie(regel);
            System.out.println(new ObjectMapper().setDateFormat(new SimpleDateFormat("yyyy-MM-dd")).writeValueAsString(t) + ",");
        }
    }

    @Test
    public void uniekeOmschrijvingTest() throws ParseException, IOException {
        Set<String> omschrijvings = new HashSet<>();
        for (File file : new File(".").listFiles(new AbnAmroFilenameFilter())) {
            String regel = null;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((regel = reader.readLine()) != null) omschrijvings.add(regel.split("\t")[7]);
        }

        Pattern[] ps = {
                Pattern.compile("^([B|G]EA)\\s+([^\\s]+)\\s+\\d{2}\\.\\d{2}\\.\\d{2}/\\d{2}\\.\\d{2}\\s+(.+)\\s.*,"),
                Pattern.compile("^(/TRTP).*(/IBAN/.+?)/.*/NAME/(.+?)/"),
                Pattern.compile("^(SEPA) Overboeking\\s.*(IBAN:\\s[^\\s]+)\\s.*Naam:\\s(.*)Omschrijving:\\s"),
                Pattern.compile("^(SEPA) Overboeking\\s.*(IBAN:\\s[^\\s]+)\\s.*Naam:\\s(.*)Kenmerk:\\s"),
                Pattern.compile("^(SEPA) Overboeking\\s.*(IBAN:\\s[^\\s]+)\\s.*Naam:\\s(.*)$"),
                Pattern.compile("^(SEPA) iDEAL\\s.*(IBAN:\\s[^\\s]+)\\s.*Naam:\\s(.*)Omschrijving:\\s"),
                Pattern.compile("^(SEPA) Acceptgirobetaling\\s.*(IBAN:\\s[^\\s]+)\\s.*Naam:\\s(.*)Betalingskenm\\.:\\s"),
                Pattern.compile("^(SEPA) Incasso algemeen doorlopend\\s.*(Incassant:\\s[^\\s]+)\\s+Naam:\\s(.*)Machtiging:\\s"),
                Pattern.compile("^(SEPA) Periodieke overb\\.\\s.*(IBAN:\\s[^\\s]+).*Naam:\\s(.*)Omschrijving:\\s"),

                Pattern.compile("^(SEPA) Incasso algemeen doorlopend\\s.*Naam:\\s(.*)Machtiging:\\s.*(IBAN:\\s[^\\s]+)"),
                Pattern.compile("^(/TRTP).*/NAME/(.+?)/.*(/IBAN/.+?)/"),

                Pattern.compile("^(ABN).*"),
                Pattern.compile("^(AFSLUITING).*"),
                Pattern.compile("^(NR\\.).*"),
                Pattern.compile("^(PAKKETVERZ\\.).*"),
                Pattern.compile("^(RENTE).*"),
        };

        Matcher m;
        Set<ConfigElement> config = new HashSet<>();
        String type = "";
        String pattern = "";
        String naam = "";
        for (String omschrijving : omschrijvings) {
            boolean gevonden = false;
            for (int i=0; i<9&&!gevonden; i++) {
                gevonden = (m = ps[i].matcher(omschrijving)).find();
                if (gevonden) {
                    type = m.group(1);
                    pattern = "^" + type + ".*" + m.group(2) + ".*";
                    naam = m.group(3).trim();
                }
            }
            for (int i=9; i<11&&!gevonden; i++) {
                gevonden = (m = ps[i].matcher(omschrijving)).find();
                if (gevonden) {
                    type = m.group(1);
                    naam = m.group(2).trim();
                    pattern = "^" + type + ".*" + m.group(3) + ".*";
                }
            }
            for (int i=11; i<16&&!gevonden; i++) {
                gevonden = (m = ps[i].matcher(omschrijving)).find();
                if (gevonden) {
                    type = "ABN/AMRO";
                    pattern = "^" + m.group(1).replace(".", "\\.") + ".*";
                    naam = m.group(1).trim();
                }
            }
            if (!gevonden) {
                type = "";
                pattern = omschrijving;
                naam = "";
            }
            config.add(new ConfigElement(pattern, false, "", naam));
        }
        System.out.println("Aantal = " + config.size());
        System.out.println(new ObjectMapper().writeValueAsString(config));

            System.out.printf("%s\t%s\t%s\n"
                    , "pattern"
                    , "categorie"
                    , "naam"
            );
        for (ConfigElement element : config) {
            System.out.printf("%s\t%s\t%s\n"
                    , element.pattern
                    , element.categorie
                    , element.naam
            );
        }
    }

    @Test
    public void txt2jsonText() throws IOException {
        String regel = null;
        BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
        Set<ConfigElement> config = new HashSet<>();
        while ((regel = reader.readLine()) != null) {
            String[] elements = regel.split("\t");
            config.add(new ConfigElement(elements[0], false, elements[1], elements[2]));
        }
        System.out.println(new ObjectMapper().writeValueAsString(config));
    }
}
