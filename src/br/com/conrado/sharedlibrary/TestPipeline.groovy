package br.com.conrado.sharedlibrary

import br.com.itau.jdi.core.service.ServiceRegistry

import java.util.jar.JarEntry
import java.util.jar.JarFile

class TestPipeline {

    def steps

    TestPipeline(def steps) {
        this.steps = steps
    }

    void teste() {

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


        //steps.println "${steps.class}\n\n"
        System.out.println("")

       /* Object[] classes = ServiceRegistry.servicesDecovery("br.com.conrado.sharedlibrary")
        for(Object obj : classes) {
            System.out.println("Classe: ${obj.class.canonicalName}\n")
           // steps.println "Classe: ${obj.class}\n"
        }*/

        JarFile jarFile = new JarFile(pathToJar);
        Enumeration<JarEntry> e = jarFile.entries();

        URL[] urls = { new URL("jar:file:" + "/var/jenkins_home/war/WEB-INF/lib/jdi-0.0.1-SNAPSHOT.jar"+"!/") };
        URLClassLoader cl = URLClassLoader.newInstance(urls);

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            Class c = cl.loadClass(className);
            steps.println "Classe: ${c.class.canonicalName}\n"
        }

    }
}
