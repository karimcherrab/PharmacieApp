package my.gamekarimbull_cow.pharmacie.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.Adabpter.Adabter_Commande;
import my.gamekarimbull_cow.pharmacie.Adabpter.Adabter_Products;
import my.gamekarimbull_cow.pharmacie.PannerActivity;
import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.models.Commande;
import my.gamekarimbull_cow.pharmacie.models.Product;


public class CammandeFragment extends Fragment {

    FirebaseDatabase database;
    private RecyclerView recyclerView_commande;
    ArrayList<Commande> arrayList_commande ;
    Adabter_Commande adabter_commande ;
    EditText search_commande;
    FirebaseAuth auth;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_cammande, container, false);

        database = FirebaseDatabase.getInstance();


        auth = FirebaseAuth.getInstance();



        search_commande= view.findViewById(R.id.search_commande);
        search_commande.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() != null &&  !s.toString().equals("")){
                    getData("-"+s.toString());
                }else {
                    getData("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {




            }
        });




        recyclerView_commande = view.findViewById(R.id.recyclerView_commande);
        recyclerView_commande.setHasFixedSize(true);
        recyclerView_commande.setLayoutManager(new LinearLayoutManager(getActivity()));
        arrayList_commande = new ArrayList<>();
         uid = auth.getCurrentUser().getUid() ;
        adabter_commande = new Adabter_Commande( getActivity() , arrayList_commande ,database);
        recyclerView_commande.setAdapter(adabter_commande);
        getData("");



        return  view;
    }

    private void getData(String text){

        database.getReference().child("Commandes").orderByChild("ref").startAt(text).endAt(text+"\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                arrayList_commande.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Commande commande = dataSnapshot.getValue(Commande.class);
                    if(commande.getUid().equals(uid)){
                        arrayList_commande.add(commande);
                    }
                }

                adabter_commande.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}