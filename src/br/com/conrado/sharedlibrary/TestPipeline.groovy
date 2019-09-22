package br.com.conrado.sharedlibrary

import br.com.conrado.sharedlibrary.services.ArtifactoryService
import br.com.itau.jdi.core.annotation.Configurations
import br.com.itau.jdi.core.injection.Injector
import br.com.itau.jdi.core.reflection.ReflectionUtils
import br.com.itau.jdi.core.service.ServiceRegistry

import javax.management.ReflectionException
import javax.print.DocFlavor

@Grapes([
        @Grab(group = 'br.com.itau', module = 'jdi', version = '0.0.1-SNAPSHOT'),
        @Grab(group = 'commons-primitives', module = 'commons-primitives', version = '1.0')
])

@Configurations(packageScan = "br.com.conrado.sharedlibrary", properties = "application.properties")
class TestPipeline {

    def steps

    TestPipeline(def steps) {
        this.steps = steps
    }

    public <T> void teste() {

        Injector.newInstance().configure(this)
        /*       Class<?> clazz = null
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

*/
        try {
            String path = "br/com/conrado/sharedlibrary".replace('.', '/');
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources(path);
            HashSet dirs = new HashSet();

            while (resources.hasMoreElements()) {
                URL resource = (URL) resources.nextElement();
                dirs.add(new File(resource.getFile()));
            }

            Set<Class<?>> classes = new HashSet();
            Iterator var6 = dirs.iterator();

            while (var6.hasNext()) {
                File directory = (File) var6.next();
                steps.println "Diretorio: ${directory.getCanonicalFile()}"
                findClasses(directory, path);
            }

        } catch (IOException var8) {
            throw new br.com.itau.jdi.core.reflection.exception.ReflectionException(var8);
        }

        /* Set<Class<?>> classess = ReflectionUtils.classesDiscovery(this.getClass().getClassLoader(), "br.com.conrado.sharedlibrary")
         for (Class<T> clazz : classess) {
             steps.println("Class ---> ${clazz}")
         }*/

        Configurations c = this.getClass().getAnnotation(Configurations.class);
        steps.print("Annotation Configurations: ${c}")
        Injector.getInjector().configure(this)

        ArtifactoryService artifactoryService = ServiceRegistry.getService(ArtifactoryService.class)
        steps.println "#####################\n\n\tAplication name: ${artifactoryService.appName}\n\n\tAplication version: ${artifactoryService.appVersion}"
    }

    private void findClasses(File directory, String packageName) throws br.com.itau.jdi.core.reflection.exception.ReflectionException {
        Set<Class<?>> classes = new HashSet();
        if (!directory.exists()) {
            return
        } else {
            File[] files = directory.listFiles();
            File[] var4 = files;
            int var5 = files.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                File file = var4[var6];
                String fileName = file.getName();
                steps.println "File path: ${file.getCanonicalFile()}"
                if (file.isDirectory()) {
                    assert !fileName.contains(".");

                    findClasses(file, packageName + "." + fileName)
                } else if (file.getName().endsWith(".class")) {
                    steps.println "File path: ${file.getCanonicalFile()}"
                } else if (file.getName().endsWith(".groovy")) {
                    steps.println "Load File path: ${file.getCanonicalFile()}"
                    URL url = new File("/var/jenkins_home/jobs/TESTE/builds/66/libs/shared-library/src/").toURI().toURL()
                    URL[] urlArray = new URL[1]
                    urlArray[0] = url
                    ClassLoader cl = new URLClassLoader(urlArray);
                    Class cls = cl.loadClass("br.com.conrado.sharedlibrary" + '.' + fileName.substring(0, fileName.length() - 7));
                    steps.println "Class loaded: ${cls}"
                    //  classes.add(loadClass(packageName + '.' + fileName.substring(0, fileName.length() - 7)));
                }
            }

        }
    }
}
