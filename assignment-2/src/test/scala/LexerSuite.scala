import org.scalatest.FunSuite

/**
  * Created by nhphung on 4/28/17.
  */
class LexerSuite extends FunSuite with TestLexer {

  test("a simple identifier") {
    val input = """printf("Enter a base number: ");""""
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
  val expect = """Illegal escape in string: abc aks    ab ' c 

""";
  assert(checkLex(input,expect,12));
}

test("false string"){
    val input = """"\"abc aks \t ab \' c \n  d a\""""";
  val expect = """\"abc aks \t ab \' c \n  d a\",<EOF>""";
  assert(checkLex(input,expect,13));
}

test("test string"){
  val input = "\"Hello. It's string. It use all support escape like as \\ \\ \t \n \r \" ";
  val expect = """Illegal escape in string: Hello. It's string. It use all support escape like as \""";
  assert(checkLex(input,expect,14));
}

test("15 open and close parentheses"){
    val input = "0.5 0.8e-1 int"
    val expect = """0.5,0.8e-1,int,<EOF>"""
    assert(checkLex(input,expect,15))
  }

  test("float and comment"){
    val input = "0.5 0.8e-1 //"
    val expect = """0.5,0.8e-1,<EOF>"""
    assert(checkLex(input,expect,16))
  }

  test("float comment nest commnet"){
    val input = """/*this is /*comment nest comment*/*/""";
    var expect = "<EOF>";
    assert(checkLex(input,expect,17));
  }


  test("test float8"){
    val input=".2e2"
    val expect=""".2e2,<EOF>"""
    assert(checkLex(input,expect,18))
  }
  test("test float9"){
    val input=".2e2"
    val expect=""".2e2,<EOF>"""
    assert(checkLex(input,expect,19))
  }

  test("test float11") {
    val input = "2e-2"
    val expect ="""2e-2,<EOF>"""
    assert(checkLex(input, expect, 22))
  }
  test("test bool1") {
    val input = "true"
    val expect ="""true,<EOF>"""
    assert(checkLex(input, expect, 20))
  }

  test("test cmt") {
    val input = """/*abc*/"""
    val expect ="""<EOF>"""
    assert(checkLex(input, expect, 21))
  }


  test("test23") {
    val input =""" "abc\r" """
    val expect = """abc\r,<EOF>"""
    assert(checkLex(input,expect,23))
  }

  test("test24"){
    val input = """ "abc\r\n\" """
    val expect = """Unclosed string: abc\r\n\" """
    assert(checkLex(input,expect,24))
  }

  test("test25"){
    val input = "\"Illegal escape in string: in string in string\n\"legal string\""
    val expect = """ Unclosed string: Illegal escape in string: in string in string """
    assert(checkLex(input,expect,25))
  }

  test("test26"){
    val input =  """ "abc \asd"""
    val expect = """Illegal escape in string: in string in string in string: abc \"""
    assert(checkLex(input,expect,26))
  }

  test("test27"){
    val input =   """string s = "abc"""
    val expect =  """string,s,=,Unclosed string: abc"""
    assert(checkLex(input,expect,27))
  }

  test("test28"){
    val input = "/*comment ne /*nest ne*/ end */ abcxyz"
    val expect =   """end,*,/,abcxyz,<EOF>"""
    assert(checkLex(input,expect,28))
  }

  test("test29"){
    val input = """ 12_abc """
    val expect =   """12,_abc,<EOF>"""
    assert(checkLex(input,expect,29))
  }

  test("test30"){
    val input = """ "ac\" """
    val expect =   """Unclosed string: ac\" """
    assert(checkLex(input,expect,30))
  }



  test("test32"){
    val input = """"abc\n""""
    val expect = """abc\n"""
    assert(checkLex(input,expect,32))
  }
  test("test33"){
    val input = """ ""abc" """
    val expect = """,,abc,Unclosed string:"""
    assert(checkLex(input,expect,33))
  }
  test("test34"){
    val input = """ "ac """
    val expect =   """Unclosed string: ac"""
    assert(checkLex(input,expect,34))
  }
  test("test35"){
    val input = """ "ac\" """
    val expect =   """Unclosed string: ac\",<EOF>"""
    assert(checkLex(input,expect,35))
  }
  test("test36"){
    val input = """ "ac\n """
    val expect =   """Unclosed string: ac\n,<EOF>"""
    assert(checkLex(input,expect,36))
  }
  test("test37"){
    val input = """ /*abcs*/ """
    val expect =   """<EOF>"""
    assert(checkLex(input,expect,37))
  }
  test("test38"){
    val input = """ //bc """
    val expect =   """<EOF>"""
    assert(checkLex(input,expect,38))
  }
  test("test39"){
    val input = """ //ac\ """
    val expect =   """<EOF>"""
    assert(checkLex(input,expect,39))
  }
  test("test40"){
    val input = """ bool  """
    val expect =   """bool,<EOF>"""
    assert(checkLex(input,expect,40))
  }
  test("test41"){
    val input = """ "ac\" """
    val expect =   """Unclosed string: ac\" """
    assert(checkLex(input,expect,41))
  }
  test("test42"){
    val input = """ "ac\a" """
    val expect =   """Illegal escape in string: in string ac\"""
    assert(checkLex(input,expect,42))
  }
  test("test43"){
    val input = """ "12a" """
    val expect =   """12a,<EOF>"""
    assert(checkLex(input,expect,43))
  }
  test("test44"){
    val input = """ "abc \n" """
    val expect =   """abc\n,<EOF>"""
    assert(checkLex(input,expect,44))
  }
  test("test45"){
    val input = """ "123a\\" """
    val expect =   """123a\\",<EOF>"""
    assert(checkLex(input,expect,45))
  }
  test("test46"){
    val input = """ "ac\"" """
    val expect =   """ac\",<EOF>"""
    assert(checkLex(input,expect,46))
  }
  test("test47"){
    val input = """ "ac\b" """
    val expect =   """ ac\b,<EOF>"""
    assert(checkLex(input,expect,47))
  }
  test("test48"){
    val input = """ //abc/ """
    val expect =   """<EOF>"""
    assert(checkLex(input,expect,48))
  }
  test("test49"){
    val input = """ //ac\ """
    val expect =   """<EOF>"""
    assert(checkLex(input,expect,49))
  }
  test("test50"){
    val input = """ "asd" " """
    val expect =   """ asd, Unclosed string: ,<EOF>"""
    assert(checkLex(input,expect,50))
  }
  test("test51") {
    val input = """12+3*2=5a"""
    val expect = """12,+,3,*,2,=,5,a,<EOF>"""
    assert(checkLex(input, expect, 51))
  }

    test("error token1") {
    val input = "177#12"
    val expect = """177,ErrorToken #"""
    assert(checkLex(input, expect, 52))
  }
  test("error token 2") {
    val input = "_12#12"
    val expect = """_12,ErrorToken #"""
    assert(checkLex(input, expect, 53))
  }
  test("tets id1") {
    val input = "a12"
    val expect = """a12,<EOF>"""
    assert(checkLex(input, expect, 54))
  }
  test("tets id2") {
    val input = "a12_"
    val expect = """a12_,<EOF>"""
    assert(checkLex(input, expect, 55))
  }
  test("tets id3") {
    val input = "abc"
    val expect = """abc,<EOF>"""
    assert(checkLex(input, expect, 56))
  }
  test("tets id4") {
    val input = "__"
    val expect = """__,<EOF>"""
    assert(checkLex(input, expect, 57))
  }
  test("tets id5") {
    val input = "_"
    val expect = """_,<EOF>"""
    assert(checkLex(input, expect, 58))
  }
  test("test float1"){
    val input="1.1"
    val expect="""1.1,<EOF>"""
    assert(checkLex(input,expect,59))
  }
  test("test float2"){
    val input="1."
    val expect="""1.,<EOF>"""
    assert(checkLex(input,expect,60))
  }
  test("test float3"){
    val input=".1"
    val expect=""".1,<EOF>"""
    assert(checkLex(input,expect,61))
  }
  test("test float4"){
    val input="1e2"
    val expect="""1e2,<EOF>"""
    assert(checkLex(input,expect,62))
  }
  test("test float5"){
    val input="1.2E-2"
    val expect="""1.2E-2,<EOF>"""
    assert(checkLex(input,expect,63))
  }
  test("test float6"){
    val input="1.1e-2"
    val expect="""1.1e-2,<EOF>"""
    assert(checkLex(input,expect,64))
  }
  test("test float7"){
    val input=".2"
    val expect=""".2,<EOF>"""
    assert(checkLex(input,expect,65))
  }

  test("a simple  true program") {
    val input = "int main () {}"
    val expect = """int,main,(,),{,},<EOF>"""
    assert(checkLex(input,expect,66))
  }
  test("more complex  true program") {
    val input ="""int main () {
  putIntLn(4);
}"""
    val expect ="""int,main,(,),{,putIntLn,(,4,),;,},<EOF>""";
    assert(checkLex(input,expect,67))
  }
  test("wrong program"){
    val input = "} int main {"
    val expect = """},int,main,{,<EOF>"""
    assert(checkLex(input,expect,68))
  }

    test("104-more complex program") {
    val input ="""int main () {
    int a,b,c,d[10],e,f;
}"""
    val expect ="""int,main,(,),{,int,a,,,b,,,c,,,d,[,10,],,,e,,,f,;,},<EOF>"""
    assert(checkLex(input,expect,69))
  }

  test("105-more complex program") {
    val input ="""
    int ga,ba,ag;
    int main () {
    float a,b,c;
    int d[2],e;
    string x,y;
    }"""
    val expect ="""int,ga,,,ba,,,ag,;,int,main,(,),{,float,a,,,b,,,c,;,int,d,[,2,],,,e,;,string,x,,,y,;,},<EOF>"""
    assert(checkLex(input,expect,70))
  }

    test("106-initialize variable") {
    val input ="""
    int ga,ba,ag = 5;
    int main () {
    float a,b,c;
    int d[2],e;
    string x,y;
    }"""
    val expect ="""int,ga,,,ba,,,ag,=,5,;,int,main,(,),{,float,a,,,b,,,c,;,int,d,[,2,],,,e,;,string,x,,,y,;,},<EOF>"""
    assert(checkLex(input,expect,71))
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
    val expect ="""int,ga,,,ba,,,ag,;,int,main,(,),{,float,a,,,b,,,c,;,int,d,[,2,],,,e,;,int,aa,=,2,;,string,x,,,y,;,},<EOF>"""
    assert(checkLex(input,expect,72))
  }

    test("108-comment in declaration variable") {
    val input ="""
    int ga,ba,ag;//this is comment
    int main () {/*this is block comment
    float a,b,c;
    int d[2],e;*/
    string x,y;
    }"""
    val expect ="""int,ga,,,ba,,,ag,;,int,main,(,),{,string,x,,,y,;,},<EOF>"""
    assert(checkLex(input,expect,73))
  }

        test("109-call function") {
    val input ="""
    int ga,ba,ag;//this is comment
    int main () {/*this is block comment
    float a,b,c;
    int d[2],e;*/
    helloLingo();
    }"""
    val expect ="""int,ga,,,ba,,,ag,;,int,main,(,),{,helloLingo,(,),;,},<EOF>"""
    assert(checkLex(input,expect,74))
  }

test("full program three"){
val input =
"""int k;
break;
int foo(){}
""";
val expect = """int,k,;,break,;,int,foo,(,),{,},<EOF>"""
assert(checkLex(input,expect,75))
}

test("test LExer 111")  {
    val input = """
      void goiham(){
        return;
      }
      void foo (float x[]) {
      float y[10] ;
      int z[10] ;
      goiham();
      foo (x) ; //CORRECT
      foo (y,2) ; //CORRECT
      foo (z) ; //WRONG
      return 123[2];
      }
      void goo (float x[]) {
      float y[10] ;
      int z[10] ;
      foo (x) ; //CORRECT
      foo ?? ; //CORRECT
      foo (z) ; //WRONG
      return;
      }"""
    val expect = """void,goiham,(,),{,return,;,},void,foo,(,float,x,[,],),{,float,y,[,10,],;,int,z,[,10,],;,goiham,(,),;,foo,(,x,),;,foo,(,y,,,2,),;,foo,(,z,),;,return,123,[,2,],;,},void,goo,(,float,x,[,],),{,float,y,[,10,],;,int,z,[,10,],;,foo,(,x,),;,foo,ErrorToken ?,"""
    assert(checkLex(input,expect,76))
  }

  test("test LExer 112"){
    val input = "} int main {"
    val expect = "Error on line 1 col 1: }"
    assert(checkLex(input,expect,77))
  }
   test("test LExer 113") {
    val input ="""int main () {
        int a,c,b;
        a=foo(1,2);
        return foo(a,b);
    }"""
    val expect ="sucessful"
    assert(checkLex(input,expect,78))
  }
  test("test LExer 114"){
    val input = """void fun(int a, int b){
          a[3]=b[1]=c[2];
      }"""
    val expect = "sucessful"
    assert(checkLex(input,expect,79))
  }
  test("test LExer 115"){
    val input = """
    int main(){
    int ia[50], i, in, isum ;
    isum=0;
    printf("Nhap vao gia tri n: ");
    scanf("%d", in);
    //Nhap du lieu vao mang
      for(i = 0; i < in; i=i+1)
    {
      printf("Nhap vao phan tu thu %d: ", i + 1);
      scanf("%d", ia[i]); //Nhap gia tri cho phan tu thu i
    }
    //Tinh tong gia tri cac phan tu
        for(i = 0; i < in; i=i+1)
       isum = isum + ia[i]; //cong don tung phan tu vao isum
        printf("Trung binh cong: %.2fn", (float)isum/in);
        getch();
    }
    """
    val expect = """Error on line 16 col 43: float"""
    assert(checkLex(input,expect,80))
  }
  test("test LExer 116"){
    val input = """int i ;
    int f() {
    return 200;
      }
    void main ( ) {
      int main ;
      main = f() ;
      putIntLn(i);
    {
      int i ;
      abc[123][123];
      int main ;
      int f ;
      main = f = i = 100;
      putIntLn(i) ;
      putIntLn( main ) ;
      putIntLn(f) ;
      }
      putIntLn(main);
    }
    """
    val expect = """Error on line 11 col 15: ["""
    assert(checkLex(input,expect,81))
  }
  test("test LExer 117"){
    val input = """ 
    int max_2(int a, int b) {
    return a > b ? a : b;
}
 
int max_3(int a, int b, int c) {
    return max_2(max_2(a, b), c);
}
 
int main(){
    int a = 7, b = 13, c = 4;
    printf("So lon nhat la %d", max_3(a, b, c));
    return 0;
}"""
   val expect = """Error Token ?"""
    assert(checkLex(input,expect,82))
  }
  test("test LExer 118"){
    val input = """void foo(){
      a&&b;
      do a<3; while(a > (b < c) );
    }"""
    val expect = """sucessful"""
    assert(checkLex(input,expect,83))
  }

   test("test LExer 119"){
    val input = """
    void main(){
    int a;
    for (i;!((line[i] >= "a" && line[i] <= "z") || (line[i] >= "A" && line[i] <= "Z") || line[i] == "\n");i)
    i=i+2;
    if(true) return;;
  }
    """
    val expect = """sucessful"""
    assert(checkLex(input,expect,84))
  }
  test("testcases for ParserSuite 120"){
    val input = """
void print(int );
 
int main()
{
  int n;
  scanf("%d", &&n);
 
  print(n);
  
  return 0;
}
 
void print(int n)
{
  static int c = 1;
 
  if (c == n+1)
    return;
 
  printf("%d\n", c);
  c++;
  print(n);
}
"""
   val expect = "Error Token &"
    assert(checkLex(input,expect,85))
  }

test("test lexer"){
 val input = """int main()
{
    double number;

    printf("Enter a number: ");
    scanf("%lf", &number);

    if (number <= 0.0)
  
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,86));
}

test("test lexer 87"){
 val input = """int main()
{

    if (number <= 0.0)
    {
        if (number == 0.0)
            printf("You entered 0.");
        else
            printf("You entered a negative number.");
    }
    else
        printf("You entered a positive number.");
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,87));
}

test("test lexer 88"){
 val input = """int main()
{
    double number;

    printf("Enter a number: ");
    scanf("%lf", &number);

    if (number <= 0.0)
    {
    
    }
    else
        printf("You entered a positive number.");
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,88));
}

test("test lexer 89"){
 val input = """int main()
{
    int n, i;

    printf("Enter an integer: ");
    scanf("%d",&n);

    for(i=1; i<=10; ++i)
    {
        printf("%d * %d = %d \n", n, i, n*i);
    }
    
    return 0;
}

""";
val expect = """""";
assert(checkLex(input,expect,89));
}

test("test lexer 90"){
 val input = """int main()
{
    int i, n, t1 = 0, t2 = 1, nextTerm;

    printf("Enter the number of terms: ");
    scanf("%d", &n);

    printf("Fibonacci Series: ");

    for (i = 1; i <= n; ++i)
    {
        printf("%d, ", t1);
        nextTerm = t1 + t2;
        t1 = t2;
        t2 = nextTerm;
    }
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,90));
}

test("test lexer 91"){
 val input = """int main()
{
    int t1 = 0, t2 = 1, nextTerm = 0, n;

    printf("Enter a positive number: ");
    scanf("%d", &n);

    // displays the first two terms which is always 0 and 1
    printf("Fibonacci Series: %d, %d, ", t1, t2);

    nextTerm = t1 + t2;

    while(nextTerm <= n)
    {
        printf("%d, ",nextTerm);
        t1 = t2;
        t2 = nextTerm;
        nextTerm = t1 + t2;
    }
    
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,91));
}

test("test lexer 92"){
 val input = """int main()
{
    Enter a positive integer: 100
Fibonacci Series: 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 
}""";
val expect = """""";
assert(checkLex(input,expect,92));
}

test("test lexer 93"){
 val input = """int main()
{
    int n, i, flag = 0;

    printf("Enter a positive integer: ");
    scanf("%d",&n);

    for(i=2; i<=n/2; ++i)
    {
        // condition for nonprime number
        if(n%i==0)
        {
            flag=1;
            break;
        }
    }

    if (flag==0)
        printf("%d is a prime number.",n);
    else
        printf("%d is not a prime number.",n);
    
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,93));
}

test("test lexer 94"){
 val input = """int checkPrimeNumber(int n);
int main()
{
    int n1, n2, i, flag;

    printf("Enter two positive integers: ");
    scanf("%d %d", &n1, &n2);
    printf("Prime numbers between %d and %d are: ", n1, n2);

    for(i=n1+1; i<n2; ++i)
    {
        // i is a prime number, flag will be equal to 1
        flag = checkPrimeNumber(i);

        if(flag == 1)
            printf("%d ",i);
    }
    return 0;
}

// user-defined function to check prime number
int checkPrimeNumber(int n)
{
    int j, flag = 1;

    for(j=2; j <= n/2; ++j)
    {
        if (n%j == 0)
        {
            flag =0;
            break;
        }
    }
    return flag;
}""";
val expect = """""";
assert(checkLex(input,expect,94));
}

test("test lexer 95"){
 val input = """  int firstNumber, secondNumber, sumOfTwoNumbers;
    
    printf("Enter two integers: ");

    // Two integers entered by user is stored using scanf() function
    scanf("%d %d", &firstNumber, &secondNumber);

    // sum of two numbers in stored in variable sumOfTwoNumbers
    sumOfTwoNumbers = firstNumber + secondNumber;

    // Displays sum      
    printf("%d + %d = %d", firstNumber, secondNumber, sumOfTwoNumbers);

    return 0;""";
val expect = """""";
assert(checkLex(input,expect,95));
}

test("test lexer 96"){
 val input = """int main()
{
    Enter two integers: 12
11
12 + 11 = 23
}""";
val expect = """""";
assert(checkLex(input,expect,96));
}

test("test lexer 97"){
 val input = """int main()
{
     int n1, n2, minMultiple;
    printf("Enter two positive integers: ");
    scanf("%d %d", &n1, &n2);

    // maximum number between n1 and n2 is stored in minMultiple
    minMultiple = (n1>n2) ? n1 : n2;

    // Always true
    while(1)
    {
        if( minMultiple%n1==0 && minMultiple%n2==0 )
        {
            printf("The LCM of %d and %d is %d.", n1, n2,minMultiple);
            break;
        }
        ++minMultiple;
    }
    return 0;
}""";
val expect = """""";
assert(checkLex(input,expect,97));
}

test("test lexer 98"){
 val input = """ int n1, n2, minMultiple;
    printf("Enter two positive integers: ");
    scanf("%d %d", &n1, &n2);

    // maximum number between n1 and n2 is stored in minMultiple
    minMultiple = (n1>n2) ? n1 : n2;

    // Always true
    while(1)
    {
        if( minMultiple%n1==0 && minMultiple%n2==0 )
        {
            printf("The LCM of %d and %d is %d.", n1, n2,minMultiple);
            break;
        }
        ++minMultiple;
    }
    return 0;""";
val expect = """""";
assert(checkLex(input,expect,98));
}

test("test lexer 99"){
 val input = """ int n1, n2, minMultiple;


    // Always true
    while(1)
    {
        if( minMultiple%n1==0 && minMultiple%n2==0 )
        {
            printf("The LCM of %d and %d is %d.", n1, n2,minMultiple);
            break;
        }
        ++minMultiple;
    }
    return 0;""";
val expect = """""";
assert(checkLex(input,expect,99));
}

test("test lexer 100"){
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
val expect = """""";
assert(checkLex(input,expect,100));
}


}