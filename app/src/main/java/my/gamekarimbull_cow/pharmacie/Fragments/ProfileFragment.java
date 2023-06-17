package my.gamekarimbull_cow.pharmacie.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import my.gamekarimbull_cow.pharmacie.Account.LoginActivity;
import my.gamekarimbull_cow.pharmacie.Modifier_Profile_Activity;
import my.gamekarimbull_cow.pharmacie.R;


public class ProfileFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.modifier_coordonne).setOnClickListener(
                a->startActivity(new Intent(getActivity() , Modifier_Profile_Activity.class))
        );
        firebaseAuth = FirebaseAuth.getInstance();

        view.findViewById(R.id.signOut).setOnClickListener(
                a->{
                    FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(getActivity() , LoginActivity.class));
                    getActivity().finish();
                }
        );



        return view;
    }
}