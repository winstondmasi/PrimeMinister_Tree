# PrimeMinister_Tree

This project consists of two classes `PMList` and `PMTree` which represent a list of prime ministers and a binary search tree, respectively. The main goal of the project is to store and retrieve prime ministers based on the number of days they served in office. The project also implements some additional functionality, such as finding the nth shortest serving prime minister and returning an array of the first n shortest serving prime ministers.

## Classes

### PMList.java

This class contains a nested class `Entry` and two methods:

1. `getPrimeMinisters()`: Returns a list of prime ministers, represented as instances of the nested class `Entry`. Each entry contains the number of days the prime minister served in office and their name.

2. `INCOMPLETE`: An integer array containing an incomplete list of days served by some prime ministers.

### PMTree.java

This class represents a binary search tree for prime ministers. It contains various methods to insert, delete, and retrieve prime ministers based on the number of days they served in office. The class also includes methods to find the nth shortest serving prime minister and return an array of the first n shortest serving prime ministers.

## Usage

1. Create an instance of the `PMTree` class.
2. Populate the tree with prime minister data using the `insert()` method.
3. Use the provided methods to retrieve, insert, and delete prime ministers or to find the shortest serving prime ministers.

## Examples

```java
PMTree pmTree = new PMTree();
List<PMList.Entry> primeMinisters = PMList.getPrimeMinisters();
for (PMList.Entry pm : primeMinisters) {
    pmTree.insert(pm.days, pm.name);
}

// Find the shortest serving prime minister
String shortestServingPM = pmTree.nthShortest(1);

// Find the 5th shortest serving prime minister
String fifthShortestServingPM = pmTree.nthShortest(5);

// Find an array of the first 10 shortest serving prime ministers
String[] firstTenShortestServingPMs = pmTree.allNShortest(10);
```


## Limitations

- The project currently supports a fixed list of prime ministers with a knowledge cutoff date of 2021-09.
- The code does not handle edge cases such as invalid inputs or tree operations that may lead to unexpected behavior.