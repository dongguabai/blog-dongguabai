package io.github.dongguabai.blog.others.drag.run;

import javax.swing.*;

/**
 * @author dongguabai
 * @date 2024-01-03 12:38
 */
import javax.tools.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.*;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class DragRunDemo1 extends JFrame {

    public DragRunDemo1() {
        JTextArea textArea = new JTextArea();
        textArea.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                evt.acceptDrop(DnDConstants.ACTION_COPY);
                try {
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    droppedFiles.forEach(file -> compileAndRun(file, textArea));
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

    private void compileAndRun(File file, JTextArea textArea) {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, file.getPath());
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.getParentFile().toURI().toURL()});
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DragRunDemo1::new);
    }
}