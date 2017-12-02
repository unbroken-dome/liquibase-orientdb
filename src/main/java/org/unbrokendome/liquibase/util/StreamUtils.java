package org.unbrokendome.liquibase.util;

import java.util.function.Supplier;
import java.util.stream.Stream;

public final class StreamUtils {

    public static <T>
    Stream<T> of(Supplier<? extends T> supplier) {
        return Stream.of(supplier)
                .map(Supplier::get);
    }


    public static <T>
    Stream<T> of(Supplier<? extends T> a, Supplier<? extends T> b) {
        return Stream.of(a, b)
                .map(Supplier::get);
    }


    public static <T>
    Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b, Stream<? extends T> c) {
        return Stream.concat(a,
                Stream.concat(b, c));
    }


    public static <T>
    Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b,
                     Stream<? extends T> c, Stream<? extends T> d) {
        return Stream.concat(
                Stream.concat(a, b),
                Stream.concat(c, d));
    }


    public static <T>
    Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b,
                     Stream<? extends T> c, Stream<? extends T> d,
                     Stream<? extends T> e) {
        return Stream.concat(
                concat(a, b, c),
                Stream.concat(d, e));
    }
}
