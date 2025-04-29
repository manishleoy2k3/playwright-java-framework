package data;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name = "userData")
    public static Object[][] getUserData() {
        return new Object[][]{
            {"alice", "developer"},
            {"bob", "tester"},
            {"eve", "analyst"}
        };
    }
}
