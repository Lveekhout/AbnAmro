import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by leekhout on 26-9-2017.
 * class AbnAmroFilenameFilter
 */
public class AbnAmroFilenameFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".TAB");
    }
}
