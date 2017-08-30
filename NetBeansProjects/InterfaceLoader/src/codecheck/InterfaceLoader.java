/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codecheck;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Tobias
 */
public class InterfaceLoader<T>
{
    public T loadInterface(String className, String jarFile) throws Exception
    {
        addJar(jarFile);
        T res = (T) Class.forName(className).newInstance();
        return res;
    }
    
    private static void addJar(String s) throws Exception
    {
        File f = new File(s);
        URI u = f.toURI();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class<URLClassLoader> urlClass = URLClassLoader.class;
        Method method = urlClass.getDeclaredMethod("addURL", new Class[]
        {
            URL.class
        });
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]
        {
            u.toURL()
        });
    }
}
