package support;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Configuration {
	private final static String PROP_FILE = "config.properties";
	private static Properties configuracion=new Properties(); 
	static {
		FileInputStream in;
		try {
			in = new FileInputStream(System.getProperty("user.dir")+File.separatorChar+PROP_FILE);
			try {
				configuracion.load(in);
				in.close();
			} catch (IOException e) {
				System.err.println("Configuration::Error al cargar el archivo de configuraci�n.");
			}
		} catch (FileNotFoundException e) {
			System.err.println("Configuration::No existe el archivo de configuraci�n.");
		}
	}
	
	public static String getString(String propertyName) {
		return configuracion.getProperty(propertyName);
	}
	
	public static int getInt(String propertyName) {
		return Integer.parseInt(configuracion.getProperty(propertyName));
	}

	public static double getDouble(String propertyName) {
		return Double.parseDouble(configuracion.getProperty(propertyName));
	}
	
	public static boolean getBoolean(String propertyName) {
		return Boolean.parseBoolean(configuracion.getProperty(propertyName));
	}
	
	public static Object getObject(String objectName) {
	    String objectClassName = Configuration.getString(objectName);
		// Reflexion para levantar la clase desde archivo de configuracion
		Class[] types = new Class[] {};
		Constructor cons = null;
		Object result=null;
		
		try {
			cons = Class.forName(objectClassName).getConstructor(types);
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
		Object[] args = new Object[] {}; // constructor sin argumentos
		try {
			result = cons.newInstance(args);
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
