/** Grammars always start with a grammar header. This grammar is called
 *  ArrayInit and must match the filename: ArrayInit.g4
 */
grammar Metagen;

/** A rule called init that matches comma-separated values between {...}. */
init  : value*;  // must match at least one value

/** A value can be either a nested array/struct or a simple integer (INT) */
value : INT
      | FLOAT
      ;

FLOAT       :   INT?.INT;                 // Define token float as .123123 or 12321.213123 
INT         :   [0-9]+ ;                  // Define token INT as one or more digits
COMMENT     :   '#'~('\n')*'\n' -> skip;      // Inline comments start with # and end with newline
WS          :   [ \t\r\n]+ -> skip ;      // Define whitespace rule, toss it out
