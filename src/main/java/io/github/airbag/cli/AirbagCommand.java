package io.github.airbag.cli;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;

@Command(name = "airbag", mixinStandardHelpOptions = true, subcommands = {
        GenerateCommand.class,
        RunCommand.class})
public class AirbagCommand implements Runnable {

    @Spec
    CommandLine.Model.CommandSpec spec;

    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new AirbagCommand()).execute(args);
        if (!hasGui(args)) {
            System.exit(exitCode);
        }
    }

    private static boolean hasGui(String[] args) {
        for (String arg : args) {
            if (arg.equals("--gui")) {
                return true;
            }
        }
        return false;
    }
}
