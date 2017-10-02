package POJO.Configuratie;

public class ConfigElement {
    public String pattern;
    public Boolean vast;
    public String categorie;
    public String naam;

    public ConfigElement() {
    }

    public ConfigElement(String pattern, Boolean vast, String categorie, String naam) {
        this.pattern = pattern;
        this.vast = vast;
        this.categorie = categorie;
        this.naam = naam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConfigElement)) return false;

        ConfigElement that = (ConfigElement) o;

//        if (categorie != null ? !categorie.equals(that.categorie) : that.categorie != null) return false;
//        if (naam != null ? !naam.equals(that.naam) : that.naam != null) return false;
        if (pattern != null ? !pattern.equals(that.pattern) : that.pattern != null) return false;
//        if (vast != null ? !vast.equals(that.vast) : that.vast != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }

//    @Override
//    public int hashCode() {
//        int result = pattern != null ? pattern.hashCode() : 0;
//        result = 31 * result + (vast != null ? vast.hashCode() : 0);
//        result = 31 * result + (categorie != null ? categorie.hashCode() : 0);
//        result = 31 * result + (naam != null ? naam.hashCode() : 0);
//        return result;
//    }
}