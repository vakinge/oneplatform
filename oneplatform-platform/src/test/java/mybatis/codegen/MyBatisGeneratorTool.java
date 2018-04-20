/**
 * 
 */
package mybatis.codegen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 生成entity，mapper
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年7月27日
 */
public class MyBatisGeneratorTool {
	public static void main(String[] args) {
		URL resource = Thread.currentThread().getContextClassLoader().getResource("");
		System.out.println(resource.getPath());
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(resource.getPath() + "../../src/test/resources/generator/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = null;
		try {
			myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			myBatisGenerator.generate(null);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}