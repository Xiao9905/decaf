//### This file created by BYACC 1.8(/Java extension  1.13)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//###           14 Sep 06  -- Keltin Leung-- ReduceListener support, eliminate underflow report in error recovery
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 11 "Parser.y"
package decaf.frontend;

import decaf.tree.Tree;
import decaf.tree.Tree.*;
import decaf.error.*;
import java.util.*;
//#line 25 "Parser.java"
interface ReduceListener {
  public boolean onReduce(String rule);
}




public class Parser
             extends BaseParser
             implements ReduceListener
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

ReduceListener reduceListener = null;
void yyclearin ()       {yychar = (-1);}
void yyerrok ()         {yyerrflag=0;}
void addReduceListener(ReduceListener l) {
  reduceListener = l;}


//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:SemValue
String   yytext;//user variable to return contextual strings
SemValue yyval; //used to return semantic vals from action routines
SemValue yylval;//the 'lval' (result) I got from yylex()
SemValue valstk[] = new SemValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new SemValue();
  yylval=new SemValue();
  valptr=-1;
}
final void val_push(SemValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    SemValue[] newstack = new SemValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final SemValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final SemValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short VOID=257;
public final static short BOOL=258;
public final static short INT=259;
public final static short STRING=260;
public final static short CLASS=261;
public final static short NULL=262;
public final static short EXTENDS=263;
public final static short THIS=264;
public final static short WHILE=265;
public final static short FOR=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short RETURN=269;
public final static short BREAK=270;
public final static short NEW=271;
public final static short PRINT=272;
public final static short READ_INTEGER=273;
public final static short READ_LINE=274;
public final static short LITERAL=275;
public final static short IDENTIFIER=276;
public final static short AND=277;
public final static short OR=278;
public final static short STATIC=279;
public final static short INSTANCEOF=280;
public final static short SEALED=281;
public final static short VAR=282;
public final static short IN=283;
public final static short LESS_EQUAL=284;
public final static short GREATER_EQUAL=285;
public final static short EQUAL=286;
public final static short NOT_EQUAL=287;
public final static short CONCAT=288;
public final static short SUBARRAY=289;
public final static short UMINUS=290;
public final static short ARRAYREPEAT=291;
public final static short EMPTY=292;
public final static short DEFAULT=293;
public final static short SCOPY=294;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    3,    4,    5,    5,    5,    5,    5,
    5,    2,    2,    6,    6,    7,    7,    7,    9,    9,
   10,   10,    8,    8,   11,   12,   12,   13,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   14,   14,   14,
   25,   25,   22,   22,   22,   24,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   23,   23,   27,   27,   26,   26,
   28,   28,   16,   17,   20,   15,   29,   29,   18,   18,
   19,   21,
};
final static short yylen[] = {                            2,
    1,    2,    1,    2,    2,    1,    1,    1,    1,    2,
    3,    6,    7,    2,    0,    2,    2,    0,    1,    0,
    3,    1,    7,    6,    3,    2,    0,    1,    2,    1,
    1,    1,    2,    2,    2,    1,    1,    3,    1,    0,
    2,    0,    2,    4,    2,    5,    1,    1,    1,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    2,    2,    3,    3,    1,    4,    5,
    6,    5,    3,    3,    6,    6,    1,    1,    1,    0,
    3,    1,    5,    9,    1,    6,    2,    0,    2,    1,
    4,    6,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    0,    2,    0,    0,
    0,   14,   18,    0,    0,   18,    7,    8,    6,    9,
    0,    0,   12,   16,    0,    0,   17,    0,   10,    0,
    4,    0,    0,   13,    0,    0,   11,    0,   22,    0,
    0,    0,    0,    5,    0,    0,    0,   27,   24,   21,
   23,    0,   78,   68,    0,    0,    0,    0,   85,    0,
    0,    0,    0,   77,    0,    0,    0,    0,    0,   25,
    0,   28,   36,   26,    0,   30,   31,   32,    0,    0,
    0,   37,    0,    0,    0,    0,   49,    0,    0,    0,
   47,    0,   48,    0,    0,    0,    0,    0,    0,   45,
    0,    0,    0,    0,    0,   29,   33,   34,   35,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   66,   67,    0,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,    0,    0,
    0,    0,   69,    0,    0,   91,    0,    0,    0,    0,
    0,    0,    0,   83,    0,    0,   70,    0,    0,   72,
    0,    0,    0,   46,    0,    0,   86,   71,   92,    0,
   75,    0,   87,    0,   84,
};
final static short yydgoto[] = {                          3,
    4,    5,   72,   25,   40,   10,   15,   27,   41,   42,
   73,   52,   74,   75,   76,   77,   78,   79,   80,   81,
   82,   91,   84,   93,   86,  172,   87,  135,  187,
};
final static short yysindex[] = {                      -246,
 -258, -239,    0, -246,    0, -235, -232,    0, -231,  -86,
 -235,    0,    0,  -70,   50,    0,    0,    0,    0,    0,
 -215,  -81,    0,    0,   16,  -83,    0,   83,    0,  -75,
    0,   43,   -9,    0,   45,  -81,    0,  -81,    0,  -74,
   46,   44,   48,    0,  -32,  -81,  -32,    0,    0,    0,
    0,   -2,    0,    0,   53,   58,   59,  460,    0, -109,
   62,   63,   65,    0,   66, -168,  460,  460,  383,    0,
   78,    0,    0,    0,   51,    0,    0,    0,   68,   75,
   76,    0,   61,  518,    0, -152,    0,  460,  460,  460,
    0,  518,    0,   85,   49,  460,   95,  103,  460,    0,
  -42,  -42, -139,  153, -131,    0,    0,    0,    0,  460,
  460,  460,  460,  460,  460,  460,  460,  460,  460,  460,
  460,  460,  460,    0,  460,  460, -243,  106,  405,  100,
  417,  114,  729,  518,   -8,    0,    0,  429,  121,    0,
  127,  518,  569,  558,    9,    9,  580,  580,  -17,  -17,
  -43,  -43,  -43,    9,    9,   96,  580,    0,  460,   24,
  460,   24,    0,  441,  460,    0, -103,  460,  460, -119,
  460,  140,  142,    0,  467,  -84,    0,  518,  147,    0,
  494,  460,  506,    0,  460,   24,    0,    0,    0,  518,
    0,  150,    0,   24,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  192,    0,   74,    0,    0,    0,    0,
   74,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  144,    0,    0,    0,  163,    0,  163,    0,    0,
    0,  164,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -58,    0,    0,    0,    0,    0,  -57,    0,    0,
    0,    0,    0,    0,    0,    0,  -69,  -69,  -69,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  530,    0,  123,    0,    0,  -69,  -58,  -69,
    0,  151,    0,    0,    0,  -69,    0,    0,  -69,    0,
  591,  844,    0,    0,    0,    0,    0,    0,    0,  -69,
  -69,  -69,  -69,  -69,  -69,  -69,  -69,  -69,  -69,  -69,
  -69,  -69,  -69,    0,  -69,  -69,    0,   35,    0,    0,
    0,    0,  -69,   22,    0,    0,    0,    0,    0,    0,
    0,  -36,   60,   27,  789,  891,  113,  618,  953,  975,
  868,  897,  921,  980,  997,    0,  858,    0,  -31,  -58,
  -69,  -58,    0,    0,  -69,    0,    0,  -69,  -69,   70,
  -69,    0,  168,    0,    0,  -33,    0,   26,    0,    0,
    0,  -69,    0,    0,  -30,  -58,    0,    0,    0,  -35,
    0,    0,    0,  -58,    0,
};
final static short yygindex[] = {                         0,
    0,  207,   19,    4,   -1,  201,  200,    0,  179,    0,
   15,    0, -121,  -76,    0,    0,    0,    0,    0,    0,
    0,  481, 1212,  761,    0,    0,   93,   64,    0,
};
final static int YYTABLESIZE=1394;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         88,
   40,   90,  124,  124,   38,   76,   88,   33,   76,   80,
   40,   88,  130,   26,    1,   33,   33,    6,   53,  121,
   30,    7,   38,   76,  119,   88,   26,    9,  124,  120,
   68,   64,  166,   24,    2,  165,   13,   69,  174,   39,
  176,   39,   67,   11,   12,  121,   24,  125,  125,   50,
  119,  117,   16,  118,  124,  120,   68,   76,   95,   49,
   29,   51,   82,   69,  193,   82,   81,   62,   67,   81,
   62,   43,  195,  125,   31,   43,   43,   43,   43,   43,
   43,   43,   36,   37,   38,   62,   45,   46,   47,   88,
   48,   88,   88,   43,   43,   43,   43,   89,   90,  125,
   61,   96,   97,   61,   98,   99,   44,  100,  192,  106,
   44,   44,   44,   44,   44,   44,   44,  105,   61,   62,
   48,  110,   70,  128,  132,   43,  107,   43,   44,   44,
   44,   44,  121,  108,  109,  136,  139,  119,  117,  133,
  118,  124,  120,  137,  141,  159,   48,   17,   18,   19,
   20,   21,   61,   55,  163,  123,   55,  122,  161,   48,
   44,  168,   44,   39,   48,   48,   94,   48,   48,   48,
  169,   55,  179,  182,   23,   17,   18,   19,   20,   21,
  184,   39,   48,  186,   48,  165,  125,  188,  170,  121,
  194,    1,   32,  140,  119,  117,   15,  118,  124,  120,
   35,   44,    5,   20,   19,   55,   42,   34,   79,   89,
    8,   14,  123,   48,  122,   28,   43,   42,   42,  158,
    0,    0,  173,   88,   88,   88,   88,   88,   88,    0,
   88,   88,   88,   88,    0,   88,   88,   88,   88,   88,
   88,   88,   88,  125,   42,   42,   88,  127,   88,    0,
    0,    0,    0,   76,   17,   18,   19,   20,   21,   53,
   88,   54,   55,   56,   57,    0,   58,   59,   60,   61,
   62,   63,   64,  127,    0,    0,    0,   65,    0,   66,
   17,   18,   19,   20,   21,   53,    0,   54,   55,   56,
   57,   71,   58,   59,   60,   61,   62,   63,   64,  127,
    0,    0,    0,   65,   62,   66,   17,   18,   19,   20,
   21,   43,   43,    0,    0,   62,    0,   71,   43,   43,
   43,   43,   43,   43,    0,   43,    0,    0,   22,    0,
    0,    0,    0,    0,    0,    0,   61,   61,    0,   17,
   18,   19,   20,   21,    0,    0,   44,   44,   61,    0,
    0,    0,    0,   44,   44,   44,   44,   44,   44,    0,
   44,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  111,  112,    0,    0,    0,    0,    0,  113,
  114,  115,  116,  126,  171,    0,  127,    0,    0,   55,
   55,    0,    0,    0,    0,    0,    0,    0,    0,   48,
   48,   55,    0,    0,    0,    0,   48,   48,   48,   48,
   48,    0,    0,   48,    0,   68,    0,    0,    0,    0,
    0,    0,   69,    0,    0,    0,    0,   67,    0,  111,
  112,    0,    0,    0,    0,    0,  113,  114,  115,  116,
  126,  121,    0,  127,    0,  160,  119,  117,    0,  118,
  124,  120,    0,  121,    0,    0,    0,  162,  119,  117,
    0,  118,  124,  120,  123,  121,  122,    0,    0,    0,
  119,  117,  167,  118,  124,  120,  123,  121,  122,    0,
    0,    0,  119,  117,    0,  118,  124,  120,  123,    0,
  122,    0,   68,    0,    0,  125,    0,    0,    0,   69,
  123,    0,  122,  121,   67,    0,    0,  125,  119,  117,
    0,  118,  124,  120,    0,    0,    0,    0,    0,  125,
    0,    0,    0,    0,    0,  185,  123,    0,  122,    0,
  121,  125,   83,  177,  189,  119,  117,    0,  118,  124,
  120,    0,  121,    0,    0,    0,    0,  119,  117,    0,
  118,  124,  120,  123,  121,  122,    0,  125,    0,  119,
  117,    0,  118,  124,  120,  123,   47,  122,    0,   83,
    0,   47,   47,    0,   47,   47,   47,  123,    0,  122,
    0,    0,    0,    0,  125,    0,    0,    0,    0,   47,
    0,   47,    0,    0,  121,    0,  125,    0,  191,  119,
  117,    0,  118,  124,  120,  121,    0,    0,  125,    0,
  119,  117,    0,  118,  124,  120,  121,  123,    0,  122,
   47,  119,  117,    0,  118,  124,  120,   64,  123,    0,
  122,   64,   64,   64,   64,   64,    0,   64,    0,  123,
   83,  122,   83,  103,   53,    0,   54,    0,  125,   64,
   64,    0,   64,   60,    0,   62,   63,   64,   56,  125,
    0,   56,   65,    0,   66,   83,   83,    0,    0,    0,
  125,    0,    0,    0,   83,    0,   56,    0,    0,    0,
    0,  111,  112,   64,    0,    0,    0,    0,  113,  114,
  115,  116,  126,  111,  112,  127,    0,    0,    0,    0,
  113,  114,  115,  116,  126,  111,  112,  127,    0,    0,
   56,    0,  113,  114,  115,  116,  126,  111,  112,  127,
    0,   53,    0,   54,  113,  114,  115,  116,  126,    0,
   60,  127,   62,   63,   64,    0,    0,    0,    0,   65,
    0,   66,    0,  111,  112,    0,    0,    0,    0,    0,
  113,  114,  115,  116,  126,    0,    0,  127,    0,    0,
    0,   68,    0,    0,    0,    0,    0,    0,   69,    0,
  111,  112,    0,   67,    0,    0,    0,  113,  114,  115,
  116,  126,  111,  112,  127,    0,    0,    0,    0,  113,
  114,  115,  116,  126,  111,  112,  127,    0,    0,    0,
    0,  113,  114,  115,  116,  126,   47,   47,  127,    0,
    0,    0,   85,   47,   47,   47,   47,   47,    0,    0,
   47,   37,    0,    0,    0,    0,    0,    0,    0,   59,
    0,    0,   59,    0,  111,    0,    0,    0,    0,    0,
    0,  113,  114,  115,  116,  126,    0,   59,  127,   85,
    0,    0,  113,  114,  115,  116,  126,    0,    0,  127,
    0,    0,    0,  113,  114,    0,    0,   64,   64,    0,
  127,    0,    0,    0,   64,   64,   64,   64,   64,   64,
   65,   59,    0,    0,   65,   65,   65,   65,   65,    0,
   65,    0,    0,    0,   56,   56,    0,    0,   74,    0,
    0,   74,   65,   65,   52,   65,   56,    0,   52,   52,
   52,   52,   52,    0,   52,    0,   74,    0,    0,    0,
   85,    0,   85,    0,    0,    0,   52,   52,    0,   52,
    0,   60,    0,   53,   60,    0,   65,   53,   53,   53,
   53,   53,    0,   53,    0,   85,   85,    0,    0,   60,
   74,    0,    0,    0,   85,   53,   53,   54,   53,    0,
   52,   54,   54,   54,   54,   54,    0,   54,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
   54,    0,   54,   60,    0,    0,    0,    0,    0,   53,
   53,    0,   54,   50,    0,   50,   50,   50,    0,   60,
    0,   62,   63,   64,    0,    0,    0,    0,   65,    0,
   66,   50,   50,   54,   50,   51,    0,   51,   51,   51,
   58,    0,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   51,   51,    0,   51,   57,   58,    0,
   57,    0,    0,    0,    0,   50,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   57,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   59,   59,   51,    0,    0,
    0,    0,   58,    0,   59,   59,   59,   59,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   57,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,   65,    0,    0,    0,    0,    0,   65,   65,   65,
   65,   65,   65,    0,   74,   74,    0,    0,    0,    0,
    0,    0,    0,    0,   52,   52,   74,    0,    0,    0,
    0,   52,   52,   52,   52,   52,   52,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   60,   60,    0,
    0,    0,    0,   53,   53,    0,   60,   60,   60,   60,
   53,   53,   53,   53,   53,   53,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   54,   54,    0,
    0,    0,    0,    0,   54,   54,   54,   54,   54,   54,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   50,
   50,    0,    0,    0,    0,    0,   50,   50,   50,   50,
   50,   50,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   51,   51,    0,    0,    0,   58,   58,   51,   51,
   51,   51,   51,   51,    0,   58,   58,   58,   58,   92,
    0,    0,    0,   57,   57,    0,    0,    0,  101,  102,
  104,    0,   57,   57,   57,   57,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  129,
    0,  131,    0,    0,    0,    0,    0,  134,    0,    0,
  138,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  142,  143,  144,  145,  146,  147,  148,  149,  150,
  151,  152,  153,  154,  155,    0,  156,  157,    0,    0,
    0,    0,    0,    0,  164,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  134,    0,  175,    0,    0,    0,  178,    0,    0,  180,
  181,    0,  183,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  190,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   59,   59,   46,   46,   41,   41,   40,   91,   44,   41,
   41,   45,   89,   15,  261,   91,   91,  276,  262,   37,
   22,  261,   59,   59,   42,   59,   28,  263,   46,   47,
   33,  275,   41,   15,  281,   44,  123,   40,  160,   36,
  162,   38,   45,  276,  276,   37,   28,   91,   91,   46,
   42,   43,  123,   45,   46,   47,   33,   93,   60,   45,
  276,   47,   41,   40,  186,   44,   41,   41,   45,   44,
   44,   37,  194,   91,   59,   41,   42,   43,   44,   45,
   46,   47,   40,   93,   40,   59,   41,   44,   41,  123,
  123,  125,   40,   59,   60,   61,   62,   40,   40,   91,
   41,   40,   40,   44,   40,   40,   37,  276,  185,   59,
   41,   42,   43,   44,   45,   46,   47,   40,   59,   93,
  123,   61,  125,  276,   40,   91,   59,   93,   59,   60,
   61,   62,   37,   59,   59,   41,  276,   42,   43,   91,
   45,   46,   47,   41,  276,   40,  123,  257,  258,  259,
  260,  261,   93,   41,   41,   60,   44,   62,   59,   37,
   91,   41,   93,   41,   42,   43,  276,   45,   46,   47,
   44,   59,  276,  293,  125,  257,  258,  259,  260,  261,
   41,   59,   60,  268,   62,   44,   91,   41,   93,   37,
   41,    0,  276,   41,   42,   43,  123,   45,   46,   47,
  276,  276,   59,   41,   41,   93,  276,  125,   41,   59,
    4,   11,   60,   91,   62,   16,   38,  276,  276,  127,
   -1,   -1,  159,  257,  258,  259,  260,  261,  262,   -1,
  264,  265,  266,  267,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,   91,  276,  276,  280,  291,  282,   -1,
   -1,   -1,   -1,  289,  257,  258,  259,  260,  261,  262,
  294,  264,  265,  266,  267,   -1,  269,  270,  271,  272,
  273,  274,  275,  291,   -1,   -1,   -1,  280,   -1,  282,
  257,  258,  259,  260,  261,  262,   -1,  264,  265,  266,
  267,  294,  269,  270,  271,  272,  273,  274,  275,  291,
   -1,   -1,   -1,  280,  278,  282,  257,  258,  259,  260,
  261,  277,  278,   -1,   -1,  289,   -1,  294,  284,  285,
  286,  287,  288,  289,   -1,  291,   -1,   -1,  279,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,  257,
  258,  259,  260,  261,   -1,   -1,  277,  278,  289,   -1,
   -1,   -1,   -1,  284,  285,  286,  287,  288,  289,   -1,
  291,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,  284,
  285,  286,  287,  288,  289,   -1,  291,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,  289,   -1,   -1,   -1,   -1,  284,  285,  286,  287,
  288,   -1,   -1,  291,   -1,   33,   -1,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   -1,   -1,   -1,   45,   -1,  277,
  278,   -1,   -1,   -1,   -1,   -1,  284,  285,  286,  287,
  288,   37,   -1,  291,   -1,   41,   42,   43,   -1,   45,
   46,   47,   -1,   37,   -1,   -1,   -1,   41,   42,   43,
   -1,   45,   46,   47,   60,   37,   62,   -1,   -1,   -1,
   42,   43,   44,   45,   46,   47,   60,   37,   62,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   60,   -1,
   62,   -1,   33,   -1,   -1,   91,   -1,   -1,   -1,   40,
   60,   -1,   62,   37,   45,   -1,   -1,   91,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   91,
   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,   62,   -1,
   37,   91,   52,   93,   41,   42,   43,   -1,   45,   46,
   47,   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,
   45,   46,   47,   60,   37,   62,   -1,   91,   -1,   42,
   43,   -1,   45,   46,   47,   60,   37,   62,   -1,   89,
   -1,   42,   43,   -1,   45,   46,   47,   60,   -1,   62,
   -1,   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   60,
   -1,   62,   -1,   -1,   37,   -1,   91,   -1,   93,   42,
   43,   -1,   45,   46,   47,   37,   -1,   -1,   91,   -1,
   42,   43,   -1,   45,   46,   47,   37,   60,   -1,   62,
   91,   42,   43,   -1,   45,   46,   47,   37,   60,   -1,
   62,   41,   42,   43,   44,   45,   -1,   47,   -1,   60,
  160,   62,  162,  261,  262,   -1,  264,   -1,   91,   59,
   60,   -1,   62,  271,   -1,  273,  274,  275,   41,   91,
   -1,   44,  280,   -1,  282,  185,  186,   -1,   -1,   -1,
   91,   -1,   -1,   -1,  194,   -1,   59,   -1,   -1,   -1,
   -1,  277,  278,   93,   -1,   -1,   -1,   -1,  284,  285,
  286,  287,  288,  277,  278,  291,   -1,   -1,   -1,   -1,
  284,  285,  286,  287,  288,  277,  278,  291,   -1,   -1,
   93,   -1,  284,  285,  286,  287,  288,  277,  278,  291,
   -1,  262,   -1,  264,  284,  285,  286,  287,  288,   -1,
  271,  291,  273,  274,  275,   -1,   -1,   -1,   -1,  280,
   -1,  282,   -1,  277,  278,   -1,   -1,   -1,   -1,   -1,
  284,  285,  286,  287,  288,   -1,   -1,  291,   -1,   -1,
   -1,   33,   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,
  277,  278,   -1,   45,   -1,   -1,   -1,  284,  285,  286,
  287,  288,  277,  278,  291,   -1,   -1,   -1,   -1,  284,
  285,  286,  287,  288,  277,  278,  291,   -1,   -1,   -1,
   -1,  284,  285,  286,  287,  288,  277,  278,  291,   -1,
   -1,   -1,   52,  284,  285,  286,  287,  288,   -1,   -1,
  291,   93,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,
   -1,   -1,   44,   -1,  277,   -1,   -1,   -1,   -1,   -1,
   -1,  284,  285,  286,  287,  288,   -1,   59,  291,   89,
   -1,   -1,  284,  285,  286,  287,  288,   -1,   -1,  291,
   -1,   -1,   -1,  284,  285,   -1,   -1,  277,  278,   -1,
  291,   -1,   -1,   -1,  284,  285,  286,  287,  288,  289,
   37,   93,   -1,   -1,   41,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,  277,  278,   -1,   -1,   41,   -1,
   -1,   44,   59,   60,   37,   62,  289,   -1,   41,   42,
   43,   44,   45,   -1,   47,   -1,   59,   -1,   -1,   -1,
  160,   -1,  162,   -1,   -1,   -1,   59,   60,   -1,   62,
   -1,   41,   -1,   37,   44,   -1,   93,   41,   42,   43,
   44,   45,   -1,   47,   -1,  185,  186,   -1,   -1,   59,
   93,   -1,   -1,   -1,  194,   59,   60,   37,   62,   -1,
   93,   41,   42,   43,   44,   45,   -1,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   60,   -1,   62,   93,   -1,   -1,   -1,   -1,   -1,   93,
  262,   -1,  264,   41,   -1,   43,   44,   45,   -1,  271,
   -1,  273,  274,  275,   -1,   -1,   -1,   -1,  280,   -1,
  282,   59,   60,   93,   62,   41,   -1,   43,   44,   45,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   41,   59,   -1,
   44,   -1,   -1,   -1,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  277,  278,   93,   -1,   -1,
   -1,   -1,   93,   -1,  286,  287,  288,  289,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   93,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  277,  278,   -1,   -1,   -1,   -1,   -1,  284,  285,  286,
  287,  288,  289,   -1,  277,  278,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  277,  278,  289,   -1,   -1,   -1,
   -1,  284,  285,  286,  287,  288,  289,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,  277,  278,   -1,  286,  287,  288,  289,
  284,  285,  286,  287,  288,  289,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,  278,   -1,
   -1,   -1,   -1,   -1,  284,  285,  286,  287,  288,  289,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  277,
  278,   -1,   -1,   -1,   -1,   -1,  284,  285,  286,  287,
  288,  289,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  277,  278,   -1,   -1,   -1,  277,  278,  284,  285,
  286,  287,  288,  289,   -1,  286,  287,  288,  289,   58,
   -1,   -1,   -1,  277,  278,   -1,   -1,   -1,   67,   68,
   69,   -1,  286,  287,  288,  289,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   88,
   -1,   90,   -1,   -1,   -1,   -1,   -1,   96,   -1,   -1,
   99,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  110,  111,  112,  113,  114,  115,  116,  117,  118,
  119,  120,  121,  122,  123,   -1,  125,  126,   -1,   -1,
   -1,   -1,   -1,   -1,  133,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  159,   -1,  161,   -1,   -1,   -1,  165,   -1,   -1,  168,
  169,   -1,  171,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  182,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=294;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"VOID","BOOL","INT","STRING",
"CLASS","NULL","EXTENDS","THIS","WHILE","FOR","IF","ELSE","RETURN","BREAK",
"NEW","PRINT","READ_INTEGER","READ_LINE","LITERAL","IDENTIFIER","AND","OR",
"STATIC","INSTANCEOF","SEALED","VAR","IN","LESS_EQUAL","GREATER_EQUAL","EQUAL",
"NOT_EQUAL","CONCAT","SUBARRAY","UMINUS","ARRAYREPEAT","EMPTY","DEFAULT",
"SCOPY",
};
final static String yyrule[] = {
"$accept : Program",
"Program : ClassList",
"ClassList : ClassList ClassDef",
"ClassList : ClassDef",
"VariableDef : Variable ';'",
"Variable : Type IDENTIFIER",
"Type : INT",
"Type : VOID",
"Type : BOOL",
"Type : STRING",
"Type : CLASS IDENTIFIER",
"Type : Type '[' ']'",
"ClassDef : CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ClassDef : SEALED CLASS IDENTIFIER ExtendsClause '{' FieldList '}'",
"ExtendsClause : EXTENDS IDENTIFIER",
"ExtendsClause :",
"FieldList : FieldList VariableDef",
"FieldList : FieldList FunctionDef",
"FieldList :",
"Formals : VariableList",
"Formals :",
"VariableList : VariableList ',' Variable",
"VariableList : Variable",
"FunctionDef : STATIC Type IDENTIFIER '(' Formals ')' StmtBlock",
"FunctionDef : Type IDENTIFIER '(' Formals ')' StmtBlock",
"StmtBlock : '{' StmtList '}'",
"StmtList : StmtList Stmt",
"StmtList :",
"Stmt : VariableDef",
"Stmt : SimpleStmt ';'",
"Stmt : IfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : ReturnStmt ';'",
"Stmt : PrintStmt ';'",
"Stmt : BreakStmt ';'",
"Stmt : StmtBlock",
"Stmt : OCStmt",
"SimpleStmt : LValue '=' Expr",
"SimpleStmt : Call",
"SimpleStmt :",
"Receiver : Expr '.'",
"Receiver :",
"LValue : Receiver IDENTIFIER",
"LValue : Expr '[' Expr ']'",
"LValue : VAR IDENTIFIER",
"Call : Receiver IDENTIFIER '(' Actuals ')'",
"Expr : LValue",
"Expr : Call",
"Expr : Constant",
"Expr : Expr '+' Expr",
"Expr : Expr '-' Expr",
"Expr : Expr '*' Expr",
"Expr : Expr '/' Expr",
"Expr : Expr '%' Expr",
"Expr : Expr EQUAL Expr",
"Expr : Expr NOT_EQUAL Expr",
"Expr : Expr '<' Expr",
"Expr : Expr '>' Expr",
"Expr : Expr LESS_EQUAL Expr",
"Expr : Expr GREATER_EQUAL Expr",
"Expr : Expr AND Expr",
"Expr : Expr OR Expr",
"Expr : '(' Expr ')'",
"Expr : '-' Expr",
"Expr : '!' Expr",
"Expr : READ_INTEGER '(' ')'",
"Expr : READ_LINE '(' ')'",
"Expr : THIS",
"Expr : NEW IDENTIFIER '(' ')'",
"Expr : NEW Type '[' Expr ']'",
"Expr : INSTANCEOF '(' Expr ',' IDENTIFIER ')'",
"Expr : '(' CLASS IDENTIFIER ')' Expr",
"Expr : Expr ARRAYREPEAT Constant",
"Expr : Expr CONCAT Expr",
"Expr : Expr '[' Expr SUBARRAY Expr ']'",
"Expr : Expr '[' Expr ']' DEFAULT Expr",
"Constant : LITERAL",
"Constant : NULL",
"Actuals : ExprList",
"Actuals :",
"ExprList : ExprList ',' Expr",
"ExprList : Expr",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' SimpleStmt ';' Expr ';' SimpleStmt ')' Stmt",
"BreakStmt : BREAK",
"IfStmt : IF '(' Expr ')' Stmt ElseClause",
"ElseClause : ELSE Stmt",
"ElseClause :",
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"PrintStmt : PRINT '(' ExprList ')'",
"OCStmt : SCOPY '(' IDENTIFIER ',' Expr ')'",
};

//#line 457 "Parser.y"
    
	/**
	 * 打印当前归约所用的语法规则<br>
	 * 请勿修改。
	 */
    public boolean onReduce(String rule) {
		if (rule.startsWith("$$"))
			return false;
		else
			rule = rule.replaceAll(" \\$\\$\\d+", "");

   	    if (rule.endsWith(":"))
    	    System.out.println(rule + " <empty>");
   	    else
			System.out.println(rule);
		return false;
    }
    
    public void diagnose() {
		addReduceListener(this);
		yyparse();
	}
//#line 681 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    //if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      //if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        //if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        //if (yychar < 0)    //it it didn't work/error
        //  {
        //  yychar = 0;      //change it to default string (no -1!)
          //if (yydebug)
          //  yylexdebug(yystate,yychar);
        //  }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        //if (yydebug)
          //debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      //if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0 || valptr<0)   //check for under & overflow here
            {
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            //if (yydebug)
              //debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            //if (yydebug)
              //debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0 || valptr<0)   //check for under & overflow here
              {
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        //if (yydebug)
          //{
          //yys = null;
          //if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          //if (yys == null) yys = "illegal-symbol";
          //debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          //}
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    //if (yydebug)
      //debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    if (reduceListener == null || reduceListener.onReduce(yyrule[yyn])) // if intercepted!
      switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 52 "Parser.y"
{
						tree = new Tree.TopLevel(val_peek(0).clist, val_peek(0).loc);
					}
break;
case 2:
//#line 58 "Parser.y"
{
						yyval.clist.add(val_peek(0).cdef);
					}
break;
case 3:
//#line 62 "Parser.y"
{
                		yyval.clist = new ArrayList<Tree.ClassDef>();
                		yyval.clist.add(val_peek(0).cdef);
                	}
break;
case 5:
//#line 72 "Parser.y"
{
						yyval.vdef = new Tree.VarDef(val_peek(0).ident, val_peek(1).type, val_peek(0).loc);
					}
break;
case 6:
//#line 79 "Parser.y"
{
						yyval.type = new Tree.TypeIdent(Tree.INT, val_peek(0).loc);
					}
break;
case 7:
//#line 83 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.VOID, val_peek(0).loc);
                	}
break;
case 8:
//#line 87 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.BOOL, val_peek(0).loc);
                	}
break;
case 9:
//#line 91 "Parser.y"
{
                		yyval.type = new Tree.TypeIdent(Tree.STRING, val_peek(0).loc);
                	}
break;
case 10:
//#line 95 "Parser.y"
{
                		yyval.type = new Tree.TypeClass(val_peek(0).ident, val_peek(1).loc);
                	}
break;
case 11:
//#line 99 "Parser.y"
{
                		yyval.type = new Tree.TypeArray(val_peek(2).type, val_peek(2).loc);
                	}
break;
case 12:
//#line 105 "Parser.y"
{
						yyval.cdef = new Tree.ClassDef(false, val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
					}
break;
case 13:
//#line 109 "Parser.y"
{
				        yyval.cdef = new Tree.ClassDef(true, val_peek(4).ident, val_peek(3).ident, val_peek(1).flist, val_peek(5).loc);
				    }
break;
case 14:
//#line 115 "Parser.y"
{
						yyval.ident = val_peek(0).ident;
					}
break;
case 15:
//#line 119 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 16:
//#line 125 "Parser.y"
{
						yyval.flist.add(val_peek(0).vdef);
					}
break;
case 17:
//#line 129 "Parser.y"
{
						yyval.flist.add(val_peek(0).fdef);
					}
break;
case 18:
//#line 133 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.flist = new ArrayList<Tree>();
                	}
break;
case 20:
//#line 141 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.vlist = new ArrayList<Tree.VarDef>(); 
                	}
break;
case 21:
//#line 148 "Parser.y"
{
						yyval.vlist.add(val_peek(0).vdef);
					}
break;
case 22:
//#line 152 "Parser.y"
{
                		yyval.vlist = new ArrayList<Tree.VarDef>();
						yyval.vlist.add(val_peek(0).vdef);
                	}
break;
case 23:
//#line 159 "Parser.y"
{
						yyval.fdef = new MethodDef(true, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 24:
//#line 163 "Parser.y"
{
						yyval.fdef = new MethodDef(false, val_peek(4).ident, val_peek(5).type, val_peek(2).vlist, (Block) val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 25:
//#line 169 "Parser.y"
{
						yyval.stmt = new Block(val_peek(1).slist, val_peek(2).loc);
					}
break;
case 26:
//#line 175 "Parser.y"
{
						yyval.slist.add(val_peek(0).stmt);
					}
break;
case 27:
//#line 179 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.slist = new ArrayList<Tree>();
                	}
break;
case 28:
//#line 186 "Parser.y"
{
						yyval.stmt = val_peek(0).vdef;
					}
break;
case 29:
//#line 191 "Parser.y"
{
                		if (yyval.stmt == null) {
                			yyval.stmt = new Tree.Skip(val_peek(0).loc);
                		}
                	}
break;
case 38:
//#line 208 "Parser.y"
{
						yyval.stmt = new Tree.Assign(val_peek(2).lvalue, val_peek(0).expr, val_peek(1).loc);
					}
break;
case 39:
//#line 212 "Parser.y"
{
                		yyval.stmt = new Tree.Exec(val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 40:
//#line 216 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 42:
//#line 223 "Parser.y"
{
                		yyval = new SemValue();
                	}
break;
case 43:
//#line 229 "Parser.y"
{
						yyval.lvalue = new Tree.Ident(val_peek(1).expr, val_peek(0).ident, val_peek(0).loc);
						if (val_peek(1).loc == null) {
							yyval.loc = val_peek(0).loc;
						}
					}
break;
case 44:
//#line 236 "Parser.y"
{
                		yyval.lvalue = new Tree.Indexed(val_peek(3).expr, val_peek(1).expr, val_peek(3).loc);
                	}
break;
case 45:
//#line 240 "Parser.y"
{
                        yyval.lvalue = new Tree.VarIdent(val_peek(0).ident, val_peek(0).loc);
                    }
break;
case 46:
//#line 246 "Parser.y"
{
						yyval.expr = new Tree.CallExpr(val_peek(4).expr, val_peek(3).ident, val_peek(1).elist, val_peek(3).loc);
						if (val_peek(4).loc == null) {
							yyval.loc = val_peek(3).loc;
						}
					}
break;
case 47:
//#line 255 "Parser.y"
{
						yyval.expr = val_peek(0).lvalue;
					}
break;
case 50:
//#line 261 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.PLUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 51:
//#line 265 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MINUS, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 52:
//#line 269 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MUL, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 53:
//#line 273 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.DIV, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 54:
//#line 277 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.MOD, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 55:
//#line 281 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.EQ, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 56:
//#line 285 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.NE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 57:
//#line 289 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 58:
//#line 293 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GT, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 59:
//#line 297 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.LE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 60:
//#line 301 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.GE, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 61:
//#line 305 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.AND, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 62:
//#line 309 "Parser.y"
{
                		yyval.expr = new Tree.Binary(Tree.OR, val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 63:
//#line 313 "Parser.y"
{
                		yyval = val_peek(1);
                	}
break;
case 64:
//#line 317 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NEG, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 65:
//#line 321 "Parser.y"
{
                		yyval.expr = new Tree.Unary(Tree.NOT, val_peek(0).expr, val_peek(1).loc);
                	}
break;
case 66:
//#line 325 "Parser.y"
{
                		yyval.expr = new Tree.ReadIntExpr(val_peek(2).loc);
                	}
break;
case 67:
//#line 329 "Parser.y"
{
                		yyval.expr = new Tree.ReadLineExpr(val_peek(2).loc);
                	}
break;
case 68:
//#line 333 "Parser.y"
{
                		yyval.expr = new Tree.ThisExpr(val_peek(0).loc);
                	}
break;
case 69:
//#line 337 "Parser.y"
{
                		yyval.expr = new Tree.NewClass(val_peek(2).ident, val_peek(3).loc);
                	}
break;
case 70:
//#line 341 "Parser.y"
{
                		yyval.expr = new Tree.NewArray(val_peek(3).type, val_peek(1).expr, val_peek(4).loc);
                	}
break;
case 71:
//#line 345 "Parser.y"
{
                		yyval.expr = new Tree.TypeTest(val_peek(3).expr, val_peek(1).ident, val_peek(5).loc);
                	}
break;
case 72:
//#line 349 "Parser.y"
{
                		yyval.expr = new Tree.TypeCast(val_peek(2).ident, val_peek(0).expr, val_peek(0).loc);
                	}
break;
case 73:
//#line 353 "Parser.y"
{
                        yyval.expr = new Tree.ArrayRepeat(val_peek(2).expr, val_peek(0).typeTag, val_peek(0).literal, val_peek(1).loc);
                    }
break;
case 74:
//#line 357 "Parser.y"
{
                        yyval.expr = new Tree.Concat(val_peek(2).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 75:
//#line 361 "Parser.y"
{
                        yyval.expr = new Tree.SubArray(val_peek(5).expr, val_peek(3).expr, val_peek(1).expr, val_peek(2).loc);
                    }
break;
case 76:
//#line 365 "Parser.y"
{
                        yyval.expr = new Tree.Default(val_peek(5).expr, val_peek(3).expr, val_peek(0).expr, val_peek(1).loc);
                    }
break;
case 77:
//#line 371 "Parser.y"
{
						yyval.expr = new Tree.Literal(val_peek(0).typeTag, val_peek(0).literal, val_peek(0).loc);
					}
break;
case 78:
//#line 375 "Parser.y"
{
						yyval.expr = new Null(val_peek(0).loc);
					}
break;
case 80:
//#line 383 "Parser.y"
{
                		yyval = new SemValue();
                		yyval.elist = new ArrayList<Tree.Expr>();
                	}
break;
case 81:
//#line 390 "Parser.y"
{
						yyval.elist.add(val_peek(0).expr);
					}
break;
case 82:
//#line 394 "Parser.y"
{
                		yyval.elist = new ArrayList<Tree.Expr>();
						yyval.elist.add(val_peek(0).expr);
                	}
break;
case 83:
//#line 401 "Parser.y"
{
						yyval.stmt = new Tree.WhileLoop(val_peek(2).expr, val_peek(0).stmt, val_peek(4).loc);
					}
break;
case 84:
//#line 407 "Parser.y"
{
						yyval.stmt = new Tree.ForLoop(val_peek(6).stmt, val_peek(4).expr, val_peek(2).stmt, val_peek(0).stmt, val_peek(8).loc);
					}
break;
case 85:
//#line 413 "Parser.y"
{
						yyval.stmt = new Tree.Break(val_peek(0).loc);
					}
break;
case 86:
//#line 419 "Parser.y"
{
						yyval.stmt = new Tree.If(val_peek(3).expr, val_peek(1).stmt, val_peek(0).stmt, val_peek(5).loc);
					}
break;
case 87:
//#line 425 "Parser.y"
{
						yyval.stmt = val_peek(0).stmt;
					}
break;
case 88:
//#line 429 "Parser.y"
{
						yyval = new SemValue();
					}
break;
case 89:
//#line 435 "Parser.y"
{
						yyval.stmt = new Tree.Return(val_peek(0).expr, val_peek(1).loc);
					}
break;
case 90:
//#line 439 "Parser.y"
{
                		yyval.stmt = new Tree.Return(null, val_peek(0).loc);
                	}
break;
case 91:
//#line 445 "Parser.y"
{
						yyval.stmt = new Print(val_peek(1).elist, val_peek(3).loc);
					}
break;
case 92:
//#line 451 "Parser.y"
{
                        yyval.stmt = new Tree.Scopy(val_peek(3).ident, val_peek(1).expr, val_peek(5).loc);
                    }
break;
//#line 1310 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    //if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      //if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        //if (yychar<0) yychar=0;  //clean, if necessary
        //if (yydebug)
          //yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      //if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
