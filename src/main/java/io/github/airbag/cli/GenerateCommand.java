package io.github.airbag.cli;

import org.antlr.v4.Tool;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.*;

import static java.util.Objects.nonNull;

@Command(
        name = "generate",
        description = "Generate parser and lexer from an ANTLR grammar file:", mixinStandardHelpOptions = true)
public class GenerateCommand implements Runnable {

    @Option(
            names = {"-o", "--out"},
            description = "The directory where generated files will be placed",
            defaultValue = Option.NULL_VALUE
    )
    private Path output;

    @Option(
            names = "--lib",
            description = "Specified location of grammar and tokens files",
            defaultValue = Option.NULL_VALUE
    )
    private Path library;

    @Option(
            names = "--atn",
            description = "Generate rule augmented transition network diagrams"
    )
    private boolean atn;

    @Option(
            names = "--encoding",
            description = "Specify grammar file encoding; e.g, euc-jp",
            paramLabel = "<enc>",
            defaultValue = Option.NULL_VALUE

    )
    private String encoding;

    @Option(
            names = "--message-format",
            description = "Specify output style for messages in antlr, gnu, vs2005",
            defaultValue = Option.NULL_VALUE
    )
    private String messageFormat;

    @Option(
            names = "--long-messages",
            description = "Show exception details when available for errors and warnings"
    )
    private boolean longMessages;

    @Option(
            names = "--listener",
            description = "Generate parse tree listener (default)",
            defaultValue = "true"
    )
    private boolean listener;

    @Option(
            names = "--no-listener",
            description = "Don't generate parse tree listener",
            defaultValue = "true"
    )
    private boolean noListener;

    @Option(
            names = "--visitor",
            description = "Generate a parse tree visitor"
    )
    private boolean visitor;

    @Option(
            names = "--no-visitor",
            description = "Don't generate parse tree visitor (default)",
            defaultValue = "true"
    )
    private boolean noVisitor;

    @Option(
            names = "--package",
            description = "Specify a package/namespace for the generated code"
    )
    private String namespace;

    @Option(
            names = "--depend",
            description = "Generate file dependencies"
    )
    private boolean depend;

    @Option(
            names = "-D",
            description = "Set/Override a grammar-level option",
            split = "=",
            paramLabel = "<option>=<value>"
    )
    private Map<String, String> options;

    @Option(
            names = "--Werror",
            description = "Treat warnings as errors"
    )
    private boolean wError;

    @Option(
            names = "--XdbgST",
            description = "Launch StringTemplate visualizer on generated code"
    )
    private boolean xDbgST;

    @Option(
            names = "--XdbgSTWait",
            description = "Wait for STViz to close before continuing"
    )
    private boolean xDbgSTWait;

    @Option(
            names = "--Xforce-atn",
            description = "Use the ATN simulator for all predications"
    )
    private boolean xForceAtn;

    @Option(
            names = "--Xlog",
            description = "Dump lots of logging info to antlr-timestamp.log"
    )
    private boolean xLog;

    @Option(
            names = "--Xexact-output-dir",
            description = "All output goes into -o dir regardless of path/package"
    )
    private boolean xExactOutputDir;

    @Parameters(
            arity = "1..*",
            description = "The ANTLR grammar file(s) (.g4) to process"
    )
    private List<Path> grammarFiles;

    @Override
    public void run() {
        List<String> args = new ArrayList<>();

        //Set arguments
        if (nonNull(output)) {
            args.add("-o");
            args.add(output.toString());
        }
        if (nonNull(library)) {
            args.add("-lib");
            args.add(library.toString());
        }
        if (atn) {
            args.add("-atn");
        }
        if (nonNull(encoding)) {
            args.add("-encoding");
            args.add(encoding);
        }
        if (nonNull(messageFormat)) {
            args.add("-message-format");
            args.add(messageFormat);
        }
        if (longMessages) {
            args.add("-long-messages");
        }
        if (listener) {
            args.add("-listener");
        }
        if (noListener) {
            args.add("-no-listener");
        }
        if (visitor) {
            args.add("-visitor");
        }
        if (noVisitor) {
            args.add("-no-visitor");
        }
        if (nonNull(namespace)) {
            args.add("-package");
            args.add(namespace);
        }
        if (depend) {
            args.add("-depend");
        }
        if (nonNull(options)) {
            for (var e : options.entrySet()) {
                args.add("-D%s=%s".formatted(e.getKey(), e.getValue()));
            }
        }
        if (wError) {
            args.add("-Werror");
        }
        if (xDbgST) {
            args.add("-XdbgST");
        }
        if (xDbgSTWait) {
            args.add("-XdbgSTWait");
        }
        if (xForceAtn) {
            args.add("-Xforce-atn");
        }
        if (xLog) {
            args.add("-Xlog");
        }
        if (xExactOutputDir) {
            args.add("-Xexact-output-dir");
        }

        for (Path grammarFile : grammarFiles) {
            args.add(grammarFile.toString());
        }

        Tool antlrTool = new Tool(args.toArray(new String[0]));
        antlrTool.processGrammarsOnCommandLine();
    }
}
