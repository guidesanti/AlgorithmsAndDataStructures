# EDX-AlgorithmsAndDataStructures-UCSanDiego
Repository to store study and statements for EDX MicroMasters Program Algorithms and Data Structures from UC San Diego

## Requirements
* Java 1.8
* Maven 3.3.9

## Build
```shell script
mvn clean package
```

## Test

### Unit Tests
```shell script
mvn clean verify
```

## Run
```shell script
# HelloWorld
java -cp target/EDX-AlgorithmsAndDataStructures-UCSanDiego.jar br.com.eventhorizon.edx.HelloWorld -Xmx1024m
# SumOfTwoDigits
java -cp target/EDX-AlgorithmsAndDataStructures-UCSanDiego.jar br.com.eventhorizon.edx.SumOfTwoDigits -Xmx1024m
# MaximumPairwiseProduct
java -cp target/EDX-AlgorithmsAndDataStructures-UCSanDiego.jar br.com.eventhorizon.edx.MaximumPairwiseProduct -Xmx1024m
```
