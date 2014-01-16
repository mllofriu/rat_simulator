package support;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nslj.src.lang.NslModule;

public class ReflexionLoader {

	public static NslModule getReflexionModel(String module, String moduleName,
			Object owner) {
		NslModule result = null;

		Class[] types = new Class[] { String.class, NslModule.class };
		Constructor cons = null;
		try {
			cons = Class.forName(module).getConstructor(types);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object[] args = new Object[] { moduleName, owner };
		try {
			result = (NslModule) cons.newInstance(args);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
