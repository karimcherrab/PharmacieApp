package my.gamekarimbull_cow.pharmacie;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Data_sharedPreferences {
    public Data_sharedPreferences() {
    }

    String get_Data(Context context , String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Pharmacies",MODE_PRIVATE);
        String data = sharedPreferences.getString(key,"");
        return data;

    }

    void set_Data(Context context , String key,String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Pharmacies",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.apply();

    }
}
