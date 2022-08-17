package notjunit;

public class Assert {
	public static void equals(double expected, double actual) {
		if(expected != actual) throw new AssertException();
	}
	
	public static void willThrow(Class<?> exceptiontype, Runnable testMethod) {
		try {
			testMethod.run();
			throw new AssertException();
		} catch (Exception ex) {
			if(!ex.getClass().equals(exceptiontype)) throw new AssertException();
		}
	}

	public static void equals(String expected, String actual) {
		// TODO Auto-generated method stub
		if(!expected.equals(actual)) throw new AssertException();
	}
}
