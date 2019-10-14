package site.zhongkai.ask.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesUtils {

	private static Logger logger = LogManager.getLogger(PropertiesUtils.class);

	public static String getValue(String propertiesFile, String key){
		Properties properties = new Properties();
		InputStream resourceAsStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(propertiesFile);
		if (resourceAsStream == null) return null;
		try {
			properties.load(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8));
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return properties.getProperty(key).trim();
	}

}
