package io.github.safari.lwjgl3.maingame;

public class ShopItem {
    private String name;
    private int price;
    private boolean buying;

    public ShopItem(String name, int price ) {
        this.name = name;
        this.price = price;

    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBuying() {
        return buying;
    }

    public void setBuying(boolean buying) {
        this.buying = buying;
    }
}
