import org.scalatest.FunSuite

/**
  * Created by nhphung on 4/28/17.
  */
class LexerSuite extends FunSuite with TestLexer {

  test("a simple identifier") {
    val input = "abc int"
    val expect = "abc,int,<EOF>"
    assert(checkLex(input,expect,1))
  }
  test("half function declare") {
    val input = """main int  
      {"""
    val expect = """main,int,{,<EOF>"""
    assert(checkLex(input,expect,2))
  }
  test("open and close parentheses"){
    val input = "} int main \n \r {"
    val expect = """},int,main,{,<EOF>"""
    assert(checkLex(input,expect,3))
  }

  test("comment"){
    val input = "/*abc*/";
    val expect = "<EOF>";
    assert(checkLex(input,expect,4));
  }

  test("underscore"){
    val input = "//-3241\r hello \n abc //comment";
    val expect = "hello,abc,<EOF>";
    assert(checkLex(input,expect,5));
  }

  test("float literal"){
    val input = """1.2 1. .1 1e2 1.2E-2 1.2e-2 .1E2 9.0 12e8 0.33E-3 128e-42 e4 4e .44E""";
    val expect = "1.2,1.,.1,1e2,1.2E-2,1.2e-2,.1E2,9.0,12e8,0.33E-3,128e-42,e4,4,e,.44,E,<EOF>";
    assert(checkLex(input,expect,6));
  }

  test("string literal"){
    val input = """ "This is a string" int abc""";
    var expect = """This is a string,int,abc,<EOF>""";
    assert(checkLex(input,expect,7));
  }

test("boolean string"){
  var input = """"Hello" true""";
  var expect = "Hello,true,<EOF>";
  assert(checkLex(input,expect,8));
}

test("Unclose string"){
  val input = """"Hello world. This is unclosed string""";
  val expect = """Unclosed string: Hello world. This is unclosed string""";
  assert(checkLex(input,expect,9));
}

test("Ellige string"){
  val input = """"Hello world.

  Error string""";
  val expect = """"Illegal escape in string: Hello world.
  """;
  assert(checkLex(input,expect,10));
}

test("Error string 1"){
  val input = """"abc\""";
  val expect = """Illegal escape in string: abc\""";
  assert(checkLex(input,expect,11));
}

test("true string"){
  val input = "\"abc aks \t ab \' c \n  d a\"";
  val expect = "abc aks \t ab \' a,<EOF>";
  assert(checkLex(input,expect,12));
}

test("false string"){
    val input = """"\"abc aks \t ab \' c \n  d a\""""";
  val expect = "abc aks \t ab \' a,<EOF>";
  assert(checkLex(input,expect,13));
}

test("test string"){
  val input = "\"Hello. It's string. It use all support escape like as \\ \\ \t \n \r \" ";
  val expect = """"""";
  assert(checkLex(input,expect,14));
}

test("15 open and close parentheses"){
    val input = "0.5 0.8e-1 int"
    val expect = """0.5,0.8e-1,int,<EOF>"""
    assert(checkLex(input,expect,15))
  }
}