package co._7bit.dev.java.javametrics;

import co._7bit.dev.java.javametrics.analyse.ClassAnalyser;
import co._7bit.dev.java.javametrics.analyse.ClassDescription;
import co._7bit.dev.java.javametrics.analyse.DirectoryAnalysisContext;
import co._7bit.dev.java.javametrics.analyse.SimpleAnalyser;

import java.io.File;
import java.io.IOException;

/**
 * Created by myltik on 11/04/2014.
 */
public class JavaMetrics {

    public static void main(String[] args) {
        if (args.length <= 0) {
            throw new IllegalArgumentException("Directory with source files must be set");
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            try {
                throw new IllegalArgumentException("File not found in path '" + file.getCanonicalPath() + "'");
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
        }

        ClassAnalyser analyser = new SimpleAnalyser();
        analyser.analyse(new DirectoryAnalysisContext(file));

        for (ClassDescription classDescription : analyser.classes()) {
            System.out.println("Class " + classDescription);
        }
        System.out.println("Total number of methods: " + analyser.totalNumberOfMethods());
    }
}
