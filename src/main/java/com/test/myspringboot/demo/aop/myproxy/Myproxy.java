package com.test.myspringboot.demo.aop.myproxy;

import org.apache.ibatis.executor.Executor;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Myproxy{

    public static String rn = "\r\n";

    /**
     * @param loader     类加载器，动态生成的java文件，需要加载到内存中
     * @param interfaces 接口数据，jdk动态代理需要继承同样的父类接口
     * @param h          代理类的增强方法
     * @return
     * @throws IllegalArgumentException
     */
    public static Object newProxyInstance(ClassLoader loader,
                                          Class<?>[] interfaces,
                                          DataInvocationHandler h)
            throws Exception {
        //生成代理类实例
        //1.生成一个java类
        StringBuilder proxyJava = new StringBuilder();
        proxyJava.append("package com.test.myspringboot.demo.aop.myproxy;").append(rn)
                 .append("import java.lang.reflect.Method;").append(rn)
                 .append("import com.test.myspringboot.demo.aop.DataService;").append(rn)
                 .append("public final class $proxy0 extends Myproxy implements DataService {").append(rn)
                 .append("private MyInvocationHandler h;").append(rn)
                 .append("public $proxy0(MyInvocationHandler handler) {").append(rn)
                 .append("this.h=handler;").append(rn).append("}").append(rn)
                 .append(createMethods(interfaces))
                 .append(rn).append("}");

        String filePath = "F:/work/idea/my-springboot/$proxy0.java";
        File proxyFile = new File(filePath);
        FileWriter fw = new FileWriter(proxyFile);
        fw.write(proxyJava.toString());
        fw.flush();
        fw.close();
        //2.文件生成后，动态编译java文件
        //获取java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //获取标准的文件系统
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        //获取刚刚生成的文件对象
        Iterable iterable = fileManager.getJavaFileObjects(filePath);
        //生成一个编译任务(.call真正编译),有多个参数
        compiler.getTask(null, fileManager,null, null, null, iterable).call();

        MyClassLoader loader1 = new MyClassLoader("F:/work/idea/my-springboot/");
//        loader.loadClass()
        Class proxyClass = loader1.findClass("$proxy0");
        //通过反射获取构造器，需要使用有参的构造方法
        Constructor constructor = proxyClass.getConstructor(MyInvocationHandler.class);
        return constructor.newInstance(h);
    }
    //获取接口所有的方法，并写在生成的java文件中
    private static String createMethods(Class<?>[] interfaces){
        StringBuffer methods = new StringBuffer();
        for (Class inter : interfaces) {
            //获取包含父类的所有public方法
            Method[] methods1 = inter.getMethods();
            for (Method method : methods1) {
                methods.append("public final void ")
//                        .append(method.getReturnType().getName())
                        .append(method.getName()).append("(String param)throws Exception{").append(rn)
                        //获取mehtod方法Method m2 = clazz.getMethod("show", String.class, int.class);
                        //clazz即class对象
                        .append("Method md = ")
                        .append(inter.getName()).append(".class.getMethod(\"")
                        .append(method.getName()).append("\",new Class[]{String.class});").append(rn)
                        .append("this.h.invoke(this,md,new Object[]{param});").append(rn)
                        .append("}").append(rn);
            }
        }

        return methods.toString();
    }
}
