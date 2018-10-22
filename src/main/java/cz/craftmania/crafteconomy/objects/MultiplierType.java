package cz.craftmania.crafteconomy.objects;

public enum MultiplierType {

    GLOBAL("Global"),
    SERVER("Server"),
    PERSONAL("Osobni");

    private String normalName;

    MultiplierType() {
    }

    MultiplierType(String name) {
        this.normalName = name;
    }

    public String getNormalName() {
        return normalName;
    }
}
