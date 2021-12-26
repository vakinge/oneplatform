package helper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;



public class MyBatisGeneratorTest {
	
	
	public static void main(String[] args)  throws Exception{
		doGenerate();
	}

	//mvn test -Dtest=helper.MyBatisGeneratorTest#testRun
	//@Test
	public void testRun() throws Exception {
		doGenerate();
	}

	private static void doGenerate() throws Exception {
		System.out.println("==========MyBatis Generate Begin..==============");
		URL resource = Thread.currentThread().getContextClassLoader().getResource("");
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		String configPath = resource.getPath() + "generatorConfig1.xml";
		System.out.println(">>configPath:" + configPath);
		File configFile = new File(configPath);
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("==========MyBatis Generate finished==============");
	}
}