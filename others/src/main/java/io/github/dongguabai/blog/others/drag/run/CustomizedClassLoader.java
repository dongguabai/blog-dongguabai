package io.github.dongguabai.blog.others.drag.run;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author dongguabai
 * @date 2024-01-04 11:56
 */
public class CustomizedClassLoader extends URLClassLoader {

    public CustomizedClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            return findClass(name);
        } catch (ClassNotFoundException e) {
            return super.loadClass(name);
        }
    }
}