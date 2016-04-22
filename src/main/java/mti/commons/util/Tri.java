package mti.commons.util;

import java.io.Serializable;

public class Tri<A, B, C> implements
	Serializable
{
	public static <A, B, C> Tri<A, B, C> create(
		final A a,
		final B b,
		final C c ) {
		return new Tri<A, B, C>(
			a,
			b,
			c);
	}

	private static final long serialVersionUID = 1L;

	private A a;
	private B b;
	private C c;

	protected Tri() {}

	public Tri(
		final A a,
		final B b,
		final C c ) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public A getA() {
		return a;
	}

	public B getB() {
		return b;
	}

	public C getC() {
		return c;
	}

}
