package my.gamekarimbull_cow.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.Adabpter.Adabter_Products;
import my.gamekarimbull_cow.pharmacie.Adabpter.Adanter_Panier;
import my.gamekarimbull_cow.pharmacie.models.CodePromo;
import my.gamekarimbull_cow.pharmacie.models.Panier;
import my.gamekarimbull_cow.pharmacie.models.Product;

public class PannerActivity extends AppCompatActivity {
    private RecyclerView recyclerView_panier;
    ArrayList arrayList_panier ;
    Adanter_Panier adanter_panier ;
    String uid;
    FirebaseDatabase database;
    FirebaseAuth auth;
    TextView prix_sansCodePromo , procentage,remise , total;
    Button btn_lancer;
    ArrayList<Integer> listInia = new ArrayList<>();
    boolean isFalse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panner);
        database = FirebaseDatabase.getInstance();


        prix_sansCodePromo = findViewById(R.id.prix_sansCodePromo);
        procentage = findViewById(R.id.procentage);
        remise = findViewById(R.id.remise);
        total = findViewById(R.id.total);
        btn_lancer = findViewById(R.id.btn_lancer);

        auth = FirebaseAuth.getInstance();
        //Re
        recyclerView_panier = findViewById(R.id.recyclerView_panier);
        recyclerView_panier.setHasFixedSize(true);
        recyclerView_panier.setLayoutManager(new LinearLayoutManager(PannerActivity.this));
        arrayList_panier = new ArrayList<>();
         uid = auth.getCurrentUser().getUid() ;
        adanter_panier = new Adanter_Panier(PannerActivity.this  , arrayList_panier,database,uid,
                prix_sansCodePromo , procentage,remise,total,btn_lancer , listInia,0

                );
        recyclerView_panier.setAdapter(adanter_panier);
        getData();
        
        findViewById(R.id.ajouter_au_panier_btn).setOnClickListener(
                a->{
                    afficheAlertDialogCodePromo();
                }
        );
    }

    private void afficheAlertDialogCodePromo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PannerActivity.this);
        View layoutView = getLayoutInflater().inflate(R.layout.layout_code_promo, null);

        builder.setView(layoutView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();


        EditText code_promo = layoutView.findViewById(R.id.code_promo);

        layoutView.findViewById(R.id.btn_ajouter).setOnClickListener(
                v -> {
                    if (code_promo.getText().toString().equals("")) {
                        emptyError(code_promo, "empty");
                    }else {
                         isFalse = false;
                        database.getReference().child("CodePromo").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                    CodePromo codePromo = dataSnapshot.getValue(CodePromo.class);
                                    if (codePromo.getCodePromo().equals(code_promo.getText().toString())){
                                    isFalse = true;
                                        double Percentage =Double.parseDouble(codePromo.getPercentage()) ;
                                        procentage.setText(Percentage+"");
                                        int totalPaid = Integer.parseInt(total.getText().toString().substring(0,total.getText().toString().indexOf("D")-1));
                                        double total_withCode = totalPaid*(Percentage/100);

                                        adanter_panier.setPercentage(total_withCode);
                                        total.setText( new DecimalFormat("##.##").format(total_withCode)+"");
                                        remise.setText( new DecimalFormat("##.##").format(totalPaid-total_withCode)+"");

                                        alertDialog.dismiss();
                                    }

                                }
                                if (!isFalse){
                                    Toast.makeText(PannerActivity.this , "Le code est faux",Toast.LENGTH_LONG).show();
                                    isFalse = false;
                                    alertDialog.dismiss();
                                }




                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

                    }

                }
        );
    }

    private void getData(){

        database.getReference().child("Panier").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList_panier.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Panier panier  = dataSnapshot.getValue(Panier.class);
                    arrayList_panier.add(panier);
                    listInia.add(0);

                }

                adanter_panier.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void emptyError(EditText editText , String error){
        editText.setError(error);
        editText.requestFocus();
    }

}