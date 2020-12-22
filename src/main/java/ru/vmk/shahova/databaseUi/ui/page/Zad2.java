package ru.vmk.shahova.databaseUi.ui.page;

public class Zad2 {
    private int codeSupplier;
    private int unitPrice;

    public Zad2(int codeSupplier, int unitPrice) {
        this.codeSupplier = codeSupplier;
        this.unitPrice = unitPrice;
    }

    public int getCodeSupplier() {
        return codeSupplier;
    }

    public void setCodeSupplier(int codeSupplier) {
        this.codeSupplier = codeSupplier;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}
