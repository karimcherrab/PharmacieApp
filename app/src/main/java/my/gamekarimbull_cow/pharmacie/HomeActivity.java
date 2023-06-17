package my.gamekarimbull_cow.pharmacie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.gamekarimbull_cow.pharmacie.Fragments.CammandeFragment;
import my.gamekarimbull_cow.pharmacie.Fragments.ProfileFragment;
import my.gamekarimbull_cow.pharmacie.Fragments.StrockFragment;
import my.gamekarimbull_cow.pharmacie.models.Commande;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    StrockFragment strockFragment = new StrockFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CammandeFragment cammandeFragment = new CammandeFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(HomeActivity.this);
        bottomNavigationView.setSelectedItemId(R.id.stock);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, cammandeFragment).commit();

        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, strockFragment).commit();

        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
                return true;

            case R.id.stock:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, strockFragment).commit();
                return true;

            case R.id.cammande:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, cammandeFragment).commit();
                return true;
        }
        return false;
    }

}