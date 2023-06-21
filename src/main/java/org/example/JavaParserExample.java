package org.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import org.slf4j.Logger;

import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JavaParserExample {
    private static final String FILE_PATH = "D:\\Temp\\EDMBomItmDaoImpl2.java";

    public static void main(String args[]) {
        CompilationUnit cu = null;
        try {
            cu = StaticJavaParser.parse(new File(FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CompilationUnit cuChanged = removeAdfDependencies(cu);

        CompilationUnit cuModified = modifyFunctionDAOAndQueryString(cuChanged);


        saveJavaFile(cuModified, "D:\\Temp\\EDMBomItmDaoImplChanged2.java");

    }

    private static CompilationUnit removeAdfDependencies(CompilationUnit cu) {
        cu.findAll(ImportDeclaration.class)
                .stream()
                .filter(importDecl -> importDecl.getName().asString().contains("adf"))
                .forEach(cu::remove);

        cu.findAll(ImportDeclaration.class)
                .stream()
                .filter(importDecl -> importDecl.getName().asString().contains("LogUtil"))
                .forEach(importDecl -> importDecl.setName("org.slf4j.LogUtil"));

        cu.findAll(ImportDeclaration.class)
                .stream()
                .filter(importDecl -> importDecl.getName().asString().equals("StringUtils"))
                .forEach(importDecl -> importDecl.setName("org.apache.commons.lang3.StringUtils"));

        cu.addImport("org.slf4j.Logger");

        // Add logger for each class
        cu.findAll(ClassOrInterfaceDeclaration.class)
                .forEach(classDecl -> {
                    Expression expression = StaticJavaParser.parseExpression("LoggerFactory.getLogger(\"Omega:"+classDecl.getName().asString()+"\")");
                    VariableDeclarator variables = new VariableDeclarator();
                    variables.setName("LOGGER");
                    variables.setType(Logger.class);
                    variables.setInitializer(expression);
                    FieldDeclaration filed = new FieldDeclaration().addVariable(variables);
                    classDecl.getMembers().add(0,filed);

                    // Replace buildCriteria method calls
                    classDecl.accept(new ModifierVisitor<Void>() {
                        @Override
                        public Visitable visit(MethodCallExpr methodCallExpr, Void arg) {
                            if (methodCallExpr.getName().asString().equals("buildCriteria")) {
                                String query = convertBuildCriteriaToSQLQuery(methodCallExpr);
                                return new NameExpr(query);
//                                super.visit()
//                                return null;
                            }
                            return super.visit(methodCallExpr, arg);
                        }
                    }, null);
                });

        return cu;
    }

//    private static CompilationUnit addLoggerStatementForClasses(CompilationUnit cu) {
//        List<ClassDefinition> methods = cu.findAll(ClassDefinition.class);
//        for (MethodDeclaration method : methods) {
//        }
//    }

    private static CompilationUnit modifyFunctionDAOAndQueryString(CompilationUnit cu) {
        List<MethodDeclaration> methods = cu.findAll(MethodDeclaration.class);
        for (MethodDeclaration method : methods) {

            //step 1
            // Add the three parameters to the method signature
            method.addParameter("Map<String, Object>", "listOfMap");
            method.addParameter("String", "fldPrefix");
            method.addParameter("Map<String, Object>", "dataMap");


            //step 2
            // Remove all occurrences of the queryString generation statement except the return statement

            BlockStmt body = method.getBody().orElse(null);
//            if (body != null) {
//                List<Statement> statements = body.getStatements();
//                for (Statement statement : statements) {
//                    if (statement.toString().contains("queryString")) {
//                        statements.remove(statement);
//                        break; // Remove only the first occurrence
//                    }
//                }
//            }

//            if (body != null) {
//                List<Statement> statements = body.getStatements();
//                List<Statement> statementsToRemove = new ArrayList<>();
//                boolean returnStatementFound = false;
//
//                for (Statement statement : statements) {
//                    if (statement.toString().contains("queryString")) {
//                        if (statement.isReturnStmt()) {
//                            returnStatementFound = true;
//                        } else {
//                            statementsToRemove.add(statement);
//                        }
//                    }
//                }
//                statements.removeAll(statementsToRemove);
//
//            }


            // Step 3: Modify the getting result part
            method.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
                String methodName = methodCallExpr.getNameAsString();
                if (methodName.equals("queryForObject") || methodName.equals("queryForList")) {
                    methodCallExpr.setArgument(0, new NameExpr("fldPrefix"));
                    methodCallExpr.setArgument(1, new NameExpr("dataMap"));
                    methodCallExpr.addArgument("test");
                    ClassOrInterfaceDeclaration classDeclaration = cu.findFirst(ClassOrInterfaceDeclaration.class).orElse(null);
                    String className = classDeclaration != null ? classDeclaration.getNameAsString() : null;
                    methodCallExpr.setArgument(3, new NameExpr(className + ".class"));
                    methodCallExpr.addArgument("listOfMap");
                    methodCallExpr.setArgument(2, new NameExpr("\"\" "));

                } else if (methodName.startsWith("AdfViewHelper.queryForList")) {
                    methodCallExpr.setName(methodName.substring("AdfViewHelper.".length()));
                    methodCallExpr.removeScope();
                    methodCallExpr.setArgument(0, new NameExpr("fldPrefix"));
                    methodCallExpr.setArgument(1, new NameExpr("dataMap"));
                    methodCallExpr.setArgument(2, new NameExpr("test"));
                    ClassOrInterfaceDeclaration classDeclaration = cu.findFirst(ClassOrInterfaceDeclaration.class).orElse(null);
                    String className = classDeclaration != null ? classDeclaration.getNameAsString() : null;
                    methodCallExpr.setArgument(3, new NameExpr(className + ".class"));
                    methodCallExpr.addArgument("listOfMap");
                } else if (methodName.equals("fetchByKey")) {
                    methodCallExpr.setName("queryForObject");
                    NodeList<Expression> arguments = methodCallExpr.getArguments();
                    methodCallExpr.setArgument(0, arguments.get(0));
                    methodCallExpr.setArgument(1, arguments.get(1));
                    methodCallExpr.addArgument(arguments.get(2));
                    ClassOrInterfaceDeclaration classDeclaration = cu.findFirst(ClassOrInterfaceDeclaration.class).orElse(null);
                    String className = classDeclaration != null ? classDeclaration.getNameAsString() : null;
                    methodCallExpr.setArgument(3, new NameExpr(className + ".class"));
                    methodCallExpr.addArgument("listOfMap");
                }
            });

            //Step 4
            // Replace log statements with logger.info()
            method.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
                if (methodCallExpr.toString().startsWith("LogUtil.getCoreLog().info(")) {
                    methodCallExpr.setScope(new NameExpr("logger"));
                }
            });

            // step 5
            // Remove the Map.Entry<> from the return type
            method.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
                AtomicInteger i = new AtomicInteger();
                methodCallExpr.getArguments().forEach(argument -> {
                    if (argument.toString().contains("Map.Entry")) {
                        String newExpression = argument.toString().replace("Map.Entry", "Map");
                        Expression expression = StaticJavaParser.parseExpression(newExpression);
                        methodCallExpr.setArgument(i.get(), expression);
                        System.out.println(methodCallExpr.getArguments().toString());
                    }
                    i.set(i.get() + 1);
                });
            });

//            method.findAll(MethodDeclaration.class).forEach(innerMethodDeclaration -> {
//                innerMethodDeclaration.findAll(MethodCallExpr.class).forEach(methodCallExpr -> {
//                    if (methodCallExpr.getTypeArguments().toString().contains("Map.Entry")) {
//                        NodeList<Type> ty = methodCallExpr.getTypeArguments().get();
//                        if (ty.contains("Map.Entry")) {
//                            ty.toString().replace("Map.Entry", "Map");
//                        }
//                        methodCallExpr.setTypeArguments(ty);
//                    }
//                });
//            });
        }
        return cu;
    }

    private static String convertBuildCriteriaToSQLQuery(MethodCallExpr methodCallExpr) {
        // Implement the conversion logic here according to your requirements
        return "SELECT * FROM table WHERE column = value";
    }

    private static void saveJavaFile(CompilationUnit cu, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(cu.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}