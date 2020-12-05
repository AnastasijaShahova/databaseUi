package ru.vmk.shahova.databaseUi.ui.page;

public class Result {

    private int detailCode;
    private String name;
    private int price;
    private int plan;

    public Result(int detailCode, String name, int price, int plan) {
        this.detailCode = detailCode;
        this.name = name;
        this.price = price;
        this.plan = plan;
    }

    public int getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(int detailCode) {
        this.detailCode = detailCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }
}
