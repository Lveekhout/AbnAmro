import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Ignore
public class ConfigTest {
    @Test
    public void readJsonTest() throws IOException {
        ObjectMapper om = new ObjectMapper();
        List<List<Config>> c = om.readValue(new File("config.json"), new TypeReference<List<List<Config>>>(){});
        System.out.println(c);
    }
}