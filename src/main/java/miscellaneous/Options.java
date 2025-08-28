package miscellaneous;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Options {
	public static void main(String[] args) {
		ChromeOptions option=new ChromeOptions();
		 // 1. Set Proxy	
		Proxy proxy=new Proxy();
		proxy.setHttpProxy("192.168.1.100:8080");
		option.setCapability("proxy", proxy);
		 // 2. Add Chrome Extension (.crx file)
		 File extension = new File("E:\\Extensions\\AdBlock.crx");
         option.addExtensions(extension);
      // 3. Set Chrome Preferences
		Map<String,Object> prefs=new HashMap<String,Object>();
		prefs.put("download.default_directory","download path");
		option.setExperimentalOption("prefs", prefs);
		 // 4. Accept Insecure Certificates
		option.setAcceptInsecureCerts(true);
		WebDriver driver=new ChromeDriver(option);
		driver.get("https://expired.badssl.com/");
		System.out.println("Page Title: " + driver.getTitle());
		
	}

}