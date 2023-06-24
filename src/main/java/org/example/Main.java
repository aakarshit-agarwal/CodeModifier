package org.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;

import static org.example.JavaParser.saveJavaFile;

public class Main {
    public static void main(String[] args) {
        final String FILE_PATH = "D:\\Temp\\EDMBomItmDaoImpl2.java";

        CompilationUnit cu = null;
        try {
            cu = StaticJavaParser.parse(new File(FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert cu != null;
        CompilationUnit cuChanged = JavaParser.removeAdfDependencies(cu);

        CompilationUnit cuModified = JavaParser.modifyFunctionDAOAndQueryString(cuChanged);

        saveJavaFile(cuModified, "D:\\Temp\\EDMBomItmDaoImplChanged.java");

    }
}

