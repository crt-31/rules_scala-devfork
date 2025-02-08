package io.bazel.rulesscala.scalac;

class ScalacWorkerExceptions {

  public static class InvalidSettings extends Exception {
    public InvalidSettings() {
      super("Failed to invoke Scala compiler, ensure passed options are valid");
    }
  }

  public static class CompilationFailed extends Exception {
    public CompilationFailed(String reason, Throwable cause) {
      super("Build failure " + reason, cause);
    }
    public CompilationFailed(String reason) {
      this(reason, null);
    }
  }
}
