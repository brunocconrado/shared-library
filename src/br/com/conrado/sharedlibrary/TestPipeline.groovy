package br.com.conrado.sharedlibrary

class TestPipeline {

    def steps

    TestPipeline(def steps) {
        this.steps = steps
    }

    void teste() {

        //special-key
        steps.withCredentials([steps.usernamePassword(credentialsId: 'git-pass', usernameVariable: 'USERPASS', passwordVariable: 'PASS')]) {
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
        steps.println "Senha abc: ${abc}"

    }
}
