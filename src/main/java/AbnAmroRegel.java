public class AbnAmroRegel {
    public static String getOverrule(String regel, int level) {
        if (level<1)
            return null;
        else {
            try {
                return regel.split("\t")[7+level];
            } catch (IndexOutOfBoundsException e) {
                return getOverrule(regel, level-1);
            }
        }
    }
}
