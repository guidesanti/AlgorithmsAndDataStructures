package br.com.eventhorizon.common.datastructures.graphs;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//  public static Map<Integer, List<Integer>> readDirectedGraphStronglyConnectedComponentsFromCsvFile(String file) {
//    try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
//      Map<Integer, List<Integer>> components = new HashMap<>();
//      csvReader.skip(1);
//      String[] values;
//      while ((values = csvReader.readNext()) != null) {
//        int id = Integer.parseInt(values[0]);
//        String[] verticesStr = values[1].split(" ");
//        List<Integer> vertices = new ArrayList<>();
//        for (String vertexStr : verticesStr) {
//          vertices.add(Integer.valueOf(vertexStr));
//        }
//        components.put(id, vertices);
//      }
//      return components;
//    } catch (Exception exception) {
//      throw new RuntimeException("Failed to read graph from CSV file", exception);
//    }
//  }
}
