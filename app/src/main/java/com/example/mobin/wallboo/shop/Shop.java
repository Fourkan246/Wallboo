package com.example.mobin.wallboo.shop;


import java.util.Arrays;
import java.util.List;


/**
 * Created by yarolegovich on 07.03.2017.
 */

public class Shop {

//    private static final String STORAGE = "shop";
//    private SharedPreferences storage;


    public static Shop get() {
        return new Shop();
    }


    private Shop() {
//        App app = new App();
//        storage = app.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(3, "Favourite Board", "$265.00 USD", "https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg"),
                new Item(1, "Everyday Candle", "$12.00 USD", "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"),
                new Item(2, "Small Porcelain Bowl", "$50.00 USD", "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg"));
//                new Item(3, "Favourite Board", "$265.00 USD", R.drawable.shop3),
//                new Item(4, "Earthenware Bowl", "$18.00 USD", R.drawable.shop4),
//                new Item(5, "Porcelain Dessert Plate", "$36.00 USD", R.drawable.shop5),
//                new Item(6, "Detailed Rolling Pin", "$145.00 USD", R.drawable.shop6));
    }

//    public boolean isRated(int itemId) {
//        return storage.getBoolean(String.valueOf(itemId), false);
//    }

    public void setRated(int itemId, boolean isRated) {
//        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}
