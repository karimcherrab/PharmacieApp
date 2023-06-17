package my.gamekarimbull_cow.pharmacie.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import my.gamekarimbull_cow.pharmacie.HomeActivity;
import my.gamekarimbull_cow.pharmacie.LivreurActivity;

public class User {
    String uid,email , password,first_name,family_name,phone_number,address;

    public User() {
    }

    public User(String uid, String email, String password, String first_name, String family_name, String phone_number, String address) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.family_name = family_name;
        this.phone_number = phone_number;
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void SingUp(User user , FirebaseAuth mauth , Context context , FirebaseDatabase database)
    {
        mauth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {

                String uid= mauth.getCurrentUser().getUid();

                user.setUid(uid);
                database.getReference("User").child(uid).setValue(user);

                context.startActivity(new Intent(context, HomeActivity.class));
                ((Activity)context).finish();


            }
        });
    }

    public void login(String email,String password , FirebaseAuth auth , Context context,FirebaseDatabase database) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                String uid = auth.getCurrentUser().getUid() ;

                database.getReference().child("Livreur").child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (!snapshot.exists()){
                            context.startActivity(new Intent(context, HomeActivity.class));
                        }else {
                            context.startActivity(new Intent(context, LivreurActivity.class));
                        }
                        ((Activity)context).finish();
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

            }
        });
    }

}
