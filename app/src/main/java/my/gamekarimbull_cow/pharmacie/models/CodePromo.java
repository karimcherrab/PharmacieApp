package my.gamekarimbull_cow.pharmacie.models;

public class CodePromo {
    String  codePromo , percentage;

    public CodePromo() {
    }

    public CodePromo(String codePromo, String percentage) {
        this.codePromo = codePromo;
        this.percentage = percentage;
    }

    public String getCodePromo() {
        return codePromo;
    }

    public void setCodePromo(String codePromo) {
        this.codePromo = codePromo;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
