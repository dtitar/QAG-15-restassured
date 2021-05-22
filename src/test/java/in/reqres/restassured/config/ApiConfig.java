package in.reqres.restassured.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:api.properties"
})
public interface ApiConfig extends Config {

    @Key("api.base.uri")
    String baseUri();

    @Key("api.endpoints.resource")
    String resourceEndpoint();

    @Key("api.endpoints.register")
    String registerEndpoint();

    @Key("api.endpoints.login")
    String loginEndpoint();

    @Key("api.endpoints.users")
    String usersEndpoint();
}
