package io.github.dongguabai.apt;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author dongguabai
 * @date 2024-03-28 13:58
 */
@SupportedAnnotationTypes("io.github.dongguabai.apt.BuilderProperty")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(BuilderProperty.class)) {
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement method = (ExecutableElement) element;
                String className = ((TypeElement) method.getEnclosingElement()).getQualifiedName().toString();
                String methodName = method.getSimpleName().toString();
                String returnType = method.getReturnType().toString();
                String parameterType = method.getParameters().get(0).asType().toString();

                try {
                    JavaFileObject file = processingEnv.getFiler().createSourceFile(className + "Builder");
                    try (Writer writer = file.openWriter()) {
                        writer.write("public class " + className + "Builder {\n");
                        writer.write("    private " + returnType + " instance = new " + returnType + "();\n");
                        writer.write("    public " + className + "Builder " + methodName + "(" + parameterType + " value) {\n");
                        writer.write("        instance." + methodName + "(value);\n");
                        writer.write("        return this;\n");
                        writer.write("    }\n");
                        writer.write("    public " + returnType + " build() {\n");
                        writer.write("        return instance;\n");
                        writer.write("    }\n");
                        writer.write("}\n");
                    }
                } catch (IOException e) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                }
            }
        }
        return true;
    }
}