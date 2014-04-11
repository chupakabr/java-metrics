package co._7bit.dev.java.javametrics.analyse;

import java.util.Collection;

/**
 * Created by myltik on 11/04/2014.
 */
public interface ClassAnalyser {

    /**
     * Do code analysis on specified context
     */
    void analyse(AnalysisContext context);

    /**
     * @return Calculated total number of methods
     */
    int totalNumberOfMethods();

    /**
     * @return Found classes' descriptions
     */
    Collection<ClassDescription> classes();
}
