// ASSIGNMENT 1 - PART 2
package assignment;

class Delegation {

	public static void main(String args[]) {
		B b = new B();
		System.out.println(b.f() + b.g() - b.p(1) + b.q(2));

		B2 b2 = new B2();
		System.out.println(b2.f() + b2.g() - b2.p(1) + b2.q(2));

		D d = new D();
		System.out.println(d.f() + d.g() - d.h() + d.p(1) - d.q(2) + d.r());

		D2 d2 = new D2();
		System.out.println(d2.f() + d2.g() - d2.h() + d2.p(1) - d2.q(2) + d2.r());

	}
}

class Delegation2 {

	public static void main(String args[]) {

		E e = new E();
		System.out.println(e.f() - e.g() + e.h() - e.p(1) + e.q(2) - e.r() + e.k(100));

		E2 e2 = new E2();
		System.out.println(e2.f() - e2.g() + e2.h() - e2.p(1) + e2.q(2) - e2.r() + e2.k(100));

		F f = new F();
		System.out.println(f.f() + f.g() + f.h() + f.p(1) - f.q(2) - f.r() - f.j(10) + f.l(100));

		F2 f2 = new F2();
		System.out.println(f2.f() + f2.g() + f2.h() + f2.p(1) - f2.q(2) - f2.r() - f2.j(10) + f2.l(100));
	}
}

abstract class A {
	int a1 = 100;
	int a2 = 200;

	public int f() {
		return a1 + p(100) + q(100);
	}

	protected abstract int p(int m);

	protected abstract int q(int m);
}

class B extends A {
	int b1 = 1000;
	int b2 = 2000;

	public int g() {
		return this.p(100) + this.q(200);
	}

	public int p(int m) {
		return m + a1 + b1;
	}

	public int q(int m) {
		return m + a2 + b2;
	}
}

abstract class C extends B {
	int c1 = 10000;
	int c2 = 20000;

	public int r() {
		return f() + g() + h();
	}

	public int q(int m) {
		return m + a2 + b2 + c2;
	}

	protected abstract int h();
}

class D extends C {
	int d1 = 1000000;
	int d2 = 2000000;

	public int r() {
		return f() + g() + h();
	}

	public int p(int m) {
		return super.p(m) + d2;
	}

	public int h() {
		return a1 + b1 + c1;
	}

	public int j(int n) {
		return r() + super.r();
	}

}

class E extends C {
	int e1 = 1;
	int e2 = 2;

	public int q(int m) {
		return p(m) + c2;
	}

	public int h() {
		return a1 + b1 + e1;
	}

	public int k(int n) {
		return q(n) + super.q(n);
	}

}

class F extends D {
	int f1 = 10;
	int f2 = 20;

	public int q(int m) {
		return p(m) + c1 + d1;
	}

	public int h() {
		return c2 + f2;
	}

	public int l(int n) {
		return q(n) + super.q(n);
	}
}

// ===== TRANSFORMATION IN TERMS OF DELEGATION ======

// INTERFACES

interface IA {

	int f();

	abstract int p(int m);

	abstract int q(int m);
}

interface IB extends IA {
	int g();

	int p(int m);

	int q(int m);
}

interface IC extends IB {
	int r();

	int q(int m);

	abstract int h();

}

interface ID extends IC {
	int r();

	int p(int m);

	int h();

	int j(int m);
}

interface IE extends IC {
	int q(int m);

	int h();

	int k(int m);

}

interface IF extends ID {
	int q(int m);

	int h();

	int l(int m);
}

// CLASSES 

	class A2 implements IA {
		int a1 = 100;
		int a2 = 200;
	
		public A2(IA p) {
			sub = p;
		}
	
		public int f() {
			return a1 + p(100) + q(100);
		}
	
		public int p(int m) {
			return sub.p(m);
		}
	
		public int q(int m) {
			return sub.q(m);
		}
	
		IA sub;
	}

class B2 implements IB {

	int b1 = 1000;
	int b2 = 2000;

	public B2() {
		super_a2 = new A2(this);
	}

	public B2(IB p) {
		sub = p;
		super_a2 = new A2(sub);
	}

	public int f() {
		return super_a2.f();
	}

	public int g() {
		return sub.p(100) + sub.q(200);
	}

	public int p(int m) {
		return m + super_a2.a1 + b1;
	}

	public int q(int m) {
		return m + super_a2.a2 + b2;
	}

	A2 super_a2;
	IB sub = this;
}

class C2 implements IC {

	int c1 = 10000;
	int c2 = 20000;

//	public C2() {
//		super_b2 = new B2(this);
//	}

	public C2(IC p) {
		sub = p;
		super_b2 = new B2(sub);
	}

	public int g() {
		return super_b2.g();
	}

	public int p(int m) {
		return super_b2.p(m);
	}

	public int f() {
		return super_b2.f();
	}

	public int r() {
		return sub.f() + sub.g() + sub.h();
	}

	public int q(int m) {
		return m + super_b2.super_a2.a2 + super_b2.b2 + c2;
	}

	public int h() {
		return sub.h();
	}

	IC sub = this;
	B2 super_b2;
}

class D2 implements ID {

	int d1 = 1000000;
	int d2 = 2000000;

	public D2() {
		super_c2 = new C2(this);
	}

	public D2(ID p) {
		sub = p;
		super_c2 = new C2(sub);
	}

	public int q(int m) {
		return super_c2.q(m);
	}

	public int g() {
		return super_c2.g();
	}

	public int f() {
		return super_c2.f();
	}

	public int r() {
		return sub.f() + sub.g() + sub.h();
	}

	public int p(int m) {
		return super_c2.p(m) + d2;
	}

	public int h() {
		return super_c2.super_b2.super_a2.a1 + super_c2.super_b2.b1 + super_c2.c1;
	}

	public int j(int m) {
		return r() + super_c2.r();
	}

	C2 super_c2;
	ID sub = this;
}

class E2 implements IE {

	int e1 = 1;
	int e2 = 2;

	public E2() {
		super_c2 = new C2(this);
	}

	public E2(IE p) {
		sub = p;
		super_c2 = new C2(sub);
	}

	public int r() {
		return super_c2.r();
	}

	public int g() {
		return super_c2.g();
	}

	public int p(int m) {
		return super_c2.p(m);
	}

	public int f() {
		return super_c2.f();
	}

	public int q(int m) {
		return p(m) + super_c2.c2;
	}

	public int h() {
		return super_c2.super_b2.super_a2.a1 + super_c2.super_b2.b1 + e1;
	}

	public int k(int m) {
		return q(m) + super_c2.q(m);
	}

	C2 super_c2;
	IE sub;
}

class F2 implements IF {

	int f1 = 10;
	int f2 = 20;

	public F2() {
		super_d2 = new D2(this);
	}

	public F2(IF p) {
		sub = p;
		super_d2 = new D2(sub);
	}

	public int r() {
		return super_d2.r();
	}

	public int p(int m) {
		return super_d2.p(m);
	}

	public int j(int m) {
		return super_d2.j(m);
	}

	public int g() {
		return super_d2.g();
	}

	public int f() {
		return super_d2.f();
	}

	public int q(int m) {
		return p(m) + super_d2.super_c2.c1 + super_d2.d1;
	}

	public int h() {
		return super_d2.super_c2.c2 + f2;
	}

	public int l(int m) {
		return q(m) + super_d2.q(m);
	}

	D2 super_d2;
	IF sub;
}
