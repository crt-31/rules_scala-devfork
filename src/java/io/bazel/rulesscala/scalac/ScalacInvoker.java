package io.bazel.rulesscala.scalac;
import java.net.URLClassLoader;
import java.net.URL;
import io.bazel.rulesscala.scalac.compileoptions.CompileOptions;
import java.nio.file.Path;
import java.util.Arrays;

class ScalacInvokerer{
  
  static ScalacInvokerResults invokeCompiler(CompileOptions ops, String[] compilerArgs){

      try{
        URL[] urls = Arrays.stream(ops.compilerBridgeJars)
          .map(j -> {
            try{
              return new URL(
                Path.of(j).toAbsolutePath().toUri().toString()
              );
            }catch(Exception e){
                //TODO: Fix this CLAY
            }
            return null;
          })
          .toArray(URL[]::new);

        String[] parentClasses = {
          "io.bazel.rulesscala.scalac.IScalacInvoker",
          "io.bazel.rulesscala.scalac.ReportableMainClass"
        };

        URLClassLoader bridgeClassLoader = new URLClassLoader(urls, null  );
        System.out.println("bro " + ops.getClass().getClassLoader().toString());
        ParentClassLoader classLoader = new ParentClassLoader(urls, ops.getClass().getClassLoader(), parentClasses);

        System.out.println("Yo");
        Object i =  classLoader.loadClass("io.bazel.rulesscala.scalac.ScalacInvoker");//.newInstance();
        System.out.println(i);
        
        IScalacInvoker invoker = (IScalacInvoker)classLoader.loadClass("io.bazel.rulesscala.scalac.ScalacInvoker").newInstance();
        System.out.println("Yo");
        

        return invoker.invokeCompiler(ops, compilerArgs);
      }catch(Exception e){
          System.out.println(e);
      }      
      return null;
  }

}