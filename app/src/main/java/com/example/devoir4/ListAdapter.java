package com.example.devoir4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    LayoutInflater inflater;
    ConstraintSet constraintSet = new ConstraintSet();
    int id;
    LinearLayoutManager manager;
    NavController navController;
    boolean fav = true;
    String[] section;

    public ListAdapter(LayoutInflater layoutInflater, int ids,
                       LinearLayoutManager linearLayoutManager, NavController navcont) {
        inflater = layoutInflater;
        id = ids;
        manager = linearLayoutManager;
        navController = navcont;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(id, parent, false);
        section = view.getResources().getStringArray(R.array.section_home);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.resto1.setImageResource(Main.restoList.get(position).picture);
        holder.name1.setText(Main.restoList.get(position).name);
        holder.name1.setTag(position);
        if (id == R.layout.row_fav) {
            holder.resto2.setImageResource(Main.restoList.get(17 - position).picture);
            holder.name2.setText(Main.restoList.get(17 - position).name);
            holder.name2.setTag(17 - position);
        }
        if (id == R.layout.row_home1) {
            holder.amount.setText(Main.restoList.get(position).amountRating);
            holder.rating.setText(Main.restoList.get(position).rating);
            holder.type.setText(Main.restoList.get(position).cuisine);
            constraintSet.clone(holder.layout);
            if (position <= 4) {
                holder.proposition.setVisibility(View.VISIBLE);
                holder.proposition.setText(section[position]);
                constraintSet.connect(R.id.row_home_card,ConstraintSet.TOP, R.id.home_section, ConstraintSet.BOTTOM, 20);
            }
            else {
                holder.proposition.setVisibility(View.INVISIBLE);
                constraintSet.connect(R.id.row_home_card,ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            }
            constraintSet.applyTo(holder.layout);
        }
    }



    @Override
    public int getItemCount() {
        if (id == R.layout.row_fav) {
            return 9;
        }
        else {
            return Main.restoList.size() - Main.reservationList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton resto1, resto2;
        TextView name1, name2, proposition, rating, amount, type;
        ConstraintLayout layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            resto1 = itemView.findViewById(R.id.fav_pic1);
            name1 = itemView.findViewById(R.id.fav_nom1);
            if (id == R.layout.row_fav) {
                resto2 = itemView.findViewById(R.id.fav_pic2);
                name2 = itemView.findViewById(R.id.fav_nom2);
            }
            if (id == R.layout.row_home1) {
                rating = itemView.findViewById(R.id.ratingzz);
                layout = itemView.findViewById(R.id.row_home_layout);
                amount = itemView.findViewById(R.id.amount_ratingzz);
                proposition = itemView.findViewById(R.id.home_section);
                type = itemView.findViewById(R.id.cuisine_type);
            }
            resto1.setOnClickListener(V -> {
                View val = manager.findViewByPosition(getAdapterPosition());
                assert val != null;
                if (id == R.layout.row_home1) {
                    HomeDirections.ActionNavHomeToRestaurantInfo action =
                            HomeDirections.actionNavHomeToRestaurantInfo((int)name1.getTag());
                    navController.navigate(action);
                }
                if (id == R.layout.row_fav) {
                    FavorisDirections.ActionNavFavorisToRestaurantInfo action =
                            FavorisDirections.actionNavFavorisToRestaurantInfo((int)name1.getTag());
                    navController.navigate(action);
                }

            });
            if (id == R.layout.row_fav) {
                resto2.setOnClickListener(V -> {
                    View val = manager.findViewByPosition(getAdapterPosition());
                    assert val != null;
                    if (id == R.layout.row_home1) {
                        HomeDirections.ActionNavHomeToRestaurantInfo action =
                                HomeDirections.actionNavHomeToRestaurantInfo((int)name2.getTag());
                        navController.navigate(action);
                    }
                    if (id == R.layout.row_fav) {
                        FavorisDirections.ActionNavFavorisToRestaurantInfo action =
                                FavorisDirections.actionNavFavorisToRestaurantInfo((int)name2.getTag());
                        navController.navigate(action);
                    }

                });
            }
        }
    }
}
