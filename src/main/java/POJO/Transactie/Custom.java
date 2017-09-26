package POJO.Transactie;

public class Custom {
    public boolean vast;
    public String categorie;
    public String naam;

    public Custom(boolean vast, String categorie, String naam) {
        this.vast = vast;
        this.categorie = categorie;
        this.naam = naam;
    }

    public Custom(String omschrijving) {
        this.vast = false;
        this.categorie = "Boodschappen";
        this.naam = "Albert Heijn";
    }
}