package com.kingmanzhang;

import picocli.CommandLine;
import picocli.CommandLine.Command;


@Command(name = "app", mixinStandardHelpOptions = true, version = "1.0",
        description = "A command-line application with two subcommands: download and ontologize.")
public class UkbShowcaseOntologizer implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new UkbShowcaseOntologizer())
                .addSubcommand("download", new DownloadCommand())
                .addSubcommand("ontologize", new OntologizeCommand())
                .execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        // If no subcommand is provided, show usage help
        System.out.println("Please specify a subcommand: download or ontologize.");
        new CommandLine(this).usage(System.out);
    }

}
