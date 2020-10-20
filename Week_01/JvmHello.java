package myjvm;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
/**
 * @author zcl
 * @date 2020/10/20-18:13
 */


public class JvmHello extends ClassLoader{
    public static void main(String[] args) {
        try {
            Class<?> clazz=new JvmHello().findClass("Hello.xlass");
            if (null != clazz) {
                Method hello=clazz.getDeclaredMethod("hello");
                hello.invoke(clazz.newInstance());
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        printFileBytes();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException{
        try {
            InputStream classInputStream = this.getClass().getResourceAsStream(name);
            byte[] bytes = new byte[classInputStream.available()];
            classInputStream.read(bytes);
            for(int i=0;i<bytes.length;i++) {
                bytes[i]= (byte) (255 - bytes[i]);
            }
            classInputStream.close();
            return defineClass(name.substring(0,name.indexOf(".")),bytes,0,bytes.length);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static byte[] encode(byte[] content) {
        return Base64.getEncoder().encode(content);
    }

    public static void printFileBytes(){
        File file = new File("D:\\javacode\\jixunjvm\\src\\Hello.xlass");
        int length = (int) file.length();
        byte[] fileBytes = new byte[length];

        try{
            new FileInputStream(file).read(fileBytes);
        }catch (IOException e) {
            e.printStackTrace();
        }

        for (int i=0;i<fileBytes.length;i++){
            fileBytes[i]= (byte) (255-fileBytes[i]);
        }

        String s = new String(fileBytes);
        System.out.println(s);

    }
}
