package co._7bit.dev.java.agent;

import java.lang.instrument.Instrumentation;
import java.util.UUID;

/**
 * Non thread-safe - bad :)
 *
 * Created by myltik on 11/04/2014.
 */
public class InstrumentHook {

    public static void premain(String agentArgs, Instrumentation inst) {
        if (agentArgs != null) {
            System.getProperties().put(AGENT_ARGS_KEY, agentArgs);
        }
        System.getProperties().put(INSTRUMENTATION_KEY, inst);
    }

    public static Instrumentation getInstrumentation() {
        return (Instrumentation) System.getProperties().get(INSTRUMENTATION_KEY);
    }

    // Needn't be a UUID - can be a String or any other object that implements equals().
    private static final Object AGENT_ARGS_KEY = UUID.fromString("7918415B-1BAF-4188-BBA1-37E32A8DBC4E");
    private static final Object INSTRUMENTATION_KEY = UUID.fromString("6B1606F8-7EF8-472D-AC60-F812F3CBA680");

}
