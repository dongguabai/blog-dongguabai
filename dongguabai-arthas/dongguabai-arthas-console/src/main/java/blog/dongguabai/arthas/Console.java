package blog.dongguabai.arthas;

import com.sun.tools.attach.VirtualMachine;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dongguabai
 * @date 2024-01-14 00:06
 */
public class Console {

    public static void main(String[] args) {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();
            Set<String> pids = getRunningJavaProcesses();
            interactWithUser(reader, pids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<String> getRunningJavaProcesses() throws Exception {
        Set<String> pids = new HashSet<>();
        Process process = Runtime.getRuntime().exec("jps");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            System.out.println("Currently running Java processes：");
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                String pid = line.split(" ")[0];
                pids.add(pid);
            }
        }
        return pids;
    }

    private static void interactWithUser(LineReader reader, Set<String> pids) {
        while (true) {
            String input = reader.readLine("> ");
            if ("quit".equals(input)) {
                break;
            } else if (pids.contains(input)) {
                handleUserInput(input, reader);
            } else {
                System.out.println("Error: The input is not a PID of a currently running Java process");
            }
        }
    }

    private static void handleUserInput(String pid, LineReader reader) {
        try {
            attachToProcess(pid);
            System.out.println("Attach success. Please input the class and method (format: com.example.MyClass#myMethod):");
            String classAndMethod = reader.readLine("> ");
            String[] parts = classAndMethod.split("#");
            if (parts.length == 2) {
                String className = parts[0];
                String methodName = parts[1];
                applyAgent(pid, className, methodName);
            } else {
                System.out.println("Error: Invalid input format");
            }
        } catch (Exception e) {
            System.out.println("Error: Failed to attach to the process");
            e.printStackTrace();
        }
    }

    private static void attachToProcess(String pid) throws Exception {
        /*VirtualMachine vm = VirtualMachine.attach(pid);
        System.out.println("Attach success");
        vm.detach();*/
    }

    private static void applyAgent(String pid, String className, String methodName) throws Exception {
        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent("/Users/dongguabai/IdeaProjects/gitee/blog-dongguabai/dongguabai-arthas/dongguabai-arthas-agent/target/dongguabai-arthas-agent-1.0-SNAPSHOT-jar-with-dependencies.jar", className + "#" + methodName);
        vm.detach();
        System.out.println("Agent applied to " + className + "#" + methodName);
    }
}