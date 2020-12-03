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
# SumOfTwoDigits
java -cp target/EDX-AlgorithmsAndDataStructures-UCSanDiego.jar edx.pa1.SumOfTwoDigits -Xmx1024m
# MaximumPairwiseProduct
java -cp target/EDX-AlgorithmsAndDataStructures-UCSanDiego.jar edx.pa1.MaximumPairwiseProduct -Xmx1024m
```
