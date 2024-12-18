package compiladorfinal;
import static compiladorfinal.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_$]+
D=[1-9]+
N=[0-9]*
S=  "\""+
C= \n+
espacio=[ \t\r]+
%{
    public String lexeme;
%}
%%
int |
then|
print|
read|
if |
else |
char |
float |
string |
while {lexeme=yytext(); return Reservadas;}
{espacio} {/*Ignore*/}
"//".* {/*Ignore*/}
"=" {return Igual;}
"+" {return Suma;}
"-" {return Resta;}
"*" {return Multiplicacion;}
"/" {return Division;}
">" {return mayor;}
"<" {return menor;}
";" {return punto_coma;}
"(" {return parentesisa;}
")" {return parentesisc;}
"<=" {return menorigual;}
">=" {return mayorigual;}
"==" {return igualdad;}
"!=" {return diferente;}
"{" {return llavea;}
"}" {return llavec;}





{L}({L}|{D})* {lexeme=yytext(); return id;}
("-"{D}{N}*)|({D}{N}*)|(("0"|{D}{N}*)".""0"*{D}{N}*)|("-"("0"|{D}*)".""0"*{D}{N}*) {return num;}
"0"                                           { return num;}
","                                         {return coma;}
("0"{N}+) {return ERROR;}
{S}.*{S} {lexeme=yytext(); return cadena;}
\'[^\']\' {lexeme=yytext(); return caracter;}
{C} {lexeme=yytext(); return SaltoLinea;}
 . {return ERROR;}
