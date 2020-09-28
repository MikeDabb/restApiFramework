package pl.javastart.restassured.main.properties;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:EnvironmentConfig.properties")
public interface EnvironmentConfig extends Config{

    @Key("BASE_URI")
    @DefaultValue("https://swaggerpetstore.przyklady.javastart.pl")
    String baseUri();

    @Key("BASE_PATH")
    @DefaultValue("v2")
    String basePath();
}
