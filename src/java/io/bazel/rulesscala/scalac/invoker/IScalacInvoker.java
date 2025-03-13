package io.bazel.rulesscala.scalac;
import io.bazel.rulesscala.scalac.compileoptions.CompileOptions;
import io.bazel.rulesscala.scalac.ScalacInvokerResults;
import java.io.IOException;

public abstract class  IScalacInvoker{
  abstract ScalacInvokerResults invokeCompiler(CompileOptions ops, String[] compilerArgs)
    throws IOException, Exception;
}
