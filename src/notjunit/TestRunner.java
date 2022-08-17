package notjunit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class<Tests> obj = Tests.class;
		System.out.println("Running Tests...");
		
		for(Method method : obj.getDeclaredMethods()) {
			if(method.isAnnotationPresent(Test.class)) {
				try {
					System.out.println("Testing method: " + method.getName());
					method.invoke(obj.getConstructor().newInstance());
					System.out.println("Test passed");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// if this gets thrown it means our test failed
					System.out.println("Test failed" + e.getTargetException());
					//e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

	}
	}
}
