package ru.vmk.shahova.databaseUi.ui.page;

public class Catalog {

    private int detailCode;
    private String type;
    private String name;
    private String init;
    private int price;

    public Catalog(int detailCode, String type, String name, String init, int price) {
        this.detailCode = detailCode;
        this.type = type;
        this.name = name;
        this.init = init;
        this.price = price;
    }

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
