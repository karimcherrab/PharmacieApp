package my.gamekarimbull_cow.pharmacie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import my.gamekarimbull_cow.pharmacie.Account.LoginActivity;

public class MainActivity extends AppCompatActivity {
    TextView text_title , text_description;
    ImageView imageView_photos;
    String[] list_data = {"commander","A pertir de votre maison vous pouvez recevoir vos médicaments","livraison gratuit" , "Notre service est classé parmi les plus rapide service en algérie"};
    int[] list_image = {R.drawable.commander_image , R.drawable.livraison_image};

    int item = 0 ;
    Data_sharedPreferences data_sharedPreferences = new Data_sharedPreferences();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String isWalcomPage = data_sharedPreferences.get_Data(MainActivity.this ,"welcomePage" );
        if(!isWalcomPage.equals("")){
            startActivity(new Intent(MainActivity.this , LoginActivity.class));
            finish();
        }

        imageView_photos = findViewById(R.id.imageView_photos);
        text_title = findViewById(R.id.text_title);
        text_description = findViewById(R.id.text_description);

        remplirData();



    }

    void remplirData(){
        text_title.setText(list_data[item*2]);
        text_description.setText(list_data[item*2+1]);
        imageView_photos.setBackgroundResource(list_image[item]);
    }

    public void clickItem(View view) {
        switch (view.getId()){
            case R.id.text_suivant:
                if(item==0){
                    item = item + 1 ;
                    remplirData();
                }else {
                    data_sharedPreferences.set_Data(MainActivity.this , "welcomePage","false");
                    startActivity(new Intent(MainActivity.this , LoginActivity.class));
                    finish();
                }

                break;
            case R.id.text_precedent:
                if(item>0){
                    item = item - 1 ;
                    remplirData();
                }
                break;
        }
    }
}

