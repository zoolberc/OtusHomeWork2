package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface VariableConfig extends Config{
    @Key("urlOtus")
    String urlOtus();

    @Key("emailOtus")
    String emailOtus();

    @Key("passwordOtus")
    String passwordOtus();

    @Key("firstName")
    String firstName();

    @Key("firstNameLatin")
    String firstNameLatin();

    @Key("lastName")
    String lastName();

    @Key("lastNameLatin")
    String lastNameLatin();

    @Key("blogName")
    String blogName();

    @Key("DOB")
    String DOB();

    @Key("phoneNumber")
    String phoneNumber();
}
