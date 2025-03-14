package io.bazel.rulesscala.scalac;

import io.bazel.rulesscala.scalac.reporter.DepsTrackingReporter;
import io.bazel.rulesscala.scalac.compileoptions.CompileOptions;
import java.nio.file.Paths;
import io.bazel.rulesscala.scalac.reporter.ProtoReporter;
import scala.tools.nsc.reporters.ConsoleReporter;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;

//Invokes Scala 2 compiler
  public class ScalacInvoker extends IScalacInvoker{

  public ScalacInvokerResults invokeCompiler(CompileOptions ops, String[] compilerArgs)
    throws IOException, Exception{

    ReportableMainClass comp = new ReportableMainClass(ops);

    ScalacInvokerResults results = new ScalacInvokerResults();

    results.startTime = System.currentTimeMillis();
    try {
      comp.process(compilerArgs);
    } catch (Throwable ex) {
      if (ex.toString().contains("scala.reflect.internal.Types$TypeError")) {
        throw new Exceptions.CompilationFailed("with type error", ex);
      } else if (ex.toString().contains("java.lang.StackOverflowError")) {
        throw new Exceptions.CompilationFailed("with StackOverflowError", ex);
      } else if (isMacroException(ex)) {
        String reason;

        if (ex instanceof ClassFormatError) {
          reason = "during macro expansion. You may have declared a target containing a macro as a `scala_library` target instead of a `scala_macro_library` target";
        } else {
          reason = "during macro expansion";
        }

        throw new Exceptions.CompilationFailed(reason, ex);
      } else {
        throw ex;
      }
    } finally {
      comp.close();
    }

    results.stopTime = System.currentTimeMillis();

    ConsoleReporter reporter = (ConsoleReporter) comp.getReporter();
    if (reporter == null) {
      // Can happen only when `ReportableMainClass::newCompiler` was not invoked,
      // typically due to invalid settings
      throw new Exceptions.InvalidSettings();
    }

    if (reporter instanceof ProtoReporter) {
      ProtoReporter protoReporter = (ProtoReporter) reporter;
      protoReporter.writeTo(Paths.get(ops.diagnosticsFile));
    }

    if (reporter instanceof DepsTrackingReporter) {
      DepsTrackingReporter depTrackingReporter = (DepsTrackingReporter) reporter;
      depTrackingReporter.prepareReport();
      depTrackingReporter.writeDiagnostics(ops.diagnosticsFile);
    }

    if (reporter.hasErrors()) {
      reporter.flush();
      throw new Exceptions.CompilationFailed("with errors.");
    }

    return results;
  }

  public static boolean isMacroException(Throwable ex) {
    for (StackTraceElement elem : ex.getStackTrace()) {
      if (elem.getMethodName().equals("macroExpand")) {
        return true;
      }
    }
    return false;
  }
}
