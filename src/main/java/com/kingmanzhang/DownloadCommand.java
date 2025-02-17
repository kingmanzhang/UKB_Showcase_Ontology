package com.kingmanzhang;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "download", description = "Download a file from a URL.")
public class DownloadCommand implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "The URL of the file to download.")
    private String url;

    @CommandLine.Option(names = {"-o", "--output"}, description = "The output file path.")
    private File outputFile;

    @Override
    public Integer call() throws Exception {
        System.out.println("Downloading file from: " + url);
        // Add your download logic here
        if (outputFile != null) {
            System.out.println("Saving to: " + outputFile.getAbsolutePath());
        } else {
            System.out.println("No output file specified. Using default name.");
        }
        return 0; // Success
    }
}
