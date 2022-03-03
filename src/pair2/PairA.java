package pair2;

/**
 * @author Tomek
 *
 */
public class PairA<T> {

    private T first;
    private T second;

    public PairA() { first = null; second = null;}
    public PairA(T first, T second) {this.first = first; this.second = second;}

    public T getFirst() {return first;}
    public T getSecond() {return second;}

    public void setFirst(T newValue) {first = newValue;}
    public void setSecond(T newValue) {second = newValue;}

}
