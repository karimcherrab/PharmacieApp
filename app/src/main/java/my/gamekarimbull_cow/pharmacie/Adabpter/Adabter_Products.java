package my.gamekarimbull_cow.pharmacie.Adabpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;


import my.gamekarimbull_cow.pharmacie.PannerActivity;
import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.models.Panier;
import my.gamekarimbull_cow.pharmacie.models.Product;


public class Adabter_Products extends RecyclerView.Adapter<Adabter_Products.MyViewHolder>{
    Context context ;
    ArrayList<Product> list;
    FirebaseDatabase database;
    String uid;
    Animation scale_up , scale_down;
    public Adabter_Products(Context context, ArrayList<Product> list , FirebaseDatabase database,String uid) {
        this.context = context;
        this.list = list;
        this.database = database;
        this.uid = uid;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.alertdialog_produit, parent,false);

        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         Picasso.get().load(list.get(position).getUrl_photo()).into(holder.imageProduct_pair);
        scale_up = AnimationUtils.loadAnimation(context , R.anim.scale_up);
        scale_down = AnimationUtils.loadAnimation(context , R.anim.scale_down);


            holder.nomProduct_pair.setText(list.get(position).getNom_produit());
            holder.dateProduct_pair.setText(list.get(position).getDate_de_premeption());
            holder.prixProduct_pair.setText("Prix : "  + list.get(position).getPrix() + " DA");


   
            holder.ajouter_au_panier_btn.setOnClickListener(

                    z->{
                       holder.ajouter_au_panier_btn.startAnimation(scale_up);
                        Panier panier  = new Panier(
                                list.get(position).getIdProduct(),
                                list.get(position).getNom_produit() ,
                                list.get(position).getUrl_photo(),
                                list.get(position).getPrix(),
                                list.get(position).getQuantity()
                        );
                        database.getReference().child("Panier").child(uid).child(list.get(position).getIdProduct()).setValue(panier);

                    }

            );



    }



    @Override
    public int getItemCount() {


        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomProduct_pair  , dateProduct_pair
                 ,prixProduct_pair;
        RelativeLayout click_product;
        ImageView imageProduct_pair;
        EditText ajouter_au_panier_btn;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            nomProduct_pair = itemView.findViewById(R.id.nomProduct_pair);
            dateProduct_pair = itemView.findViewById(R.id.dateProduct_pair);

            prixProduct_pair = itemView.findViewById(R.id.prixProduct_pair);
            click_product = itemView.findViewById(R.id.click_product);
            imageProduct_pair = itemView.findViewById(R.id.imageProduct_pair);
            ajouter_au_panier_btn = itemView.findViewById(R.id.ajouter_au_panier_btn);
            

        }
    }
}
