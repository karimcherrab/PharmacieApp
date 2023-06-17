package my.gamekarimbull_cow.pharmacie.Adabpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import my.gamekarimbull_cow.pharmacie.R;
import my.gamekarimbull_cow.pharmacie.models.Product;


public class Adapter_LigneDeCommande extends RecyclerView.Adapter<Adapter_LigneDeCommande.MyViewHolder>{
    Context context ;
    ArrayList<Product> list;
    public Adapter_LigneDeCommande(Context context, ArrayList<Product> list ) {
        this.context = context;
        this.list = list;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_detail_commande_produit, parent,false);

        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Picasso.get().load(list.get(position).getUrl_photo()).into(holder.image_detail);



        holder.prix_detail.setText(list.get(position).getPrix()+"");
        holder.Quantitu_detail.setText(list.get(position).getQuantity()+"");








    }



    @Override
    public int getItemCount() {


        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Quantitu_detail  , prix_detail;
        ImageView image_detail;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            image_detail = itemView.findViewById(R.id.image_detail);
            Quantitu_detail = itemView.findViewById(R.id.Quantitu_detail);
            prix_detail = itemView.findViewById(R.id.prix_detail);


        }
    }
}
