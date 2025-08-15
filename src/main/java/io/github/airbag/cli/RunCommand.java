package io.github.airbag.cli;

import org.antlr.v4.gui.TestRig;
import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(
        name = "run",
        description = "Run a lexer/parser combo, optionally printing tree string or generating postscript file. Optionally taking input file.", mixinStandardHelpOptions = true)
public class RunCommand implements Runnable {

    @Parameters(index = "0", arity = "1", description = "The name of the grammar to use for parsing")
    private String grammarName;

    @Parameters(index = "1", arity = "1", description = "The name of the start rule to use as the entry point")
    private String startRuleName;

    @Option(names = "--tokens", description = "Print the token stream from the lexer")
    private boolean tokens;

    @Option(names = "--tree", description = "Print the parse tree in LISP-style format")
    private boolean tree;

    @Option(names = "--gui", description = "Visualize the parse tree in a dialog")
    private boolean gui;

    @Option(names = "--ps", description = "Generate a PostScript visualization of the parse tree", paramLabel = "<file.ps>")
    private String psFile;

    @Option(names = "--encoding", description = "Specify the input file encoding", paramLabel = "<name>")
    private String encoding;

    @Option(names = "--trace", description = "Print rule entry and exit events during parsing")
    private boolean diagnostics;

    @Option(names = "--SLL", description = "Use the SLL prediction mode for parsing")
    private boolean sll;

    @Parameters(index = "2", arity = "0..*", description = "The input file(s) to process. If not specified, reads from stdin.")
    List<String> filenames;

    @Override
    public void run() {
        List<String> args = new ArrayList<>();
        try {
            TestRig testRig = new TestRig(args.toArray(new String[0]));
            testRig.process();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}