package my.gamekarimbull_cow.pharmacie.models;

public class Product {
    String idProduct,code_produit ,nom_produit,date_de_premeption,url_photo;
    int prix , quantity;

    public Product(String idProduct, String url_photo, int prix, int quantity) {
        this.idProduct = idProduct;
        this.url_photo = url_photo;
        this.prix = prix;
        this.quantity = quantity;
    }

    public Product() {
    }

    public Product(String idProduct,String code_produit, String nom_produit, String date_de_premeption, int quantity, String url_photo,int prix) {
        this.idProduct = idProduct;
        this.code_produit = code_produit;
        this.nom_produit = nom_produit;
        this.date_de_premeption = date_de_premeption;
        this.quantity = quantity;
        this.url_photo = url_photo;
        this.prix = prix;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getCode_produit() {
        return code_produit;
    }

    public void setCode_produit(String code_produit) {
        this.code_produit = code_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getDate_de_premeption() {
        return date_de_premeption;
    }

    public void setDate_de_premeption(String date_de_premeption) {
        this.date_de_premeption = date_de_premeption;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }
}
