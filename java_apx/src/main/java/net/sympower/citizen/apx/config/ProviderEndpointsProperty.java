package net.sympower.citizen.apx.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("provider-endpoints")
public class ProviderEndpointsProperty {
    private String someProviderGet;
    private String someProviderPost;
}
