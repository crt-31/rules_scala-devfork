load("@io_bazel_rules_scala_config//:config.bzl", "ENABLE_COMPILER_DEPENDENCY_TRACKING")
load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("//scala:scala_cross_version_select.bzl", "select_for_scala_version")

DEFAULT_SCALAC_DEPS = [
    Label(dep)
    for dep in [
#        "//scala/private/toolchain_deps:scala_compile_classpath",
        "//src/java/io/bazel/rulesscala/io_utils",
        "//src/java/io/bazel/rulesscala/jar",
        "//src/java/io/bazel/rulesscala/worker",
        "//src/protobuf/io/bazel/rules_scala:diagnostics_java_proto",
        "//src/java/io/bazel/rulesscala/scalac/compileoptions",
        #"//src/java/io/bazel/rulesscala/scalac/reporter",
        "@bazel_tools//src/main/protobuf:worker_protocol_java_proto",
    ]
]

DEFAULT_SRCS = [
    Label("//src/java/io/bazel/rulesscala/scalac:scalac_files"),
]

def define_scalac(name = "scalac", srcs = DEFAULT_SRCS, deps = DEFAULT_SCALAC_DEPS):
    java_binary(
        name = name,
        srcs = srcs,
        javacopts = [
            "-source 1.8",
            "-target 1.8",
        ],
        main_class = "io.bazel.rulesscala.scalac.ScalacWorker",
        visibility = ["//visibility:public"],
        deps = deps,
    )

    java_library(
        name = "scalac_bridge",
        srcs = [
        ]  + select_for_scala_version(
            any_2 = native.glob(["scala_2/*.java"]),
            any_3 = native.glob(["scala_3/*.java"]),
        ),
        javacopts = [
            "-source 1.8",
            "-target 1.8",
        ],
        visibility = ["//visibility:public"],
        deps = ([
            "//third_party/dependency_analyzer/src/main/io/bazel/rulesscala/dependencyanalyzer/compiler:dep_reporting_compiler",
        ] if ENABLE_COMPILER_DEPENDENCY_TRACKING else []) + [
            "//scala/private/toolchain_deps:scala_compile_classpath",
            "//src/java/io/bazel/rulesscala/scalac/compileoptions",
            "//src/java/io/bazel/rulesscala/scalac/reporter",
            "scalac_bridge_if"
        ],
    )

def define_scalac_bootstrap(name = "scalac_bootstrap", srcs = DEFAULT_SRCS, deps = DEFAULT_SCALAC_DEPS):
    java_library(
        name = "scalac_bootstrap_bridge",
        srcs = [

        ]  + select_for_scala_version(
            any_2 = native.glob(["scala_2/*.java"]),
            any_3 = native.glob(["scala_3/*.java"]),
        ),
        javacopts = [
            "-source 1.8",
            "-target 1.8",
        ],
        visibility = ["//visibility:public"],
        deps = [
            "//scala/private/toolchain_deps:scala_compile_classpath",
            "//src/java/io/bazel/rulesscala/scalac/compileoptions",
            "//src/java/io/bazel/rulesscala/scalac/reporter",
            "scalac_bridge_if"

        ]
    )
    '''
    java_binary(
        name = name,
        srcs = srcs,
        javacopts = [
            "-source 1.8",
            "-target 1.8",
        ],
        main_class = "io.bazel.rulesscala.scalac.ScalacWorker",
        visibility = ["//visibility:public"],
        deps = deps + ["//scala/private/toolchain_deps:scala_compile_classpath"],
    )
    '''
