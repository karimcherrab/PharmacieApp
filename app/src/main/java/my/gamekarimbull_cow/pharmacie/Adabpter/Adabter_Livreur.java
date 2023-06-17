package my.gamekarimbull_cow.pharmacie.Adabpter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.commande_detail_Activity;
import my.gamekarimbull_cow.pharmacie.models.Commande;

public class Adabter_Livreur  extends RecyclerView.Adapter<Adabter_Livreur.MyViewHolder> {
    Context context ;
    ArrayList<Commande> list;
    FirebaseDatabase database;

    public Adabter_Livreur(Context context, ArrayList<Commande> list , FirebaseDatabase database) {
        this.context = context;
        this.list = list;
        this.database = database;


    }
    @NonNull
    @Override
    public Adabter_Livreur.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_cammande, parent,false);

        return  new Adabter_Livreur.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adabter_Livreur.MyViewHolder holder, int position) {


        holder.commande_ref.setText("Ref: "+list.get(position).getRef().substring(1,10));
        holder.commande_number_produit.setText(list.get(position).getNumero_produit()+" PRODUITS");
        holder.commande_name.setText(list.get(position).getName());
        holder.commande_address.setText(list.get(position).getAddress());
        holder.commande_date.setText(list.get(position).getDate());
        holder.commande_time.setText(list.get(position).getTime());
        holder.commande_prix.setText(list.get(position).getPrix()+"");
        holder.commande_status.setText(list.get(position).getStatue());

        holder.click_product.setOnClickListener(
                a->{
                    System.out.println(list.get(position).getPrix());

                    Intent intent  = new Intent(context , commande_detail_Activity.class);
                    intent.putExtra("name",list.get(position).getName());
                    intent.putExtra("address",list.get(position).getAddress());
                    intent.putExtra("ref",list.get(position).getRef().substring(1,10));
                    intent.putExtra("prix",list.get(position).getPrix()+"");
                    intent.putExtra("ID",list.get(position).getRef());
                    intent.putExtra("Nom_Livreur",list.get(position).getNomLivreur());
                    intent.putExtra("numero_client",list.get(position).getNuméroClient());
                    context.startActivity(intent);


                }
        );

        holder.click_product.setOnLongClickListener(
                b->{
                        cofirmeAlert(position);



                    return false;
                }
        );

    }

    void cofirmeAlert(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View layoutView = inflater.inflate( R.layout.confirme_supp, null );

        TextView textView2 = layoutView.findViewById(R.id.textView2);
        builder.setView(layoutView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
        //  alertDialog.setCancelable(false);

        textView2.setText("La livraison est-elle terminée ?");


        layoutView.findViewById(R.id.btn_yes).setOnClickListener(
                v -> {

                    database.getReference("Commandes").child(list.get(position).getRef()).child("statue").setValue("livré");
                    alertDialog.dismiss();


                }
        );

        layoutView.findViewById(R.id.btn_no).setOnClickListener(
                v -> {

                    alertDialog.dismiss();


                }
        );


    }


    @Override
    public int getItemCount() {


        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView commande_ref,commande_number_produit,commande_name,commande_address,
                commande_date,commande_time,commande_prix;
        Button commande_status;

        RelativeLayout click_product;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            commande_ref = itemView.findViewById(R.id.commande_ref);
            commande_number_produit = itemView.findViewById(R.id.commande_number_produit);
            commande_name = itemView.findViewById(R.id.commande_name);
            commande_address = itemView.findViewById(R.id.commande_address);
            commande_date = itemView.findViewById(R.id.commande_date);
            commande_time = itemView.findViewById(R.id.commande_time);
            commande_prix = itemView.findViewById(R.id.commande_prix);
            commande_status = itemView.findViewById(R.id.commande_status);
            click_product = itemView.findViewById(R.id.click_product);


        }
    }
}

