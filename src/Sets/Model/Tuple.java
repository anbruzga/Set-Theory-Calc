package Sets.Model;


public class Tuple<T, E, K> {
    private T t0;
    private E t1;
    private K t2;

    public Tuple(T t0, E t1, K t2) {
        this.t0 = t0;
        this.t1 = t1;
        this.t2 = t2;
    }

    public static <T, E, K> Tuple of(T arg0, E arg1, K arg2) {
        return new Tuple(arg0, arg1, arg2);
    }

    public T getLeft() {
        return t0;
    }

    public void setLeft(T t0) {
        this.t0 = t0;
    }

    public E getMiddle() {
        return t1;
    }

    public void setMiddle(K t2) {
        this.t2 = t2;
    }

    public K getRight() {
        return t2;
    }

    public void setRight(E t1) {
        this.t1 = t1;
    }

    public String toString() {
        String str = "(" +
                t0.toString() +
                "," +
                t1.toString() +
                "," +
                t2.toString() +
                ")";
        return str;
    }
}
