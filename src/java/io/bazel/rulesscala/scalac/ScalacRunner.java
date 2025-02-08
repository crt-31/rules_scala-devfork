package io.bazel.rulesscala.scalac;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.net.URL;
import java.net.URISyntaxException;
import java.util.stream.Collectors;

import io.bazel.rulesscala.scalac.IScalacInvoker;
import io.bazel.rulesscala.scalac.ScalacInvokerResults;
import io.bazel.rulesscala.scalac.compileoptions.CompileOptions;

public class ScalacRunner {
    public ScalacRunner() throws Exception{
        try{
            /*
            InputStream runner_deps_file = this.getClass().getClassLoader().getResourceAsStream("java/io/bazel/rulesscala/scalac/runner_deps");

            List<String> lines = new ArrayList<>();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(runner_deps_file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
                 
            
            _coreRunnerJars = lines.stream().map(l -> {
                try{
                    return Path.of(l).toUri().toURL();
                }catch(Exception e){
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            */
        }catch( Exception e){
            throw new Exception(e);
        }
    }     

//public List<URL> _coreRunnerJars;
       
  public ScalacInvokerResults invokeCompiler(CompileOptions ops, String[] compilerArgs)
    throws IOException, Exception{
      
        
        List<URL> scalaCompilerJars = Arrays.stream(ops.scalaCompilerJars).map(p -> {
            try{
                return Path.of(p).toUri().toURL();
            }catch(Exception e){
                throw new RuntimeException(e);
            } 
        }).collect(Collectors.toList());
        
       
        List<URL> invokerJars = new ArrayList<URL>();
       // invokerJars.addAll(_coreRunnerJars);
        invokerJars.addAll(scalaCompilerJars);

        URLClassLoader classLoader = new URLClassLoader(invokerJars.toArray(URL[]::new));
        
        /*
         for(URL jar :scalaCompilerJars){
            if(Files.exists(Path.of(jar.toURI()))){
                System.out.println(jar);
            }else{
                System.out.println(jar);
                System.out.println("DOES NOT EXIST");
            }

        }
 */        

        //if(ops.scalaVersion.startsWith("2.")){
            Object bridgeObj = classLoader.loadClass("io.bazel.rulesscala.scalac.ScalacInvoker").getDeclaredConstructor().newInstance();

            IScalacInvoker bridge = (IScalacInvoker)bridgeObj;

            bridge.invokeCompiler(ops, compilerArgs);
/*
        //}else{
        //    throw new Exception("Unsupported scalaversion");
        //}
 */
        classLoader.close();
        return new ScalacInvokerResults();
    }
}
