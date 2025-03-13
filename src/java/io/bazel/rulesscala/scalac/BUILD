load("//scala:scala_cross_version_select.bzl", "select_for_scala_version")
load("//src/java/io/bazel/rulesscala/scalac:definitions.bzl", "define_scalac", "define_scalac_bootstrap")
load("@rules_java//java:defs.bzl", "java_library")
load("@io_bazel_rules_scala_config//:config.bzl", "ENABLE_COMPILER_DEPENDENCY_TRACKING")

define_scalac()

define_scalac_bootstrap()

java_library(
    name = "scalac_bridge_if",
    srcs = [
        "invoker/IScalacInvoker.java",
        "invoker/ScalacInvokerResults.java",
        "invoker/Exceptions.java",
    ],
    neverlink = True,
    javacopts = [
        "-source 1.8",
        "-target 1.8",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//src/java/io/bazel/rulesscala/scalac/compileoptions",

     ], 
)

filegroup(
    name = "scalac_files",
    srcs = [
#        "ScalacInvokerResults.java",
        "ScalacWorker.java",
        "ScalacInvoker.java",
        "invoker/IScalacInvoker.java",
        "invoker/ScalacInvokerResults.java",
        "invoker/Exceptions.java",
        "ParentClassloader.java"
    ],
    
    # + select_for_scala_version(
    ##    any_2 = glob(["scala_2/*.java"]),
    #    any_3 = glob(["scala_3/*.java"]),
    #),
    visibility = ["//visibility:public"],
)





