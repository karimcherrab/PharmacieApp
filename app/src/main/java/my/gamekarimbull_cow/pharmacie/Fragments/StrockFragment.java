package my.gamekarimbull_cow.pharmacie.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.Adabpter.Adabter_Products;
import my.gamekarimbull_cow.pharmacie.MainActivity;
import my.gamekarimbull_cow.pharmacie.PannerActivity;
import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.models.Product;


public class StrockFragment extends Fragment {

    FirebaseDatabase database;
    private RecyclerView recyclerView_products;
    ArrayList arrayList_products ;
    Adabter_Products adabter_products ;
    EditText search_produit;
    FirebaseAuth auth;
    String uid;
    FloatingActionButton floating_addProduit;
    ProgressBar progressBar_stock;
    CardView cardView2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_strock, container, false);


        database = FirebaseDatabase.getInstance();


        auth = FirebaseAuth.getInstance();
        floating_addProduit =   view.findViewById(R.id.floating_addProduit);
        cardView2 =   view.findViewById(R.id.cardView2);



        search_produit= view.findViewById(R.id.search_produit);
        progressBar_stock= view.findViewById(R.id.progressBar_stock);
        search_produit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() != null &&  !s.toString().equals("")){
                    getData(s.toString());
                }else {
                    getData("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {




            }
        });



        //Re
        recyclerView_products = view.findViewById(R.id.recyclerView_products);
        recyclerView_products.setHasFixedSize(true);
        recyclerView_products.setLayoutManager(new LinearLayoutManager(getActivity()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2 ,GridLayoutManager.VERTICAL,false);
        recyclerView_products.setLayoutManager(gridLayoutManager);
        arrayList_products = new ArrayList<>();
         uid = auth.getCurrentUser().getUid() ;
        adabter_products = new Adabter_Products( getActivity() , arrayList_products , database,uid);
        recyclerView_products.setAdapter(adabter_products);
        getData("");

        view.findViewById(R.id.floating_addProduit).setOnClickListener(
                a-> {

                    Intent intent = new Intent(getActivity(), PannerActivity.class);

                    startActivity(intent);




                }
        );
        return  view;
    }

    private void getData(String text){

        database.getReference().child("Product").orderByChild("nom_produit").startAt(text).endAt(text+"\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList_products.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    if (product.getQuantity()!=0){
                        arrayList_products.add(product);
                    }

                }
                progressBar_stock.setVisibility(View.GONE);
                cardView2.setVisibility(View.VISIBLE);
                recyclerView_products.setVisibility(View.VISIBLE);
                floating_addProduit.setVisibility(View.VISIBLE);

                adabter_products.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }















}