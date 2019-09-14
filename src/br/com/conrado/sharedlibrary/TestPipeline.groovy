package br.com.conrado.sharedlibrary
@Grapes([
        @Grab(group = 'br.com.itau', module = 'jdi', version = '0.0.1'),
        @Grab(group = 'commons-primitives', module = 'commons-primitives', version = '1.0')
])
import br.com.itau.jdi.core.service.ServiceRegistry
import br.com.conrado.sharedlibrary.services.ArtifactoryService

class TestPipeline {

    def steps

    TestPipeline(def steps) {
        this.steps = steps
    }

    public <T> void teste() {

        //special-key
        /* steps.withCredentials([steps.usernamePassword(credentialsId: 'git-pass', usernameVariable: 'USERPASS', passwordVariable: 'PASS')]) {
             String teste = steps.sh (returnStdout: true, script: 'export TESTE=`$USERPASS` && echo "este é o usuario: $USERPASS $TESTE" &&  echo $TESTE > file.txt && cat file.txt')
             steps.sh 'echo \"este é a senha: $PASS ${teste}\"'
             steps.println "Senha: ${teste}"
         }

         steps.withCredentials([steps.usernamePassword(credentialsId: 'special-key', usernameVariable: 'USERPASS', passwordVariable: 'PASS')]) {
             String teste = steps.sh (returnStdout: true, script: 'export TESTE=$PASS && echo "este é o usuario: $USERPASS $TESTE" &&  echo "$TESTE" > file.txt && echo "blac bla">> file.txt && cat file.txt')
             steps.sh 'echo \"este é a senha: $PASS ${teste}\"'
             steps.println "Senha: ${teste}"

             String abc = steps.sh (returnStdout: true, script: 'export PWD=$PASS\necho $PWD >> file.txt\necho $PWD\ncat file.txt')
             steps.println "Senha abc: ${abc}"

         }

         String abc = steps.sh (returnStdout: true, script: 'cat file.txt')
         steps.println "Senha abc: ${abc}"*/

        Class<?> clazz = null
        steps.println "CLass --> ${new ArtifactoryService().class.getCanonicalName()}"
        //Class<?> clazz = Class.forName(new ArtifactoryService().class.getCanonicalName())
        //steps.println "CLass --> ${clazz}"
        clazz = this.getClass().getClassLoader().loadClass("br.com.conrado.sharedlibrary.services.ArtifactoryService");
        steps.println "CLass --> ${clazz}"
        clazz = ServiceRegistry.discoveryClass(this.getClass().getClassLoader(), "br.com.conrado.sharedlibrary.services.ArtifactoryService")
        steps.println "CLass --> ${clazz}"

        Enumeration<URL> enumeration = this.getClass().getClassLoader().getResources("br/com/conrado/sharedlibrary/services")
        steps.println "enumeration --> ${enumeration}"

        List<URL> enums = enumeration.toList()
        steps.println "enumeration --> ${enums.size()}"
        steps.println "enumeration --> ${enums.get(0)}"

        File file = new File(enums.get(0).toURI())
        steps.println "FIle --> ${file}"

        steps.println "Files --> \n ${file.listFiles()}"


        Object[] objects = ServiceRegistry.servicesDiscovery(this.getClass().getClassLoader(), "br.com.conrado.sharedlibrary")
        steps.println "objects --> \n ${objects}"

    }
}
