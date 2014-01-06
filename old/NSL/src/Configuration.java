import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

}
