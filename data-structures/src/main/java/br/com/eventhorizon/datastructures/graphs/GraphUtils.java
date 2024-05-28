package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.common.utils.Utils;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GraphUtils {

  public static UndirectedGraph readUndirectedGraphFromCsvFile(String file) {
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      csvReader.skip(2);
      String[] values = csvReader.readNext();
      int numberOfVertices = Integer.parseInt(values[0]);
      int numberOfEdges = Integer.parseInt(values[1]);
      UndirectedGraph graph = new UndirectedGraph(numberOfVertices);
      while ((values = csvReader.readNext()) != null) {
        if (values.length != 2) {
          throw new RuntimeException("Invalid CSV graph file, each line should have 2 integers");
        }
        graph.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
      }
      if (graph.numberOfEdges() != numberOfEdges) {
        throw new RuntimeException("Invalid CSV graph file, number of edges on first line does not match the provided edges");
      }
      return graph;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read graph from CSV file", exception);
    }
  }

  public static VertexWeightedUndirectedGraph readVertexWeightedUndirectedGraphFromCsvFile(String file) {
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      csvReader.skip(3);
      // Line 1: number of vertices and number of edges
      String[] values = csvReader.readNext();
      int numberOfVertices = Integer.parseInt(values[0]);
      int numberOfEdges = Integer.parseInt(values[1]);
      // Line 2: vertex weights
      String[] line2 = csvReader.readNext();
      if (line2.length != numberOfVertices) {
        throw new RuntimeException("Invalid CSV graph file, line 2 should have " + numberOfVertices + " integers, but received " + line2.length);
      }
      int[] weights = new int[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        weights[i] = Integer.parseInt(line2[i]);
      }
      // Remaining lines: edges
      VertexWeightedUndirectedGraph graph = new VertexWeightedUndirectedGraph(weights);
      while ((values = csvReader.readNext()) != null) {
        if (values.length != 2) {
          throw new RuntimeException("Invalid CSV graph file, each line should have 2 integers");
        }
        graph.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
      }
      if (graph.numberOfEdges() != numberOfEdges) {
        throw new RuntimeException("Invalid CSV graph file, number of edges on first line does not match the provided edges");
      }
      return graph;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read graph from CSV file", exception);
    }
  }

  public static DirectedGraph readDirectedGraphFromCsvFile(String file) {
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      csvReader.skip(2);
      String[] values = csvReader.readNext();
      int numberOfVertices = Integer.parseInt(values[0]);
      int numberOfEdges = Integer.parseInt(values[1]);
      DirectedGraph graph = new DirectedGraph(numberOfVertices);
      while ((values = csvReader.readNext()) != null) {
        if (values.length != 2) {
          throw new RuntimeException("Invalid CSV graph file, each line should have 2 integers");
        }
        graph.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
      }
      if (graph.numberOfEdges() != numberOfEdges) {
        throw new RuntimeException("Invalid CSV graph file, number of edges on first line does not match the provided edges");
      }
      return graph;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read graph from CSV file", exception);
    }
  }

  public static WeightedUndirectedGraph readWeightedUndirectedGraphFromCsvFile(String file) {
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      csvReader.skip(2);
      String[] values = csvReader.readNext();
      int numberOfVertices = Integer.parseInt(values[0]);
      int numberOfEdges = Integer.parseInt(values[1]);
      WeightedUndirectedGraph graph = new WeightedUndirectedGraph(numberOfVertices);
      while ((values = csvReader.readNext()) != null) {
        if (values.length != 3) {
          throw new RuntimeException("Invalid CSV graph file, each line should have 3 values");
        }
        graph.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Double.parseDouble(values[2]));
      }
      if (graph.numberOfEdges() != numberOfEdges) {
        throw new RuntimeException("Invalid CSV graph file, number of edges on first line does not match the provided edges");
      }
      return graph;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read graph from CSV file", exception);
    }
  }

  public static WeightedDirectedGraph readWeightedDirectedGraphFromCsvFile(String file) {
    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
      csvReader.skip(2);
      String[] values = csvReader.readNext();
      int numberOfVertices = Integer.parseInt(values[0]);
      int numberOfEdges = Integer.parseInt(values[1]);
      WeightedDirectedGraph graph = new WeightedDirectedGraph(numberOfVertices);
      while ((values = csvReader.readNext()) != null) {
        if (values.length != 3) {
          throw new RuntimeException("Invalid CSV graph file, each line should have 3 values");
        }
        graph.addEdge(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Double.parseDouble(values[2]));
      }
      if (graph.numberOfEdges() != numberOfEdges) {
        throw new RuntimeException("Invalid CSV graph file, number of edges on first line does not match the provided edges");
      }
      return graph;
    } catch (Exception exception) {
      throw new RuntimeException("Failed to read graph from CSV file", exception);
    }
  }

  public static <T> T generateRandomGraph(int numberOfVertices, int numberOfEdges, Class type) {
    if (type == UndirectedGraphV2.class) {
      UndirectedGraphV2 graph = new UndirectedGraphV2(numberOfVertices);
      for (int edge = 0; edge < numberOfEdges; edge++) {
        graph.addEdge(Utils.getRandomInteger(0, numberOfVertices - 1), Utils.getRandomInteger(0, numberOfVertices - 1));
      }
      return (T) graph;
    }
    throw new RuntimeException("Type not supported: " + type.getName());
  }

  public static boolean isIndependentSet(UndirectedGraph graph, List<Integer> independentSet) {
    for (int i = 0; i < independentSet.size(); i++) {
      for (int j = i + 1; j < independentSet.size(); j++) {
        if (graph.adjacencies(independentSet.get(i)).contains(independentSet.get(j))) {
          return false;
        }
      }
    }
    return true;
  }

  public static boolean isVertexCover(UndirectedGraphV2 graph, List<Integer> vertexCover) {
    Set<UndirectedGraphV2.Edge> notCoveredEdges = new HashSet<>();
    for (UndirectedGraphV2.Edge edge : graph.edges()) {
      notCoveredEdges.add(edge);
    }
    for (int vertex : vertexCover) {
      notCoveredEdges.removeIf(edge -> edge.vertex1() == vertex || edge.vertex2() == vertex);
      for (int adjVertex : graph.adjacencies(vertex)) {
        notCoveredEdges.removeIf(edge -> edge.vertex1() == adjVertex || edge.vertex2() == adjVertex);
      }
    }
    return notCoveredEdges.isEmpty();
  }
}
