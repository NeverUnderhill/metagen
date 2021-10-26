/** Grammars always start with a grammar header. This grammar is called
 *  ArrayInit and must match the filename: ArrayInit.g4
 */
grammar Metagen;

tokens {INDENT, DEDENT}

@lexer::header {
    import com.yuvalshavit.antlr4.DenterHelper;
}

@lexer::members {
    private final DenterHelper denter = new DenterHelper(NL, MetagenParser.INDENT, MetagenParser.DEDENT) {
        @Override
        public Token pullToken() {
          return MetagenLexer.super.nextToken();
        }
    };

    @Override
    public Token nextToken() {
        return denter.nextToken();
    }
}

init  : model;

model                   : MODEL ':' block;
block                   : INDENT (attribute|component|trait)+ DEDENT;
component               : IDENTIFIER list_modifier? ':' block;
trait                   : IDENTIFIER INDENT (category)+ DEDENT;
category                : '*' IDENTIFIER NUMBER (block|NL);
attribute               : IDENTIFIER modifier? NUMBER NUMBER NL;
modifier                : '[' IDENTIFIER ']';
list_modifier           : '[' NUMBER ']';

fragment DIGIT :[0-9];
fragment LETTER:[a-zA-Z];

MODEL       :   'model';
IDENTIFIER  :   LETTER(LETTER|DIGIT|'_')*;
NUMBER      :   DIGIT+([.]DIGIT+)?;                 // Define token float as .123123 or 12321.213123 
NL          :   ('\r'? '\n' ' '*);
WS          :   [ \t]+ -> skip ;
COMMENT     :   '#'~('\n')*NL -> skip;      // Inline comments start with # and end with newline