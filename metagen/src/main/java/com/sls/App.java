package com.sls;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class App {
    public static void main(String[] args) throws Exception {
        // create a CharStream that reads from file
        Path path = Paths.get(ClassLoader.getSystemResource("ghost.sm").toURI());
        CharStream input = CharStreams.fromPath(path);

        // create a lexer that feeds off of input CharStream
        MetagenLexer lexer = new MetagenLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        MetagenParser parser = new MetagenParser(tokens);

        ParseTree tree = parser.init(); // begin parsing at init rule
        ParseTreeWalker walker = new ParseTreeWalker();
        MetagenListenerImpl listener = new MetagenListenerImpl();
        
        walker.walk(listener, tree);
    }
}
