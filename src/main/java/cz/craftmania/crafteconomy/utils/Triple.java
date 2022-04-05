package cz.craftmania.crafteconomy.utils;

public record Triple<T, U, V>(T first, U second, V third) {

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }
}
