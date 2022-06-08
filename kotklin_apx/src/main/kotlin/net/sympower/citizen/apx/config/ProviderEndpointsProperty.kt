package net.sympower.citizen.apx.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("provider-endpoints")
class ProviderEndpointsProperty {
    var someProviderPost: String = ""
    var someProviderGet: String = ""
}