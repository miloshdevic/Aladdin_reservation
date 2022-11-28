package com.example.devoir4;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Favoris extends Fragment {
    LinearLayoutManager linearLayoutManager;
    ArrayList<Integer> favorites;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.favoris, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.fav_contain);
        favorites = favoriteResto();
        linearLayoutManager = new  LinearLayoutManager(getContext());
        recyclerView.setAdapter(new FavorisAdapter());
        recyclerView.setLayoutManager(linearLayoutManager);

        return root;

    }
    public ArrayList<Integer> favoriteResto() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (int i = 0; i < Main.restoList.size(); i++) {
            if (Main.restoList.get(i).like == Color.RED) {
                ids.add(i);
            }
        }
        return ids;
    }
    public class FavorisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoris_header, parent, false);
                return new ViewHolder2(view);
            }
            else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fav, parent, false);
                return new ViewHolder1(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position != 0) {
                ViewHolder1 holder1 = (ViewHolder1)holder;
                RestoList.Resto resto1 = Main.restoList.get(favorites.get(position - 1));
                if (favorites.size() % 2 != 0 && position == favorites.size()/2) {
                    holder1.resto1.setImageResource(resto1.picture);
                    holder1.name1.setTag(favorites.get(position - 1));
                    holder1.name1.setText(resto1.name);
                    holder1.favLayout.setVisibility(View.INVISIBLE);
                }
                else {
                    RestoList.Resto resto2 = Main.restoList.get(favorites.get(favorites.size() - position));
                    holder1.name1.setText(resto1.name);
                    holder1.name2.setText(resto2.name);

                    holder1.resto1.setImageResource(resto1.picture);
                    holder1.resto2.setImageResource(resto2.picture);

                    holder1.name1.setTag(favorites.get(position - 1));
                    holder1.name2.setTag(favorites.get(favorites.size() - position));
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 1;
            }
            else {
                return 2;
            }
        }

        @Override
        public int getItemCount() {
            if (favorites.size() % 2 == 0) {
                return favorites.size()/2;
            }
            else {
                return favorites.size()/2 + 1;
            }
        }
        public class ViewHolder1 extends RecyclerView.ViewHolder {
            ImageButton resto1, resto2;
            TextView name1, name2;
            ConstraintLayout favLayout;
            public ViewHolder1(@NonNull View itemView) {
                super(itemView);
                favLayout = itemView.findViewById(R.id.fav2_layout);
                resto1 = itemView.findViewById(R.id.fav_pic1);
                resto2 = itemView.findViewById(R.id.fav_pic2);
                name1 = itemView.findViewById(R.id.fav_nom1);
                name2 = itemView.findViewById(R.id.fav_nom2);
                resto1.setOnClickListener(V -> {
                    View val = linearLayoutManager.findViewByPosition(getAdapterPosition());
                    assert val != null;
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    FavorisDirections.ActionNavFavorisToRestaurantInfo action =
                            FavorisDirections.actionNavFavorisToRestaurantInfo((int)name1.getTag());
                    navController.navigate(action);
                });
                resto2.setOnClickListener(V -> {
                    View val = linearLayoutManager.findViewByPosition(getAdapterPosition());
                    assert val != null;
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                        FavorisDirections.ActionNavFavorisToRestaurantInfo action =
                                FavorisDirections.actionNavFavorisToRestaurantInfo((int)name2.getTag());
                        navController.navigate(action);
                });
            }
        }
        public class ViewHolder2 extends RecyclerView.ViewHolder {
            public ViewHolder2(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}