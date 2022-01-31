package fr.epsi.MSPR.models;

public class Equipement {
    private String Cle;
    private String Label;

    public Equipement(String Cle, String Label) {
        this.Cle = Cle;
        this.Label = Label;
    }

    public String getCle() {
        return Cle;
    }

    public void setCle(String cle) {
        Cle = cle;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }
}