package co._7bit.dev.java.javametrics.analyse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Non thread-safe.
 * TODO Error handling
 *
 * Created by myltik on 11/04/2014.
 */
public class SimpleAnalyser implements ClassAnalyser {

    private List<ClassDescription> classes;

    @Override
    public void analyse(AnalysisContext context) {
        try {
            classes = new ArrayList<ClassDescription>();
            for (Class clazz : context.getClasses()) {
                classes.add(new ClassDescription(clazz));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int totalNumberOfMethods() {
        int cnt = 0;
        for (ClassDescription clazz : classes()) {
            cnt += clazz.methodsNumber();
        }
        return cnt;
    }

    @Override
    public Collection<ClassDescription> classes() {
        return classes;
    }
}
