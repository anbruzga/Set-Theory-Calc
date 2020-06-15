package Sets.Model;

public class Pair<T, E> {
    private T t0;
    private E t1;

    public Pair(T t0, E t1) {
        this.t0 = t0;
        this.t1 = t1;
    }

    public static <T, E> Pair of(T arg0, E arg1) {
        return new Pair(arg0, arg1);
    }

    public T getLeft() {
        return t0;
    }

    public void setLeft(T t0) {
        this.t0 = t0;
    }

    public E getRight() {
        return t1;
    }

    public void setRight(E t1) {
        this.t1 = t1;
    }

    public String toString() {

        String firstMember = t0.toString();
        if (t0 instanceof Double && t0.toString().contains(".0")) {
            firstMember = t0.toString().replace(".0", "");
        }

        String secondMember = t1.toString();
        if (t1 instanceof Double && t1.toString().contains(".0")) {
            secondMember = t1.toString().replace(".0", "");
        }

        String str = "(" +
                firstMember +
                "," +
                secondMember +
                ")";
        return str;
    }

    public boolean equals(Pair<T, E> pair) {
        return this.toString().equals(pair.toString());
    }
}
