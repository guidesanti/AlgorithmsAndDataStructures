package br.com.eventhorizon.datastructures.graphs;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.HashSet;
import java.util.Set;

public class StringToSetOfWeightedUndirectedEdges extends SimpleArgumentConverter {

    @Override
    protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
        if (object == null) {
            return null;
        }
        if (!(object instanceof String)) {
            throw new IllegalArgumentException("object must be a String");
        }
        if (!Set.class.isAssignableFrom(targetType)) {
            throw new IllegalArgumentException("targetType must be Set");
        }
        if (((String) object).isEmpty()) {
            return null;
        }
        Set<WeightedUndirectedEdge> edges = new HashSet<>();
        String[] edgesStr = ((String) object).split(" ");
        if (edgesStr.length % 3 != 0) {
            throw new RuntimeException("Invalid list of edges");
        }
        for (int i = 0; i < edgesStr.length; i += 3) {
            edges.add(new WeightedUndirectedEdge(
                    Integer.parseInt(edgesStr[i]),
                    Integer.parseInt(edgesStr[i + 1]),
                    Double.parseDouble(edgesStr[i + 2])));
        }
        return edges;
    }
}
