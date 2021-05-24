package com.github.prakashsharma91.domain;

public class Field {
    String name;

    public Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Field) {
            return name.equals(((Field) o).getName());
        }
        return false;
    }
}
