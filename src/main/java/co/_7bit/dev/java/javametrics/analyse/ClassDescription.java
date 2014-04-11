package co._7bit.dev.java.javametrics.analyse;

/**
 * Created by myltik on 11/04/2014.
 */
public class ClassDescription {

    private final String name;
    private final String uniqueName;
    private final int methodsNumber;

    /**
     * @param clazz    Class object
     */
    public ClassDescription(Class clazz) {
        this.name = clazz.getSimpleName();
        this.uniqueName = clazz.getName();
        this.methodsNumber = clazz.getDeclaredMethods().length;
    }

    /**
     * @param name             Class name
     * @param uniqueName       Class name including package
     * @param methodsNumber    Number of methods in the class
     */
    public ClassDescription(String name, String uniqueName, int methodsNumber) {
        this.name = name;
        this.uniqueName = uniqueName;
        this.methodsNumber = methodsNumber;
    }

    /**
     * @return Class name
     */
    public String name() {
        return name;
    }

    /**
     * @return Class name including package
     */
    public String uniqueName() {
        return uniqueName;
    }

    /**
     * @return Number of methods in the class
     */
    public int methodsNumber() {
        return methodsNumber;
    }

    @Override
    public String toString() {
        return name + "{'" +
                "uniqueName='" + uniqueName + '\'' +
                ", methodsNumber=" + methodsNumber +
                '}';
    }
}
