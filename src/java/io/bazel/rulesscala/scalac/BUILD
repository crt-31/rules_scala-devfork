load("@rules_java//java:defs.bzl", "java_binary", "java_library")
load("@io_bazel_rules_scala_config//:config.bzl", "ENABLE_COMPILER_DEPENDENCY_TRACKING")
load("//scala:scala_cross_version_select.bzl", "select_for_scala_version")
load("runner.bzl", "default_scala_version_transition")

SCALAC_DEPS = [
    #"//scala/private/toolchain_deps:scala_compile_classpath",
    "//src/java/io/bazel/rulesscala/io_utils",
    "@bazel_tools//src/main/protobuf:worker_protocol_java_proto",
    "@io_bazel_rules_scala//src/java/io/bazel/rulesscala/jar",
    "@io_bazel_rules_scala//src/java/io/bazel/rulesscala/worker",
    #"@io_bazel_rules_scala//src/protobuf/io/bazel/rules_scala:diagnostics_java_proto",
    "//src/java/io/bazel/rulesscala/scalac/compileoptions",
    #"//src/java/io/bazel/rulesscala/scalac/reporter",
]
'''
java_library(
    name = "scalac_link",
    srcs = ["scalac_link/dummy.java"],
    
    neverlink = True,
    javacopts = [
        "-source 1.8",
        "-target 1.8",
    ],
    deps = ["//scala/private/toolchain_deps:scala_compile_classpath"],
)
'''

java_library(
    name = "scalacrunner",
    srcs =  
        select_for_scala_version(
            any_2 = glob(["runner/scala_2/*.java", "runner/runner_shared/*.java"]),
            any_3 = glob(["runner/scala_3/*.java", "runner/runner_shared/*.java"]),
    ),
    javacopts = [
        "-source 1.8",
        "-target 1.8",
    ],

    deps = (["//third_party/dependency_analyzer/src/main/io/bazel/rulesscala/dependencyanalyzer/compiler:dep_reporting_compiler"] if ENABLE_COMPILER_DEPENDENCY_TRACKING else []) + [
        "//scala/private/toolchain_deps:scala_compile_classpath",
        "//src/java/io/bazel/rulesscala/scalac/compileoptions",
        "//src/java/io/bazel/rulesscala/scalac/reporter",
    ],
        visibility = ["//visibility:public"],
)


java_library(
    name = "scalacrunner_bootstrap",
    srcs =  
        select_for_scala_version(
            any_2 = glob(["runner/scala_2/*.java", "runner/runner_shared/*.java"]),
            any_3 = glob(["runner/scala_3/*.java", "runner/runner_shared/*.java"]),
    ),
    javacopts = [
        "-source 1.8",
        "-target 1.8",
    ],

    deps = [
        "//scala/private/toolchain_deps:scala_compile_classpath",
        "//src/java/io/bazel/rulesscala/scalac/compileoptions",
        "//src/java/io/bazel/rulesscala/scalac/reporter",        
    ] ,    
    visibility = ["//visibility:public"],
)


java_binary(
    name = "scalac",
    srcs = [
        ":scalac_files",        
    ] + glob(["runner/runner_shared/*.java"]),

    javacopts = [
        "-source 1.8",
        "-target 1.8",
    ],
    main_class = "io.bazel.rulesscala.scalac.ScalacWorker",
    visibility = ["//visibility:public"],


#    data = ["scalacrunner_2"],
   # resources = ["scalacrunner_deps_file"],
    deps = SCALAC_DEPS,
    
    #deps = ([
    #    "//third_party/dependency_analyzer/src/main/io/bazel/rulesscala/dependencyanalyzer/compiler:dep_reporting_compiler",
    #] if ENABLE_COMPILER_DEPENDENCY_TRACKING else []) + SCALAC_DEPS,

)

'''
java_binary(
    name = "scalac_bootstrap",
    srcs = [
        ":scalac_files",
    ],
    javacopts = [
        "-source 1.8",
        "-target 1.8",
    ],
    
    data = ["scalacrunner_2"],
    resources = ["scalacrunner_deps_file"],

    main_class = "io.bazel.rulesscala.scalac.ScalacWorker",
    visibility = ["//visibility:public"],
    deps = SCALAC_DEPS,
)
'''

filegroup(
    name = "scalac_files",
    srcs = [
        "ScalacWorker.java",
        "ScalacRunner.java",        
    ], 
    visibility = ["//visibility:public"],
    
    #+ select_for_scala_version(
    #    any_2 = glob(["scala_2/*.java"]),
    #    any_3 = glob(["scala_3/*.java"]),
    #),
        
)
