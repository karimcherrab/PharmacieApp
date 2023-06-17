package my.gamekarimbull_cow.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.Adabpter.Adabter_Commande;
import my.gamekarimbull_cow.pharmacie.Adabpter.Adabter_Livreur;
import my.gamekarimbull_cow.pharmacie.models.Commande;

public class LivreurActivity extends AppCompatActivity {
    FirebaseDatabase database;
    private RecyclerView recyclerView_commande;
    ArrayList<Commande> arrayList_commande ;
    Adabter_Livreur adabter_livreur ;
    FirebaseAuth auth;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livreur);
        database = FirebaseDatabase.getInstance();


        auth = FirebaseAuth.getInstance();
        recyclerView_commande = findViewById(R.id.recy_livreur);
        recyclerView_commande.setHasFixedSize(true);
        recyclerView_commande.setLayoutManager(new LinearLayoutManager(LivreurActivity.this));
        arrayList_commande = new ArrayList<>();
        uid = auth.getCurrentUser().getUid() ;
        adabter_livreur = new Adabter_Livreur( LivreurActivity.this , arrayList_commande ,database);
        recyclerView_commande.setAdapter(adabter_livreur);
        getData();

    }
    private void getData(){

        database.getReference().child("Commandes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList_commande.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Commande commande = dataSnapshot.getValue(Commande.class);
                    if (commande.getUidLivreur().equals(uid)){
                        arrayList_commande.add(commande);
                    }


                }

                adabter_livreur.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}