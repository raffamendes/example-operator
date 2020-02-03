## Operator example with quarkus

Based on the Instana Agent Operator and blog

[Instana Github](https://github.com/instana/instana-agent-operator/tree/master/src/main/java/com/instana/operator/env)

[Instana Three Part Blog](https://github.com/instana/instana-agent-operator/tree/master/src/main/java/com/instana/operator/env)


Use this as a base to learn how to create a operator using quarkus.

## Install the Operator

First create a serviceaccount and give cluster-admin permission to it

`oc create sa quarkus-user`
`oc adm policy add-cluster-role-to-user cluster-admin system:serviceaccount:quarkus-operator:quarkus-user`

Create the CRD

```yaml
apiVersion: apiextensions.k8s.io/v1beta1
kind: CustomResourceDefinition
metadata:
  name: examples.rmendes.com
spec:
  group: rmendes.com
  names:
    kind: Example
    listKind: ExampleList
    plural: examples
    singular: example
  scope: Namespaced
  version: v1alpha1
```

To install the operator, download the source code, and execute the following steps:

`mvn package -Pnative -Dquarkus.native.container-build=true -DskipTests`

For more instructions on how to build a native application using quarkus you can use this [link](https://quarkus.io/guides/building-native-image)

Then after this you can execute the build executing this command 

`oc start-build quarkus-operator --from-dir=. --follow`

Notice that you execute this on the root folder of the source code

After the build change the service account using this command

`oc patch dc/w3af-centos --patch '{"spec":{"template":{"spec":{"serviceAccountName": "quarkus-user"}}}}'`

A new deploy will happen and the operator will be ready to accept the custom resources.

CustomResource example:

```yaml
apiVersion: rmendes.com/v1alpha1
kind: Example
metadata:
  name: example
spec:
  messageParts:
  - Hello
  - Quarkus
  - works
  namespace: quarkus-operator
```
