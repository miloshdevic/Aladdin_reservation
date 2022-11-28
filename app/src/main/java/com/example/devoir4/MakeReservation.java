package com.example.devoir4;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


public class MakeReservation extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_make_reservation, container, false);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        TextView title = root.findViewById(R.id.makeres_title);
        RestoList.Resto resto;
        MakeReservationArgs args = MakeReservationArgs.fromBundle(getArguments());
        int restoIndex = args.getRestoName();
        resto = Main.restoList.get(args.getRestoName());
        EditText nbEnfant = root.findViewById(R.id.nb_enfant),
                nbAdulte = root.findViewById(R.id.nb_adulte), heure = root.findViewById(R.id.heure_input);
        Button annuler = root.findViewById(R.id.annuler_reserv), confirmer = root.findViewById(R.id.confirmer_reserv), dateInput = root.findViewById(R.id.date_input);
        dateInput.setOnClickListener(view -> {
            LayoutInflater inflater1 = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater1.inflate(R.layout.pick_date, null);

            //Specify the length and width through constants
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            //Make Inactive Items Outside Of PopupWindow
            boolean focusable = true;

            //Create a window with our parameters
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.showAtLocation(root, Gravity.CENTER, 400, 400);
            Button close_date = popupView.findViewById(R.id.confirm_date);
            CalendarView calendarView = popupView.findViewById(R.id.calendarView);
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month,
                                                int dayOfMonth) {
                    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, dayOfMonth);
                    String sDate = sdf.format(calendar.getTime());
                    dateInput.setText(sDate);
                }
            });
            close_date.setOnClickListener(view1 -> {
                popupWindow.dismiss();
            });
        });
        if (args.getModified()) {
            resto = Main.reservationList.get(restoIndex);
            dateInput.setText(resto.date);
            nbEnfant.setText(resto.nbEnfant);
            nbAdulte.setText(resto.nbAdulte);
            heure.setText(resto.heure);
        }
        title.setText(resto.name);
        annuler.setOnClickListener(view -> {
            LayoutInflater inflater1 = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater1.inflate(R.layout.popup_reserve, null);

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
            popupWindow.showAtLocation(root, Gravity.CENTER, 400, 400);
            Button close = popupView.findViewById(R.id.quit_popup);
            restoTitre.setVisibility(View.INVISIBLE);
            pourLe.setVisibility(View.INVISIBLE);
            date.setVisibility(View.INVISIBLE);
            close.setOnClickListener(u -> {
                if (args.getModified()) {
                    Main.reservationList.remove(args.getRestoName());
                }
                NavController navController1 = Navigation.findNavController(requireActivity(), R.id.nav_host);
                navController1.navigate(R.id.action_makeReservation_to_nav_home);
                popupWindow.dismiss();
            });
        });
        RestoList.Resto finalResto = resto;
        confirmer.setOnClickListener(view -> {
            LayoutInflater inflater1 = (LayoutInflater) root.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View popupView = inflater1.inflate(R.layout.popup_reserve, null);

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
            //Set the location of the window on the scree

            popupWindow.showAtLocation(root, Gravity.CENTER, 400, 400);
            Button close = popupView.findViewById(R.id.quit_popup);
            if (dateInput.getText().length() == 0) {
                statut.setText(R.string.missing_info1);
                restoTitre.setVisibility(View.INVISIBLE);
                pourLe.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
            }
            else if (heure.getText().length() == 0) {
                statut.setText(R.string.missing_info4);
                restoTitre.setVisibility(View.INVISIBLE);
                pourLe.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
            }
            else if (nbAdulte.getText().length() == 0) {
                statut.setText(R.string.missing_info2);
                restoTitre.setVisibility(View.INVISIBLE);
                pourLe.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
            }
            else if (nbEnfant.getText().length() == 0) {
                statut.setText(R.string.missing_info3);
                restoTitre.setVisibility(View.INVISIBLE);
                pourLe.setVisibility(View.INVISIBLE);
                date.setVisibility(View.INVISIBLE);
            }

            else {
                restoTitre.setText(title.getText());
                date.setText(dateInput.getText());
                restoTitre.setVisibility(View.VISIBLE);
                pourLe.setVisibility(View.VISIBLE);
                date.setVisibility(View.VISIBLE);
                statut.setText(R.string.commande_est_confirm);
            }
            close.setOnClickListener(u -> {
                if (dateInput.getText().length() != 0 && nbAdulte.getText().length() != 0 && nbEnfant.getText().length() != 0) {
                    if (!args.getModified()) {
                        int nb= Integer.parseInt( nbEnfant.getText().toString()) + Integer.parseInt( nbAdulte.getText().toString()) ;
                        RestoList.Resto restor = new RestoList.Resto(finalResto.name, finalResto.cuisine, finalResto.rating,
                                finalResto.picture, finalResto.adress, finalResto.amountRating, date.getText().toString(),
                                nbAdulte.getText().toString(), null, heure.getText().toString(), nbEnfant.getText().toString(), Color.RED, null);
                        Main.reservationList.add(restor);
                    }
                    else {
                        RestoList.Resto resto1 = Main.reservationList.get(Main.reservationList.size() - 1);
                        resto1.nbEnfant = nbEnfant.getText().toString();
                        resto1.nbAdulte = nbAdulte.getText().toString();
                        resto1.date = date.getText().toString();
                        resto1.heure = heure.getText().toString();
                    }
                    Main.restoList.get(restoIndex).date = date.getText().toString();
                    NavController navController1 = Navigation.findNavController(requireActivity(), R.id.nav_host);
                    navController1.navigate(R.id.action_makeReservation_to_nav_reservation);
                }
                popupWindow.dismiss();
            });
        });
        return root;
    }
}