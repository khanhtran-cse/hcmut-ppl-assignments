import org.scalatest.FunSuite
import mc.utils._

/**
  * Created by nhphung on 4/29/17.
  */
class AstSuite extends FunSuite with TestAst {
   test("201: a simple program with void as return type of main") {
    val input = """void main(){}"""
    val expected = Program(List(FuncDecl(Id("main"),List(),VoidType,Block(List(),List()))))
    assert(checkAst(input,expected,201))
  }
  test("202: another simple program with int as return type of main") {
    val input = "int main () {}"
    val expected = Program(List(FuncDecl(Id("main"),List(),IntType,Block(List(),List()))))
    assert(checkAst(input,expected,202))
  }
  test("203: a simple program has a simple call putIntLn") {
    val input = "void main () {putIntLn(5);}"
    val expected = Program(List(FuncDecl(Id("main"),List(),VoidType,Block(List(),
      List(CallExpr(Id("putIntLn"),List(IntLiteral(5))))))))
    assert(checkAst(input,expected,203))
  }
  test("204: a simple program has a simple identify and function declaration") {
    val input = "int a; void main() {}"
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(),List()))))
    assert(checkAst(input,expected,204))
  }
  test("205: simple identify declaration") {
    val input = "int a,b,c,d; int b;"
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),
      VarDecl(Id("c"),IntType),VarDecl(Id("d"),IntType),VarDecl(Id("b"),IntType)))
    assert(checkAst(input,expected,205))
  }
  test("206: Parameter in funtion") {
    val input = "int funcc(int a, int b[]){}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),ArrayPointerType(IntType))),
      IntType,Block(List(),List()))))
    assert(checkAst(input,expected,206))
  }
  test("207: Declaration in body of function") {
    val input = "int funcc(){int a; int b[5];}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),
      ArrayType(IntLiteral(5),IntType))),List()))))
    assert(checkAst(input,expected,207))
  }
  test("208: Declaration and expression in body of function") {
    val input = "int funcc(){int a; int b[5]; a = a + b;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),
      ArrayType(IntLiteral(5),IntType))),List(BinaryOp("=",Id("a"),BinaryOp("+",Id("a"),Id("b"))))))))
    assert(checkAst(input,expected,208))
  }
  test("209: If statement in body of function") {
    val input = "int funcc(){if (a>b) c=c+1;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(),List(If(BinaryOp(">",Id("a"),Id("b")),
      BinaryOp("=",Id("c"),BinaryOp("+",Id("c"),IntLiteral(1))),None))))))
    assert(checkAst(input,expected,209))
  }
  test("210: Break statement in body of function") {
    val input = "int funcc(){break;continue;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(),List(Break,Continue)))))
    assert(checkAst(input,expected,210))
  }
  test("211: Continue statement in body of function") {
    val input = "int funcc(){continue;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(),List(Continue)))))
    assert(checkAst(input,expected,211))
  }
  test("212: Do While statement in body of function") {
    val input = "int funcc(){do a=a+b; while a>b;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(),List(Dowhile(List(BinaryOp("=",Id("a"),
      BinaryOp("+",Id("a"),Id("b")))),BinaryOp(">",Id("a"),Id("b"))))))))
    assert(checkAst(input,expected,212))
  }
  test("213: For statement in body of function") {
    val input = "int funcc(){for(i=0;i<5;i=i+1) a=a+b;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(),List(For(BinaryOp("=",Id("i"),
      IntLiteral(0)),BinaryOp("<",Id("i"),IntLiteral(5)),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),
    BinaryOp("=",Id("a"),BinaryOp("+",Id("a"),Id("b")))))))))
    assert(checkAst(input,expected,213))
  }
  test("214: simple statement") {
    val input = "int funcc(){a;}"
    val expected = Program(List(FuncDecl(Id("funcc"),List(),IntType,Block(List(),List(Id("a"))))))
    assert(checkAst(input,expected,214))
  }
  test("215: add 2 integer funtion") {
    val input = """int add(int a,int b){
      return a+b;
    }"""
    val expected = Program(List(FuncDecl(Id("add"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType)),
      IntType,Block(List(),List(Return(Some(BinaryOp("+",Id("a"),Id("b")))))))))
    assert(checkAst(input,expected,215))
  }
  test("216: test expression Unary: Logic Not ") {
    val input =""" 
      int a; 
      /* comment here */
      void main() {
       boolean arr[5];
       arr[0] = true ;
        a = !arr[0] ; 
              }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,
      Block(List(VarDecl(Id("arr"),ArrayType(IntLiteral(5),BoolType))),List(BinaryOp("=",ArrayCell(Id("arr"),
      IntLiteral(0)),BooleanLiteral(true)),BinaryOp("=",Id("a"),UnaryOp("!",ArrayCell(Id("arr"),IntLiteral(0)))))))))
    assert(checkAst(input,expected,216))
  }
  test("217: test expression Mul 1: *  "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 3*2 ;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,
      Block(List(VarDecl(Id("a"),IntType)),List(BinaryOp("=",Id("a"),BinaryOp("*",IntLiteral(3),IntLiteral(2))))))))
    assert(checkAst(input,expected,217))
  }
  test("218: identify declaration") {
    val input = """int b[10];
                   float a;
                   boolean a, b[11];
                   """
    val expected = Program(List(VarDecl(Id("b"),ArrayType(IntLiteral(10),IntType)),
      VarDecl(Id("a"),FloatType),VarDecl(Id("a"),BoolType),
      VarDecl(Id("b"),ArrayType(IntLiteral(11),BoolType))));
    assert(checkAst(input,expected,218))
  }
  test("219: test exp Assign"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        int  b;
        b = 0;
        b = 0 = 1 = 2;
      } 
      // comment in function name
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,
      Block(List(VarDecl(Id("b"),IntType)),List(BinaryOp("=",Id("b"),IntLiteral(0)),BinaryOp("=",Id("b"),
        BinaryOp("=",IntLiteral(0),BinaryOp("=",IntLiteral(1),IntLiteral(2)))))))))
    assert(checkAst(input,expected,219))
  }
  test("220: test exp Assign 2 "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        int  b;
        b = 0;
        a = a + 2 = 4 + 5  = 2;
      } 
      // comment in function name
    """
   val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),
    VoidType,Block(List(VarDecl(Id("b"),IntType)),List(BinaryOp("=",Id("b"),IntLiteral(0)),
      BinaryOp("=",Id("a"),BinaryOp("=",BinaryOp("+",Id("a"),IntLiteral(2)),
        BinaryOp("=",BinaryOp("+",IntLiteral(4),IntLiteral(5)),IntLiteral(2)))))))))
    assert(checkAst(input,expected,220))
  }
  test("221: test exp Assign 10 "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        int  b;
        b = 0;
        a[10] = true;
      }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),
      VoidType,Block(List(VarDecl(Id("b"),IntType)),List(BinaryOp("=",Id("b"),IntLiteral(0)),
        BinaryOp("=",ArrayCell(Id("a"),IntLiteral(10)),BooleanLiteral(true)))))))
    assert(checkAst(input,expected,221))
  }



  test("222: identify declaration and simple function declaration") {
    val input = """int a, b, c[50];
                   int func1(){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),
      VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      FuncDecl(Id("func1"),List(),IntType,Block(List(),List()))));
    assert(checkAst(input,expected,222))
  }
  test("223: identify declaration and simple function declaration 2") {
    val input = """int a, b, c[5];
                   int func1(){}
                   int func2(){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(5),IntType)),
      FuncDecl(Id("func1"),List(),IntType,Block(List(),List())),FuncDecl(Id("func2"),List(),IntType,Block(List(),List()))));
    assert(checkAst(input,expected,223))
  }
  test("224: identify declaration and simple function declaration 3") {
    val input = """int a, b, c[50];
                   int func1(){}
                   float func2(){}
                   boolean func3(){}
                   void func4(){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      FuncDecl(Id("func1"),List(),IntType,Block(List(),List())),FuncDecl(Id("func2"),List(),FloatType,Block(List(),List())),
      FuncDecl(Id("func3"),List(),BoolType,Block(List(),List())),FuncDecl(Id("func4"),List(),VoidType,Block(List(),List()))));
    assert(checkAst(input,expected,224))
  }
  test("225: identify declaration and simple function declaration has parameter") {
    val input = """int a, b, c[50];
                   int func(int a){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      FuncDecl(Id("func"),List(VarDecl(Id("a"),IntType)),IntType,Block(List(),List()))));
    assert(checkAst(input,expected,225))
  }
  test("226: identify declaration and simple function declaration has parameter 2") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   int func1(int a){}
                   int func2(int b){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("b"),IntType)),IntType,Block(List(),List()))));
    assert(checkAst(input,expected,226))
  }
  test("227: identify declaration and simple function declaration has parameter 3") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   int func1(int a){}
                   float func2(int b){}
                   boolean func3(int c){}
                   void func4(int d){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("b"),IntType)),FloatType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("c"),IntType)),BoolType,Block(List(),List())),
      FuncDecl(Id("func4"),List(VarDecl(Id("d"),IntType)),VoidType,Block(List(),List()))));
    assert(checkAst(input,expected,227))
  }
  test("228: identify declaration and simple function declaration has parameter 4") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   int func1(int a){}
                   float func2(float b){}
                   boolean func3(boolean c){}
                   void func4(int d){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("b"),FloatType)),FloatType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("c"),BoolType)),BoolType,Block(List(),List())),
      FuncDecl(Id("func4"),List(VarDecl(Id("d"),IntType)),VoidType,Block(List(),List()))));
    assert(checkAst(input,expected,228))
  }
  test("229: identify declaration and function declaration has parameter") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   int func(int a, float b, boolean c){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),FloatType),VarDecl(Id("c"),BoolType)),IntType,Block(List(),List()))));
    assert(checkAst(input,expected,229))
  }
  test("230: identify declaration and function declaration has parameter 2") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   float func(int a, float b[], boolean c[]){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),ArrayPointerType(FloatType)),VarDecl(Id("c"),
        ArrayPointerType(BoolType))),FloatType,Block(List(),List()))));
    assert(checkAst(input,expected,230))
  }
  test("231: identify declaration and function declaration has parameter 3") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   int func1(int a, int b[]){}
                   int func2(float a, float b[]){}
                   int func3(boolean a, boolean b[]){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),ArrayPointerType(IntType))),IntType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("a"),FloatType),VarDecl(Id("b"),ArrayPointerType(FloatType))),IntType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("a"),BoolType),VarDecl(Id("b"),ArrayPointerType(BoolType))),IntType,Block(List(),List()))));
    assert(checkAst(input,expected,231))
  }
  test("232: identify declaration and function declaration has parameter 4") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   float func1(int a, int b[]){}
                   float func2(float a, float b[]){}
                   float func3(boolean a, boolean b[]){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),ArrayPointerType(IntType))),FloatType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("a"),FloatType),VarDecl(Id("b"),ArrayPointerType(FloatType))),FloatType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("a"),BoolType),VarDecl(Id("b"),ArrayPointerType(BoolType))),FloatType,Block(List(),List()))));
    assert(checkAst(input,expected,232))
  }
  test("233: identify declaration and function declaration has parameter 5") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   boolean func1(int a, int b[]){}
                   boolean func2(float a, float b[]){}
                   boolean func3(boolean a, boolean b[]){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),ArrayPointerType(IntType))),BoolType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("a"),FloatType),VarDecl(Id("b"),ArrayPointerType(FloatType))),BoolType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("a"),BoolType),VarDecl(Id("b"),ArrayPointerType(BoolType))),BoolType,Block(List(),List()))));
    assert(checkAst(input,expected,233))
  }
  test("234: identify declaration and function declaration has parameter 6") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   int func1(int a, float b, boolean c[]){}
                   float func2(float a, int b[], boolean c){}
                   boolean func3(boolean a, float b[], int c){}
                   void func3(boolean a, int b, float c[]){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),FloatType),VarDecl(Id("c"),ArrayPointerType(BoolType))),IntType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("a"),FloatType),VarDecl(Id("b"),ArrayPointerType(IntType)),VarDecl(Id("c"),BoolType)),FloatType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("a"),BoolType),VarDecl(Id("b"),ArrayPointerType(FloatType)),VarDecl(Id("c"),IntType)),BoolType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("a"),BoolType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayPointerType(FloatType))),VoidType,Block(List(),List()))));
    assert(checkAst(input,expected,234))
  }
  test("235: identify declaration and function declaration has parameter 7") {
    val input = """int a, b, c[50];
                   float d, e, f[50];
                   void func1(int a, int b[]){}
                   void func2(float a, float b[]){}
                   void func3(boolean a, boolean b[]){}"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      VarDecl(Id("d"),FloatType),VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(50),FloatType)),
      FuncDecl(Id("func1"),List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),ArrayPointerType(IntType))),VoidType,Block(List(),List())),
      FuncDecl(Id("func2"),List(VarDecl(Id("a"),FloatType),VarDecl(Id("b"),ArrayPointerType(FloatType))),VoidType,Block(List(),List())),
      FuncDecl(Id("func3"),List(VarDecl(Id("a"),BoolType),VarDecl(Id("b"),ArrayPointerType(BoolType))),VoidType,Block(List(),List()))));
    assert(checkAst(input,expected,235))
  }
  test("236: test exp Unary: SUB "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        int  b;
        b = -5;
        -5  = b;
      }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,
      Block(List(VarDecl(Id("b"),IntType)),List(BinaryOp("=",Id("b"),UnaryOp("-",IntLiteral(5))),
      BinaryOp("=",UnaryOp("-",IntLiteral(5)),Id("b")))))))
    assert(checkAst(input,expected,236))
  }
  test("237: test exp Unary 2: SUB "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        float  c,d;
         -d =---3-2-1;  
      }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,
      Block(List(VarDecl(Id("c"),FloatType),VarDecl(Id("d"),FloatType)),
      List(BinaryOp("=",UnaryOp("-",Id("d")),BinaryOp("-",BinaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",IntLiteral(3)))),IntLiteral(2)),
      IntLiteral(1))))))))
    assert(checkAst(input,expected,237))
  }
  test("238: test exp Unary 3: SUB "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        float  c,d;
        d = -----c[1] - 1 - 2 -5;
        e = -true ;
      }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,
      Block(List(VarDecl(Id("c"),FloatType),VarDecl(Id("d"),FloatType)),List(BinaryOp("=",Id("d"),
      BinaryOp("-",BinaryOp("-",BinaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",ArrayCell(Id("c"),
      IntLiteral(1))))))),IntLiteral(1)),IntLiteral(2)),IntLiteral(5))),BinaryOp("=",Id("e"),UnaryOp("-",BooleanLiteral(true))))))))
    assert(checkAst(input,expected,238))
  }
  test("239: test exp Unary: Logic Not "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        boolean t;
        t = true ;
        a = !t ; 
              }
    """
   val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("t"),BoolType)),
    List(BinaryOp("=",Id("t"),BooleanLiteral(true)),BinaryOp("=",Id("a"),UnaryOp("!",Id("t"))))))))
    assert(checkAst(input,expected,239))
  }
    test("240: test exp Unary 2: Logic Not "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        boolean t;
        t = true ;
        a = !1 ; 
              }
    """
   val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("t"),BoolType)),
    List(BinaryOp("=",Id("t"),BooleanLiteral(true)),BinaryOp("=",Id("a"),UnaryOp("!",IntLiteral(1))))))))
    assert(checkAst(input,expected,240))
  }

  test("241: test exp Unary 3: Logic Not "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       boolean arr[5];
       arr[0] = true ;
        a = !arr[0] ; 
              }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("arr"),
      ArrayType(IntLiteral(5),BoolType))),List(BinaryOp("=",ArrayCell(Id("arr"),IntLiteral(0)),BooleanLiteral(true)),
      BinaryOp("=",Id("a"),UnaryOp("!",ArrayCell(Id("arr"),IntLiteral(0)))))))))
    assert(checkAst(input,expected,241))
  }

  test("242: test exp Unary 4: Logic Not "){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
        a = !!!!!a; 
              }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(),
      List(BinaryOp("=",Id("a"),UnaryOp("!",UnaryOp("!",UnaryOp("!",UnaryOp("!",UnaryOp("!",Id("a"))))))))))))
    assert(checkAst(input,expected,242))
  }

  test("243: test exp Unary 5: Logic Not "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       -3 == !!!!!true ;
              }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(),
      List(BinaryOp("==",UnaryOp("-",IntLiteral(3)),UnaryOp("!",UnaryOp("!",UnaryOp("!",
      UnaryOp("!",UnaryOp("!",BooleanLiteral(true))))))))))))
    assert(checkAst(input,expected,243))
  }
  test("244: test exp Div: / "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       4/3;
              }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(),
      List(BinaryOp("/",IntLiteral(4),IntLiteral(3)))))))
    assert(checkAst(input,expected,244))
  }
  test("245: test exp Div 4: / "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       4/true;
              }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(),
      List(BinaryOp("/",IntLiteral(4),BooleanLiteral(true)))))))
    assert(checkAst(input,expected,245))
  }
  test("246: basic program") {
    val input = """int a, b, c[5];

                   int main(){
                     /* declaration */
                     float d;
                     int e, f, g[7];

                     // statement part
                     do
                       e = e + 1;
                       putInt(e);
                     while e > 5;
                   }"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(5),IntType)),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("d"),FloatType),VarDecl(Id("e"),IntType),VarDecl(Id("f"),IntType),
      VarDecl(Id("g"),ArrayType(IntLiteral(7),IntType))),List(Dowhile(List(BinaryOp("=",Id("e"),BinaryOp("+",Id("e"),IntLiteral(1))),
      CallExpr(Id("putInt"),List(Id("e")))),BinaryOp(">",Id("e"),IntLiteral(5))))))));
    assert(checkAst(input,expected,246))
  }
  test("247: basic program 2") {
    val input = """int a, b, c[5];

                   int main(){
                     /* declaration */
                     float d;
                     int e, f, g[7];

                     // statement part
                     for(e = 1; e < 100; e = e + 1)
                       f = f + 2;
                   }"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(5),IntType)),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("d"),FloatType),VarDecl(Id("e"),IntType),VarDecl(Id("f"),IntType),
      VarDecl(Id("g"),ArrayType(IntLiteral(7),IntType))),List(For(BinaryOp("=",Id("e"),IntLiteral(1)),BinaryOp("<",Id("e"),IntLiteral(100)),
      BinaryOp("=",Id("e"),BinaryOp("+",Id("e"),IntLiteral(1))),BinaryOp("=",Id("f"),BinaryOp("+",Id("f"),IntLiteral(2)))))))));
    assert(checkAst(input,expected,247))
  }
  test("248: basic program 3") {
    val input = """int a, b, c[5];

                   int main(){
                     /* declaration */
                     float d;
                     int e, f, g[7];

                     // statement part
                     break;
                   }"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(5),IntType)),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("d"),FloatType),VarDecl(Id("e"),IntType),VarDecl(Id("f"),IntType),
      VarDecl(Id("g"),ArrayType(IntLiteral(7),IntType))),List(Break)))));
    assert(checkAst(input,expected,248))
  }
  test("249: basic program 4") {
    val input = """int a, b, c[50];

                   int main(){
                     /* declaration */
                     float d;
                     int e, f, g[100];

                     // statement part
                     continue;
                   }"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("d"),FloatType),VarDecl(Id("e"),IntType),VarDecl(Id("f"),IntType),
      VarDecl(Id("g"),ArrayType(IntLiteral(100),IntType))),List(Continue)))));
    assert(checkAst(input,expected,249))
  }
  test("250: basic program 5") {
    val input = """int a, b, c[50];

                   int main(){
                     /* declaration */
                     float d;
                     int e, f, g[70];

                     // statement part
                     return;
                   }"""
    val expected = Program(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),IntType),VarDecl(Id("c"),ArrayType(IntLiteral(50),IntType)),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("d"),FloatType),VarDecl(Id("e"),IntType),VarDecl(Id("f"),IntType),
      VarDecl(Id("g"),ArrayType(IntLiteral(70),IntType))),List(Return(None))))));
    assert(checkAst(input,expected,250))
  }
  test("251: test exp Mul: *  "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 3*2 ;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("*",IntLiteral(3),IntLiteral(2))))))))
    assert(checkAst(input,expected,251))
  }
  test("252: test exp Mul 2: *  "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 3*2*true ;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("*",BinaryOp("*",IntLiteral(3),IntLiteral(2)),BooleanLiteral(true))))))))
    assert(checkAst(input,expected,252))
  }
  test("253: test exp Mod : %  "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 8%2 ;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("%",IntLiteral(8),IntLiteral(2))))))))
    assert(checkAst(input,expected,253))
  }
  test("254: test exp Mod 1 : %  "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = false%true ;
       }
    """
   val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
    List(BinaryOp("=",Id("a"),BinaryOp("%",BooleanLiteral(false),BooleanLiteral(true))))))))
    assert(checkAst(input,expected,254))
  }

  test("255: test exp Mod 2 : %  "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 8%2%2.0%true;
       }
    """
   val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
    List(BinaryOp("=",Id("a"),BinaryOp("%",BinaryOp("%",BinaryOp("%",IntLiteral(8),IntLiteral(2)),FloatLiteral(2.0f)),BooleanLiteral(true))))))))
  }

  test("256: test exp Mod Div Mul    "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 2*3/4.5;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("/",BinaryOp("*",IntLiteral(2),IntLiteral(3)),FloatLiteral(4.5f))))))))
    assert(checkAst(input,expected,256))
  }

  test("257: test exp Mod Div Mul 2    "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 2*3/4.5%2;
       }
    """
  val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
    List(BinaryOp("=",Id("a"),BinaryOp("%",BinaryOp("/",BinaryOp("*",IntLiteral(2),IntLiteral(3)),FloatLiteral(4.5f)),IntLiteral(2))))))))
    assert(checkAst(input,expected,257))
  }

   test("258: test exp Mod Div Mul 3    "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 2*3/4.5%true;
       }
    """
   val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
    List(BinaryOp("=",Id("a"),BinaryOp("%",BinaryOp("/",BinaryOp("*",IntLiteral(2),IntLiteral(3)),FloatLiteral(4.5f)),BooleanLiteral(true))))))))
    assert(checkAst(input,expected,258))
  }

   test("259: test exp Mod Div Mul 4    "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 2*3/4.5%true%2%3;
       }
    """
  val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
    List(BinaryOp("=",Id("a"),BinaryOp("%",BinaryOp("%",BinaryOp("%",BinaryOp("/",BinaryOp("*",IntLiteral(2),IntLiteral(3)),FloatLiteral(4.5f)),
    BooleanLiteral(true)),IntLiteral(2)),IntLiteral(3))))))))
    assert(checkAst(input,expected,259))
  }

   test("260: test exp Mod Div Mul 5    "){
    val input = """ 
      boolean a; 
      /* comment here */
      void main() {
       int a;
        a = 2*3/4.5%true = 4;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),BoolType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("=",BinaryOp("%",BinaryOp("/",BinaryOp("*",IntLiteral(2),IntLiteral(3)),FloatLiteral(4.5f)),
      BooleanLiteral(true)),IntLiteral(4))))))))
    assert(checkAst(input,expected,260))
  }
  test("261: test exp binary ADD SUB 1"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       int a;
        a = b[1] - 3 + 3;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("+",BinaryOp("-",ArrayCell(Id("b"),IntLiteral(1)),IntLiteral(3)),IntLiteral(3))))))))
    assert(checkAst(input,expected,261))
  }
  test("262: test exp binary ADD SUB 2"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       int a;
        a = b[1] - 3 + 3 - true;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp("=",Id("a"),BinaryOp("-",BinaryOp("+",BinaryOp("-",ArrayCell(Id("b"),IntLiteral(1)),IntLiteral(3)),IntLiteral(3)),BooleanLiteral(true))))))))
    assert(checkAst(input,expected,262))
  }
  test("263: test exp binary GREAT: >"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       int a;
        a > 2;
       }
    """
     val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
      List(BinaryOp(">",Id("a"),IntLiteral(2)))))))
    assert(checkAst(input,expected,263))
  }

  test("264: test exp binary GREATEQ: >="){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       int a;
        a >= 2.5;
       }
    """
   val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("a"),IntType)),
    List(BinaryOp(">=",Id("a"),FloatLiteral(2.5f)))))))
  }

  test("265: test exp binary LESS: <"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
        b < 2.5;
       }
    """
     val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("<",Id("b"),FloatLiteral(2.5f)))))))
  }

  test("266: test exp binary LESSEQ: <="){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
        b <= 2.5 + 3;
       }
    """
     val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("<=",Id("b"),BinaryOp("+",FloatLiteral(2.5f),IntLiteral(3))))))))
    assert(checkAst(input,expected,266))
  }

  test("267: test exp binary Logic EQ: =="){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
       2==6;
       }
    """
     val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("==",IntLiteral(2),IntLiteral(6)))))))
    assert(checkAst(input,expected,267))
  }

    test("268: test exp binary Logic not EQ: !="){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
       true!=false;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("!=",BooleanLiteral(true),BooleanLiteral(false)))))))
    assert(checkAst(input,expected,268))
  }

  test("269: test exp binary Logic And: &&"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
       2&&true;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("&&",IntLiteral(2),BooleanLiteral(true)))))))
  }

  test("270: test exp binary Logic And 2: Left associative for &&"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
       2&&true&&5;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("&&",BinaryOp("&&",IntLiteral(2),BooleanLiteral(true)),IntLiteral(5)))))))
    assert(checkAst(input,expected,270))
  }

  test("271: test exp binary Logic Or: ||"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
       true||true;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("||",BooleanLiteral(true),BooleanLiteral(true)))))))
    assert(checkAst(input,expected,271))
  }

  test("272: test exp binary Logic Or: left associative for ||"){
    val input = """ 
      int a; 
      /* comment here */
      void main() {
       float b;
       true||a||false;
       }
    """
    val expected = Program(List(VarDecl(Id("a"),IntType),FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("b"),FloatType)),
      List(BinaryOp("||",BinaryOp("||",BooleanLiteral(true),Id("a")),BooleanLiteral(false)))))))
    assert(checkAst(input,expected,272))
  }
  test("273: Normal program") {
    val input ="""
        int f () {
          return 200;
                }
        void main() {
          int main ;
          main = f ();
          putIntLn( i );
            {
              int i ;
              int main ;
              int f ;
              main = f = i = 100;
              putIntLn( i );
              putIntLn(main );
              putIntLn( f );
            }
          putIntLn(main );
            }"""
    val expected = Program(List(FuncDecl(Id("f"),List(),IntType,Block(List(),List(Return(Some(IntLiteral(200)))))),FuncDecl(Id("main"),List(),
      VoidType,Block(List(VarDecl(Id("main"),IntType)),List(BinaryOp("=",Id("main"),CallExpr(Id("f"),List())),CallExpr(Id("putIntLn"),List(Id("i"))),
      Block(List(VarDecl(Id("i"),IntType),VarDecl(Id("main"),IntType),VarDecl(Id("f"),IntType)),List(BinaryOp("=",Id("main"),BinaryOp("=",Id("f"),
      BinaryOp("=",Id("i"),IntLiteral(100)))),CallExpr(Id("putIntLn"),List(Id("i"))),CallExpr(Id("putIntLn"),List(Id("main"))),CallExpr(Id("putIntLn"),
      List(Id("f"))))),CallExpr(Id("putIntLn"),List(Id("main"))))))))
    assert(checkAst(input,expected,273))
  }
  test("274: Normal program 2") {
    val input ="""
        int f () {
        float b[9], c[10];
          {
            if (2&&3) c[10] != 4; d = 2 + 3;
          }
        a = 4;
        do a = 2 + 3; a==2; { 2 + 3; a = 10;} while c[10] > 0 ;
            }"""
   val expected = Program(List(FuncDecl(Id("f"),List(),IntType,Block(List(VarDecl(Id("b"),ArrayType(IntLiteral(9),FloatType)),VarDecl(Id("c"),
    ArrayType(IntLiteral(10),FloatType))),List(Block(List(),List(If(BinaryOp("&&",IntLiteral(2),IntLiteral(3)),BinaryOp("!=",ArrayCell(Id("c"),
    IntLiteral(10)),IntLiteral(4)),None),BinaryOp("=",Id("d"),BinaryOp("+",IntLiteral(2),IntLiteral(3))))),BinaryOp("=",Id("a"),IntLiteral(4)),
    Dowhile(List(BinaryOp("=",Id("a"),BinaryOp("+",IntLiteral(2),IntLiteral(3))),BinaryOp("==",Id("a"),IntLiteral(2)),Block(List(),
    List(BinaryOp("+",IntLiteral(2),IntLiteral(3)),BinaryOp("=",Id("a"),IntLiteral(10))))),BinaryOp(">",ArrayCell(Id("c"),IntLiteral(10)),IntLiteral(0))))))))
    assert(checkAst(input,expected,274))
  }
  test("275: Normal Program 3") {
    val input ="""
        int f (int a) {
        float b[9], c[10];
          {
            if (2&&3) c[10] != 4;
            else f(1);
          }
        a = 4;
        do a = 2 + 3; a==2; { 2 + 3; a = 10;}
        if (2==2) continue; else a = a + 4;
        while c[10] > 0 ;
            }"""
    val expected = Program(List(FuncDecl(Id("f"),List(VarDecl(Id("a"),IntType)),IntType,Block(List(VarDecl(Id("b"),ArrayType(IntLiteral(9),FloatType)),
      VarDecl(Id("c"),ArrayType(IntLiteral(10),FloatType))),List(Block(List(),List(If(BinaryOp("&&",IntLiteral(2),IntLiteral(3)),
      BinaryOp("!=",ArrayCell(Id("c"),IntLiteral(10)),IntLiteral(4)),Some(CallExpr(Id("f"),List(IntLiteral(1))))))),BinaryOp("=",Id("a"),IntLiteral(4)),
      Dowhile(List(BinaryOp("=",Id("a"),BinaryOp("+",IntLiteral(2),IntLiteral(3))),BinaryOp("==",Id("a"),IntLiteral(2)),Block(List(),
      List(BinaryOp("+",IntLiteral(2),IntLiteral(3)),BinaryOp("=",Id("a"),IntLiteral(10)))),If(BinaryOp("==",IntLiteral(2),IntLiteral(2)),
      Continue,Some(BinaryOp("=",Id("a"),BinaryOp("+",Id("a"),IntLiteral(4)))))),BinaryOp(">",ArrayCell(Id("c"),IntLiteral(10)),IntLiteral(0))))))))
    assert(checkAst(input,expected,275))
  }
  test("276: Normal program 4") {
    val input ="""int base, exponent;
                  int main()
                  {
                     printf("Enter a base number: ");
                     scanf("%d", base);
                     printf("Enter an exponent: ");
                     scanf("%d", exponent);
                     do
                         result = base;
                         --exponent;
                     while (exponent != 0);
                     printf("Answer = %lld", result);
                     return 0;
                  }"""
    val expected = Program(List(VarDecl(Id("base"),IntType),VarDecl(Id("exponent"),IntType),
      FuncDecl(Id("main"),List(),IntType,Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("Enter a base number: "))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("base"))),CallExpr(Id("printf"),List(StringLiteral("Enter an exponent: "))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("exponent"))),Dowhile(List(BinaryOp("=",Id("result"),Id("base")),
      UnaryOp("-",UnaryOp("-",Id("exponent")))),BinaryOp("!=",Id("exponent"),IntLiteral(0))),
      CallExpr(Id("printf"),List(StringLiteral("Answer = %lld"),Id("result"))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,276))
  }
  test("277: Normal program 5") {
    val input ="""int low, high, i, flag;
                  void main()
                  {
                     printf("Enter two numbers(intervals): ");
                     scanf("%d %d", low, high);
                     printf("Prime numbers between %d and %d are: ", low, high);
                     do
                         flag = 0;
                         for(i = 2; i <= low / 2; i = i + 1)
                         {
                             if(low / i == 0)
                             {
                                 flag = 1;
                                 break;
                             }
                         }
                         if (flag == 0)
                             printf("%d ", low);
                         low = low + 1;
                     while (low < high);
                     return;
                  }"""
    val expected = Program(List(VarDecl(Id("low"),IntType),VarDecl(Id("high"),IntType),VarDecl(Id("i"),IntType),VarDecl(Id("flag"),IntType),
      FuncDecl(Id("main"),List(),VoidType,Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("Enter two numbers(intervals): "))),
      CallExpr(Id("scanf"),List(StringLiteral("%d %d"),Id("low"),Id("high"))),CallExpr(Id("printf"),List(
      StringLiteral("Prime numbers between %d and %d are: "),Id("low"),Id("high"))),Dowhile(List(BinaryOp("=",Id("flag"),IntLiteral(0)),
      For(BinaryOp("=",Id("i"),IntLiteral(2)),BinaryOp("<=",Id("i"),BinaryOp("/",Id("low"),IntLiteral(2))),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),
      IntLiteral(1))),Block(List(),List(If(BinaryOp("==",BinaryOp("/",Id("low"),Id("i")),IntLiteral(0)),Block(List(),
      List(BinaryOp("=",Id("flag"),IntLiteral(1)),Break)),None)))),If(BinaryOp("==",Id("flag"),IntLiteral(0)),CallExpr(Id("printf"),
      List(StringLiteral("%d "),Id("low"))),None),BinaryOp("=",Id("low"),BinaryOp("+",Id("low"),IntLiteral(1)))),BinaryOp("<",Id("low"),
      Id("high"))),Return(None))))));
    assert(checkAst(input,expected,277))
  }
  test("278: Normal program 6") {
    val input ="""int checkPrime(int n){}
                  int main()
                  {
                     int n, i, flag;
                     printf("Enter a positive integer: ");
                     scanf("%d", n);
                     for(i = 2; i<= n/2; i = i + 1)
                     {
                         // condition for i to be a prime number
                         if (checkPrime(i) == 1)
                         {
                             // condition for n-i to be a prime number
                             if (checkPrime(n-i) == 1)
                             {
                                 // n = primeNumber1 + primeNumber2
                                 printf("%d = %d + %d\n", n, i, n - i);
                                 flag = 1;
                             }
                         }
                     }
                     if (flag == 0)
                         printf("%d cannot be expressed as the sum of two prime numbers.", n);
                     return 0;
                  }
                  // Function to check prime number
                  int checkPrime(int n)
                  {
                     int i, isPrime;
                     for(i = 2; i <= n/2; i = i + 1)
                     {
                         if(n / i == 0)
                         {
                             isPrime = 0;
                             break;
                         }
                     }
                     return isPrime;
                  }"""
    val expected = Program(List(FuncDecl(Id("checkPrime"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("n"),IntType),VarDecl(Id("i"),IntType),VarDecl(Id("flag"),IntType)),
      List(CallExpr(Id("printf"),List(StringLiteral("Enter a positive integer: "))),CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("n"))),
      For(BinaryOp("=",Id("i"),IntLiteral(2)),BinaryOp("<=",Id("i"),BinaryOp("/",Id("n"),IntLiteral(2))),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),
      IntLiteral(1))),Block(List(),List(If(BinaryOp("==",CallExpr(Id("checkPrime"),List(Id("i"))),IntLiteral(1)),Block(List(),List(If(
      BinaryOp("==",CallExpr(Id("checkPrime"),List(BinaryOp("-",Id("n"),Id("i")))),IntLiteral(1)),Block(List(),List(CallExpr(Id("printf"),
      List(StringLiteral("""%d = %d + %d\n"""),Id("n"),Id("i"),BinaryOp("-",Id("n"),Id("i")))),BinaryOp("=",Id("flag"),IntLiteral(1)))),None))),None)))),
      If(BinaryOp("==",Id("flag"),IntLiteral(0)),CallExpr(Id("printf"),List(StringLiteral("%d cannot be expressed as the sum of two prime numbers."),
      Id("n"))),None),Return(Some(IntLiteral(0)))))),FuncDecl(Id("checkPrime"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(VarDecl(Id("i"),IntType),
      VarDecl(Id("isPrime"),IntType)),List(For(BinaryOp("=",Id("i"),IntLiteral(2)),BinaryOp("<=",Id("i"),BinaryOp("/",Id("n"),IntLiteral(2))),
      BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),Block(List(),List(If(BinaryOp("==",BinaryOp("/",Id("n"),Id("i")),IntLiteral(0)),
      Block(List(),List(BinaryOp("=",Id("isPrime"),IntLiteral(0)),Break)),None)))),Return(Some(Id("isPrime"))))))));
    assert(checkAst(input,expected,278))
  }
  test("279: Normal program 7") {
    val input ="""int multiplyNumbers(int n){}
                  int main()
                  {
                     int n;
                     printf("Enter a positive integer: ");
                     scanf("%d", n);
                     printf("Factorial of %d = %ld", n, multiplyNumbers(n));
                     return 0;
                  }
                  int multiplyNumbers(int n)
                  {
                     if (n >= 1)
                         return n * multiplyNumbers(n-1);
                     else
                         return 1;
                  }"""
    val expected = Program(List(FuncDecl(Id("multiplyNumbers"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("n"),IntType)),List(CallExpr(Id("printf"),
      List(StringLiteral("Enter a positive integer: "))),CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("n"))),CallExpr(Id("printf"),
      List(StringLiteral("Factorial of %d = %ld"),Id("n"),CallExpr(Id("multiplyNumbers"),List(Id("n"))))),Return(Some(IntLiteral(0)))))),
      FuncDecl(Id("multiplyNumbers"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(),List(If(BinaryOp(">=",Id("n"),IntLiteral(1)),
      Return(Some(BinaryOp("*",Id("n"),CallExpr(Id("multiplyNumbers"),List(BinaryOp("-",Id("n"),IntLiteral(1))))))),
      Some(Return(Some(IntLiteral(1))))))))));
    assert(checkAst(input,expected,279))
  }
  test("280: Normal program 8") {
    val input ="""int hcf(int n1, int n2){}
                  int main()
                  {
                    int n1, n2;
                    printf("Enter two positive integers: ");
                    scanf("%d %d", n1, n2);
                    printf("G.C.D of %d and %d is %d.", n1, n2, hcf(n1,n2));
                    return 0;
                  }
                  int hcf(int n1, int n2)
                  {
                     if (n2 != 0)
                        return hcf(n2, n1/n2);
                     else
                        return n1;
                  }
                  """
    val expected = Program(List(FuncDecl(Id("hcf"),List(VarDecl(Id("n1"),IntType),VarDecl(Id("n2"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("n1"),IntType),VarDecl(Id("n2"),IntType)),List(CallExpr(Id("printf"),
      List(StringLiteral("Enter two positive integers: "))),CallExpr(Id("scanf"),List(StringLiteral("%d %d"),Id("n1"),Id("n2"))),CallExpr(Id("printf"),
      List(StringLiteral("G.C.D of %d and %d is %d."),Id("n1"),Id("n2"),CallExpr(Id("hcf"),List(Id("n1"),Id("n2"))))),Return(Some(IntLiteral(0)))))),
      FuncDecl(Id("hcf"),List(VarDecl(Id("n1"),IntType),VarDecl(Id("n2"),IntType)),IntType,Block(List(),List(If(BinaryOp("!=",Id("n2"),
      IntLiteral(0)),Return(Some(CallExpr(Id("hcf"),List(Id("n2"),BinaryOp("/",Id("n1"),Id("n2")))))), Some(Return(Some(Id("n1"))))))))));
    assert(checkAst(input,expected,280))
  }
  test("281: matrix program") {
    val input ="""int matrix[100];
                  int i,j,r,c;
                  float sum,product;
                  int main()
                  {
                     printf("Enter number of Rows :");
                     scanf("%d",r);
                     printf("Enter number of Cols :");
                     scanf("%d",c);
                     printf("\nEnter matrix elements :\n");
                     for(i = 0; i < r; i = i + 1)
                     {
                         for(j = 0; j < c; j = j + 1)
                         {
                             printf("Enter element [%d,%d] : ", i+1, j+1);
                             scanf("%d",matrix[i]);
                         }
                     }
                     /*sum and product of all elements*/
                     /*initializing sun and product variables*/
                     sum = 0;
                     product = 1;
                     for(i = 0; i < r; i = i + 1)
                     {
                         for(j = 0; j < c; j = j + 1)
                         {
                             sum = matrix[i];
                             product = matrix[j];
                         }
                     }
                     printf("\nSUM of all elements : %d \nProduct of all elements :%d", sum, product);
                     return 0;
                  }"""
    val expected = Program(List(VarDecl(Id("matrix"),ArrayType(IntLiteral(100),IntType)),VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType),
      VarDecl(Id("r"),IntType),VarDecl(Id("c"),IntType),VarDecl(Id("sum"),FloatType),VarDecl(Id("product"),FloatType),
      FuncDecl(Id("main"),List(),IntType,Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("Enter number of Rows :"))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("r"))),CallExpr(Id("printf"),List(StringLiteral("Enter number of Cols :"))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("c"))),CallExpr(Id("printf"),List(StringLiteral("""\nEnter matrix elements :\n"""))),
      For(BinaryOp("=",Id("i"),IntLiteral(0)),BinaryOp("<",Id("i"),Id("r")),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),
      Block(List(),List(For(BinaryOp("=",Id("j"),IntLiteral(0)),BinaryOp("<",Id("j"),Id("c")),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(1))),
      Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("Enter element [%d,%d] : "),BinaryOp("+",Id("i"),IntLiteral(1)),BinaryOp("+",Id("j"),
      IntLiteral(1)))),      CallExpr(Id("scanf"),List(StringLiteral("%d"),ArrayCell(Id("matrix"),Id("i")))))))))),BinaryOp("=",Id("sum"),IntLiteral(0)),
      BinaryOp("=",Id("product"),IntLiteral(1)),For(BinaryOp("=",Id("i"),IntLiteral(0)),BinaryOp("<",Id("i"),Id("r")),
      BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),Block(List(),List(For(BinaryOp("=",Id("j"),IntLiteral(0)),
      BinaryOp("<",Id("j"),Id("c")),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(1))),Block(List(),List(BinaryOp("=",Id("sum"),
      ArrayCell(Id("matrix"),Id("i"))),BinaryOp("=",Id("product"),ArrayCell(Id("matrix"),Id("j"))))))))),CallExpr(Id("printf"),
      List(StringLiteral("""\nSUM of all elements : %d \nProduct of all elements :%d"""),Id("sum"),Id("product"))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,281))
  }
  test("282: factorial program "){
    val input = """int main(){  
                    int i,giaithua,sobatky;   
                    printf("Nhap mot so bat ky: ");  
                    scanf("%d",sobatky);  
                    for(i=1;i<=sobatky;i=i+1){  
                        giaithua=giaithua*i;  
                    }  
                    printf("\nGiai thua cua %d la: %d",sobatky,giaithua);  
                    getch();  
                  }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("i"),IntType),VarDecl(Id("giaithua"),IntType),
      VarDecl(Id("sobatky"),IntType)),List(CallExpr(Id("printf"),List(StringLiteral("Nhap mot so bat ky: "))),CallExpr(Id("scanf"),
      List(StringLiteral("%d"),Id("sobatky"))),For(BinaryOp("=",Id("i"),IntLiteral(1)),BinaryOp("<=",Id("i"),Id("sobatky")),BinaryOp("=",Id("i"),
      BinaryOp("+",Id("i"),IntLiteral(1))),Block(List(),List(BinaryOp("=",Id("giaithua"),BinaryOp("*",Id("giaithua"),Id("i")))))),CallExpr(Id("printf"),
      List(StringLiteral("\\nGiai thua cua %d la: %d"),Id("sobatky"),Id("giaithua"))),CallExpr(Id("getch"),List()))))))
    assert(checkAst(input,expected,282))
  }
  test("283: Normal program 9"){
    val input = """float main(){
      int x, y;
      for(x=1;x<=36;x = x + 1)
        for(y=1;y<=36;y=y+1)
        {
          {
          printf("\n%d %d", x, y);
          }
        }
      }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),FloatType,Block(List(VarDecl(Id("x"),IntType),VarDecl(Id("y"),IntType)),List(For(
      BinaryOp("=",Id("x"),IntLiteral(1)),BinaryOp("<=",Id("x"),IntLiteral(36)),BinaryOp("=",Id("x"),BinaryOp("+",Id("x"),IntLiteral(1))),
      For(BinaryOp("=",Id("y"),IntLiteral(1)),BinaryOp("<=",Id("y"),IntLiteral(36)),BinaryOp("=",Id("y"),BinaryOp("+",Id("y"),IntLiteral(1))),
      Block(List(),List(Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("\\n%d %d"),Id("x"),Id("y"))))))))))))))
    assert(checkAst(input,expected,283))
  }
  test("284: Normal program 10"){
    val input = """int main()
                    {
                       int c;
                       printf( "Nhap mot gia tri: ");
                       c = getchar();
                       printf( "\nGia tri ban da nhap la: ");
                       putchar();
                       return 0.123e4;
                    }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("c"),IntType)),List(CallExpr(Id("printf"),
      List(StringLiteral("Nhap mot gia tri: "))),BinaryOp("=",Id("c"),CallExpr(Id("getchar"),List())),CallExpr(Id("printf"),List(
      StringLiteral("\\nGia tri ban da nhap la: "))),CallExpr(Id("putchar"),List()),Return(Some(FloatLiteral(0.123e4f))))))))
    assert(checkAst(input,expected,284))
  }
  test("285: Normal program 11") {
    val input="""int[] whatIsFuntionName() {
      // Loi theo huong hoa
      // may mu giang Loi
      int sonTung;
      int MTP;
      Sky = sonTung + MTP ;
    }
    int[] main(){}
    """
    val expected = Program(List(FuncDecl(Id("whatIsFuntionName"),List(),ArrayPointerType(IntType),Block(List(VarDecl(Id("sonTung"),IntType),
      VarDecl(Id("MTP"),IntType)),List(BinaryOp("=",Id("Sky"),BinaryOp("+",Id("sonTung"),Id("MTP")))))),FuncDecl(Id("main"),List(),
      ArrayPointerType(IntType),Block(List(),List()))))
    assert(checkAst(input,expected,285))
  }
  test("286: Normal program 12") {
    val input="""void main(int jessica){
          // start declaration part
          int a , b , c ;
          float f[5] ;
          // end declaration part
          // start statement part
          a=b=2;
          if (a=b) f[0] = 1.0 ;
          // end statement part
          }
    """
    val expected = Program(List(FuncDecl(Id("main"),List(VarDecl(Id("jessica"),IntType)),VoidType,Block(List(VarDecl(Id("a"),IntType),VarDecl(Id("b"),
      IntType),VarDecl(Id("c"),IntType),VarDecl(Id("f"),ArrayType(IntLiteral(5),FloatType))),List(BinaryOp("=",Id("a"),BinaryOp("=",Id("b"),
      IntLiteral(2))),If(BinaryOp("=",Id("a"),Id("b")),BinaryOp("=",ArrayCell(Id("f"),IntLiteral(0)),FloatLiteral(1.0f)),None))))))
    assert(checkAst(input,expected,286))
  }
  test("287: Function call"){
    val input = """int main(){
      theLegends(andTheMyth(Spiderman(Control(batman(i,o,p,q,z,e,r,t,y,u,1,2,1.2)))));
    }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),IntType,Block(List(),List(CallExpr(Id("theLegends"),List(CallExpr(Id("andTheMyth"),
      List(CallExpr(Id("Spiderman"),List(CallExpr(Id("Control"),List(CallExpr(Id("batman"),List(Id("i"),Id("o"),Id("p"),Id("q"),Id("z"),Id("e"),Id("r"),
      Id("t"),Id("y"),Id("u"),IntLiteral(1),IntLiteral(2),FloatLiteral(1.2f))))))))))))))))
    assert(checkAst(input,expected,287))
  }
  test("288: Normal program 13"){
    val input = """boolean main()
    {
       FILE *fp;
       fp = fopen("vidu.txt", "w+");
       fprintf(fp, "Vi du kiem tra ham fprintf ...\n");
       fputs("Vi du kiem tra ham fputs ...\n", fp);
       fclose(fp);
    }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),BoolType,Block(List(),List(BinaryOp("*",Id("FILE"),Id("fp")),BinaryOp("=",Id("fp"),
      CallExpr(Id("fopen"),List(StringLiteral("vidu.txt"),StringLiteral("w+")))),CallExpr(Id("fprintf"),List(Id("fp"),
      StringLiteral("Vi du kiem tra ham fprintf ...\\n"))),CallExpr(Id("fputs"),List(StringLiteral("Vi du kiem tra ham fputs ...\\n"),Id("fp"))),
      CallExpr(Id("fclose"),List(Id("fp"))))))))
    assert(checkAst(input,expected,288))
  }
  test("289: Mix-up program"){
    val input = """boolean main()
    {
       int a[100],b[100],a[100],d,f,e,r,g;
       float floaaa[500],BichPhuong[60],kimochi;
       do
        if (1+1 != 2) 1+1=3;
       else 1+1=4;
       continue;
       break;
       for (Vo;Xuan;Bach) 1510143;
       do bla;bla;bla; while bla;
       while nothing;
    }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),BoolType,Block(List(VarDecl(Id("a"),ArrayType(IntLiteral(100),IntType)),
      VarDecl(Id("b"),ArrayType(IntLiteral(100),IntType)),VarDecl(Id("a"),ArrayType(IntLiteral(100),IntType)),VarDecl(Id("d"),IntType),
      VarDecl(Id("f"),IntType),VarDecl(Id("e"),IntType),VarDecl(Id("r"),IntType),VarDecl(Id("g"),IntType),VarDecl(Id("floaaa"),ArrayType(IntLiteral(500),
      FloatType)),VarDecl(Id("BichPhuong"),ArrayType(IntLiteral(60),FloatType)),VarDecl(Id("kimochi"),FloatType)),List(Dowhile(List(If(
      BinaryOp("!=",BinaryOp("+",IntLiteral(1),IntLiteral(1)),IntLiteral(2)),BinaryOp("=",BinaryOp("+",IntLiteral(1),IntLiteral(1)),IntLiteral(3)),
      Some(BinaryOp("=",BinaryOp("+",IntLiteral(1),IntLiteral(1)),IntLiteral(4)))),Continue,Break,For(Id("Vo"),Id("Xuan"),Id("Bach"),IntLiteral(1510143)),
      Dowhile(List(Id("bla"),Id("bla"),Id("bla")),Id("bla"))),Id("nothing")))))))
    assert(checkAst(input,expected,289))
  }
  test("290: full program"){
    val input = """
    int arr[5],sontung;
    float arr[5],ducphuc;
    boolean arr[5],hani;
    string arr[5],sohuyn;
    int[] notAFunction(int theChainsmokers[],float martinGarrix[], string dubstep, boolean truthOfW){
      int a[100],b[100],a[100],d,f,e,r,g;
      float floaaa[500],BichPhuong[60],kimochi;
      do
        if (1+1 != 2) 1+1=3;
       else 1+1=4;
       continue;
       break;
       for (Vo;Xuan;Bach) 1510143;
       do bla;bla;bla; while bla;
       while nothing;
       a[b[c[d[e[r[t[y[u[5]]]]]]]]];
       theLegends(andTheMyth(Spiderman(Control(batman(i,o,p,q,z,e,r,t,y,u,1,2,1.2)))));
       {
        -subcrise = --------------------------------------a+b*c/d%q%q%q%q%q%i;
              a > b;
              b < c;
              c >= d;
              d <= e;
              e != f;
              f == g;
              g || h;
              h && j;
              !j = j; 
        }
        ending;
    }
    void main(){
      notAFunction(ahihi);
      return;
    }
    """
    val expected = Program(List(VarDecl(Id("arr"),ArrayType(IntLiteral(5),IntType)),VarDecl(Id("sontung"),IntType),VarDecl(Id("arr"),
      ArrayType(IntLiteral(5),FloatType)),VarDecl(Id("ducphuc"),FloatType),VarDecl(Id("arr"),ArrayType(IntLiteral(5),BoolType)),VarDecl(Id("hani"),
      BoolType),VarDecl(Id("arr"),ArrayType(IntLiteral(5),StringType)),VarDecl(Id("sohuyn"),StringType),FuncDecl(Id("notAFunction"),
      List(VarDecl(Id("theChainsmokers"),ArrayPointerType(IntType)),VarDecl(Id("martinGarrix"),ArrayPointerType(FloatType)),VarDecl(Id("dubstep"),
      StringType),VarDecl(Id("truthOfW"),BoolType)),ArrayPointerType(IntType),Block(List(VarDecl(Id("a"),ArrayType(IntLiteral(100),IntType)),
      VarDecl(Id("b"),ArrayType(IntLiteral(100),IntType)),VarDecl(Id("a"),ArrayType(IntLiteral(100),IntType)),VarDecl(Id("d"),IntType),VarDecl(Id("f"),
      IntType),VarDecl(Id("e"),IntType),VarDecl(Id("r"),IntType),VarDecl(Id("g"),IntType),VarDecl(Id("floaaa"),ArrayType(IntLiteral(500),FloatType)),
      VarDecl(Id("BichPhuong"),ArrayType(IntLiteral(60),FloatType)),VarDecl(Id("kimochi"),FloatType)),List(Dowhile(List(If(BinaryOp("!=",BinaryOp("+",
      IntLiteral(1),IntLiteral(1)),IntLiteral(2)),BinaryOp("=",BinaryOp("+",IntLiteral(1),IntLiteral(1)),IntLiteral(3)),Some(BinaryOp("=",BinaryOp("+",
      IntLiteral(1),IntLiteral(1)),IntLiteral(4)))),Continue,Break,For(Id("Vo"),Id("Xuan"),Id("Bach"),IntLiteral(1510143)),Dowhile(List(Id("bla"),
      Id("bla"),Id("bla")),Id("bla"))),Id("nothing")),ArrayCell(Id("a"),ArrayCell(Id("b"),ArrayCell(Id("c"),ArrayCell(Id("d"),ArrayCell(Id("e"),
      ArrayCell(Id("r"),ArrayCell(Id("t"),ArrayCell(Id("y"),ArrayCell(Id("u"),IntLiteral(5)))))))))),CallExpr(Id("theLegends"),
      List(CallExpr(Id("andTheMyth"),List(CallExpr(Id("Spiderman"),List(CallExpr(Id("Control"),List(CallExpr(Id("batman"),List(Id("i"),Id("o"),
      Id("p"),Id("q"),Id("z"),Id("e"),Id("r"),Id("t"),Id("y"),Id("u"),IntLiteral(1),IntLiteral(2),FloatLiteral(1.2f))))))))))),Block(List(),
      List(BinaryOp("=",UnaryOp("-",Id("subcrise")),BinaryOp("+",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",
      UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",
      UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",
      UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",UnaryOp("-",Id("a"))))))))))))))))))))))))))))))))))))))),
      BinaryOp("%",BinaryOp("%",BinaryOp("%",BinaryOp("%",BinaryOp("%",BinaryOp("%",BinaryOp("/",BinaryOp("*",Id("b"),Id("c")),Id("d")),Id("q")),Id("q")),
      Id("q")),Id("q")),Id("q")),Id("i")))),BinaryOp(">",Id("a"),Id("b")),BinaryOp("<",Id("b"),Id("c")),BinaryOp(">=",Id("c"),Id("d")),BinaryOp("<=",
      Id("d"),Id("e")),BinaryOp("!=",Id("e"),Id("f")),BinaryOp("==",Id("f"),Id("g")),BinaryOp("||",Id("g"),Id("h")),BinaryOp("&&",Id("h"),Id("j")),
      BinaryOp("=",UnaryOp("!",Id("j")),Id("j")))),Id("ending")))),FuncDecl(Id("main"),List(),VoidType,Block(List(),List(CallExpr(Id("notAFunction"),
      List(Id("ahihi"))),Return(None))))))
    assert(checkAst(input,expected,290))
  }
  test("291: Big program") {
    val input ="""void fibonacci(int term){
                     /* Exit condition of recursion*/
                     if(term < 2)
                        return term;
                     if(term == true)
                         return fibonacci(term - 1) + fibonacci(term - 2);
                     else
                         return -1;
                  }
                  void main() {
                     int decimalNumber;
                     int hexDigits[16];
                     int hexadecimal[30];
                     int i, j, power, digit;
                     float a, b, c, d, e, f[2];
                     a = 1.2;
                     b = 1.;
                     c = .1;
                     d = 1e2;
                     e = 1.2E2;
                     f[0] = 1.2e-2;
                     f[1] = .1E2;
                     printf("Enter a Hexadecimal Number\n");
                     scanf("%s", hexadecimal);
                     /* Converting hexadecimal number to decimal number */
                     for(i = strlen(hexadecimal) - 1; i >= 0; --i) {
                         /*search currect character in hexDigits array */
                         for(j = 0; j < 16; j = j + 1){
                             if(hexadecimal[i] == hexDigits[j]){
                                 decimalNumber = decimalNumber + j * pow(16, power);
                             }
                             else
                                 break;
                         }
                         power = power + 1;
                     }
                     if(decimalNumber >= 37.5 && hexadecimal < 10)
                         continue;
                     else
                         return;
                     printf("Decimal Number : %ld", decimalNumber);
                     return 0;
                  }"""
    val expected = Program(List(FuncDecl(Id("fibonacci"),List(VarDecl(Id("term"),IntType)),VoidType,
      Block(List(),List(If(BinaryOp("<",Id("term"),IntLiteral(2)),Return(Some(Id("term"))),None),
      If(BinaryOp("==",Id("term"),BooleanLiteral(true)),Return(Some(BinaryOp("+",CallExpr(Id("fibonacci"),List(BinaryOp("-",Id("term"),IntLiteral(1)))),
      CallExpr(Id("fibonacci"),List(BinaryOp("-",Id("term"),IntLiteral(2))))))),Some(Return(Some(UnaryOp("-",IntLiteral(1))))))))),
      FuncDecl(Id("main"),List(),VoidType,Block(List(VarDecl(Id("decimalNumber"),IntType),VarDecl(Id("hexDigits"),ArrayType(IntLiteral(16),IntType)),
      VarDecl(Id("hexadecimal"),ArrayType(IntLiteral(30),IntType)),VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType),VarDecl(Id("power"),IntType),
      VarDecl(Id("digit"),IntType),VarDecl(Id("a"),FloatType),VarDecl(Id("b"),FloatType),VarDecl(Id("c"),FloatType),VarDecl(Id("d"),FloatType),
      VarDecl(Id("e"),FloatType),VarDecl(Id("f"),ArrayType(IntLiteral(2),FloatType))),List(BinaryOp("=",Id("a"),FloatLiteral(1.2f)),
      BinaryOp("=",Id("b"),FloatLiteral(1.0f)),BinaryOp("=",Id("c"),FloatLiteral(0.1f)),BinaryOp("=",Id("d"),FloatLiteral(100.0f)),
      BinaryOp("=",Id("e"),FloatLiteral(120.0f)),BinaryOp("=",ArrayCell(Id("f"),IntLiteral(0)),FloatLiteral(0.012f)),
      BinaryOp("=",ArrayCell(Id("f"),IntLiteral(1)),FloatLiteral(10.0f)),CallExpr(Id("printf"),List(StringLiteral("""Enter a Hexadecimal Number\n"""))),
      CallExpr(Id("scanf"),List(StringLiteral("%s"),Id("hexadecimal"))),For(BinaryOp("=",Id("i"),BinaryOp("-",CallExpr(Id("strlen"),List(Id("hexadecimal"))),
      IntLiteral(1))),BinaryOp(">=",Id("i"),IntLiteral(0)),UnaryOp("-",UnaryOp("-",Id("i"))),Block(List(),List(For(BinaryOp("=",Id("j"),IntLiteral(0)),
      BinaryOp("<",Id("j"),IntLiteral(16)),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(1))),Block(List(),
      List(If(BinaryOp("==",ArrayCell(Id("hexadecimal"),Id("i")),ArrayCell(Id("hexDigits"),Id("j"))),Block(List(),
      List(BinaryOp("=",Id("decimalNumber"),BinaryOp("+",Id("decimalNumber"),BinaryOp("*",Id("j"),CallExpr(Id("pow"),List(IntLiteral(16),Id("power")))))))),
      Some(Break))))),BinaryOp("=",Id("power"),BinaryOp("+",Id("power"),IntLiteral(1)))))),
      If(BinaryOp("&&",BinaryOp(">=",Id("decimalNumber"),FloatLiteral(37.5f)),BinaryOp("<",Id("hexadecimal"),IntLiteral(10))),Continue,Some(Return(None))),
      CallExpr(Id("printf"),List(StringLiteral("Decimal Number : %ld"),Id("decimalNumber"))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,291))
  }
  test("292: Big program 2") {
    val input ="""int checkLeapYear(int year)
                  {
                  }
                  int main(int argc, float argv[])
                  {
                     int i, j, n;
                     printf("Enter the value of N: ");
                     scanf("%d", n);
                     printf("Leap years from 1 to %d:\n", n);
                     for(i = 1; i <= n; i = i + 1)
                     {
                         if(checkLeapYear(i))
                         if(i == 2)
                             printf("%d\t", i);
                         else
                             for(j = 1; j <= n; j = j + 1)
                                 checkLeapYear(j);
                     }
                     return 0;
                  }"""
    val expected = Program(List(FuncDecl(Id("checkLeapYear"),List(VarDecl(Id("year"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("main"),List(VarDecl(Id("argc"),IntType),VarDecl(Id("argv"),ArrayPointerType(FloatType))),IntType,
      Block(List(VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType),VarDecl(Id("n"),IntType)),List(CallExpr(Id("printf"),List(StringLiteral("Enter the value of N: "))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("n"))),CallExpr(Id("printf"),List(StringLiteral("""Leap years from 1 to %d:\n"""),Id("n"))),
      For(BinaryOp("=",Id("i"),IntLiteral(1)),BinaryOp("<=",Id("i"),Id("n")),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),
      Block(List(),List(If(CallExpr(Id("checkLeapYear"),List(Id("i"))),If(BinaryOp("==",Id("i"),IntLiteral(2)),
      CallExpr(Id("printf"),List(StringLiteral("""%d\t"""),Id("i"))),Some(For(BinaryOp("=",Id("j"),IntLiteral(1)),
      BinaryOp("<=",Id("j"),Id("n")),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(1))),CallExpr(Id("checkLeapYear"),List(Id("j")))))),None)))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,292))
  }
  test("293: Loop program") {
    val input ="""int main()
                  {
                     int i,j;    /*Here, we will use i for outer loop counter
                                   and j for inner loop counter*/
                     int num;
                     for(i = 1; i <= 20; i = i + 1) /*to print table 1 to 20*/
                     {
                         /*each number has 10 multiples*/
                         num= i;     /*to initialize number with i ( 1 to 20)*/
                         for(j = 1; j <= 10; j = j + 1)
                         {
                             /*values will be padded with 3 spaces*/
                             printf("%3d\t", (num * j));
                             if(i == 10)
                                 continue;
                         }
                         printf("\n"); /*after printing table of each number*/
                     }
                     return 0;
                  }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType),
      VarDecl(Id("num"),IntType)),List(For(BinaryOp("=",Id("i"),IntLiteral(1)),BinaryOp("<=",Id("i"),IntLiteral(20)),
      BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),Block(List(),List(BinaryOp("=",Id("num"),Id("i")),
      For(BinaryOp("=",Id("j"),IntLiteral(1)),BinaryOp("<=",Id("j"),IntLiteral(10)),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(1))),
      Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("""%3d\t"""),BinaryOp("*",Id("num"),Id("j")))),
      If(BinaryOp("==",Id("i"),IntLiteral(10)),Continue,None)))),CallExpr(Id("printf"),List(StringLiteral("""\n""")))))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,293))
  }
  test("294: Factorial program 2") {
    val input ="""float factorial(int n)
                  {
                     int i, j;
                     int fact;
                     fact = 1;
                     if(n == 1)
                         return fact;
                     for(i = n; i >= 1; --i){
                         if(i < 0)
                             continue;
                         else{
                             for(j = 0; j < 100; j = j + 1)
                                 fact = fact * i;
                             if(j == 90)
                                 j = j + 2;
                             else
                                 continue;
                         }
                     }
                     return fact;
                  }
                  int main()
                  {
                     int num;
                     printf("Enter an integer number :");
                     scanf("%d", num);
                     printf("\nFactorial of %d is = %ld", num, factorial(num));
                     return 0;
                  }"""
    val expected = Program(List(FuncDecl(Id("factorial"),List(VarDecl(Id("n"),IntType)),FloatType,Block(List(VarDecl(Id("i"),IntType),
      VarDecl(Id("j"),IntType),VarDecl(Id("fact"),IntType)),List(BinaryOp("=",Id("fact"),IntLiteral(1)),If(BinaryOp("==",Id("n"),IntLiteral(1)),
      Return(Some(Id("fact"))),None),For(BinaryOp("=",Id("i"),Id("n")),BinaryOp(">=",Id("i"),IntLiteral(1)),UnaryOp("-",UnaryOp("-",Id("i"))),
      Block(List(),List(If(BinaryOp("<",Id("i"),IntLiteral(0)),Continue,Some(Block(List(),List(For(BinaryOp("=",Id("j"),IntLiteral(0)),
      BinaryOp("<",Id("j"),IntLiteral(100)),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(1))),BinaryOp("=",Id("fact"),BinaryOp("*",Id("fact"),Id("i")))),
      If(BinaryOp("==",Id("j"),IntLiteral(90)),BinaryOp("=",Id("j"),BinaryOp("+",Id("j"),IntLiteral(2))),Some(Continue))))))))),Return(Some(Id("fact")))))),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("num"),IntType)),List(CallExpr(Id("printf"),List(StringLiteral("Enter an integer number :"))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("num"))),CallExpr(Id("printf"),List(StringLiteral("""\nFactorial of %d is = %ld"""),Id("num"),
      CallExpr(Id("factorial"),List(Id("num"))))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,294))
  }
    test("295: Max program") {
    val input ="""float main()
                  {
                     float n1, n2, n3;
                     printf("Enter three numbers: ");
                     scanf("%lf %lf %lf", n1, n2, n3);
                     if (n1 >= n2)
                     {
                         if(n1 >= n3)
                             printf("%.2lf is the largest number.", n1);
                         else
                             printf("%.2lf is the largest number.", n3);
                     }
                     else
                     {
                         if(n2 >= n3)
                             printf("%.2lf is the largest number.", n2);
                         else
                             printf("%.2lf is the largest number.", n3);
                     }
                     return 0.0;
                 }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),FloatType,Block(List(VarDecl(Id("n1"),FloatType),VarDecl(Id("n2"),FloatType),
      VarDecl(Id("n3"),FloatType)),List(CallExpr(Id("printf"),List(StringLiteral("Enter three numbers: "))),
      CallExpr(Id("scanf"),List(StringLiteral("%lf %lf %lf"),Id("n1"),Id("n2"),Id("n3"))),If(BinaryOp(">=",Id("n1"),Id("n2")),
      Block(List(),List(If(BinaryOp(">=",Id("n1"),Id("n3")),CallExpr(Id("printf"),List(StringLiteral("%.2lf is the largest number."),Id("n1"))),
      Some(CallExpr(Id("printf"),List(StringLiteral("%.2lf is the largest number."),Id("n3"))))))),Some(Block(List(),List(If(BinaryOp(">=",
      Id("n2"),Id("n3")),CallExpr(Id("printf"),List(StringLiteral("%.2lf is the largest number."),Id("n2"))),Some(CallExpr(Id("printf"),
      List(StringLiteral("%.2lf is the largest number."),Id("n3"))))))))),Return(Some(FloatLiteral(0.0f))))))));
    assert(checkAst(input,expected,295))
  }
  test("296: If program") {
    val input ="""int main(){
                     if ( a > b == c > d ) {}
                     if ( (a > b) < c ) {}
                     if ( e > a == b ) {}
                     if ( e > !-a/2 && a%3+f<a=b==c || a >= b ) {}
                     if ( a = b == c = d == e ) {}
                     if ( e > a == b || a > b == c > d || (a > b) > c){}
                  }"""
    val expected = Program(List(FuncDecl(Id("main"),List(),IntType,Block(List(),List(If(BinaryOp("==",BinaryOp(">",Id("a"),Id("b")),BinaryOp(">",Id("c"),Id("d"))),Block(List(),List()),None),
      If(BinaryOp("<",BinaryOp(">",Id("a"),Id("b")),Id("c")),Block(List(),List()),None),
      If(BinaryOp("==",BinaryOp(">",Id("e"),Id("a")),Id("b")),Block(List(),List()),None),
      If(BinaryOp("=",BinaryOp("&&",BinaryOp(">",Id("e"),BinaryOp("/",UnaryOp("!",UnaryOp("-",Id("a"))),IntLiteral(2))),
        BinaryOp("<",BinaryOp("+",BinaryOp("%",Id("a"),IntLiteral(3)),Id("f")),Id("a"))),BinaryOp("||",BinaryOp("==",Id("b"),Id("c")),
        BinaryOp(">=",Id("a"),Id("b")))),Block(List(),List()),None),
      If(BinaryOp("=",Id("a"),BinaryOp("=",BinaryOp("==",Id("b"),Id("c")),BinaryOp("==",Id("d"),Id("e")))),Block(List(),List()),None),
      If(BinaryOp("||",BinaryOp("||",BinaryOp("==",BinaryOp(">",Id("e"),Id("a")),Id("b")),BinaryOp("==",BinaryOp(">",Id("a"),Id("b")),
        BinaryOp(">",Id("c"),Id("d")))),BinaryOp(">",BinaryOp(">",Id("a"),Id("b")),Id("c"))),Block(List(),List()),None))))));
    assert(checkAst(input,expected,296))
  }
  test("297: multiple program") {
    val input ="""int multiplyNumbers(int n){}

                  int main()
                  {
                     int n;
                     printf("Enter a positive integer: ");
                     scanf("%d", n);
                     printf("Factorial of %d = %ld", n, multiplyNumbers(n));
                     return 0;
                  }
                  int multiplyNumbers(int n)
                  {
                     if (n >= 1)
                         return n * multiplyNumbers(n-1);
                     else
                         return 1;
                  }"""
    val expected = Program(List(FuncDecl(Id("multiplyNumbers"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("n"),IntType)),List(CallExpr(Id("printf"),List(StringLiteral("Enter a positive integer: "))),
      CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("n"))),CallExpr(Id("printf"),List(StringLiteral("Factorial of %d = %ld"),Id("n"),
      CallExpr(Id("multiplyNumbers"),List(Id("n"))))),Return(Some(IntLiteral(0)))))),
      FuncDecl(Id("multiplyNumbers"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(),List(If(BinaryOp(">=",Id("n"),IntLiteral(1)),
      Return(Some(BinaryOp("*",Id("n"),CallExpr(Id("multiplyNumbers"),List(BinaryOp("-",Id("n"),IntLiteral(1))))))), Some(Return(Some(IntLiteral(1))))))))));
    assert(checkAst(input,expected,297))
  }
  test("298: check prime number program") {
    val input ="""int checkPrime(int n){}
                  int main()
                  {
                     int n, i, flag;
                     printf("Enter a positive integer: ");
                     scanf("%d", n);
                     for(i = 2; i<= n/2; i = i + 1)
                     {
                         // condition for i to be a prime number
                         if (checkPrime(i) == 1)
                         {
                             // condition for n-i to be a prime number
                             if (checkPrime(n-i) == 1)
                             {
                                 // n = primeNumber1 + primeNumber2
                                 printf("%d = %d + %d\n", n, i, n - i);
                                 flag = 1;
                             }

                         }
                     }
                     if (flag == 0)
                         printf("%d cannot be expressed as the sum of two prime numbers.", n);
                     return 0;
                  }
                  // Function to check prime number
                  int checkPrime(int n)
                  {
                     int i, isPrime;

                     for(i = 2; i <= n/2; i = i + 1)
                     {
                         if(n / i == 0)
                         {
                             isPrime = 0;
                             break;
                         }
                     }

                     return isPrime;
                  }"""
    val expected = Program(List(FuncDecl(Id("checkPrime"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(),List())),
      FuncDecl(Id("main"),List(),IntType,Block(List(VarDecl(Id("n"),IntType),VarDecl(Id("i"),IntType),VarDecl(Id("flag"),IntType)),
      List(CallExpr(Id("printf"),List(StringLiteral("Enter a positive integer: "))),CallExpr(Id("scanf"),List(StringLiteral("%d"),Id("n"))),
      For(BinaryOp("=",Id("i"),IntLiteral(2)),BinaryOp("<=",Id("i"),BinaryOp("/",Id("n"),IntLiteral(2))),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),
      IntLiteral(1))),Block(List(),List(If(BinaryOp("==",CallExpr(Id("checkPrime"),List(Id("i"))),IntLiteral(1)),Block(List(),List(If(BinaryOp("==",
      CallExpr(Id("checkPrime"),List(BinaryOp("-",Id("n"),Id("i")))),IntLiteral(1)),Block(List(),List(CallExpr(Id("printf"),
      List(StringLiteral("""%d = %d + %d\n"""),Id("n"),Id("i"),BinaryOp("-",Id("n"),Id("i")))),BinaryOp("=",Id("flag"),IntLiteral(1)))),None))),None)))),
      If(BinaryOp("==",Id("flag"),IntLiteral(0)),CallExpr(Id("printf"),List(StringLiteral("%d cannot be expressed as the sum of two prime numbers."),
      Id("n"))),None),Return(Some(IntLiteral(0)))))),FuncDecl(Id("checkPrime"),List(VarDecl(Id("n"),IntType)),IntType,Block(List(VarDecl(Id("i"),IntType),
      VarDecl(Id("isPrime"),IntType)),List(For(BinaryOp("=",Id("i"),IntLiteral(2)),BinaryOp("<=",Id("i"),BinaryOp("/",Id("n"),IntLiteral(2))),
      BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),Block(List(),List(If(BinaryOp("==",BinaryOp("/",Id("n"),Id("i")),IntLiteral(0)),
      Block(List(),List(BinaryOp("=",Id("isPrime"),IntLiteral(0)),Break)),None)))),Return(Some(Id("isPrime"))))))));
    assert(checkAst(input,expected,298))
  }
  test("299: Big program 3") {
    val input ="""float str[10];
                  int intValue;
                  int stringToInt(float str[]){
                     int i, sum;
                     i = sum = 0;
                     if (str[i] != "0"){
                          if(str[i] < 48 || str[i] > 57){
                             printf("Unable to convert it into integer.\n");
                             return 0;
                          }
                          else{
                              sum = sum * 10 + (str[i] - 48);
                          }
                     }
                     return sum;
                  }
                  int[] main(){
                     printf("Enter any integer as a string: ");
                     scanf("%s",str);
                     intValue = stringToInt(str);
                     printf("Equivalent integer value: %d", intValue);
                     return 0;
                  }"""
    val expected = Program(List(VarDecl(Id("str"),ArrayType(IntLiteral(10),FloatType)),VarDecl(Id("intValue"),IntType),
      FuncDecl(Id("stringToInt"),List(VarDecl(Id("str"),ArrayPointerType(FloatType))),IntType,Block(List(VarDecl(Id("i"),IntType),
      VarDecl(Id("sum"),IntType)),List(BinaryOp("=",Id("i"),BinaryOp("=",Id("sum"),IntLiteral(0))),If(BinaryOp("!=",ArrayCell(Id("str"),Id("i")),StringLiteral("0")),
      Block(List(),List(If(BinaryOp("||",BinaryOp("<",ArrayCell(Id("str"),Id("i")),IntLiteral(48)),BinaryOp(">",ArrayCell(Id("str"),Id("i")),IntLiteral(57))),
      Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("""Unable to convert it into integer.\n"""))),Return(Some(IntLiteral(0))))),
      Some(Block(List(),List(BinaryOp("=",Id("sum"),BinaryOp("+",BinaryOp("*",Id("sum"),IntLiteral(10)),BinaryOp("-",ArrayCell(Id("str"),Id("i")),IntLiteral(48)))))))))),None),Return(Some(Id("sum")))))),
      FuncDecl(Id("main"),List(),ArrayPointerType(IntType),Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("Enter any integer as a string: "))),
      CallExpr(Id("scanf"),List(StringLiteral("%s"),Id("str"))),BinaryOp("=",Id("intValue"),CallExpr(Id("stringToInt"),List(Id("str")))),
      CallExpr(Id("printf"),List(StringLiteral("Equivalent integer value: %d"),Id("intValue"))),Return(Some(IntLiteral(0))))))));
    assert(checkAst(input,expected,299))
  }
  test("300: Huge program") {
    val input ="""/* global variable */
                  int s[10];
                  /* print contents of array */
                  void print_array(int s[])
                  {
                     int i;
                     for(i = 0; i < strlen(s); i = i + 1){
                      printf("%c", s[i]);
                      i = s = 2;
                      if(i = s){
                        s[0] = 1.2E2;
                        continue;
                      }
                     }
                     printf("\n");
                  }
                  /* reverse contents of array in place */
                  void rev_array(int s[])
                  {
                    int c, i, j;
                    for(i = 0; j = strlen(s) - 1 && i < j; i=i+1 && --j)
                    {
                      boolean b[2];
                      b[0] = true;
                      c = s[i];
                      s[i] = s[j];
                      s[j] = c;
                      for(i = 0; i < strlen(s); i = i + 1){
                        printf("%c", s[i]);
                          i = s = 2;
                          if(b[1] == false)
                            s[0] = 1.2E2;
                          else
                            break;
                      }
                    }
                  }

                  /* helper function - performs the recursion */
                  int rec_rev(float s[], int i, int j)
                  {
                    float c;
                    c = s[i];
                    s[i] = s[j];
                    s[j] = c;
                    if(i < j)
                    {
                      i = i + 1;
                      j = j - 1;
                      rec_rev(s, i, j);
                      /*do
                        i = i +1;
                        putInt(i);
                      while (i > 5);*/
                    }
                    return 1;
                  }
                  /* reverse contents of array recursively in place */
                  void rev_array_rec(int s[])
                  {
                    int i, j;
                    i = 0;
                    j = strlen(s)-1;
                    rec_rev(s, i, j);
                  }"""
    val expected = Program(List(VarDecl(Id("s"),ArrayType(IntLiteral(10),IntType)),FuncDecl(Id("print_array"),List(VarDecl(Id("s"),
      ArrayPointerType(IntType))), VoidType,Block(List(VarDecl(Id("i"),IntType)),List(For(BinaryOp("=",Id("i"),IntLiteral(0)),
      BinaryOp("<",Id("i"),CallExpr(Id("strlen"),List(Id("s")))),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),
      Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("%c"),ArrayCell(Id("s"),Id("i")))),BinaryOp("=",Id("i"),BinaryOp("=",Id("s"),
      IntLiteral(2))),If(BinaryOp("=",Id("i"),Id("s")),Block(List(),List(BinaryOp("=",ArrayCell(Id("s"),IntLiteral(0)),FloatLiteral(120.0f)),
      Continue)),None)))),CallExpr(Id("printf"),List(StringLiteral("""\n""")))))),FuncDecl(Id("rev_array"),List(VarDecl(Id("s"),
      ArrayPointerType(IntType))),VoidType,Block(List(VarDecl(Id("c"),IntType),VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType)),
      List(For(BinaryOp("=",Id("i"),IntLiteral(0)),BinaryOp("=",Id("j"),BinaryOp("&&",BinaryOp("-",CallExpr(Id("strlen"),List(Id("s"))),IntLiteral(1)),
      BinaryOp("<",Id("i"),Id("j")))),BinaryOp("=",Id("i"),BinaryOp("&&",BinaryOp("+",Id("i"),IntLiteral(1)),UnaryOp("-",UnaryOp("-",Id("j"))))),
      Block(List(VarDecl(Id("b"),ArrayType(IntLiteral(2),BoolType))),List(BinaryOp("=",ArrayCell(Id("b"),IntLiteral(0)),BooleanLiteral(true)),
      BinaryOp("=",Id("c"),ArrayCell(Id("s"),Id("i"))),BinaryOp("=",ArrayCell(Id("s"),Id("i")),ArrayCell(Id("s"),Id("j"))),
      BinaryOp("=",ArrayCell(Id("s"),Id("j")),Id("c")),For(BinaryOp("=",Id("i"),IntLiteral(0)),BinaryOp("<",Id("i"),CallExpr(Id("strlen"),
      List(Id("s")))),BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),Block(List(),List(CallExpr(Id("printf"),List(StringLiteral("%c"),
      ArrayCell(Id("s"),Id("i")))),BinaryOp("=",Id("i"),BinaryOp("=",Id("s"),IntLiteral(2))),If(BinaryOp("==",ArrayCell(Id("b"),
      IntLiteral(1)),BooleanLiteral(false)),BinaryOp("=",ArrayCell(Id("s"),IntLiteral(0)),FloatLiteral(120.0f)),Some(Break))))))))))),
      FuncDecl(Id("rec_rev"),List(VarDecl(Id("s"),ArrayPointerType(FloatType)),VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType)),IntType,
      Block(List(VarDecl(Id("c"),FloatType)),List(BinaryOp("=",Id("c"),ArrayCell(Id("s"),Id("i"))),BinaryOp("=",ArrayCell(Id("s"),Id("i")),
      ArrayCell(Id("s"),Id("j"))),BinaryOp("=",ArrayCell(Id("s"),Id("j")),Id("c")),If(BinaryOp("<",Id("i"),Id("j")),Block(List(),
      List(BinaryOp("=",Id("i"),BinaryOp("+",Id("i"),IntLiteral(1))),BinaryOp("=",Id("j"),BinaryOp("-",Id("j"),IntLiteral(1))),
      CallExpr(Id("rec_rev"),List(Id("s"),Id("i"),Id("j"))))),None),Return(Some(IntLiteral(1)))))),FuncDecl(Id("rev_array_rec"),
      List(VarDecl(Id("s"),ArrayPointerType(IntType))),VoidType,Block(List(VarDecl(Id("i"),IntType),VarDecl(Id("j"),IntType)),
      List(BinaryOp("=",Id("i"),IntLiteral(0)),BinaryOp("=",Id("j"),BinaryOp("-",CallExpr(Id("strlen"),
      List(Id("s"))),IntLiteral(1))),CallExpr(Id("rec_rev"),List(Id("s"),Id("i"),Id("j"))))))));
    assert(checkAst(input,expected,300))
  }
}
