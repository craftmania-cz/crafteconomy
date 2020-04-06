package cz.craftmania.crafteconomy.objects;

import lombok.NonNull;

public enum MultiplierType {

    GLOBAL("Global"),
    SERVER("Server"),
    PERSONAL("Osobni");

    private String normalName;

    MultiplierType() {
    }

    MultiplierType(@NonNull String name) {
        this.normalName = name;
    }

    public String getNormalName() {
        return normalName;
    }
}
