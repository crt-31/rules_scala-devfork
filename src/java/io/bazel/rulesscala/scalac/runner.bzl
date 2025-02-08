load("@io_bazel_rules_scala_config//:config.bzl", "SCALA_VERSION")

'''
def runner_deps_imp(ctx):
    #Remove the cmpiler jars fromt the dep list:
    
    all_runtime_jars = depset(
        #direct =  [jo.class_jar for jo in ctx.attr.top_level_jar[JavaInfo].java_outputs],
        transitive = [dep[JavaInfo].transitive_runtime_jars  for dep in ctx.attr.deps])


    all_runtime_jars_set = all_runtime_jars.to_list()

    for compilerJar in ctx.attr.compiler_jars: 
        for compiler_jar in compilerJar[JavaInfo].runtime_output_jars:
            all_runtime_jars_set.remove(compiler_jar)
    
    file_contents = "\n".join([jar.path for jar in all_runtime_jars_set])
    
    runner_deps_file = ctx.actions.declare_file("runner_deps")
    ctx.actions.write(runner_deps_file, file_contents)

    return DefaultInfo(
        files = depset([runner_deps_file])
    )


runner_deps = rule(
    implementation = runner_deps_imp,
    attrs  = {
        "compiler_jars" : attr.label_list(providers = [JavaInfo]),
        "deps" : attr.label_list(providers = [JavaInfo])
    }
)
'''

def _default_scala_version_transition_imp(settings, attr):    
    print(SCALA_VERSION)
    return {
        "@io_bazel_rules_scala_config//:scala_version": SCALA_VERSION,
 #       "//command_line_option:platforms": "@local_config_platform//:host"
        }

default_scala_version_transition = transition(
    implementation = _default_scala_version_transition_imp,
    inputs = [],
    outputs = [
        "@io_bazel_rules_scala_config//:scala_version",
#        "//command_line_option:platforms",
        ],
)


RUNNER_ATTRS = {
    "_scalacrunner_jars": attr.label_list(
        cfg = "exec",
        #default = Label("@io_bazel_rules_scala//src/java/io/bazel/rulesscala/scalac:scalac_bootstrap"),
        default = [Label("@io_bazel_rules_scala//src/java/io/bazel/rulesscala/scalac:scalacrunner"),],
        allow_files = True,
    ),

    "_scalac": attr.label(
        executable = True,
        cfg = "exec",
        #TODO: This should transition to exec & Scala_version        
        default = Label("@io_bazel_rules_scala//src/java/io/bazel/rulesscala/scalac"),
        allow_files = True,
    ),
}

BOOTSTRAP_RUNNER_ATTRS = {
    "_scalacrunner_jars": attr.label_list(
        cfg = "exec",        
        default = [Label("@io_bazel_rules_scala//src/java/io/bazel/rulesscala/scalac:scalacrunner_bootstrap")],
        allow_files = True,
    ),

    "_scalac": attr.label(
        executable = True,
        cfg = "exec",
        #TODO: This should transition to exec & Scala_version
        #cfg = default_scala_version_transition,
        default = Label("@io_bazel_rules_scala//src/java/io/bazel/rulesscala/scalac"),
        allow_files = True,
    ),
}