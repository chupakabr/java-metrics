#!/bin/bash

pushd target
java -jar -javaagent:java-metrics-1.0-SNAPSHOT-agent.jar java-metrics-1.0-SNAPSHOT-jar-with-dependencies.jar $1
popd

