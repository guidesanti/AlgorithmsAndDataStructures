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
}
