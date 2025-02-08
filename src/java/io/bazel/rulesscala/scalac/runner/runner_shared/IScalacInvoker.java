package io.bazel.rulesscala.scalac;
import java.io.IOException;

import io.bazel.rulesscala.scalac.ScalacInvokerResults;
import io.bazel.rulesscala.scalac.compileoptions.CompileOptions;

public interface IScalacInvoker {
     ScalacInvokerResults invokeCompiler(CompileOptions ops, String[] compilerArgs)
    throws IOException, Exception;

}
