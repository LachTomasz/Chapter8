package pair2;

import java.time.*;

import pair1.Pair;
/**
 * @author Tomek
 *
 */
public class PairTest2 {

    public static void main(String[] args) {

        LocalDate[] birthdays =
                {
                        LocalDate.of(1906, 12, 9),	//G.Hopper
                        LocalDate.of(1815, 12, 10),	//A.Lovelace
                        LocalDate.of(1910, 6, 3),	//J. von Neumann
                        LocalDate.of(1910, 6, 22),	//K.Zuse
                };
        PairA<LocalDate> mm = ArrayAlg.minmax(birthdays);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
    }
}

class ArrayAlg {
    /**
     * Pobiera najmniejszą i największą wartość z tablicy obiektów typu T.
     * @param a tablica obiektów typu T
     * @return para złożona z najmniejszej i największej wartości lub wartość null,
     * jeśli tablica "a" jest null bądż pusta
     */
    public static <T extends Comparable> PairA<T> minmax(T[] a){

        if(a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];
        for(int i = 0; i < a.length; i++) {
            if(min.compareTo(a[i]) > 0) min = a[i];
            if(max.compareTo(a[i]) < 0) max = a[i];
        }
        return new PairA<T>(min, max);
    }
}