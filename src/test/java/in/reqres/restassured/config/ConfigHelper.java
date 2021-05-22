package in.reqres.restassured.config;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    public static String getBaseUri() {
        return getConfig().baseUri();
    }

    public static String getResourceEndpoint() {
        return getConfig().resourceEndpoint();
    }

    public static String getRegisterEndpoint() {
        return getConfig().registerEndpoint();
    }

    public static String getUsersEndpoint() {
        return getConfig().usersEndpoint();
    }

    public static String getLoginEndpoint() {
        return getConfig().loginEndpoint();
    }

    private static ApiConfig getConfig() {
        return ConfigFactory.newInstance().create(ApiConfig.class);
    }
}
