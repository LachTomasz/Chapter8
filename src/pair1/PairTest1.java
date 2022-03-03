package pair1;

/**
 *
 * @author Tomek
 *
 */
public class PairTest1 {

    public static void main(String[] args) {
        String[] words = {"Ala", "ma", "kota", "i", "psa"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}

class ArrayAlg{

    /**
     * Pobiera najmniejszą i największą wartosć z tablicy łańcuchów
     * @param a tablica łańcuchów
     * @return złożona z najmniejszej wartości lub null, jeśli tablica "a" jest null bądż pusta
     */
    public static Pair<String> minmax(String[] a) {
        if(a== null || a.length == 0) return null;
        String min = a[0];
        String max = a[0];
        for (int i = 0; i < a.length; i++) {
            if(min.compareTo(a[i]) > 0) min = a[i];
            if(max.compareTo(a[i]) < 0) max = a[i];
        }
        //return new Pair <String>(min, max);
        return new Pair <>(min, max);
    }
}