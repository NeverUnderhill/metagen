package com.sls;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sls.generators.ComponentGenerator;
import com.sls.properties.Component;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Metagen {
    ComponentGenerator cg;

    private ComponentGenerator generateModelFromFile(String url) throws IOException {
        Path path = Paths.get(url);
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
        
        return listener.getModel();
    }
    
    public Metagen(String url) throws IOException {
        cg = generateModelFromFile(url);
    }
    
    public Component generate() {
        return cg.generate();
    }
}
