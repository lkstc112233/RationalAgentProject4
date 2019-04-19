package com.photoncat.rationalagent.project4;

import com.photoncat.rationalagent.project4.util.FileLoader;
import com.photoncat.rationalagent.project4.util.LoadedData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CommandLineMain {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: ");
            System.err.println("CommandLineMain inputFilename");
            System.exit(0xDEADBEEF);
        }
        File inputFile = new File(args[0]);
        LoadedData data = null;
        try {
            data = FileLoader.load(new FileReader(inputFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // TODO: handle the file.
    }
}
