package POJO.Configuratie;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {
    private static Config instance = null;
    public final List<ConfigElement> vast = null;
    public final List<ConfigElement> incidenteel = null;

    private Config() {}

    public static Config getInstance() {
        if(instance == null) {
            try {
                instance = new ObjectMapper().readValue(new File("config.json"), Config.class);
            } catch (IOException e) {
                return null;
            }
        }
        return instance;
    }
}
