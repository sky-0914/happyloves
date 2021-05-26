package cn.happyloves.java.agent;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * 包含如下两个方法中的任一个:
 * 【1】premain(String agentArgs, Instrumentation inst)
 * 【2】premain(String agentArgs)
 * 其中，【1】和【2】同时存在时，【1】会优先被执行，而【2】则会被忽略。
 *
 * @author zc
 * @date 2021/5/25 15:33
 */
public class MyAgent {
    /**
     * 【1】
     *
     * @param agentArgs 参数
     * @param inst      Instrumentation
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("1.my-agent: " + agentArgs);
        String classNameArg = null;
        String methodNameArg = null;
        if (agentArgs != null) {
            final String[] args = agentArgs.split(",");
            classNameArg = args[0];
            methodNameArg = args.length > 1 ? args[1] : null;
        }
        // 添加 Transformer
        ClassFileTransformer transformer = new MyClassFileTransformer(classNameArg, methodNameArg);
        inst.addTransformer(transformer);
    }

    /**
     * 【2】
     *
     * @param agentArgs 参数
     */
    public static void premain(String agentArgs) {
        System.out.println("【2】my-agent: " + agentArgs);
    }

    /**
     * 实现ClassFileTransformer接口
     */
    static class MyClassFileTransformer implements ClassFileTransformer {
        private final String className;
        private final String methodName;

        public MyClassFileTransformer(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
        }

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            //className 是以/分隔的，需转成.
            String currentClassName = className.replaceAll("/", ".");
            if (!currentClassName.equals(this.className)) {
                //如果不是我们需要拦截的类，直接放过
                return classfileBuffer;
            } else {
                System.out.println("transform: [" + currentClassName + "]");
                try {
                    if (this.methodName != null && !"".equals(this.methodName)) {
                        return this.assignMethod(this.className, this.methodName);
                    } else {
                        return this.allMethod(this.className);
                    }
                } catch (NotFoundException | CannotCompileException | IOException e) {
                    e.printStackTrace();
                }
            }
            return classfileBuffer;
        }

        /**
         * 指定类和方法
         *
         * @param className  类名
         * @param methodName 方法名
         * @return 处理后字节码
         * @throws NotFoundException      异常
         * @throws CannotCompileException 异常
         * @throws IOException            异常
         */
        private byte[] assignMethod(String className, String methodName) throws NotFoundException, CannotCompileException, IOException {
            final ClassPool classPool = ClassPool.getDefault();
            final CtClass ctClass = classPool.get(className);
            final CtMethod declaredMethod = ctClass.getDeclaredMethod(methodName);
            declaredMethod.insertBefore("{ System.out.println(\"start\"); }");
            declaredMethod.insertAfter("{ System.out.println(\"end\"); }");
            return ctClass.toBytecode();
        }

        /**
         * 指定该类下所有方法
         *
         * @param className 类型
         * @return 处理后字节码
         * @throws NotFoundException      异常
         * @throws CannotCompileException 异常
         * @throws IOException            异常
         */
        private byte[] allMethod(String className) throws NotFoundException, CannotCompileException, IOException {
            final ClassPool classPool = ClassPool.getDefault();
            final CtClass ctClass = classPool.get(className);
            final CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
            for (CtMethod declaredMethod : declaredMethods) {
                String methodName = declaredMethod.getName();
                if (methodName.equalsIgnoreCase("main")) { // 不提升main方法
                    continue;
                }

                final StringBuilder source = new StringBuilder();
                source.append("{")
                        .append("long start = System.nanoTime();\n") // 前置增强: 打入时间戳
                        .append("$_ = $proceed($$);\n") // 保留原有的代码处理逻辑
                        .append("System.out.print(\"method:[")
                        .append(methodName)
                        .append("]\");\n")
                        .append("System.out.println(\" cost:[\" +(System.nanoTime() -start)+ \"ns]\");") // 后置增强
                        .append("}");
                ExprEditor editor = new ExprEditor() {
                    @Override
                    public void edit(MethodCall methodCall) throws CannotCompileException {
                        methodCall.replace(source.toString());
                    }
                };
                declaredMethod.instrument(editor);
            }
            return ctClass.toBytecode();
        }
    }

}
