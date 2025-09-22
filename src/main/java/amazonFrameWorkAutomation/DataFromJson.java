package amazonFrameWorkAutomation;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataFromJson {

	public static List<HashMap<String, String>> getJsonDataToMap() throws IOException {
		// Read JSON file into String
		String jsonContent = FileUtils.readFileToString(
				new File(System.getProperty("user.dir") + "\\src\\test\\resources\\data.json"), "UTF-8");

		// Parse JSON content (Converts String to Object)
		ObjectMapper mapper = new ObjectMapper();

		// Convert JSON string to List of HashMaps
		 List<HashMap<String, String>> data=mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
			});
		return data;
	}
}
