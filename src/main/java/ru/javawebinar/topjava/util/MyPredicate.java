package ru.javawebinar.topjava.util;

import java.util.function.Predicate;

public class MyPredicate<T> implements Predicate<T> {
    T var1;
    @Override
    public boolean test(Object o) {
        if(var1.equals(o)) {
            return true;
        }
        return false;
    }
}
