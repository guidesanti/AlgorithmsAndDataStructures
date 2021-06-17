# Algorithms

This module contains implementations of some traditional algorithms, like sorting and string matching among many others.
The purpose of these implementations is for study and learning only, so do not use the code
in the algorithms developed here for production purposes.

## Contents

# Graph Algorithms
- [Maximum Independent Set in Trees](./src/main/java/br/com/eventhorizon/graph/MaxIndependentSetInTree.java)
- [Maximum Weighted Independent Set in Trees](./src/main/java/br/com/eventhorizon/graph/MaxWeightedIndependentSetInTree.java)

More graph algorithms can be found [here](../Common/src/main/java/br/com/eventhorizon/common/datastructures/graphs). 

# Sorting Algorithms
- [Heap Sort](./src/main/java/br/com/eventhorizon/sorting/HeapSort.java)
- [Insertion Sort](./src/main/java/br/com/eventhorizon/sorting/InsertionSort.java)
- [Merge Sort](./src/main/java/br/com/eventhorizon/sorting/MergeSort.java)
- [Quick Sort](./src/main/java/br/com/eventhorizon/sorting/QuickSort.java)
- [Selection Sort](./src/main/java/br/com/eventhorizon/sorting/SelectionSort.java)

# String Algorithms

## Suffix Array Builder

Implementations of suffix array construction.

- [Naive](./src/main/java/br/com/eventhorizon/string/NaiveSuffixArrayBuilder.java)
- [Improved](./src/main/java/br/com/eventhorizon/string/ImprovedSuffixArrayBuilder.java)

## Burrows-Wheeler Transform

Some implementations of the Burrows-Wheeler transform and its respective reverse.

### Transform
- [Naive](./src/main/java/br/com/eventhorizon/string/bwt/NaiveBurrowsWheelerTransform.java)
- [Improved1](./src/main/java/br/com/eventhorizon/string/bwt/ImprovedBurrowsWheelerTransform1.java)
- [Improved2](./src/main/java/br/com/eventhorizon/string/bwt/ImprovedBurrowsWheelerTransform2.java)

### Reverse
- [Naive](./src/main/java/br/com/eventhorizon/string/bwt/NaiveReverseBurrowsWheelerTransform.java)
- [Improved](./src/main/java/br/com/eventhorizon/string/bwt/ImprovedReverseBurrowsWheelerTransform.java)

## Pattern Matching

Many algorithms for pattern matching.

- [Naive (brute force)](./src/main/java/br/com/eventhorizon/string/matching/NaivePatternMatcher.java)
- [Rabin-Karp](./src/main/java/br/com/eventhorizon/string/matching/RabinKarpPatternMatcher.java)
- [Prefix Trie](./src/main/java/br/com/eventhorizon/string/matching/TriePatternMatcher.java)
- [Burrows-Wheeler](./src/main/java/br/com/eventhorizon/string/matching/BurrowsWheelerTransformPatternMatcher.java)

## Shortest Non-Shared Pattern

Implementations for shortest non-shared pattern problem.

- [Naive (brute force)](./src/main/java/br/com/eventhorizon/string/matching/NaiveShortestNonSharedPattern.java)
- [Suffix Tree](./src/main/java/br/com/eventhorizon/string/matching/SuffixTreeShortestNonSharedPattern.java)
