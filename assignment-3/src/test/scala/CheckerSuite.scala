import org.scalatest.FunSuite
import mc.checker._
import mc.utils._

/**
  * ID: 1511524
  * Name: Tran Quoc Khanh
  * Created by nhphung on 4/29/17.
  */
class CheckerSuite extends FunSuite with TestChecker {
test("Undeclared Function") {
  val input = "void main () {writeIntLn(3);}"
  val expected = "Undeclared Function: writeIntLn"
  assert(checkCkr(input,expected,401))
}
  test("Type Mismatch In Expression: getInt") {
    val input = "void main () {getInt(3);}"
    val expected = "Type Mismatch In Expression: "+CallExpr(Id("getInt"),List(IntLiteral(3))).toString
    assert(checkCkr(input,expected,402))
  }
  test("Type Mismatch In Expression: putIntLn") {

    val input = "void main () {putIntLn();}"
    val expected = "Type Mismatch In Expression: "+CallExpr(Id("putIntLn"),List()).toString
    assert(checkCkr(input,expected,403))
  }
  test("Check with AST") {

    val input = Program(List(
      FuncDecl(Id("main"),List(),VoidType,
        Block(List(),
          List(CallExpr(Id("putIntLn"),List()))))))
    val expected = "Type Mismatch In Expression: "+CallExpr(Id("putIntLn"),List()).toString
    assert(checkAst(input,expected,404))
  }
  test("Check with AST putIntLn with float") {

    val input = Program(List(
      FuncDecl(Id("main"),List(),VoidType,
        Block(List(),
          List(CallExpr(Id("putIntLn"),List(FloatLiteral(1.2f))))))))
    val expected = "Type Mismatch In Expression: "+CallExpr(Id("putIntLn"),List(FloatLiteral(1.2f))).toString
    assert(checkAst(input,expected,405))

  } 

  test("Simple"){
    var input= " int a,b;";
    var expected = "";
    assert(checkCkr(input,expected,406));
  }

  test("Simple error"){
    var input= " int a,a;";
    var expected = "";
    assert(checkCkr(input,expected,407));
  }


  test("Declared Variable: in global: normal var decl") {

    val input = "int a; string b; int c;"
    val expected = ""
    assert(checkCkr(input,expected,408))
  }
  test("Redeclared Variable: in global: same int type") {

    val input = "int a; int a;"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,409))
  }
  test("Redeclared Variable: in global: same float type") {

    val input = "float f; float f;"
    val expected = "Redeclared Variable: f"
    assert(checkCkr(input,expected,410))
  }
  test("Redeclared Variable: in global: same boolean type") {

    val input = "boolean b; boolean b;"
    val expected = "Redeclared Variable: b"
    assert(checkCkr(input,expected,411))
  }
  test("Redeclared Variable: in global: same string type") {

    val input = "string str; string str;"
    val expected = "Redeclared Variable: str"
    assert(checkCkr(input,expected,412))
  }
  test("Redeclared Variable: in global: same array type") {

    val input = "int arr[4]; int arr[7];"
    val expected = "Redeclared Variable: arr"
    assert(checkCkr(input,expected,413))
  }
  test("Redeclared Variable: in global: int type vs string type") {

    val input = "int a; string a; int c;"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,414))
  }
  test("Redeclared Variable: in global: int type vs boolean type") {

    val input = "int a; string str; boolean a;"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,415))
  }
  test("Redeclared Variable: in global: int type vs float type") {

    val input = "int a; float c; float a; boolean b;"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,416))
  }
  test("Redeclared Variable: in global: string type vs float type") {

    val input = "string a; float c; float a; boolean b;"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,417))
  }
  test("Redeclared Variable: in global: string type vs array type") {

    val input = "string a; float a[9];"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,418))
  }

  test("Redeclared Variable: in global: mix type") {

    val input = "string a,b[10],c; float f[8],b;"
    val expected = "Redeclared Variable: b"
    assert(checkCkr(input,expected,419))
  }
  test("Redeclared Variable: in global: decl Function and var has same name") {

    val input = "void foo(){} string foo;"
    val expected = "Redeclared Variable: foo"
    assert(checkCkr(input,expected,420))
  }
  test("Redeclared Function: in global: string type and void foo()") {

    val input = "string foo; void foo(){} "
    val expected = "Redeclared Function: foo"
    assert(checkCkr(input,expected,421))
  }

  test("Redeclared Parameter: same int type") {

    val input = "int foo(int a, int a){}"
    val expected = "Redeclared Parameter: a"
    assert(checkCkr(input,expected,422))
  }
  test("Redeclared Parameter: different int type vs float type") {

    val input = "int foo(int a, float a){}"
    val expected = "Redeclared Parameter: a"
    assert(checkCkr(input,expected,423))
  }
  test("Redeclared Parameter: int type vs array pointer type") {

    val input = "int foo(int a, boolean a[]){}"
    val expected = "Redeclared Parameter: a"
    assert(checkCkr(input,expected,424))
  }
  test("Redeclared Parameter: same array pointer type") {

    val input = "int foo(int a[], boolean a[]){}"
    val expected = "Redeclared Parameter: a"
    assert(checkCkr(input,expected,425))
  }

  test("Redeclared Variable: func has parameter") {

    val input = "int foo(float f) {} void foo(int a, boolean c) {}"
    val expected = "Redeclared Function: foo"
    assert(checkCkr(input,expected,426))
  }

  test("Redeclared Variable: double parameter") {

    val input = "void main(int a, string a, int c){}"
    val expected = "Redeclared Parameter: a"
    assert(checkCkr(input,expected,427))
  }
  test("Redeclared Variable: in local func: same int type") {

    val input = "void main(){int a,b,a;}"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,428))
  }
  test("Redeclared Variable: in local func: int type vs  string type") {

    val input = "void main(){int a,b; string a;}"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,429))
  }
  test("Redeclared Variable: in local func: int type vs  array type") {

    val input = "void main(){int a,b; string a[10];}"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,430))
  }
  test("Redeclared Variable: parameter int type vs decl int type") {

    val input = "void main(int a_){int a_,b;}"
    val expected = "Redeclared Variable: a_"
    assert(checkCkr(input,expected,431))
  }
  test("Redeclared Variable: parameter arr pointer type vs block int type") {

    val input = "void main(int a[]){int a,b;}"
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,432))
  }
  test("Redeclared Variable: parameter int type vs decl int type global - 433") {

    val input = "int a; void main(int a){int c5,b;} "
    val expected = ""
    assert(checkCkr(input,expected,433))
  }
  test("Redeclared Variable: scope level2: different name") {

    val input = 
    """void main(int a){
          int c,b;
          {
            int a,b;
          }
        } 
      int a;
          """
    val expected = ""
    assert(checkCkr(input,expected,434))
  }
  test("Redeclared Variable: in scope level2: same int type") {

    val input = 
    """void main(int a){
          int c,b;
          {
            int a,b,a;
          }
        } 
      int a;
          """
    val expected = "Redeclared Variable: a"
    assert(checkCkr(input,expected,435))
  }
  test("Undeclared Function: not decl foo()") {
    val input = 
    """void main(int a){
          foo();
        } 
          """
    val expected = "Undeclared Function: foo"
    assert(checkCkr(input,expected,436))
  }
  test("Undeclared Function: foo() has override by local var decl") {
    val input = 
    """void main(int a){
          int  foo;
          foo();
        }
        void foo(){} 
          """
    val expected = "Undeclared Function: foo"
    assert(checkCkr(input,expected,437))
  }
  test("Undeclared Function: scope level2: foo() has override by local var decl") {
    val input = 
    """void main(int a){
          int  foo;
          {
            foo();
          }
        }
        void foo(){} 
          """
    val expected = "Undeclared Function: foo"
    assert(checkCkr(input,expected,438))
  }
  test("Undeclared Identifier: d") {
    val input = 
    """void main(int a){
          int c,b;
          d;
        } 
          """
    val expected = "Undeclared Identifier: d"
    assert(checkCkr(input,expected,439))
  }
  test("Undeclared Identifier: d in Expression stmt") {
    val input = 
    """void main(int a){
          int c,b;
          b = d +  b - c;
        } 
          """
    val expected = "Undeclared Identifier: d"
    assert(checkCkr(input,expected,440))
  }
  test("Undeclared Identifier: in ") {
    val input = 
    """void main(int a){
          int c,b;
          foo(d);
        } 
        void foo(int b){}
          """
    val expected = "Undeclared Identifier: d"
    assert(checkCkr(input,expected,441))
  }

  test("Mismatch Stmt If: expr is 'false'") {
    val input = 
    """void main(int a){
          int c,b;
          if(false)
            return;
        } 
          """
    val expected = ""
    assert(checkCkr(input,expected,442))
  }
  test("Mismatch Stmt If: expr is 'true'") {
    val input = 
    """void main(int a){
          int b;
          if(true)
            a = 2;
        } 
          """
    val expected = ""
    assert(checkCkr(input,expected,443))
  }
  test("Mismatch Stmt If: expr is Integer") {
    val input = 
    """void main(int a){
          int b;
          if(10)
            a = 2;
        } 
          """
    val expected = """Type Mismatch In Statement: If(IntLiteral(10),BinaryOp("=",Id("a"),IntLiteral(2)),None)"""
    assert(checkCkr(input,expected,444))
  }
  test("Mismatch Stmt If: expr is String") {
    val input = 
    """void main(int a){
          int b;
          if("this is string")
            a = 2;
        } 
          """
    val expected = """Type Mismatch In Statement: If(StringLiteral("this is string"),BinaryOp("=",Id("a"),IntLiteral(2)),None)"""
    assert(checkCkr(input,expected,445))
  }
  test("Mismatch Expression: Assign: int type vs int literal") {
    val input = 
    """void main(int a){
          boolean b[10];
          a = 2;
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,446))
  }
  test("Mismatch Expression: Assign: int type vs boolean literal") {
    val input = 
    """void main(int a){
          boolean b[10];
          a = true;
        } 
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("a"),BooleanLiteral(true))"""
    assert(checkCkr(input,expected,447))
  }
  test("Mismatch Expression: Div: 2 vs 3.2") {
    val input = 
    """void main(int a){
          boolean b[10];
          2/3.2;
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,448))
  }
  test("Mismatch Expression: Div Op: 2 vs 3") {
    val input = 
    """void main(int a){
          boolean b[10];
          2/3;
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,449))
  }
  test("Mismatch Expression: Assign Op: LHS is void type") {
    val input = 
    """void main(int a){
          foo() = a;
        } 
        void foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",CallExpr(Id("foo"),List()),Id("a"))"""
    assert(checkCkr(input,expected,450))
  }
  test("Mismatch Expression: Assign Op: LHS vs RHS is void type") {
    val input = 
    """void main(int a){
          foo() = foo();
        } 
        void foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",CallExpr(Id("foo"),List()),CallExpr(Id("foo"),List()))"""
    assert(checkCkr(input,expected,451))
  }
  test("Mismatch Expression: Assign Op: RHS is void type") {
    val input = 
    """void main(int a){
          a = foo();
        } 
        void foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("a"),CallExpr(Id("foo"),List()))"""
    assert(checkCkr(input,expected,452))
  }
  test("Mismatch Expression: Assign Op: LHS is ArrayPointerType") {
    val input = 
    """void main(int a){
          foo() = 8;
        } 
        int[] foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",CallExpr(Id("foo"),List()),IntLiteral(8))"""
    assert(checkCkr(input,expected,453))
  }

  test("Mismatch Expression: Assign Op: LHS vs RHS is ArrayPointerType") {
    val input = 
    """void main(int a){
          foo() = foo();
        } 
        int[] foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",CallExpr(Id("foo"),List()),CallExpr(Id("foo"),List()))"""
    assert(checkCkr(input,expected,454))
  }
  test("Mismatch Expression: Assign Op: LHS is ArrayType") {
    val input = 
    """void main(int a){
          int arr[10];
          arr = a;
        } 
        int[] foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("arr"),Id("a"))"""
    assert(checkCkr(input,expected,455))
  }
  test("Mismatch Expression: Assign Op: LHS vs RHS is ArrayType") {
    val input = 
    """void main(int a){
          int arr[10];
          arr = arr;
        } 
        int[] foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("arr"),Id("arr"))"""
    assert(checkCkr(input,expected,456))
  }
  test("Mismatch Expression: Assign Op: RHS is ArrayType") {
    val input = 
    """void main(int a){
          int arr[10];
          foo() = arr;
        } 
        int[] foo(){}
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",CallExpr(Id("foo"),List()),Id("arr"))"""
    assert(checkCkr(input,expected,457))
  }
  test("Mismatch Expression: Assign Op: Int type to Float Type") {
    val input = 
    """void main(int a){
          float f;
          int b[10];
          f = a;
          f = 3;
          f = b[10];
          f = 2+3/4;
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,458))
  }
  test("Mismatch Expression: Assign Op: Int type to Array cell float type") {
    val input = 
    """void main(int a){
          float f[100];
          f[10] = 2+3/4 + 3%3;
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,459))
  }
  test("Mismatch Expression: Assign Op: Float type to Int Type") {
    val input = 
    """void main(int a){
          float f;
          a = f;
        } 
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("a"),Id("f"))"""
    assert(checkCkr(input,expected,460))
  }
  test("Mismatch Expression: Assign Op: Float literal to Int Type") {
    val input = 
    """void main(int a){
          float f;
          a = 4.2;
        } 
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("a"),FloatLiteral(4.2))"""
    assert(checkCkr(input,expected,461))
  }
  test("Mismatch Expression: Assign Op: boolean assign to string") {
    val input = 
    """void main(int a){
          boolean b[10];
          string str;
          str = b[9];
        }
          """
    val expected = """Type Mismatch In Expression: BinaryOp("=",Id("str"),ArrayCell(Id("b"),IntLiteral(9)))"""
    assert(checkCkr(input,expected,462))
  }
  test("Mismatch Expression: Negative Op: Float type") {
    val input = 
    """void main(int a){
          float f;
          - f;
          - 4.2;
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,463))
  }
  test("Mismatch Expression: Negative Op: Boolean literal") {
    val input = 
    """void main(int a){
          boolean b;
          - true;
        } 
          """
    val expected = """Type Mismatch In Expression: UnaryOp("-",BooleanLiteral(true))"""
    assert(checkCkr(input,expected,464))
  }
  test("Mismatch Expression: Negative Op: Boolean Type") {
    val input = 
    """void main(int a){
          boolean b;
          - b;
        } 
          """
    val expected = """Type Mismatch In Expression: UnaryOp("-",Id("b"))"""
    assert(checkCkr(input,expected,465))
  }
  test("Mismatch Expression: Not Op: Int type") {
    val input = 
    """void main(int a){
          boolean b;
          !a;
        } 
          """
    val expected = """Type Mismatch In Expression: UnaryOp("!",Id("a"))"""
    assert(checkCkr(input,expected,467))
  }
  test("Mismatch Expression: Unary Op: nest Expression") {
    val input = 
    """void main(int a){
          boolean b;
          --!b;
        } 
          """
    val expected = """Type Mismatch In Expression: UnaryOp("-",UnaryOp("!",Id("b")))"""
    assert(checkCkr(input,expected,468))
  }
  test("Mismatch Expression: Unary Op: nor op with boolean cell") {
    val input = 
    """void main(int a){
          boolean b[10];
          !b[8];
        } 
          """
    val expected = """"""
    assert(checkCkr(input,expected,469))
  }
  test("Mismatch Expression: Not Op: string literal") {
    val input = 
    """void main(int a){
          boolean b;
          !"This is string";
        } 
          """
    val expected = """Type Mismatch In Expression: UnaryOp("!",StringLiteral("This is string"))"""
    assert(checkCkr(input,expected,470))
  }
  test("Mismatch Expression: ArrayCell: array expr is int type") {
    val input = 
    """void main(int a){
          a[10];
        } 
          """
    val expected = """Type Mismatch In Expression: ArrayCell(Id("a"),IntLiteral(10))"""
    assert(checkCkr(input,expected,471))
  }
  test("Mismatch Expression: ArrayCell: index expr is float literal") {
    val input = 
    """void main(int a[]){
          a[4.3];
        } 
          """
    val expected = """Type Mismatch In Expression: ArrayCell(Id("a"),FloatLiteral(4.3))"""
    assert(checkCkr(input,expected,472))
  }
  test("Mismatch Expression: ArrayCell: array expr is function") {
    val input = 
    """void main(int a[]){
          foo()[4];
        } 
        float[] foo(){}
          """
    val expected = """"""
    assert(checkCkr(input,expected,473))
  }
  test("Mismatch Expression: ArrayCell: index expr is array type") {
    val input = 
    """void main(int a[]){
          foo()[a];
        } 
        float[] foo(){}
          """
    val expected = """Type Mismatch In Expression: ArrayCell(CallExpr(Id("foo"),List()),Id("a"))"""
    assert(checkCkr(input,expected,474))
  }
  test("Mismatch Expression: ArrayCell: array expr has blacket ") {
    val input = 
    """void main(int a[]){
          (foo())[1];
        } 
        float[] foo(){}
          """
    val expected = """"""
    assert(checkCkr(input,expected,475))
  }
  test("Mismatch Expression: ArrayCell: index expr is BinaryOp") {
    val input = 
    """void main(int a[]){
          (foo())[(1+4/3)];
        } 
        float[] foo(){}
          """
    val expected = """"""
    assert(checkCkr(input,expected,476))
  }
  test("Mismatch Expression: ArrayCell: index expr is ArrayCell") {
    val input = 
    """void main(int a[]){
          foo()[foo()[8]];
        } 
        int[] foo(){}
          """
    val expected = """"""
    assert(checkCkr(input,expected,477))
  }
  test("Mismatch Expression: ArrayCell: index expr is nest ArrayCell") {
    val input = 
    """void main(float a[]){
          foo()[foo()[a[9]]];
        } 
        int[] foo(){}
          """
    val expected = """Type Mismatch In Expression: ArrayCell(CallExpr(Id("foo"),List()),ArrayCell(Id("a"),IntLiteral(9)))"""
    assert(checkCkr(input,expected,478))
  }
  test("Mismatch Expression: Function call: reference Float type to parameter as Int Type") {
    val input = 
    """void main(float a){
          foo(a);
        } 
        int foo(int a){}
          """
    val expected = """Type Mismatch In Expression: CallExpr(Id("foo"),List(Id("a")))"""
    assert(checkCkr(input,expected,479))
  }
  test("Mismatch Expression: Function call: reference Float type to parameter as Int Type in the nest fucntion call") {
    val input = 
    """void main(float a){
          foo(foo(a));
        } 
        int foo(int a){}
          """
    val expected = """Type Mismatch In Expression: CallExpr(Id("foo"),List(Id("a")))"""
    assert(checkCkr(input,expected,480))
  }
  test("Mismatch Expression: Function call: mutitle parameter vs para has array type") {
    val input = 
    """void main(int t, float f[]){
          boolean a,b[10];
          float c,d[10];
          foo(a,foo(a,b,c,d),c,f);
          func(t);
        } 
        boolean[] foo(boolean a,boolean b[], float c, float d[]){}
        int func(float a){}
          """
    val expected = """"""
    assert(checkCkr(input,expected,481))
  }
  test("Mismatch Stmt: Return: array type") {
    val input = 
    """float main(int t, float f[]){
          int a[10];
          return 2; 
        } 
        int[] func(float a){}
          """
    val expected = """"""
    assert(checkCkr(input,expected,482))
  }

  test("Mismatch Expression: ArrayCell: index is ArrayCell") {
    val input = 
    """
          void getInt(){}
          
          int foo(float b[]) {}

        float main() {
          float f[10];
          foo(f);
//          foo(foo(fo));
        return 3;
        }   """
    val expected = """Type Mismatch In Expression: ArrayCell(Id("a"),FloatLiteral(4.3))"""
    assert(checkCkr(input,expected,483))
  }

//   test("checkType") {
//     val checker = new StaticChecker(null)
//     val l = List(FloatType,IntType,ArrayPointerType(IntType))
//     val r = List(IntType,IntType,ArrayPointerType(IntType))
// //    assert(checker.checkType(FloatType,IntType))
//     assert(l.zip(r).forall(x=> checker.checkType(x._1, x._2)))
//   }
  test("Undeclared Function 2") {
    val input = "void main () {writeIntLn(3);}"
    val expected = "Undeclared Function: writeIntLn"
    assert(checkCkr(input,expected,484))
  }
  test("Type Mismatch In Expression: getInt 485") {
    val input = "void main () {getInt(3);}"
    val expected = "Type Mismatch In Expression: "+CallExpr(Id("getInt"),List(IntLiteral(3))).toString
    assert(checkCkr(input,expected,485))
  }
  test("Type Mismatch In Expression: putIntLn 486") {

    val input = "void main () {putIntLn();}"
    val expected = "Type Mismatch In Expression: "+CallExpr(Id("putIntLn"),List()).toString
    assert(checkCkr(input,expected,486))
  }
}