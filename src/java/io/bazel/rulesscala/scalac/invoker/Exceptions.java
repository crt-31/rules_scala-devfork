package io.bazel.rulesscala.scalac;

class Exceptions{


  public static class InvalidSettings extends Exception/*WorkerException*/ {
    public InvalidSettings() {
      super("Failed to invoke Scala compiler, ensure passed options are valid");
    }
  }

  public static class CompilationFailed extends Exception/*extends WorkerException*/ {
    public CompilationFailed(String reason, Throwable cause) {
      super("Build failure " + reason, cause);
    }
    public CompilationFailed(String reason) {
      this(reason, null);
    }
  }
}