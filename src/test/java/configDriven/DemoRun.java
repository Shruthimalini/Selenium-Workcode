package configDriven;

import amazonautomate.BaseTest;

public class DemoRun extends BaseTest {

    public static void main(String[] args) {
        DemoRun test = new DemoRun();
        try {
            test.initializeDriver();   
            test.goTo();               
            Thread.sleep(3000);        
            test.quitDriver();         
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
