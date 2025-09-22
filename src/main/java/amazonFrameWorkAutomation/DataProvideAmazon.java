package amazonFrameWorkAutomation;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;

public class DataProvideAmazon {

    @DataProvider(name = "jsonData")
    public static Object[][] provideJsonData() throws Exception {

        DataFromJson jsonReader = new DataFromJson();
        List<HashMap<String, String>> dataList = jsonReader.getJsonDataToMap();

        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }
        return data;
    }
}
