package com.test.myspringboot.util;

import java.io.*;

/**
 * @author: ysh
 * @date: 2018/12/19 19:17
 * @Description:
 */
public class ToolsUtils {
    /**
     * 对象转数组
     * @param obj
     * @return
     */
    public static byte[] object2Byte (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }


    /**
     * 数组转对象
     * @param bytes
     * @return
     */
    public static Object byte2Object (byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
}
