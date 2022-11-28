package com.example.devoir4;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;


public class Reservation extends Fragment {
    boolean first, end;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.reservation, container, false);
        recyclerView = root.findViewById(R.id.reserv_container);
        linearLayoutManager = new  LinearLayoutManager(getContext());
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
        MenuList myAdapter = new MenuList(inflater);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        return root;
    }

    public class MenuList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        LayoutInflater inflater;
        int current_nb;

        public MenuList(LayoutInflater layoutInflater) {
            inflater = layoutInflater;
            current_nb = Main.reservationList.size() + 8;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                View view = inflater.inflate(R.layout.row_curr_reserv, parent, false);
                return new ViewHolder1(view);
            }
            else if (viewType == 2 || viewType == 3) {
                View view;
                if (viewType == 2) {
                    view = inflater.inflate(R.layout.reserv_titles, parent, false);
                }
                else {
                    view = inflater.inflate(R.layout.header_past_reserv, parent, false);
                }
                return new ViewHolder3(view);
            }
            else {
                View view = inflater.inflate(R.layout.row_past_orders, parent, false);
                return new ViewHolder2(view);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position - 1 == Main.reservationList.size()) {
                if (position == 0) {
                    return 2;
                }
                else {
                    return 3;
                }
            }
            else if (position - 1 < current_nb - 8) {
                return 0;
            }
            else {
                return 1;
            }
        }

        @Override
        public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
            if (position == 0 || position - 1 == Main.reservationList.size()) {
            }
            else if (position - 1 < current_nb - 8) {
                RestoList.Resto resto = Main.reservationList.get(Main.reservationList.size() - position);
                ViewHolder1 holder1 = (ViewHolder1)holder;
                holder1.resto_pic.setImageResource(resto.picture);
                holder1.date.setText(resto.date);
                holder1.adresse.setText(resto.adress);
                int nb = Integer.parseInt( resto.nbEnfant) + Integer.parseInt(resto.nbAdulte);
                String per = String.valueOf(nb) + " personnes";
                holder1.nbPersonne.setText(per);
                holder1.name.setText(resto.name);
                holder1.name.setTag(Main.reservationList.size() - position);
                String h = "- " + resto.heure + " pm";
                holder1.heure.setText(h);
            }
            else {
                RestoList.Resto resto = Main.restoList.get(position - 1);
                ViewHolder2 holder2 = (ViewHolder2)holder;
                holder2.rating.setText(resto.revue);
                holder2.resto_pic.setImageResource(resto.picture);
                holder2.date.setText(resto.date);
                holder2.name.setText(resto.name);
                holder2.name.setTag(position - 1);
            }

        }

        @Override
        public int getItemCount() {
            return current_nb;
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder {
            private final ImageView resto_pic;
            private final TextView name, date, adresse, nbPersonne, heure;


            public ViewHolder1(@NonNull View itemView) {
                super(itemView);
                heure = itemView.findViewById(R.id.curr_reserv_heure);
                Button annuler = itemView.findViewById(R.id.curr_butt_annuler);
                Button modifier = itemView.findViewById(R.id.curr_butt_confirm);
                resto_pic = itemView.findViewById(R.id.curr_reserve_picture);
                name = itemView.findViewById(R.id.curr_reserve_name);
                date = itemView.findViewById(R.id.curr_reserve_date);
                adresse = itemView.findViewById(R.id.curr_distance);
                nbPersonne = itemView.findViewById(R.id.curr_reserve_nbpersone);
                modifier.setOnClickListener(view -> {
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    ReservationDirections.ActionNavReservationToMakeReservation action =
                            ReservationDirections.actionNavReservationToMakeReservation((Integer) name.getTag());
                    action.setModified(true);
                    navController.navigate(action);
                });
                resto_pic.setOnClickListener(V -> {
                    View val = linearLayoutManager.findViewByPosition(getAdapterPosition());
                    assert val != null;
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    ReservationDirections.ActionNavReservationToRestaurantInfo action =
                            ReservationDirections.actionNavReservationToRestaurantInfo((int)name.getTag());
                    action.setReserved(true);
                    navController.navigate(action);

                });
                annuler.setOnClickListener(view -> {
                    LayoutInflater inflater1 = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater1.inflate(R.layout.annuler_popup, null);

                    //Specify the length and width through constants
                    int width = LinearLayout.LayoutParams.MATCH_PARENT;
                    int height = LinearLayout.LayoutParams.MATCH_PARENT;

                    //Make Inactive Items Outside Of PopupWindow
                    boolean focusable = true;

                    //Create a window with our parameters
                    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                    TextView statut = popupView.findViewById(R.id.reserv_statut),
                            restoTitre = popupView.findViewById(R.id.confirm_resto_name),
                            pourLe = popupView.findViewById(R.id.pour_date),
                            date = popupView.findViewById(R.id.reserv_date);
                    //Set the location of the window on the screen
                    statut.setText(R.string.commande_est_annuler);
                    popupWindow.showAtLocation(itemView, Gravity.CENTER, 400, 400);
                    Button close = popupView.findViewById(R.id.quit_popup);
                    close.setOnClickListener(u -> {
                        int newPosition = getAdapterPosition() - 1;
                        Main.reservationList.remove(newPosition);
                        notifyItemRemoved(newPosition);
                        current_nb--;
                        notifyItemRangeChanged(newPosition, current_nb);
                        popupWindow.dismiss();

                    });
                });
            }
        }
        public class ViewHolder2 extends RecyclerView.ViewHolder {
            private final ImageView resto_pic;
            private final TextView name, date, rating;

            public ViewHolder2(@NonNull View itemView) {
                super(itemView);
                Button modifier = itemView.findViewById(R.id.curr_butt_confirm);
                resto_pic = itemView.findViewById(R.id.curr_reserve_picture);
                name = itemView.findViewById(R.id.curr_reserve_name);
                date = itemView.findViewById(R.id.curr_reserve_date);
                rating = itemView.findViewById(R.id.curr_reserve_rating);
                LayoutInflater inflater1 = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater1.inflate(R.layout.rating_popup, null);
                //Specify the length and width through constants
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;

                //Make Inactive Items Outside Of PopupWindow
                boolean focusable = true;
                //Create a window with our parameters
                resto_pic.setOnClickListener(V -> {
                    View val = linearLayoutManager.findViewByPosition(getAdapterPosition());
                    assert val != null;
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    ReservationDirections.ActionNavReservationToRestaurantInfo action =
                            ReservationDirections.actionNavReservationToRestaurantInfo((int)name.getTag());
                    navController.navigate(action);

                });
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                modifier.setOnClickListener(view -> {
                    popupWindow.showAtLocation(itemView, Gravity.CENTER, 400, 400);
                    RatingBar ratingBar = popupView.findViewById(R.id.ratingBar2);
                    Button okButton = popupView.findViewById(R.id.rating_ok);
                    ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                        @Override
                        public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {
                            // TODO Auto-generated method stub
                            rating.setText(String.valueOf(rateValue));
                        }
                    });
                    okButton.setOnClickListener(view1 -> {
                        popupWindow.dismiss();
                    });
                });
            }
        }
        public class ViewHolder3 extends RecyclerView.ViewHolder {
            private final TextView section;

            public ViewHolder3(@NonNull View itemView) {
                super(itemView);
                section = itemView.findViewById(R.id.curr_reserv_section);
            }
        }
    }
}