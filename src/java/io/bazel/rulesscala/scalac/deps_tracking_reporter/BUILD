load("//scala:scala_cross_version_select.bzl", "select_for_scala_version")

filegroup(
    name = "deps_tracking_reporter",
    srcs = select_for_scala_version(
        before_2_12_13 = ["before_2_12_13/DepsTrackingReporter.java"],
        between_2_12_13_and_2_13_12 = ["after_2_12_13_and_before_2_13_12/DepsTrackingReporter.java"],
        since_2_13_12 = ["after_2_13_12/DepsTrackingReporter.java"],
    ),
    visibility = ["//visibility:public"],
)
