package co._7bit.dev.java.javametrics.analyse;

import java.io.IOException;
import java.util.List;

/**
 * Created by myltik on 11/04/2014.
 */
public interface AnalysisContext {

    /**
     * @return Found classes in the context
     */
    List<Class> getClasses() throws ClassNotFoundException,
            IllegalAccessException, NoSuchFieldException, IOException;

}
