package com.example.devoir4;

import android.content.DialogInterface;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Collections;

public class Home extends Fragment {
    LinearLayoutManager linearLayoutManager;
    String[] section;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.home, container, false);
        section = root.getResources().getStringArray(R.array.section_home);
        RecyclerView recyclerView = root.findViewById(R.id.home_contain);
        linearLayoutManager = new LinearLayoutManager(getContext());
        HomeAdapter homeAdapter = new HomeAdapter();
        recyclerView.setAdapter(homeAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        return root;
    }

    public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        boolean searched;
        public HomeAdapter() {
            searched = false;
        }
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_home, parent, false);
                return new ViewHolder2(view);
            }
            else if (viewType == 2 && !searched){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parcourir_header, parent, false);
                return new ViewHolder3(view);
            }
            else {
                View view;
                if (viewType == 4 || searched) {
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home2, parent, false);
                }
                else {
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_home1, parent, false);
                }
                return new ViewHolder1(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (position != 0 && (position != 5 || searched)) {
                ViewHolder1 holder1 = (ViewHolder1)holder;
                holder1.amount.setText(Main.restoList.get(position - 1).amountRating);
                holder1.rating.setText(Main.restoList.get(position - 1).rating);
                holder1.type.setText(Main.restoList.get(position - 1).cuisine);
                holder1.proposition.setText(section[(position) % 5]);
                holder1.name1.setText(Main.restoList.get(position - 1).name);
                holder1.resto1.setImageResource(Main.restoList.get(position - 1).picture);
                holder1.name1.setTag(position - 1);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 1;
            }
            if (position == 5 && !searched) {
                return 2;
            }
            else if (position > 5 || searched) {
                return 4;
            }
            else {
                return 3;
            }
        }

        @Override
        public int getItemCount() {
            return 15;
        }
        public class ViewHolder1 extends RecyclerView.ViewHolder {
            ImageButton resto1;
            TextView name1, proposition, rating, amount, type;
            ConstraintLayout layout;
            public ViewHolder1(@NonNull View itemView) {
                super(itemView);
                resto1 = itemView.findViewById(R.id.fav_pic1);
                name1 = itemView.findViewById(R.id.fav_nom1);
                rating = itemView.findViewById(R.id.ratingzz);
                layout = itemView.findViewById(R.id.row_home_layout);
                amount = itemView.findViewById(R.id.amount_ratingzz);
                proposition = itemView.findViewById(R.id.home_section);
                type = itemView.findViewById(R.id.cuisine_type);
                resto1.setOnClickListener(V -> {
                    View val = linearLayoutManager.findViewByPosition(getAdapterPosition());
                    assert val != null;
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    HomeDirections.ActionNavHomeToRestaurantInfo action =
                                HomeDirections.actionNavHomeToRestaurantInfo((int)name1.getTag());
                    navController.navigate(action);

                });
            }
        }
        public class ViewHolder2 extends RecyclerView.ViewHolder {
            LinearLayout linearLayout;
            public ViewHolder2(@NonNull View itemView) {
                super(itemView);
                ConstraintLayout constraintLayout = itemView.findViewById(R.id.layout_icon);
                HorizontalScrollView scrollView = itemView.findViewById(R.id.nested);
                LinearLayout linearLayout = itemView.findViewById(R.id.icon_section);
                constraintLayout.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        searched = true;
                        System.out.println("s");
                        Collections.shuffle(Main.restoList);
                        notifyItemRangeRemoved(1, 5);
                        notifyItemRangeChanged(5, 5);
                    }
                });
            }
        }
        public class ViewHolder3 extends RecyclerView.ViewHolder {
            public ViewHolder3(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}