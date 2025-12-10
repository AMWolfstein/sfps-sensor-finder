#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to locate java
if [ -n "$JAVA_HOME" ] ; then
    JAVA_EXE="$JAVA_HOME/bin/java"
else
    JAVA_EXE="java"
fi

if [ ! -x "$JAVA_EXE" ] ; then
    echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH." >&2
    exit 1
fi

# Determine the directory of this script
APP_HOME=$(cd "$(dirname "$0")"; pwd)

# Locate gradle-wrapper.jar
CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Execute Gradle
exec "$JAVA_EXE" -Xmx64m -Xms64m -cp "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
