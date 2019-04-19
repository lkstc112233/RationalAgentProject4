package com.photoncat.rationalagent.project4.util;

import java.io.*;

import static com.photoncat.rationalagent.project4.util.ExceptionHandling.panic;

/**
 * A class that loads a graph input file.
 * @author Xu Ke
 *
 */
public class FileLoader {
	/**
	 * Loads a graph form a stream.
	 */
	public static LoadedGraph load(Reader source) {
		// Create a tokenizer, either by file or by text.
		ConvenientStreamTokenizer tokens = new ConvenientStreamTokenizer(source);
		// Compile the program and return.
        LoadedGraph graph = null;
		try {
            graph = load(tokens);
		} catch (IOException e) {
			System.err.println("File format error.");
			System.exit(-1);
		}
		return graph;
	}
	/**
	 * Alias for text source.
	 */
	public static LoadedGraph load(String source) {
		return load(new StringReader(source));
	}
	/**
     * load a graph.
	 */
	private static LoadedGraph load(ConvenientStreamTokenizer tokens) throws IOException {
        LoadedGraph graph = new LoadedGraph();
        // Parse vertices.
        int token = tokens.nextToken();
        if (token != ConvenientStreamTokenizer.TT_WORD || !tokens.sval.equalsIgnoreCase("Vertices")) {
            panic("Missing 'Vertices' field.");
        }
        while (true) {
            if (!parseOptionalGraphNode(tokens, graph)) break;
        }
        graph.sortNodes();
        // Parse edges.
        token = tokens.nextToken();
        if (token != ConvenientStreamTokenizer.TT_WORD || !tokens.sval.equalsIgnoreCase("Edges")) {
            panic("Missing 'Edges' field.");
        }
        while (true) {
            if (!parseOptionalEdge(tokens, graph)) break;
        }
        graph.createEdgesCache();
        return graph;
    }
    /**
     * @throws IllegalStateException when file is corrupted.
     */
    private static boolean parseOptionalGraphNode(ConvenientStreamTokenizer tokens, LoadedGraph graph) throws IOException {
        if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER) {
            tokens.pushBack();
            return false;
        }
        int id = (int) tokens.nval;
        if (tokens.nextToken() != ',') {
            panic("Unexpected token at line " + tokens.lineno());
        }
        if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER) {
            panic("Unexpected token at line " + tokens.lineno());
        }
        int x = (int) tokens.nval;
        if (tokens.nextToken() != ',') {
            panic("Unexpected token at line " + tokens.lineno());
        }
        if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER) {
            panic("Unexpected token at line " + tokens.lineno());
        }
        int y = (int) tokens.nval;
        graph.addNode(id, x, y);
        return true;
    }
    /**
     * @throws IllegalStateException when file is corrupted.
     */
    private static boolean parseOptionalEdge(ConvenientStreamTokenizer tokens, LoadedGraph graph) throws IOException {
        if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER) {
            tokens.pushBack();
            return false;
        }
        int from = (int) tokens.nval;
        if (tokens.nextToken() != ',') {
            panic("Unexpected token at line " + tokens.lineno());
        }
        if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER) {
            panic("Unexpected token at line " + tokens.lineno());
        }
        int to = (int) tokens.nval;
        if (tokens.nextToken() != ',') {
            panic("Unexpected token at line " + tokens.lineno());
        }
        if (tokens.nextToken() != ConvenientStreamTokenizer.TT_NUMBER) {
            panic("Unexpected token at line " + tokens.lineno());
        }
        int distance = (int) tokens.nval;
        graph.addEdge(from, to, distance);
        return true;
    }
}
