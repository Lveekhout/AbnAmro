import POJO.Configuratie.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

@Ignore
public class ConfigTest {
    @Test
    public void readJsonNewStyleTest() throws IOException {
        Config c = new ObjectMapper().readValue(new File("config.json"), Config.class);
        System.out.println(c);
        Config c2 = Config.getInstance();
        System.out.println(c2);
        Config c3 = Config.getInstance();
        System.out.println(c3);
    }
}