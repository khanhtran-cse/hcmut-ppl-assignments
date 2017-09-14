/**
 * Student name: TRAN QUOC KHANH
 * Student ID: 1511524
 */
grammar MC;

@lexer::header{
	package mc.parser;
}

@lexer::members{
@Override
public Token emit() {
    switch (getType()) {
    case UNCLOSE_STRING:       
        Token result = super.emit();
        // you'll need to define this method
        throw new UncloseString(result.getText());
        
    case ILLEGAL_ESCAPE:
    	result = super.emit();
    	throw new IllegalEscape(result.getText());

    case ERROR_CHAR:
    	result = super.emit();
    	throw new ErrorToken(result.getText());	

    default:
        return super.emit();
    }
}
}

@parser::header{
	package mc.parser;
}

options{
	language=Java;
}

program  : declaration+ EOF;

declaration: variable_declaration|function_declaration;

variable_declaration:  primary_type list_of_variable SEMI;

function_declaration: return_type ID LB nullable_list_of_param RB LP nullable_body RP;

primary_type: BOOLEAN_TYPE | FLOAT_TYPE | INT_TYPE | STRING_TYPE;

list_of_variable: variable (COMMA variable)*;

return_type: primary_type|VOID_TYPE|array_type|array_ponter_type;

nullable_list_of_param: list_of_param?;

nullable_body: body?;

variable: ID (LSB INT_LIT RSB)?;

array_type:primary_type LSB INT_LIT RSB;

array_ponter_type: primary_type LSB RSB;

list_of_param: param (COMMA param)*;

param: primary_type ID (LSB RSB)?;

body: variable_declaration+;

/**
* Key word
*
*/

INT_TYPE: 'int' ;

VOID_TYPE: 'void' ;

BOOLEAN_TYPE: 'boolean';

FLOAT_TYPE: 'float';

STRING_TYPE: 'string';

BREAK: 'break';

CONTINUE: 'continue';

ELSE: 'else';

FOR: 'for';

IF: 'if';

RETURN:  'return';

DO: 'do';

WHILE: 'while';

/**
* Identifier
*
*/


ID: [a-zA-Z_][a-zA-Z0-9_]* ;

/**
*   Operator
*
*/
ADDITION: '+';

MULTIPLICATION: '*';

SUBTRACTION_OR_NEGATION: '-';

DIVISION: '/';

NOT: '!';

OR: '||';

AND: '&&';

EQUAL: '==';

NOT_EQUAL: '!=';

LESS: '<';

LESS_OR_EQUAL: '<=';

ASSIGN: '=';

MODULUS: '%';

GREATER: '>';

GREATER_OR_EQUAL: '>=';

/**
* Character
*
*/
LB: '(' ;

RB: ')' ;

LP: '{';

RP: '}';

LSB: '[';

RSB: ']';

SEMI: ';' ;

COMMA: ',';

/**
* Literal
*
*/
INT_LIT: [0-9]+;

BOOLEAN_LIT: 'false'|'true';

 FLOAT_LIT: [0-9]+'.'[0-9]*(('e'|'E')'-'?[0-9]+)? | 
                    [0-9]*'.'[0-9]+(('e'|'E')'-'?[0-9]+)? |
                    [0-9]+('e'|'E')'-'?[0-9]+;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

BLOCK_COMMENT: '/*'(~['*/'])*'*/' ->skip;//skip comment

LINE_COMMENT: '//'(~[\n\r])*->skip; //skip line comment

STRING_LIT: 
     '"'(ESC|~[\r\n"\\EOF])*'"' 
        {
            String s = getText();
            s = s.substring(1, s.length() - 1);
            setText(s);
        }
    ;

UNCLOSE_STRING: 
     '"'(ESC|~[\r\n"\\EOF])*EOF 
        {
            String s = getText();
            s = s.substring(1, s.length());
            setText(s);
        }
    ;


fragment ESC: '\\'[bfrnt'"\\];

ILLEGAL_ESCAPE:  '"'(ESC|~[\r\n"\\EOF])*~["EOF] 
        {
            String s = getText();
            s = s.substring(1, s.length());
            setText(s);
        }
    ;

ERROR_CHAR: .;
