package com.example.devoir4;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;

public class RestoList {
    Context context;
    int[] avatar;
    public RestoList(Context c) {
        context = c;
    }
    public ArrayList<Resto> initRestoList(boolean isReserve) {
        ArrayList<Resto> restoList = new ArrayList<>();
        if (isReserve) {
            restoList.add(new RestoList.Resto("Piri Piri", "Portugaise", "(4.2/5)", R.drawable.salmond, "5 Avenue des Pins", "249", "20/04/2021", "2", null, "8:30", "2", Color.RED, null));
            return restoList;
        }
        String[] names = context.getResources().getStringArray(R.array.names);
        String[] cuisine = context.getResources().getStringArray(R.array.cuisine);
        String[] adress = context.getResources().getStringArray(R.array.adress),
                amount = context.getResources().getStringArray(R.array.amount_rating),
                rating = context.getResources().getStringArray(R.array.rating);
        avatar = new int[]{R.drawable.chicken, R.drawable.hamburger, R.drawable.festin, R.drawable.indian,
                R.drawable.pasta, R.drawable.pasta2, R.drawable.pizza, R.drawable.saucisse, R.drawable.thai, R.drawable.hamburger1, R.drawable.festin2, R.drawable.veggies, R.drawable.sandwich1,
                R.drawable.saucisse1, R.drawable.food1, R.drawable.food2, R.drawable.food4, R.drawable.food5};
        for (int i = 0; i < 18; i ++) {
            int fav = Color.BLACK;
            if (i % 2 == 0) {
                fav = Color.RED;
            }
            Resto resto = new Resto(names[i], cuisine[i], rating[i], avatar[i], adress[i], amount[i], "2020/02/10", "4", "3.8", "8:00", "2", fav, null);
            restoList.add(resto);
        }
        return restoList;
    }

    public static class Resto {
        String name, cuisine, rating, adress, amountRating, nbAdulte, revue, date, heure, nbEnfant;
        String[] attribut;
        int like;
        int picture;
        public Resto(String n, String c, String r, int i, String a, String nb, String dat, String nbPer, String re, String h, String enf, int fav, String[] att) {
            name = n;
            picture = i;
            cuisine = c;
            rating = r;
            adress = a;
            amountRating = nb;
            nbAdulte = nbPer;
            date = dat;
            revue = re;
            heure = h;
            nbEnfant = enf;
            like = fav;
            attribut = att;
        }
    }
}
