package io.bazel.rulesscala.scalac;
import java.util.Arrays;
import java.util.List;
import java.net.URLClassLoader;
import java.net.URL;

class ParentClassLoader extends URLClassLoader {
    private final List<String> _parentClasses;
    private ClassLoader _parentLoader;

    public ParentClassLoader(URL[] urls, ClassLoader parentLoader, String[] parentClasses) {
        super(urls, parentLoader);
        _parentLoader = parentLoader;
        System.out.println(_parentLoader.toString());

        _parentClasses = Arrays.asList(parentClasses);
    }

  @Override  
    protected Class<?> findClass(String name)
                      throws ClassNotFoundException{
      if(_parentClasses.contains(name)){
        return _parentLoader.loadClass(name);
      }
      return super.findClass(name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
      System.out.println("Dude " + name);
      if(_parentClasses.contains(name)){
        System.out.println("sys");
        _parentLoader.loadClass(name);

        
            return _parentLoader.loadClass(name);
      }
      System.out.println("url");
      super.loadClass(name, resolve);
      System.out.println("c");
      return super.loadClass(name, resolve);
    }
}