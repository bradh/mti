package mti.commons.util;

import java.io.Serializable;

public class Pair<A, B> implements
	Serializable
{

	public static <A, B> Pair<A, B> create(
		final A l,
		final B r ) {
		return new Pair<A, B>(
			l,
			r);
	}

	private static final long serialVersionUID = 1L;

	private A a;
	private B b;

	protected Pair() {}

	public Pair(
		final A a,
		final B b ) {
		this.a = a;
		this.b = b;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}
}
