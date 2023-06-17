package my.gamekarimbull_cow.pharmacie.models;

public class Panier {
    String id_panier, nom_produit, url_photo;
    int prix, quantity;

    public Panier() {
    }

    public Panier(String id_panier, String url_photo, int prix , int quantity) {
        this.id_panier = id_panier;
        this.url_photo = url_photo;
        this.prix = prix;
        this.quantity = quantity;
    }

    public Panier(String id_panier, String nom_produit, String url_photo, int prix, int quantity) {
        this.id_panier = id_panier;
        this.nom_produit = nom_produit;
        this.url_photo = url_photo;
        this.prix = prix;
        this.quantity = quantity;
    }

    public String getId_panier() {
        return id_panier;
    }

    public void setId_panier(String id_panier) {
        this.id_panier = id_panier;
    }



    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

