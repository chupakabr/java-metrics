package co._7bit.dev.java.javametrics.analyse;

import co._7bit.dev.java.agent.InstrumentHook;
import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Created by myltik on 11/04/2014.
 */
public class DirectoryAnalysisContext implements AnalysisContext {

    private final File path;
    private final File compiledCodeDirectory;

    private static final String[] ALLOWED_EXTENSIONS;
    static {
        ALLOWED_EXTENSIONS = new String[] {"java"};
    }

    /**
     * @param file                     File or directory
     */
    public DirectoryAnalysisContext(File file) {
        this(file, file.isDirectory() ? file : file.getParentFile());
    }

    /**
     * @param file                     File or directory
     * @param compiledCodeDirectory    Path to directory where compiled code will be placed
     * @todo Implement support of additional compiledCodeDirectory parameter, now all the sources will be compiled in
     * the same directory where source files are placed
     */
    public DirectoryAnalysisContext(File file, File compiledCodeDirectory) {
        this.path = file;
        this.compiledCodeDirectory = compiledCodeDirectory;
    }

    /**
     * Non-cached implementation. Always re-read the directory.
     * @return Found classes in the context
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     * @throws MalformedURLException
     */
    @Override
    public List<Class> getClasses() throws ClassNotFoundException,
            IllegalAccessException, NoSuchFieldException, IOException
    {
        List<Class> classes = new ArrayList<Class>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (path.isDirectory()) {
            // Process directory
            Iterator<File> it = FileUtils.iterateFiles(path, ALLOWED_EXTENSIONS, true);
            File sourceFile;
            while (it.hasNext()) {
                sourceFile = it.next();

                // Compile source file.
                compiler.run(null, null, null, sourceFile.getPath());
            }
        } else {
            // Process single file
            compiler.run(null, null, null, path.getPath());
        }

        // Load and instantiate compiled class.
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { compiledCodeDirectory.toURI().toURL() }, null);

//        System.out.println("Loaded class 1: " + classLoader.loadClass("co._7bit.dev.java.javametrics.analyse.DirectoryAnalysisContext"));
        System.out.println("Loaded class 2: " + classLoader.loadClass("Perm"));

        System.out.println("Resources:");
        Enumeration<URL> resources = classLoader.getResources("");
        while (resources.hasMoreElements()) {
            URL res = resources.nextElement();
            System.out.println(" * " + res);
        }

        // Hack: wait for GC
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get a list of loaded classes
        short type = 2;
        Collection<Class> loadedClasses;
        if (type == 1) {
            // Use Reflections lib
            Reflections reflections = new Reflections("co._7bit");
            for (Class clazz : reflections.getSubTypesOf(Object.class)) {
                if (clazz.equals(Object.class)) {
                    System.out.println("WARN Skip class [" + clazz.getName() + "]");
                    continue;
                }

                try {
                    classLoader.loadClass(clazz.getName());
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    System.out.println("WARN Class [" + clazz.getName() + "] belongs to another class loader, skipping");
                }
            }
        } else if (type == 2) {
            // Use Instrument agent
            Instrumentation instrumentation = InstrumentHook.getInstrumentation();
            for (Class clazz : instrumentation.getInitiatedClasses(classLoader)) {
//            for (Class clazz : instrumentation.getAllLoadedClasses()) {
                if (clazz.equals(Object.class)) {
                    continue;
                }

                try {
                    classLoader.loadClass(clazz.getName());
                    classes.add(clazz);
                } catch (ClassNotFoundException e) {
                    System.out.println("WARN Class [" + clazz.getName() + "] belongs to another class loader, skipping");
                }
            }
        } else {
            // Oldschool method
            Field f = ClassLoader.class.getDeclaredField("classes");
            f.setAccessible(true);

            loadedClasses = (Vector<Class>) f.get(classLoader);
            classes.addAll(loadedClasses);
        }

        System.out.println("Loaded classes for directory '" + compiledCodeDirectory.getCanonicalPath() + "': " + classes);

        return classes;
    }
}
