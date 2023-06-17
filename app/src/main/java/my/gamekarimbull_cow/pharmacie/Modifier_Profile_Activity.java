package my.gamekarimbull_cow.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import my.gamekarimbull_cow.pharmacie.Account.SignUpActivity;
import my.gamekarimbull_cow.pharmacie.models.Panier;
import my.gamekarimbull_cow.pharmacie.models.User;

public class Modifier_Profile_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseAuth mauth;
    EditText modifier_nom , modifier_prenom,modifier_number_phone,modifier_address;
    String uid;
    Button btn_modifier;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_profile);
        modifier_nom = findViewById(R.id.modifier_nom);
        modifier_prenom = findViewById(R.id.modifier_prenom);
        modifier_number_phone = findViewById(R.id.modifier_number_phone);
        modifier_address = findViewById(R.id.modifier_address);
        btn_modifier = findViewById(R.id.btn_modifier);

        mauth=FirebaseAuth.getInstance();
        uid = mauth.getCurrentUser().getUid() ;
        database = FirebaseDatabase.getInstance();
        getData();
        btn_modifier.setOnClickListener(v -> {
            if (modifier_nom.getText().toString().equals(""))
            {
                emptyError(modifier_nom , "empty");
            }else if (modifier_prenom.getText().toString().equals(""))
            {
                emptyError(modifier_prenom , "empty");
            }else if (modifier_number_phone.getText().toString().equals(""))
            {
                emptyError(modifier_number_phone , "empty");
            }
            else if (modifier_address.getText().toString().equals(""))
            {
                emptyError(modifier_address , "empty");
            }
          else{

             database.getReference().child("User").child(uid).child("family_name").setValue(modifier_nom.getText().toString());
             database.getReference().child("User").child(uid).child("first_name").setValue(modifier_prenom.getText().toString());
             database.getReference().child("User").child(uid).child("phone_number").setValue(modifier_number_phone.getText().toString());
             database.getReference().child("User").child(uid).child("address").setValue(modifier_address.getText().toString());


            }
        });

    }

    void getData(){
        database.getReference().child("User").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                modifier_nom.setText(user.getFamily_name());
                modifier_prenom.setText(user.getFirst_name());
                modifier_number_phone.setText(user.getPhone_number());
                modifier_address.setText(user.getAddress());
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