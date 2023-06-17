package my.gamekarimbull_cow.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.Adabpter.Adapter_LigneDeCommande;
import my.gamekarimbull_cow.pharmacie.models.Product;

public class commande_detail_Activity extends AppCompatActivity {
    String name,address , numero="07777551122",ref,prix,Id_commande , Nom_Livreur;
    TextView ref_commande , name_client,address_client , numero_client,name_livreur_commande,prix_commande;

    FirebaseDatabase database;
    ArrayList<Product> arrayList_ligne ;
    Adapter_LigneDeCommande adapter_ligneDeCommande;
    private RecyclerView recyclerView_products;

    private int total ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande_detail);

        ref_commande = findViewById(R.id.ref_commande);
        name_client = findViewById(R.id.name_client);
        address_client = findViewById(R.id.address_client);
        numero_client = findViewById(R.id.numero_client);
        name_livreur_commande = findViewById(R.id.name_livreur_commande);
        prix_commande = findViewById(R.id.prix_commande);
        database = FirebaseDatabase.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            address = extras.getString("address");
            prix = extras.getString("prix");
            ref = extras.getString("ref");
            Id_commande = extras.getString("ID");
            Nom_Livreur = extras.getString("Nom_Livreur");
            numero = extras.getString("numero_client");
            remplirData();
        }


        recyclerView_products = findViewById(R.id.recyclerView_produits_commande);
        recyclerView_products.setHasFixedSize(true);
        recyclerView_products.setLayoutManager(new LinearLayoutManager(commande_detail_Activity.this));
        arrayList_ligne = new ArrayList<>();
        adapter_ligneDeCommande = new Adapter_LigneDeCommande( commande_detail_Activity.this , arrayList_ligne );
        recyclerView_products.setAdapter(adapter_ligneDeCommande);


        getDataCommande();






    }
    void getDataCommande(){
        total  =0 ;
        database.getReference().child("LigneDeCommande").child(Id_commande).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList_ligne.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    arrayList_ligne.add(product);

                }

                adapter_ligneDeCommande.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    void remplirData(){
        ref_commande.setText("commande Ref : "+ref);
        name_client.setText("  nom : "+name);
        address_client.setText("  address : "+address);
        numero_client.setText("  numéro : "+numero);
        name_livreur_commande.setText("Livreur : "+Nom_Livreur);
        prix_commande.setText("Total : "+prix+"DA");

    }
}