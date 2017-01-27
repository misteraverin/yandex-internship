package Task5;

class Pair<T, R> {
    private T first;
    private R second;

    Pair(T first, R second) {
        this.first = first;
        this.second = second;
    }

    T getFirst() {
        return first;
    }

    R getSecond() {
        return second;
    }
}