##Operator example with quarkus

Based on the Instana Agent Operator and blog

[Instana Github](https://github.com/instana/instana-agent-operator/tree/master/src/main/java/com/instana/operator/env)

[Instana Three Part Blog](https://github.com/instana/instana-agent-operator/tree/master/src/main/java/com/instana/operator/env)


Use this as a base to learn how to create a operator using quarkus.

##Install the Operator

To install the operator, download the source code, and execute the following steps:

`mvn package -Pnative -Dquarkus.native.container-build=true -DskipTests`

For more instructions on how to build a native application using quarkus you can use this [link](https://quarkus.io/guides/building-native-image)

Then after this you can execute the build executing this command 

`oc start-build quarkus-operator --from-dir=. --follow`

Notice that you execute this on the root folder of the source code



