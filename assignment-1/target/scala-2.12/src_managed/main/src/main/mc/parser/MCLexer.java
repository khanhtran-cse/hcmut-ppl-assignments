// Generated from src/main/mc/parser/MC.g4 by ANTLR 4.6

	package mc.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		INT_TYPE=1, VOID_TYPE=2, BOOLEAN_TYPE=3, FLOAT_TYPE=4, STRING_TYPE=5, 
		BREAK=6, CONTINUE=7, ELSE=8, FOR=9, IF=10, RETURN=11, DO=12, WHILE=13, 
		ID=14, ADDITION=15, MULTIPLICATION=16, SUBTRACTION_OR_NEGATION=17, DIVISION=18, 
		NOT=19, OR=20, AND=21, EQUAL=22, NOT_EQUAL=23, LESS=24, LESS_OR_EQUAL=25, 
		ASSIGN=26, MODULUS=27, GREATER=28, GREATER_OR_EQUAL=29, LB=30, RB=31, 
		LP=32, RP=33, LSB=34, RSB=35, SEMI=36, COMMA=37, INT_LIT=38, BOOLEAN_LIT=39, 
		FLOAT_LIT=40, WS=41, BLOCK_COMMENT=42, LINE_COMMENT=43, STRING_LIT=44, 
		UNCLOSE_STRING=45, ILLEGAL_ESCAPE=46, ERROR_CHAR=47;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"INT_TYPE", "VOID_TYPE", "BOOLEAN_TYPE", "FLOAT_TYPE", "STRING_TYPE", 
		"BREAK", "CONTINUE", "ELSE", "FOR", "IF", "RETURN", "DO", "WHILE", "ID", 
		"ADDITION", "MULTIPLICATION", "SUBTRACTION_OR_NEGATION", "DIVISION", "NOT", 
		"OR", "AND", "EQUAL", "NOT_EQUAL", "LESS", "LESS_OR_EQUAL", "ASSIGN", 
		"MODULUS", "GREATER", "GREATER_OR_EQUAL", "LB", "RB", "LP", "RP", "LSB", 
		"RSB", "SEMI", "COMMA", "INT_LIT", "BOOLEAN_LIT", "FLOAT_LIT", "WS", "BLOCK_COMMENT", 
		"LINE_COMMENT", "STRING_LIT", "UNCLOSE_STRING", "ESC", "ILLEGAL_ESCAPE", 
		"ERROR_CHAR"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'int'", "'void'", "'boolean'", "'float'", "'string'", "'break'", 
		"'continue'", "'else'", "'for'", "'if'", "'return'", "'do'", "'while'", 
		null, "'+'", "'*'", "'-'", "'/'", "'!'", "'||'", "'&&'", "'=='", "'!='", 
		"'<'", "'<='", "'='", "'%'", "'>'", "'>='", "'('", "')'", "'{'", "'}'", 
		"'['", "']'", "';'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "INT_TYPE", "VOID_TYPE", "BOOLEAN_TYPE", "FLOAT_TYPE", "STRING_TYPE", 
		"BREAK", "CONTINUE", "ELSE", "FOR", "IF", "RETURN", "DO", "WHILE", "ID", 
		"ADDITION", "MULTIPLICATION", "SUBTRACTION_OR_NEGATION", "DIVISION", "NOT", 
		"OR", "AND", "EQUAL", "NOT_EQUAL", "LESS", "LESS_OR_EQUAL", "ASSIGN", 
		"MODULUS", "GREATER", "GREATER_OR_EQUAL", "LB", "RB", "LP", "RP", "LSB", 
		"RSB", "SEMI", "COMMA", "INT_LIT", "BOOLEAN_LIT", "FLOAT_LIT", "WS", "BLOCK_COMMENT", 
		"LINE_COMMENT", "STRING_LIT", "UNCLOSE_STRING", "ILLEGAL_ESCAPE", "ERROR_CHAR"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


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


	public MCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "MC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 43:
			STRING_LIT_action((RuleContext)_localctx, actionIndex);
			break;
		case 44:
			UNCLOSE_STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 46:
			ILLEGAL_ESCAPE_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_LIT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:

			            String s = getText();
			            s = s.substring(1, s.length() - 1);
			            setText(s);
			        
			break;
		}
	}
	private void UNCLOSE_STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:

			            String s = getText();
			            s = s.substring(1, s.length());
			            setText(s);
			        
			break;
		}
	}
	private void ILLEGAL_ESCAPE_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:

			            String s = getText();
			            s = s.substring(1, s.length());
			            setText(s);
			        
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\61\u017b\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\17\3\17\7\17\u00af\n\17\f\17\16\17\u00b2\13\17\3\20\3\20\3\21\3\21\3"+
		"\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3"+
		"\27\3\30\3\30\3\30\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3"+
		"\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&"+
		"\3&\3\'\6\'\u00e9\n\'\r\'\16\'\u00ea\3(\3(\3(\3(\3(\3(\3(\3(\3(\5(\u00f6"+
		"\n(\3)\6)\u00f9\n)\r)\16)\u00fa\3)\3)\7)\u00ff\n)\f)\16)\u0102\13)\3)"+
		"\3)\5)\u0106\n)\3)\6)\u0109\n)\r)\16)\u010a\5)\u010d\n)\3)\7)\u0110\n"+
		")\f)\16)\u0113\13)\3)\3)\6)\u0117\n)\r)\16)\u0118\3)\3)\5)\u011d\n)\3"+
		")\6)\u0120\n)\r)\16)\u0121\5)\u0124\n)\3)\6)\u0127\n)\r)\16)\u0128\3)"+
		"\3)\5)\u012d\n)\3)\6)\u0130\n)\r)\16)\u0131\5)\u0134\n)\3*\6*\u0137\n"+
		"*\r*\16*\u0138\3*\3*\3+\3+\3+\3+\7+\u0141\n+\f+\16+\u0144\13+\3+\3+\3"+
		"+\3+\3+\3,\3,\3,\3,\7,\u014f\n,\f,\16,\u0152\13,\3,\3,\3-\3-\3-\7-\u0159"+
		"\n-\f-\16-\u015c\13-\3-\3-\3-\3.\3.\3.\7.\u0164\n.\f.\16.\u0167\13.\3"+
		".\3.\3.\3/\3/\3/\3\60\3\60\3\60\7\60\u0172\n\60\f\60\16\60\u0175\13\60"+
		"\3\60\3\60\3\60\3\61\3\61\2\2\62\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\2"+
		"_\60a\61\3\2\13\5\2C\\aac|\6\2\62;C\\aac|\3\2\62;\4\2GGgg\5\2\13\f\17"+
		"\17\"\"\4\2\f\f\17\17\b\2\f\f\17\17$$GHQQ^^\n\2$$))^^ddhhppttvv\5\2$$"+
		"GHQQ\u0194\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2"+
		"\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27"+
		"\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2"+
		"\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2"+
		"\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2"+
		"\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2"+
		"\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S"+
		"\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2_\3\2\2\2\2a\3\2"+
		"\2\2\3c\3\2\2\2\5g\3\2\2\2\7l\3\2\2\2\tt\3\2\2\2\13z\3\2\2\2\r\u0081\3"+
		"\2\2\2\17\u0087\3\2\2\2\21\u0090\3\2\2\2\23\u0095\3\2\2\2\25\u0099\3\2"+
		"\2\2\27\u009c\3\2\2\2\31\u00a3\3\2\2\2\33\u00a6\3\2\2\2\35\u00ac\3\2\2"+
		"\2\37\u00b3\3\2\2\2!\u00b5\3\2\2\2#\u00b7\3\2\2\2%\u00b9\3\2\2\2\'\u00bb"+
		"\3\2\2\2)\u00bd\3\2\2\2+\u00c0\3\2\2\2-\u00c3\3\2\2\2/\u00c6\3\2\2\2\61"+
		"\u00c9\3\2\2\2\63\u00cb\3\2\2\2\65\u00ce\3\2\2\2\67\u00d0\3\2\2\29\u00d2"+
		"\3\2\2\2;\u00d4\3\2\2\2=\u00d7\3\2\2\2?\u00d9\3\2\2\2A\u00db\3\2\2\2C"+
		"\u00dd\3\2\2\2E\u00df\3\2\2\2G\u00e1\3\2\2\2I\u00e3\3\2\2\2K\u00e5\3\2"+
		"\2\2M\u00e8\3\2\2\2O\u00f5\3\2\2\2Q\u0133\3\2\2\2S\u0136\3\2\2\2U\u013c"+
		"\3\2\2\2W\u014a\3\2\2\2Y\u0155\3\2\2\2[\u0160\3\2\2\2]\u016b\3\2\2\2_"+
		"\u016e\3\2\2\2a\u0179\3\2\2\2cd\7k\2\2de\7p\2\2ef\7v\2\2f\4\3\2\2\2gh"+
		"\7x\2\2hi\7q\2\2ij\7k\2\2jk\7f\2\2k\6\3\2\2\2lm\7d\2\2mn\7q\2\2no\7q\2"+
		"\2op\7n\2\2pq\7g\2\2qr\7c\2\2rs\7p\2\2s\b\3\2\2\2tu\7h\2\2uv\7n\2\2vw"+
		"\7q\2\2wx\7c\2\2xy\7v\2\2y\n\3\2\2\2z{\7u\2\2{|\7v\2\2|}\7t\2\2}~\7k\2"+
		"\2~\177\7p\2\2\177\u0080\7i\2\2\u0080\f\3\2\2\2\u0081\u0082\7d\2\2\u0082"+
		"\u0083\7t\2\2\u0083\u0084\7g\2\2\u0084\u0085\7c\2\2\u0085\u0086\7m\2\2"+
		"\u0086\16\3\2\2\2\u0087\u0088\7e\2\2\u0088\u0089\7q\2\2\u0089\u008a\7"+
		"p\2\2\u008a\u008b\7v\2\2\u008b\u008c\7k\2\2\u008c\u008d\7p\2\2\u008d\u008e"+
		"\7w\2\2\u008e\u008f\7g\2\2\u008f\20\3\2\2\2\u0090\u0091\7g\2\2\u0091\u0092"+
		"\7n\2\2\u0092\u0093\7u\2\2\u0093\u0094\7g\2\2\u0094\22\3\2\2\2\u0095\u0096"+
		"\7h\2\2\u0096\u0097\7q\2\2\u0097\u0098\7t\2\2\u0098\24\3\2\2\2\u0099\u009a"+
		"\7k\2\2\u009a\u009b\7h\2\2\u009b\26\3\2\2\2\u009c\u009d\7t\2\2\u009d\u009e"+
		"\7g\2\2\u009e\u009f\7v\2\2\u009f\u00a0\7w\2\2\u00a0\u00a1\7t\2\2\u00a1"+
		"\u00a2\7p\2\2\u00a2\30\3\2\2\2\u00a3\u00a4\7f\2\2\u00a4\u00a5\7q\2\2\u00a5"+
		"\32\3\2\2\2\u00a6\u00a7\7y\2\2\u00a7\u00a8\7j\2\2\u00a8\u00a9\7k\2\2\u00a9"+
		"\u00aa\7n\2\2\u00aa\u00ab\7g\2\2\u00ab\34\3\2\2\2\u00ac\u00b0\t\2\2\2"+
		"\u00ad\u00af\t\3\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae"+
		"\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\36\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3"+
		"\u00b4\7-\2\2\u00b4 \3\2\2\2\u00b5\u00b6\7,\2\2\u00b6\"\3\2\2\2\u00b7"+
		"\u00b8\7/\2\2\u00b8$\3\2\2\2\u00b9\u00ba\7\61\2\2\u00ba&\3\2\2\2\u00bb"+
		"\u00bc\7#\2\2\u00bc(\3\2\2\2\u00bd\u00be\7~\2\2\u00be\u00bf\7~\2\2\u00bf"+
		"*\3\2\2\2\u00c0\u00c1\7(\2\2\u00c1\u00c2\7(\2\2\u00c2,\3\2\2\2\u00c3\u00c4"+
		"\7?\2\2\u00c4\u00c5\7?\2\2\u00c5.\3\2\2\2\u00c6\u00c7\7#\2\2\u00c7\u00c8"+
		"\7?\2\2\u00c8\60\3\2\2\2\u00c9\u00ca\7>\2\2\u00ca\62\3\2\2\2\u00cb\u00cc"+
		"\7>\2\2\u00cc\u00cd\7?\2\2\u00cd\64\3\2\2\2\u00ce\u00cf\7?\2\2\u00cf\66"+
		"\3\2\2\2\u00d0\u00d1\7\'\2\2\u00d18\3\2\2\2\u00d2\u00d3\7@\2\2\u00d3:"+
		"\3\2\2\2\u00d4\u00d5\7@\2\2\u00d5\u00d6\7?\2\2\u00d6<\3\2\2\2\u00d7\u00d8"+
		"\7*\2\2\u00d8>\3\2\2\2\u00d9\u00da\7+\2\2\u00da@\3\2\2\2\u00db\u00dc\7"+
		"}\2\2\u00dcB\3\2\2\2\u00dd\u00de\7\177\2\2\u00deD\3\2\2\2\u00df\u00e0"+
		"\7]\2\2\u00e0F\3\2\2\2\u00e1\u00e2\7_\2\2\u00e2H\3\2\2\2\u00e3\u00e4\7"+
		"=\2\2\u00e4J\3\2\2\2\u00e5\u00e6\7.\2\2\u00e6L\3\2\2\2\u00e7\u00e9\t\4"+
		"\2\2\u00e8\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea\u00e8\3\2\2\2\u00ea"+
		"\u00eb\3\2\2\2\u00ebN\3\2\2\2\u00ec\u00ed\7h\2\2\u00ed\u00ee\7c\2\2\u00ee"+
		"\u00ef\7n\2\2\u00ef\u00f0\7u\2\2\u00f0\u00f6\7g\2\2\u00f1\u00f2\7v\2\2"+
		"\u00f2\u00f3\7t\2\2\u00f3\u00f4\7w\2\2\u00f4\u00f6\7g\2\2\u00f5\u00ec"+
		"\3\2\2\2\u00f5\u00f1\3\2\2\2\u00f6P\3\2\2\2\u00f7\u00f9\t\4\2\2\u00f8"+
		"\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2"+
		"\2\2\u00fb\u00fc\3\2\2\2\u00fc\u0100\7\60\2\2\u00fd\u00ff\t\4\2\2\u00fe"+
		"\u00fd\3\2\2\2\u00ff\u0102\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2"+
		"\2\2\u0101\u010c\3\2\2\2\u0102\u0100\3\2\2\2\u0103\u0105\t\5\2\2\u0104"+
		"\u0106\7/\2\2\u0105\u0104\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0108\3\2"+
		"\2\2\u0107\u0109\t\4\2\2\u0108\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a"+
		"\u0108\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u010d\3\2\2\2\u010c\u0103\3\2"+
		"\2\2\u010c\u010d\3\2\2\2\u010d\u0134\3\2\2\2\u010e\u0110\t\4\2\2\u010f"+
		"\u010e\3\2\2\2\u0110\u0113\3\2\2\2\u0111\u010f\3\2\2\2\u0111\u0112\3\2"+
		"\2\2\u0112\u0114\3\2\2\2\u0113\u0111\3\2\2\2\u0114\u0116\7\60\2\2\u0115"+
		"\u0117\t\4\2\2\u0116\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118\u0116\3\2"+
		"\2\2\u0118\u0119\3\2\2\2\u0119\u0123\3\2\2\2\u011a\u011c\t\5\2\2\u011b"+
		"\u011d\7/\2\2\u011c\u011b\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011f\3\2"+
		"\2\2\u011e\u0120\t\4\2\2\u011f\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121"+
		"\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0124\3\2\2\2\u0123\u011a\3\2"+
		"\2\2\u0123\u0124\3\2\2\2\u0124\u0134\3\2\2\2\u0125\u0127\t\4\2\2\u0126"+
		"\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0126\3\2\2\2\u0128\u0129\3\2"+
		"\2\2\u0129\u012a\3\2\2\2\u012a\u012c\t\5\2\2\u012b\u012d\7/\2\2\u012c"+
		"\u012b\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012f\3\2\2\2\u012e\u0130\t\4"+
		"\2\2\u012f\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131\u012f\3\2\2\2\u0131"+
		"\u0132\3\2\2\2\u0132\u0134\3\2\2\2\u0133\u00f8\3\2\2\2\u0133\u0111\3\2"+
		"\2\2\u0133\u0126\3\2\2\2\u0134R\3\2\2\2\u0135\u0137\t\6\2\2\u0136\u0135"+
		"\3\2\2\2\u0137\u0138\3\2\2\2\u0138\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139"+
		"\u013a\3\2\2\2\u013a\u013b\b*\2\2\u013bT\3\2\2\2\u013c\u013d\7\61\2\2"+
		"\u013d\u013e\7,\2\2\u013e\u0142\3\2\2\2\u013f\u0141\13\2\2\2\u0140\u013f"+
		"\3\2\2\2\u0141\u0144\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143\3\2\2\2\u0143"+
		"\u0145\3\2\2\2\u0144\u0142\3\2\2\2\u0145\u0146\7,\2\2\u0146\u0147\7\61"+
		"\2\2\u0147\u0148\3\2\2\2\u0148\u0149\b+\2\2\u0149V\3\2\2\2\u014a\u014b"+
		"\7\61\2\2\u014b\u014c\7\61\2\2\u014c\u0150\3\2\2\2\u014d\u014f\n\7\2\2"+
		"\u014e\u014d\3\2\2\2\u014f\u0152\3\2\2\2\u0150\u014e\3\2\2\2\u0150\u0151"+
		"\3\2\2\2\u0151\u0153\3\2\2\2\u0152\u0150\3\2\2\2\u0153\u0154\b,\2\2\u0154"+
		"X\3\2\2\2\u0155\u015a\7$\2\2\u0156\u0159\5]/\2\u0157\u0159\n\b\2\2\u0158"+
		"\u0156\3\2\2\2\u0158\u0157\3\2\2\2\u0159\u015c\3\2\2\2\u015a\u0158\3\2"+
		"\2\2\u015a\u015b\3\2\2\2\u015b\u015d\3\2\2\2\u015c\u015a\3\2\2\2\u015d"+
		"\u015e\7$\2\2\u015e\u015f\b-\3\2\u015fZ\3\2\2\2\u0160\u0165\7$\2\2\u0161"+
		"\u0164\5]/\2\u0162\u0164\n\b\2\2\u0163\u0161\3\2\2\2\u0163\u0162\3\2\2"+
		"\2\u0164\u0167\3\2\2\2\u0165\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0168"+
		"\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u0169\7\2\2\3\u0169\u016a\b.\4\2\u016a"+
		"\\\3\2\2\2\u016b\u016c\7^\2\2\u016c\u016d\t\t\2\2\u016d^\3\2\2\2\u016e"+
		"\u0173\7$\2\2\u016f\u0172\5]/\2\u0170\u0172\n\b\2\2\u0171\u016f\3\2\2"+
		"\2\u0171\u0170\3\2\2\2\u0172\u0175\3\2\2\2\u0173\u0171\3\2\2\2\u0173\u0174"+
		"\3\2\2\2\u0174\u0176\3\2\2\2\u0175\u0173\3\2\2\2\u0176\u0177\n\n\2\2\u0177"+
		"\u0178\b\60\5\2\u0178`\3\2\2\2\u0179\u017a\13\2\2\2\u017ab\3\2\2\2\35"+
		"\2\u00b0\u00ea\u00f5\u00fa\u0100\u0105\u010a\u010c\u0111\u0118\u011c\u0121"+
		"\u0123\u0128\u012c\u0131\u0133\u0138\u0142\u0150\u0158\u015a\u0163\u0165"+
		"\u0171\u0173\6\b\2\2\3-\2\3.\3\3\60\4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}