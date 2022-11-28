package com.example.devoir4;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class RestaurantInfo extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.restaurant_info, container, false);
        TextView title = root.findViewById(R.id.resto_title), adress = root.findViewById(R.id.adress),
                rating = root.findViewById(R.id.rating), amountRating = root.findViewById(R.id.review_amount);
        ImageView pic = root.findViewById(R.id.resto_pic);
        if (getArguments() != null) {
            RestaurantInfoArgs args = RestaurantInfoArgs.fromBundle(getArguments());
            RestoList.Resto resto;
            if (args.getReserved()) {
                resto = Main.reservationList.get(args.getRestoIndex());
            }
            else {
                resto = Main.restoList.get(args.getRestoIndex());
            }
            title.setText(resto.name);
            adress.setText(resto.adress);
            rating.setText(resto.rating);
            amountRating.setText(resto.amountRating);
            pic.setImageResource(resto.picture);
            ImageButton like = root.findViewById(R.id.likebutton);
            if (resto.like == Color.BLACK) {
                like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                like.setColorFilter(Color.BLACK);
            }
            else {
                like.setImageResource(R.drawable.ic_baseline_favorite_24);
                like.setColorFilter(Color.RED);
            }
            like.setOnClickListener(view -> {
                if (resto.like == Color.BLACK) {
                    like.setColorFilter(Color.RED);
                    resto.like = Color.RED;
                    like.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                else {
                    like.setColorFilter(Color.BLACK);
                    resto.like = Color.BLACK;
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
            });
        }
        Button reserver = root.findViewById(R.id.reserve_button);
        reserver.setOnClickListener(view -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            RestaurantInfoDirections.ActionRestaurantInfoToMakeReservation action =
                    RestaurantInfoDirections.actionRestaurantInfoToMakeReservation(RestaurantInfoArgs.fromBundle(getArguments()).getRestoIndex());
            navController.navigate(action);
        });
        return root;
    }
}