package com.test.myspringboot.demo.aop.myproxy;

import java.io.*;

public class MyClassLoader extends ClassLoader{

    private String dir;

    public MyClassLoader(String dir) {
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //获取到class文件
        File classFile = new File(dir + name + ".class");
        if (classFile.exists()) {
            FileInputStream intput = null;
            ByteArrayOutputStream out = null;
            try {
                intput = new FileInputStream(classFile);
                out = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while (true){
                    try {
                        if ((len = intput.read(buffer)) != -1){
                            out.write(buffer,0,len);
                        }else {
                            out.flush();
                            break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return this.defineClass("com.test.myspringboot.demo.aop.myproxy." + name, out.toByteArray(),0,out.size());
             } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (intput != null){
                    try {
                        intput.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return super.findClass(name);
    }
}
