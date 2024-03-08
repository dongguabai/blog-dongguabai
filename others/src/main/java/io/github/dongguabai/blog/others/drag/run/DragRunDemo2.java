package io.github.dongguabai.blog.others.drag.run;

import javax.swing.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author dongguabai
 * @date 2024-01-03 19:24
 */
public class DragRunDemo2 extends JFrame {

    public DragRunDemo2() {
        JTextArea textArea = new JTextArea();
        textArea.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        if (file.getName().endsWith(".java")) {
                            compileAndRunJava(file, textArea);
                        } else if (file.getName().endsWith(".jar")) {
                            String[] parts = file.getName().split("#");
                            if (parts.length > 1) {
                                String packageName = parts[1].replace("_", ".").replace(".jar", "");
                                runJar(file, textArea, packageName);
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(textArea);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dongguabai-代码拖拽执行"));
        panel.add(scrollPane, BorderLayout.CENTER);
        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 900);
        this.setVisible(true);
    }

    private void compileAndRunJava(File file, JTextArea textArea) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, file.getPath());
//            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
            CustomizedClassLoader classLoader = new CustomizedClassLoader(new URL[]{file.getParentFile().toURI().toURL()}, ClassLoader.getSystemClassLoader().getParent());
            String className = file.getName().replace(".java", "");
            Class<?> cls = Class.forName(className, true, classLoader);
            Method method = cls.getDeclaredMethod("main", String[].class);
            PrintStream originalOut = System.out;
            PrintStream out = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) {
                    textArea.append(String.valueOf((char) b));
                }
            });
            //重定向
            System.setOut(out);
            textArea.append("->" + file.getName() + ":\n");
            method.invoke(null, (Object) new String[]{});
            textArea.append("\n");
            System.setOut(originalOut);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void runJar(File file, JTextArea textArea, String packageName) {
        try {
//            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()});
            CustomizedClassLoader classLoader = new CustomizedClassLoader(new URL[]{file.toURI().toURL()}, ClassLoader.getSystemClassLoader().getParent());
            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().replace("/", ".").replace(".class", "");
                    if (className.startsWith(packageName)) {
                        Class<?> cls = Class.forName(className, true, classLoader);
                        for (Method method : cls.getDeclaredMethods()) {
                            //  if (method.getName().equals("main") && Modifier.isStatic(method.getModifiers())) {
                            if (method.getName().equals("main")) {
                                PrintStream originalOut = System.out;
                                PrintStream out = new PrintStream(new OutputStream() {
                                    @Override
                                    public void write(int b) {
                                        textArea.append(String.valueOf((char) b));
                                    }
                                });
                                System.setOut(out);
                                textArea.append("->" + file.getName() + ", class " + className + ":\n");
                                method.invoke(null, (Object) new String[]{});
                                textArea.append("\n");
                                System.setOut(originalOut);
                            }
                        }
                    }
                }
            }

            jarFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DragRunDemo2::new);
    }
}