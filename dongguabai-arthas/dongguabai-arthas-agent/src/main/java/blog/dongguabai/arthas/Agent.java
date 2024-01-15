package blog.dongguabai.arthas;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent premain start");
        try {
            initializeAgent(agentArgs, inst);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("Agent premain end");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Agent agentmain start");
        try {
            initializeAgent(agentArgs, inst);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("Agent agentmain end");
    }

    private static void initializeAgent(String agentArgs, final Instrumentation inst) throws Exception {
        String[] args = agentArgs.split("#");
        final String className = args[0];
        final String methodName = args[1];
        System.out.println("className:" + className);
        System.out.println("methodName:" + methodName);
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        System.out.println(allLoadedClasses.length);

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className1, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                if (!className1.replace("/", ".").equals(className)) {
                    return null;
                }

                try {
                    ClassPool cp = ClassPool.getDefault();
                    CtClass cc = cp.get(className1.replace("/", "."));
                    CtMethod m = cc.getDeclaredMethod(methodName);
                    System.out.println("Transforming class: " + cc.getName());
                    System.out.println("Transforming method: " + m.getName());
                    m.insertBefore("System.out.println(\"Arguments: \" + java.util.Arrays.toString($args));");
                    m.insertAfter("System.out.println(\"Return: \" + $_);");
                    return cc.toBytecode();
                } catch (Exception e) {
                    System.out.println("Failed to transform class: " + className1);
                    e.printStackTrace();
                    return null;
                }
            }
        }, true);

        for (Class<?> clazz : allLoadedClasses) {
            System.out.println("Loaded class: " + clazz.getName());
            if (clazz.getName().equals(className)) {
                System.out.println("Retransforming class: " + clazz.getName());
                inst.retransformClasses(clazz);
            }
        }
    }
}