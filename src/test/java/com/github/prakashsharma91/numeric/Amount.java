package com.github.prakashsharma91.numeric;

import com.github.prakashsharma91.domain.ColumnValue;

public class Amount implements ColumnValue<Amount> {
    double base;
    double local;

    public Amount(){}

    public Amount(Amount val) {
        this.base = val.base;
        this.local = val.local;
    }
    public Amount(double base, double local) {
        this.base = base;
        this.local = local;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getLocal() {
        return local;
    }

    public void setLocal(double local) {
        this.local = local;
    }

    public void append(Amount amount){
        this.base += amount.getBase();
        this.local += amount.getLocal();
    }
}
