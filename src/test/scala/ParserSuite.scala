import org.scalatest.FunSuite

/**
  * Created by nhphung on 4/28/17.
  */
class ParserSuite  extends FunSuite with TestParser {


  test("a simple program") {
    val input = "int main () {}"
    val expect = "sucessful"
    assert(checkRec(input,expect,101))
  }
  test("more complex program") {
    val input ="""int main () {
	putIntLn(4);
}"""
    val expect ="sucessful"
    assert(checkRec(input,expect,102))
  }
  test("wrong program"){
    val input = "} int main {"
    val expect = "Error on line 1 col 1: }"
    assert(checkRec(input,expect,103))
  }

    test("104-more complex program") {
    val input ="""int main () {
    int a,b,c,d[10],e,f;
}"""
    val expect ="sucessful"
    assert(checkRec(input,expect,104))
  }

  test("105-more complex program") {
    val input ="""
    int ga,ba,ag;
    int main () {
    float a,b,c;
    int d[2],e;
    string x,y;
    }"""
    val expect ="sucessful"
    assert(checkRec(input,expect,105))
  }

    test("106-initialize variable") {
    val input ="""
    int ga,ba,ag = 5;
    int main () {
    float a,b,c;
    int d[2],e;
    string x,y;
    }"""
    val expect ="sucessful"
    assert(checkRec(input,expect,106))
  }

    test("107-initialize variable at declaration time") {
    val input ="""
    int ga,ba,ag;
    int main () {
    float a,b,c;
    int d[2],e;
    int aa = 2;
    string x,y;
    }"""
    val expect ="sucessful"
    assert(checkRec(input,expect,107))
  }

    test("108-comment in declaration variable") {
    val input ="""
    int ga,ba,ag;//this is comment
    int main () {/*this is block comment
    float a,b,c;
    int d[2],e;*/
    string x,y;
    }"""
    val expect ="sucessful"
    assert(checkRec(input,expect,108))
  }

}
