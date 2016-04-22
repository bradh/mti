package mti.commons.util;

import java.io.Serializable;

public class Quad<A, B, C, D> implements
	Serializable
{
	public static <A, B, C, D> Quad<A, B, C, D> create(
		final A a,
		final B b,
		final C c,
		final D d ) {
		return new Quad<A, B, C, D>(
			a,
			b,
			c,
			d);
	}

	private static final long serialVersionUID = 1L;

	private A a;
	private B b;
	private C c;
	private D d;

	protected Quad() {}

	public Quad(
		final A a,
		final B b,
		final C c,
		final D d ) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
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

	public D getD() {
		return d;
	}
}
