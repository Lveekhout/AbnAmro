import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Ignore
public class DiverseTest {
    @Test
    public void datumTest1() throws ParseException {
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("dd.MM.yy/HH.mm");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(dateFormatOut.format(dateFormatIn.parse("09.03.16/17.12")));
    }

    @Test
    public void datumTest2() throws ParseException {
        SimpleDateFormat dateFormatIn = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat dateFormatOut = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(dateFormatOut.format(dateFormatIn.parse("20160305")));
    }

    @Test
    public void bedragTest() {
        BigDecimal decimal1 = new BigDecimal("-136.60");
        BigDecimal decimal2 = new BigDecimal("-136.60");
        System.out.println(decimal1.add(decimal2));
    }

    @Test
    public void omschrijvingTest() {
        String kolom[] = "BEA   NR:74020008 05.03.16/12.09 Gute Fahrt mit Agip Gien,PAS231 ".split(" ");
        System.out.printf("aantal = %s\n", kolom.length);
        for (String s : kolom) System.out.println(s);
    }

    @Test
    public void abnAmroRegelOmschrijvingTest() throws ParseException {
        AbnAmroRegelOmschrijving omschrijving = new AbnAmroRegelOmschrijving("BEA   NR:74020008 05.03.16/12.09 Gute Fahrt mit Agip Gien,PAS231 ");
        System.out.println(omschrijving);
    }

    @Test
    public void setTest() {
        Set<String> s = new HashSet();
        s.add("ABC");
        s.add("ABC");
        s.add("XYZ");
        s.add("XYZ");
        System.out.println(s.size());
    }

    @Test
    public void propertiesTest() throws IOException {
        Properties prop = new Properties();
        OutputStream output = new FileOutputStream("config.properties");
        prop.setProperty("database", "localhost");
        prop.setProperty("dbuser", "mkyong");
        prop.setProperty("dbpassword", "password");
        prop.store(output, null);
    }

    @Test
    public void fileTest() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream("test.txt"))) {
            writer.println("Dit is een test");
        }
    }

    @Test
    public void sha1Test() {
        String sha1 = DigestUtils.sha1Hex("Laurens");
        System.out.println(sha1);
    }

    @Test
    public void arrayTest() {
        String[] strings = {"412061511", "EUR", "20170302"};
        System.out.println(String.join("\t", strings));
    }

    @Test
    public void mappingTest() {
        System.out.println(Mapping.abstraheren("NR:WB65LX", 0));
        System.out.println(Mapping.abstraheren("NR:WB65LX", 1));
        System.out.println(Mapping.abstraheren("NR:WB65LX", 2));
        System.out.println(Mapping.abstraheren("NR:WB65LX", 3));
        System.out.println(Mapping.abstraheren("NR:WB65LX", 4));
    }

    @Test
    public void regExTest() {
        String p = ".*IBAN: NL89RABO0341090417.*Naam: R Jansma.*Omschrijving: Kinderalimentatie.*";
        String m = "412061511\tEUR\t20160826\t4978,78\t4408,78\t20160826\t-570,00\tSEPA Periodieke overb.           IBAN: NL89RABO0341090417        BIC: RABONL2U                    Naam: R Jansma                  Omschrijving: Kinderalimentatie                                  ";
        System.out.println(m.matches(p));
    }

    @Test
    public void regEx2Test() {
        Pattern p = Pattern.compile("IBAN: NL89RABO0341090417.*Naam: (.*?)Omschrijving: Kinderalimentatie");
        Matcher m = p.matcher("412061511\tEUR\t20160826\t4978,78\t4408,78\t20160826\t-570,00\tSEPA Periodieke overb.           IBAN: NL89RABO0341090417        BIC: RABONL2U                    Naam: R Jansma                  Omschrijving: Kinderalimentatie                                  ");
        System.out.println(m.matches());
        if (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                System.out.printf("%s\t%s\n", i, m.group(i));
            }
        }
    }

    @Test
    public void regExMatcherTest() {
        Pattern pattern = Pattern.compile("/IBAN/(.*)/BIC/(.*)/.*/EREF/Salaris-over-periode-06/(.*)");
        Matcher matcher = pattern.matcher("/TRTP/SEPA OVERBOEKING/IBAN/NL09INGB0000435000/BIC/INGBNL2A/NAME/AEGON NEDERLAND NV/EREF/Salaris-over-periode-06/2017           ");
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.printf("%s\t%s\n", i, matcher.group(i));
            }
        }
    }
}
