package server;

import java.util.HashMap;

public class Pair<A, B> {
    private HashMap<A, B> domainToRange;
    private HashMap<B, A> rangeToDomain;

    public Pair() {
        this.domainToRange = new HashMap<>();
        this.rangeToDomain = new HashMap<>();
    }

    public boolean isDefinedForDomainValue(A value) {
        return this.domainToRange.containsKey(value);
    }

    public boolean isDefinedForRangeValue(B value) {
        return this.rangeToDomain.containsKey(value);
    }

    public void define(A key, B value) {
        this.domainToRange.put(key, value);
        this.rangeToDomain.put(value, key);
    }

    public A getKeyByValue(B value) {
        return rangeToDomain.get(value);
    }

    public B getValueByKey(A key) {
        return domainToRange.get(key);
    }
}