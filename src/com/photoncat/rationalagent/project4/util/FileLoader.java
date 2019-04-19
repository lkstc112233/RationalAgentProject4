package com.photoncat.rationalagent.project4.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
	public static LoadedData load(Reader source) {
		// Create a tokenizer, either by file or by text.
		ConvenientStreamTokenizer tokens = new ConvenientStreamTokenizer(source);
		// Compile the program and return.
        LoadedData data = null;
		try {
            data = load(tokens);
		} catch (IOException e) {
			System.err.println("File format error.");
			System.exit(-1);
		}
		return data;
	}
	/**
	 * Alias for text source.
	 */
	public static LoadedData load(String source) {
		return load(new StringReader(source));
	}
	/**
     * load a graph.
	 */
	private static LoadedData load(ConvenientStreamTokenizer tokens) throws IOException {
        // Parse probability of not changing.
        int token = tokens.nextToken();
        if (token != ConvenientStreamTokenizer.TT_NUMBER) {
            panic("Missing 'probability' field.");
        }
        double probabilityOfNotSwitching = tokens.nval;
        // Parse probability of numbers.
        token = tokens.nextToken();
        double[][] p = new double[3][];
        for (int i = 0; i < 2; ++i) {
            p[i] = parseProbabilityRow(tokens);
        }
        List<Integer> emissions = parseEmissions(tokens);
        return new LoadedData(probabilityOfNotSwitching, p, emissions);
    }
    /**
     * @throws IllegalStateException when file is corrupted.
     */
    private static double[] parseProbabilityRow(ConvenientStreamTokenizer tokens) throws IOException {
        double[] row = new double[3];
        int token = tokens.nextToken();
        if (token != ConvenientStreamTokenizer.TT_NUMBER) {
            panic("Missing 'probability' field.");
        }
        row[0] = tokens.nval;
        for (int i = 1; i < 3; ++i) {
            token = tokens.nextToken();
            if (token != ',') {
                panic("Invalid symbol between probabilities.");
            }
            token = tokens.nextToken();
            if (token != ConvenientStreamTokenizer.TT_NUMBER) {
                panic("Missing 'probability' field.");
            }
            row[i] = tokens.nval;
        }
        return row;
    }
    /**
     * @throws IllegalStateException when file is corrupted.
     */
    private static List<Integer> parseEmissions(ConvenientStreamTokenizer tokens) throws IOException {
        List<Integer> emissions = new ArrayList<>();
        // Parse the emissions array
        int token = tokens.nextToken();
        if (token != '[') {
            panic("Missing 'emissions' field.");
        }
        while (tokens.nextToken() == ConvenientStreamTokenizer.TT_NUMBER) {
            emissions.add((int) tokens.nval);
            if (tokens.nextToken() == ']') {
                break;
            }
        }
        return emissions;
    }
}
