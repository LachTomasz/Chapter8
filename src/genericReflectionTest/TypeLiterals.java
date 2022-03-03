package genericReflectionTest;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;

/**
 * Literał opisuje typ, który może być generyczny, na przykład
 * ArrayList<String>
 * @author Tomek
 *
 */
class TypeLiteral<T> {

    private Type type;

    /**
     * Ten konstruktor musi być wywołany z anonimowej podklasy
     * jako new TypeLiteral<...>(){}.
     */
    public TypeLiteral() {
        Type parentType = getClass().getGenericSuperclass();
        if(parentType instanceof ParameterizedType) {
            type = ((ParameterizedType) parentType).getActualTypeArguments()[0];
        }
        else
            throw new UnsupportedOperationException(
                    "Construct as new TypeLiberal&lt;. . .&gt;(){}");

    }

    private TypeLiteral(Type type) {
        this.type = type;
    }

    /**
     * Zwraca literał typowy opisujący dany typ.
     */
    public static TypeLiteral<?> of(Type type){
        return new TypeLiteral<Object>(type);
    }

    public String toString() {
        if(type instanceof Class) return ((Class<?>)type).getName();
        else return type.toString();
    }

    public boolean equals(Object otherObject) {
        return otherObject instanceof TypeLiteral
                && type.equals(((TypeLiteral<?>) otherObject).type);
    }

    public int hashCode() {
        return type.hashCode();
    }
}

/**
 *Formatuje obiejty przy użyciu reguł wiążących typy z funkcjami formatującymi.
 */
class Formatter {

    private Map<TypeLiteral<?>, Function<?, String>> rules = new HashMap<>();

    /**
     * Dodaje regułę formatowania do tego formatera.
     * @param type typ, którego dotyczy reguła
     * @param formatterType funkcja formatująca obiekty tego typu
     */
    public <T> void forType(TypeLiteral<T> type, Function<T, String> formatterType) {
        rules.put(type, formatterType);
    }

    /**
     * Formatuje wszystkie pola obiektu przy użyciu reguł tego formatera.
     * @param obj obiekt
     * @return łańcuch zawierający wszystkie nazwy pól i sformatowane wartości
     */
    public String formatFields(Object obj)
            throws IllegalArgumentException, IllegalAccessException {

        var result = new StringBuilder();
        for(Field f : obj.getClass().getDeclaredFields()) {
            result.append(f.getName());
            result.append("=");
            f.setAccessible(true);
            Function<?, String> formatterForType =
                    rules.get(TypeLiteral.of(f.getGenericType()));
            if(formatterForType != null) {
                //formatterForType ma typ parametru ?.
                //Nic nie można przekazać do metody apply.
                //Rzutowanie zamienia typ parametru na Object,
                //aby można było wykonać wywołanie.
                @SuppressWarnings("unchecked")
                Function<Object, String> objectFormatter =
                        (Function<Object, String>) formatterForType;
                result.append(objectFormatter.apply(f.get(obj)));
            }
            else
                result.append("\n");
        }
        return result.toString();
    }
}

public class TypeLiterals {

    public static class Sample {

        ArrayList<Integer> nums;
        ArrayList<Character> chars;
        ArrayList<String> strings;

        public Sample() {
            nums = new ArrayList<>();
            nums.add(42); nums.add(1729);
            chars = new ArrayList<>();
            chars.add('H'); chars.add('i');
            strings = new ArrayList<>();
            strings.add("Hello"); strings.add("World");
        }
    }

    private static <T> String join(String separator, ArrayList<T> elements) {
        var result = new StringBuilder();
        for(T e : elements) {
            if(result.length() > 0) result.append(separator);
            result.append(e.toString());
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception {
        var formatter = new Formatter();
        formatter.forType(new TypeLiteral<ArrayList<Integer>>() {},
                lst -> join(" ", lst));
        formatter.forType(new TypeLiteral<ArrayList<Character>>() {},
                lst -> "\"" + join("",lst) + "\"");
        System.out.println(formatter.formatFields(new Sample()));


    }
}