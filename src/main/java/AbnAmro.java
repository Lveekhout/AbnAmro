import org.apache.commons.codec.digest.DigestUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

public class AbnAmro {
    public static void main(String argv[]) throws IOException, ParseException {
        File files[] = new File(".").listFiles();
        for (File file : files) handleFile(file);
    }

    private static void handleFile(File file) throws IOException, ParseException {
        if (file.getName().endsWith(".TAB")) {
            System.out.println(file.getCanonicalPath());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"
                    , "CD"
                    , "Vast"
                    , "Type"
                    , "Categorie"
                    , "Naam"
                    , "Key"
                    , "Bedrag"
                    , "Omschrijving"
                    , "Sha1" );

            String regel = null;
            while ((regel = reader.readLine()) != null) {
                AbnAmroRegelOmschrijving omschrijving = new AbnAmroRegelOmschrijving(regel.split("\t")[7]);
                if (omschrijving.type != null) {
                    System.out.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n"
                            , new BigDecimal(regel.split("\t")[6].replace(",",".")).signum() == -1 ? "Debet":"Credit"
                            , omschrijving.vast?"Vast":"Incidenteel"
                            , omschrijving.type
                            , AbnAmroRegel.getOverrule(regel,2)==null?Mapping.abstraheren(omschrijving.key, 2):AbnAmroRegel.getOverrule(regel,2)
                            , AbnAmroRegel.getOverrule(regel,1)==null?Mapping.abstraheren(omschrijving.key, 1):AbnAmroRegel.getOverrule(regel,1)
                            , omschrijving.key
                            , regel.split("\t")[6]
                            , regel.split("\t")[7]
                            , DigestUtils.sha1Hex(regel)
                    );
                }
            }
        }
    }
}
