/* The following code was generated by JFlex 1.4.3 on 11/12/24, 08:40 */

package compiladorfinal;
import static compiladorfinal.Tokens.*;

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 11/12/24, 08:40 from the specification file
 * <tt>C:/Users/gmva2/OneDrive/Documentos/NetBeansProjects/compiladorFInal/src/compiladorfinal/Lexer.flex</tt>
 */
class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\6\1\5\2\0\1\6\22\0\1\6\1\41\1\4\1\0"+
    "\1\1\2\0\1\46\1\37\1\40\1\33\1\31\1\45\1\32\1\44"+
    "\1\27\1\3\11\2\1\0\1\36\1\35\1\30\1\34\2\0\32\1"+
    "\4\0\1\1\1\0\1\16\1\1\1\23\1\17\1\13\1\20\1\25"+
    "\1\12\1\7\2\1\1\21\1\1\1\10\1\24\1\14\1\1\1\15"+
    "\1\22\1\11\2\1\1\26\3\1\1\42\1\0\1\43\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\1\2\2\3\1\1\1\4\1\5\11\2"+
    "\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15"+
    "\1\16\1\17\1\1\1\20\1\21\1\22\1\1\1\0"+
    "\1\1\1\0\1\23\1\2\1\24\10\2\1\5\1\25"+
    "\1\3\1\0\1\26\1\27\1\30\1\0\1\3\10\2"+
    "\1\31\2\2";

  private static int [] zzUnpackAction() {
    int [] result = new int[66];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\47\0\116\0\165\0\234\0\303\0\352\0\u0111"+
    "\0\u0138\0\u015f\0\u0186\0\u01ad\0\u01d4\0\u01fb\0\u0222\0\u0249"+
    "\0\u0270\0\u0297\0\u02be\0\47\0\u02e5\0\47\0\u030c\0\u0333"+
    "\0\47\0\47\0\47\0\u035a\0\47\0\47\0\47\0\u0381"+
    "\0\u03a8\0\u03cf\0\303\0\303\0\u03f6\0\116\0\u041d\0\u0444"+
    "\0\u046b\0\u0492\0\u04b9\0\u04e0\0\u0507\0\u052e\0\u0555\0\47"+
    "\0\u057c\0\u05a3\0\47\0\47\0\47\0\u05ca\0\u05f1\0\u0618"+
    "\0\u063f\0\u0666\0\u068d\0\u06b4\0\u06db\0\u0702\0\u0729\0\47"+
    "\0\u0750\0\u0777";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[66];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\3\1\12\1\3\1\13\1\14\1\15\2\3\1\16"+
    "\1\3\1\17\1\20\2\3\1\21\1\22\1\23\1\24"+
    "\1\25\1\26\1\27\1\30\1\31\1\32\1\33\1\34"+
    "\1\35\1\36\1\2\1\37\1\40\50\0\2\3\4\0"+
    "\20\3\22\0\2\4\40\0\1\41\4\0\2\42\40\0"+
    "\1\41\2\0\4\43\1\44\1\0\41\43\5\0\1\7"+
    "\47\0\1\10\41\0\2\3\4\0\1\3\1\45\7\3"+
    "\1\46\6\3\21\0\2\3\4\0\3\3\1\47\14\3"+
    "\21\0\2\3\4\0\12\3\1\50\5\3\21\0\2\3"+
    "\4\0\6\3\1\51\11\3\21\0\2\3\4\0\4\3"+
    "\1\52\13\3\21\0\2\3\4\0\12\3\1\53\5\3"+
    "\21\0\2\3\4\0\2\3\1\54\15\3\21\0\2\3"+
    "\4\0\3\3\1\55\14\3\21\0\2\3\4\0\3\3"+
    "\1\56\14\3\47\0\1\57\47\0\1\60\20\0\1\61"+
    "\1\62\40\0\1\41\32\0\1\63\46\0\1\64\46\0"+
    "\1\65\16\0\46\66\3\0\1\67\1\41\45\0\2\42"+
    "\44\0\2\3\4\0\2\3\1\46\15\3\21\0\2\3"+
    "\4\0\4\3\1\70\13\3\21\0\2\3\4\0\13\3"+
    "\1\71\4\3\21\0\2\3\4\0\1\72\17\3\21\0"+
    "\2\3\4\0\7\3\1\73\10\3\21\0\2\3\4\0"+
    "\15\3\1\74\2\3\21\0\2\3\4\0\6\3\1\75"+
    "\11\3\21\0\2\3\4\0\7\3\1\76\10\3\21\0"+
    "\2\3\4\0\1\77\17\3\20\0\5\57\1\0\41\57"+
    "\2\0\1\61\1\67\40\0\1\41\46\0\1\41\50\0"+
    "\1\100\2\0\2\67\44\0\2\3\4\0\1\3\1\46"+
    "\16\3\21\0\2\3\4\0\4\3\1\46\13\3\21\0"+
    "\2\3\4\0\1\3\1\45\16\3\21\0\2\3\4\0"+
    "\10\3\1\46\7\3\21\0\2\3\4\0\7\3\1\45"+
    "\10\3\21\0\2\3\4\0\1\101\17\3\21\0\2\3"+
    "\4\0\6\3\1\46\11\3\21\0\2\3\4\0\12\3"+
    "\1\71\5\3\21\0\2\3\4\0\1\3\1\102\16\3"+
    "\21\0\2\3\4\0\16\3\1\46\1\3\20\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1950];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\0\1\11\21\1\1\11\1\1\1\11\2\1\3\11"+
    "\1\1\3\11\1\1\1\0\1\1\1\0\14\1\1\11"+
    "\1\1\1\0\3\11\1\0\11\1\1\11\2\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[66];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    public String lexeme;


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 118) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Tokens yylex() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 25: 
          { lexeme=yytext(); return caracter;
          }
        case 26: break;
        case 21: 
          { return igualdad;
          }
        case 27: break;
        case 16: 
          { return llavea;
          }
        case 28: break;
        case 14: 
          { return parentesisa;
          }
        case 29: break;
        case 7: 
          { return Igual;
          }
        case 30: break;
        case 18: 
          { return coma;
          }
        case 31: break;
        case 10: 
          { return Multiplicacion;
          }
        case 32: break;
        case 8: 
          { return Suma;
          }
        case 33: break;
        case 12: 
          { return menor;
          }
        case 34: break;
        case 13: 
          { return punto_coma;
          }
        case 35: break;
        case 24: 
          { return diferente;
          }
        case 36: break;
        case 2: 
          { lexeme=yytext(); return id;
          }
        case 37: break;
        case 4: 
          { lexeme=yytext(); return SaltoLinea;
          }
        case 38: break;
        case 11: 
          { return mayor;
          }
        case 39: break;
        case 6: 
          { return Division;
          }
        case 40: break;
        case 9: 
          { return Resta;
          }
        case 41: break;
        case 3: 
          { return num;
          }
        case 42: break;
        case 19: 
          { lexeme=yytext(); return cadena;
          }
        case 43: break;
        case 1: 
          { return ERROR;
          }
        case 44: break;
        case 5: 
          { /*Ignore*/
          }
        case 45: break;
        case 23: 
          { return menorigual;
          }
        case 46: break;
        case 17: 
          { return llavec;
          }
        case 47: break;
        case 15: 
          { return parentesisc;
          }
        case 48: break;
        case 22: 
          { return mayorigual;
          }
        case 49: break;
        case 20: 
          { lexeme=yytext(); return Reservadas;
          }
        case 50: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            return null;
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
