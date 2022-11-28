package com.example.devoir4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Main extends AppCompatActivity {

    public static ArrayList<RestoList.Resto> restoList, reservationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        RestoList r = new RestoList(getApplicationContext());
        restoList = r.initRestoList(false);
        reservationList = r.initRestoList(true);
        BottomNavigationView navView = findViewById(R.id.bottom_nav);
        NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupWithNavController(navView, navController);
        ImageButton back = findViewById(R.id.back_button);
        back.setOnClickListener(v -> {
            if (navController.getCurrentDestination() != null) {
                navController.popBackStack();
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_favoris) {
                    back.setVisibility(View.INVISIBLE);
                }
                else if (destination.getId() == R.id.nav_home) {
                    back.setVisibility(View.INVISIBLE);
                }
                else if (destination.getId() == R.id.nav_profil) {
                    back.setVisibility(View.INVISIBLE);
                }
                else if (destination.getId() == R.id.nav_map) {
                    back.setVisibility(View.INVISIBLE);
                }
                else if (destination.getId() == R.id.nav_reservation) {
                    back.setVisibility(View.INVISIBLE);
                }
                else {
                    back.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}