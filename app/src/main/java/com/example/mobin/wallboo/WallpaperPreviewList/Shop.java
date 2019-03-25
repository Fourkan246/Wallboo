package com.example.mobin.wallboo.WallpaperPreviewList;


import java.util.Arrays;
import java.util.List;

public class Shop {
    public static Shop get() {
        return new Shop();
    }


    private Shop() {
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(3, "Favourite Board", "$265.00 USD", "https://lh4.googleusercontent.com/-WIuWgVcU3Qw/URqubRVcj4I/AAAAAAAAAbs/YvbwgGjwdIQ/s1024/Antelope%252520Walls.jpg"),
                new Item(1, "Everyday Candle", "$12.00 USD", "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg"),
                new Item(2, "Small Porcelain Bowl", "$50.00 USD", "https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg"));
    }

    public void setRated(int itemId, boolean isRated) {
    }
}
