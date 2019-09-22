package br.com.conrado.sharedlibrary.services

import br.com.itau.jdi.core.annotation.Component
import br.com.itau.jdi.core.annotation.Inject

@Component
class JenkinsService {

    @Inject
    ArtifactoryService artifactoryService
}
