package my.gamekarimbull_cow.pharmacie.Adabpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import my.gamekarimbull_cow.pharmacie.HomeActivity;
import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.models.Commande;
import my.gamekarimbull_cow.pharmacie.models.Panier;
import my.gamekarimbull_cow.pharmacie.models.Product;
import my.gamekarimbull_cow.pharmacie.models.User;

public class Adanter_Panier extends RecyclerView.Adapter<Adanter_Panier.MyViewHolder> {
    Context context ;
    ArrayList<Panier> list;
    FirebaseDatabase database;
    String uid , name, address,numéro;
    TextView prix_sansCodePromo , procentage,remise , total;

    ArrayList<Integer> quantite_chaque_produit;
    Button btn_commande ;
    double Percentage;

    public double getPercentage() {
        return Percentage;
    }

    public void setPercentage(double percentage) {
        Percentage = percentage;
    }

    public Adanter_Panier(Context context, ArrayList<Panier> list_panier, FirebaseDatabase database, String uid,
                          TextView prix_sansCodePromo , TextView procentage, TextView remise, TextView total, Button btn_commande,
                          ArrayList<Integer> quantite_chaque_produit, double Percentage
    ) {
        this.context = context;
        this.list = list_panier;
        this.database = database;
        this.uid = uid;
        this.prix_sansCodePromo = prix_sansCodePromo;
        this.procentage = procentage;
        this.remise = remise;
        this.total = total;
        this.btn_commande = btn_commande;
        this.quantite_chaque_produit  = quantite_chaque_produit;
        this.Percentage  = Percentage;


    }
    @NonNull
    @Override
    public Adanter_Panier.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_panier, parent,false);

        return  new Adanter_Panier.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adanter_Panier.MyViewHolder holder, int position) {
        getName_and_address();
        Picasso.get().load(list.get(position).getUrl_photo()).into(holder.panier_image);


        holder.panier_nom.setText(list.get(position).getNom_produit());
        holder.panier_prix.setText(list.get(position).getPrix()+" DA");



        holder.panier_supp.setOnClickListener(
                z->{
                    database.getReference("Panier").child(uid).child(list.get(position).getId_panier()).removeValue();
                    prix_sansCodePromo.setText(calcule_prix_total_Sans_Code()+" DA");
                    //total.setText((calcule_prix_total_Sans_Code()-0)+" DA");
                   // prix_sansCodePromo.setText(calcule_prix_total_Sans_Code()+" DA");
                   // total.setText((calcule_prix_total_Sans_Code()-0)+" DA");
                }

        );

        holder.panier_add.setOnClickListener(
                a->{
                    if(Integer.parseInt(holder.panier_quntite.getText().toString())<list.get(position).getQuantity()){
                        holder.panier_quntite.setText(Integer.parseInt(holder.panier_quntite.getText().toString())+1+"");
                        quantite_chaque_produit.set( position,  Integer.parseInt(holder.panier_quntite.getText().toString()) );

                        prix_sansCodePromo.setText(calcule_prix_total_Sans_Code()+" DA");
                        total.setText((calcule_prix_total_Sans_Code()-0)+" DA");

                    }
                }
        );

        holder.panier_mois.setOnClickListener(
                a->{
                    if(Integer.parseInt(holder.panier_quntite.getText().toString())>0){
                        holder.panier_quntite.setText(Integer.parseInt(holder.panier_quntite.getText().toString())-1+"");
                        quantite_chaque_produit.set( position,  Integer.parseInt(holder.panier_quntite.getText().toString()) );

                        prix_sansCodePromo.setText(calcule_prix_total_Sans_Code()+" DA");
                        total.setText((calcule_prix_total_Sans_Code()-0)+" DA");

                    }
                }
        );

        btn_commande.setOnClickListener(
                r->{
                    double prix = 0 ;
                  if (Percentage==0){
                         prix = calcule_prix_total_Sans_Code();

                    }else {

                        prix=Double.parseDouble(new DecimalFormat("##.##").format(Percentage));


                    }

                    if (prix!=0){
                        String id_command = database.getReference().push().getKey();

                        Commande commande = new Commande(
                                prix,
                                calcule_Number_produit(),
                                getDate(),
                                getTime(),
                                "en attente",
                                id_command,
                                name,
                                address,
                                uid,
                                "Pas encore",
                                numéro,
                                ""


                        );

                        database.getReference().child("Commandes").child(id_command).setValue(commande);

                        for (int qu = 0 ;qu<quantite_chaque_produit.size(); qu++){
                            if (quantite_chaque_produit.get(qu)!=0){
                                Panier panier = new Panier(list.get(qu).getId_panier() , list.get(qu).getUrl_photo(), list.get(qu).getPrix(),quantite_chaque_produit.get(qu));

                                database.getReference().child("LigneDeCommande").child(id_command).push().setValue(panier);
                                database.getReference().child("Product").child(list.get(qu).getId_panier()).child("quantity").setValue( list.get(qu).getQuantity()-quantite_chaque_produit.get(qu));



                            }
                        }

                        Intent  intent = new Intent(context , HomeActivity.class);
                        intent.putExtra("Type_fragment","commande");
                        context.startActivity(intent);
                        ((Activity)context).finish();
                        database.getReference("Panier").child(uid).removeValue();


                    }else {
                        Toast.makeText(context , "La quantité doit être ajoutée",Toast.LENGTH_LONG).show();
                    }
                }
        );


    }



    private void getName_and_address(){

        database.getReference().child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                name = user.getFamily_name() + " " + user.getFirst_name();
                address = user.getAddress();
                numéro = user.getPhone_number();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    String getDate(){
        String current_date = new SimpleDateFormat("dd/MM/yyyy" , Locale.getDefault()).format(new Date()) ;
        return current_date;
    }
    String getTime(){
        String current_time = new SimpleDateFormat("hh:mm aaa" , Locale.getDefault()).format(new Date());
        return  current_time;
    }
    int calcule_Number_produit(){
        int total = 0 ;
        for (int i=0 ;i<list.size();i++){
            total = total +quantite_chaque_produit.get(i);

        }
        return total;
    }
    int calcule_prix_total_Sans_Code(){
        int total = 0 ;

        for (int i=0 ;i<list.size();i++){
            total = total + (list.get(i).getPrix()*quantite_chaque_produit.get(i));

        }
        return total;
    }


    @Override
    public int getItemCount() {

        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView panier_nom  , panier_prix
                , panier_quntite ;
        ImageView panier_supp , panier_mois , panier_add , panier_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            panier_nom = itemView.findViewById(R.id.panier_nom);
            panier_prix = itemView.findViewById(R.id.panier_prix);

            panier_quntite = itemView.findViewById(R.id.panier_quntite);
            panier_supp = itemView.findViewById(R.id.panier_supp);
            panier_mois = itemView.findViewById(R.id.panier_mois);
            panier_add = itemView.findViewById(R.id.panier_add);
            panier_image = itemView.findViewById(R.id.panier_image);


        }
    }
}

