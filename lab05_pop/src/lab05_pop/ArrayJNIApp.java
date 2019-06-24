package lab05_pop;
//http://journals.ecs.soton.ac.uk/java/tutorial/native1.1/implementing/method.html
//https://www3.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html?fbclid=IwAR3xWIFengrc2z7NdIsazF-6TyFUuwXz-3THy09Y36Ico9HKbt6DrBzxljo#zz-5.1
public class ArrayJNIApp {
	public Double[] a;
	public Double[] b;
	public Boolean order;

	static {
		System.loadLibrary("lab05_pop_ArrayJNIApp");
	}

	private native void sayHello();

	public native Double[] sort01(Double[] a, Boolean order);

	public native Double[] sort02(Double[] a);

	public native void sort03();

	public void sort04() {
	}

	public static void main(String[] args) {
		testHello();
		testSort1(true);
		testSort2(true);
	}

	private static void testHello() {
		new ArrayJNIApp().sayHello();
	}

	private static void testSort1(boolean order) {
		ArrayJNIApp jniApp = new ArrayJNIApp();
		Double[] a = { 1.0, 4.0, 3.0 };
		Double[] returned = jniApp.sort01(a, order);
		System.out.println("Sort1");
		for (int i = 0; i < returned.length; ++i) {
			System.out.print(returned[i] + " ");
		}
		System.out.println("");
	}

	private static void testSort2(boolean order) {
		ArrayJNIApp jniApp = new ArrayJNIApp();
		jniApp.order = order;
		Double[] a = { 1.0, 4.0, 3.0 };
		Double[] returned = jniApp.sort02(a);
		System.out.println("Sort2");
		for (int i = 0; i < returned.length; ++i) {
			System.out.print(returned[i] + " ");
		}
		System.out.println("");
	}
}
