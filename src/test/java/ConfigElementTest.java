import POJO.ConfigElement;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Ignore
public class ConfigElementTest {
    @Test
    public void readJsonTest() throws IOException {
        List<List<ConfigElement>> c = new ObjectMapper().readValue(new File("config.json"), new TypeReference<List<List<ConfigElement>>>(){});
        System.out.println(c);
    }
}