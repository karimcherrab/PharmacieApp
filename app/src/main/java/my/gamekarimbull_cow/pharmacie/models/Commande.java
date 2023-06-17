package my.gamekarimbull_cow.pharmacie.models;

public class Commande {
    int numero_produit ;
    String date , time,statue,Ref,name,address,uid,nomLivreur,numéroClient,uidLivreur;
    double prix ;
    public Commande() {
    }

    public Commande(double prix, int numero_produit, String date, String time, String statue, String ref, String name, String address, String uid,String nomLivreur,String numéroClient,String uidLivreur) {
        this.prix = prix;
        this.numero_produit = numero_produit;
        this.date = date;
        this.time = time;
        this.statue = statue;
        Ref = ref;
        this.name = name;
        this.address = address;
        this.uid = uid;
        this.nomLivreur = nomLivreur;
        this.numéroClient = numéroClient;
        this.uidLivreur = uidLivreur;
    }

    public String getUidLivreur() {
        return uidLivreur;
    }

    public void setUidLivreur(String uidLivreur) {
        this.uidLivreur = uidLivreur;
    }

    public String getNuméroClient() {
        return numéroClient;
    }

    public void setNuméroClient(String numéroClient) {
        this.numéroClient = numéroClient;
    }

    public String getNomLivreur() {
        return nomLivreur;
    }

    public void setNomLivreur(String nomLivreur) {
        this.nomLivreur = nomLivreur;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getNumero_produit() {
        return numero_produit;
    }

    public void setNumero_produit(int numero_produit) {
        this.numero_produit = numero_produit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String ref) {
        Ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
