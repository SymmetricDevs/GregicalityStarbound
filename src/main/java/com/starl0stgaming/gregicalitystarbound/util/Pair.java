package com.starl0stgaming.gregicalitystarbound.util;

public class Pair<A, B> {
    A a;
    B b;
    public Pair (A a, B b) {
        this.a = a;
        this.b = b;
    }
    public A a() {
        return a;
    }
    public B b() {
        return b;
    }
    public void a(A a) {
        this.a = a;
    }
    public void b(B b) {
        this.b = b;
    }
}
