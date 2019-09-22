package br.com.conrado.sharedlibrary.services

import br.com.itau.jdi.core.annotation.Component
import br.com.itau.jdi.core.annotation.Value

@Component
class ArtifactoryService {

    @Value(key = "application.name")
    String appName

    String appVersion

    ArtifactoryService(@Value(key = "application.version") String appVersion) {
        this.appVersion = appVersion
    }


}
