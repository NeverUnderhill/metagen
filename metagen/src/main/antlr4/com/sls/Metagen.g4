/** Grammars always start with a grammar header. This grammar is called
 *  ArrayInit and must match the filename: ArrayInit.g4
 */
grammar Metagen;

/** A rule called init that matches comma-separated values between {...}. */
init  : (model)*;  // must match at least one value

model                   : MODEL '{' '}';

component               : COMPONENT IDENTIFIER '{' '}';
trait                   : TRAIT IDENTIFIER '{' (category)+ '}';
category                : category_name attribute_set;
category_name           : IDENTIFIER NUMBER NEWLINE;
attribute_set           : attribute*;
attribute               : normal_dist_attr
                        | uniform_dist_attr;
normal_dist_attr        : IDENTIFIER NUMBER NUMBER NEWLINE 
                        | IDENTIFIER 'n' NUMBER NUMBER NEWLINE;
uniform_dist_attr       : IDENTIFIER 'u' NUMBER NUMBER NEWLINE;


fragment DIGIT :[0-9];
fragment LETTER:[a-zA-Z];

MODEL       :   'model';
COMPONENT   :   'component';
TRAIT       :   'trait';
IDENTIFIER  :   LETTER(LETTER|DIGIT|'_')*;
NUMBER      :   DIGIT+([.]DIGIT+)?;                 // Define token float as .123123 or 12321.213123 
NEWLINE     :   ('\r'? '\n' | '\r')+;
COMMENT     :   '#'~('\n')*('\n'|'\r') -> skip;      // Inline comments start with # and end with newline
WS          :   [ \t\r]+ -> skip ;      // Define whitespace rule, toss it out
