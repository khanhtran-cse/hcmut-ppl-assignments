package mc.astgen
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import java.io.{PrintWriter,File}
import org.antlr.v4.runtime.ANTLRFileStream
import mc.utils._
import scala.collection.JavaConverters._
import org.antlr.v4.runtime.tree._
import mc.parser._
import mc.parser.MCParser._

class ASTGeneration extends MCBaseVisitor[Any] {

  override def visitProgram(ctx:ProgramContext)=
    Program(ctx.declaration.asScala.toList.foldLeft(List[Decl]())
            ((a,b) => a:::b.accept(this).asInstanceOf[List[Decl]]))

  	/*
  	* Declaration
  	*
  	*/

  	override def visitDeclaration(ctx:DeclarationContext)=
  		if(ctx.variable_declaration != null)
  			ctx.variable_declaration.accept(this).asInstanceOf[List[VarDecl]];
  		 else
  			ctx.function_declaration.accept(this).asInstanceOf[List[FuncDecl]];

  	override def visitVariable_declaration(ctx:Variable_declarationContext)=
  		ctx.list_of_variable.variable.asScala.toList.map(x=>
  			if(x.INT_LIT != null){
  				VarDecl(Id(x.ID.getText),
  					ArrayType(IntLiteral(x.INT_LIT.getText.toInt),
  						ctx.primary_type.accept(this).asInstanceOf[Type]));
  			} else {
  				VarDecl(Id(x.ID.getText),
  					ctx.primary_type.accept(this).asInstanceOf[Type]);
  			}
  		)

  	override def visitFunction_declaration(ctx:Function_declarationContext)=
      if(ctx.getChildCount <= 5){
        List(FuncDecl(Id(ctx.ID.getText),
          List(),
          ctx.return_type.accept(this).asInstanceOf[Type],
          ctx.block_body.accept(this).asInstanceOf[Stmt]
        ))
      } else {
          List(FuncDecl(Id(ctx.ID.getText),
          ctx.param.asScala.toList.map(x=>{
            x.accept(this).asInstanceOf[VarDecl]
          }),
          ctx.return_type.accept(this).asInstanceOf[Type],
          ctx.block_body.accept(this).asInstanceOf[Stmt]))
      }

  	/*
  	* Function declaration components
  	*
  	*/

  	override def visitList_of_variable(ctx: List_of_variableContext)=
  		{}

  	override def visitVariable(ctx: VariableContext)=
  		{}

  	override def visitParam(ctx: ParamContext)=
  		if(ctx.getChildCount > 2){
  			VarDecl(Id(ctx.ID.getText),
  					ArrayPointerType(ctx.primary_type.accept(this).asInstanceOf[Type]))
  		} else{
  			VarDecl(Id(ctx.ID.getText),
  			ctx.primary_type.accept(this).asInstanceOf[Type])
  		}

  	/*
  	* Statement
  	*
  	*/

  	override def visitBlock_body(ctx: Block_bodyContext)=
        Block(ctx.variable_declaration.asScala.toList.foldLeft(List[VarDecl]())(
          (a,b) => a:::b.accept(this).asInstanceOf[List[VarDecl]]
        ),
        ctx.statement.asScala.toList.map(x=>{
          x.accept(this).asInstanceOf[Stmt];
        })
      )
  		

  	override def visitStatement(ctx: StatementContext)=
  		if(ctx.if_statement != null){
  			ctx.if_statement.accept(this).asInstanceOf[Stmt];
  		} else if(ctx.for_statement != null) {
  			ctx.for_statement.accept(this).asInstanceOf[Stmt];
  		} else if(ctx.do_while_statement != null){
  			ctx.do_while_statement.accept(this).asInstanceOf[Stmt];
  		} else if(ctx.expression_statement != null){
  			ctx.expression_statement.accept(this).asInstanceOf[Stmt];
  		} else if(ctx.block_body != null){
  			ctx.block_body.accept(this).asInstanceOf[Stmt];
  		} else if(ctx.return_statement != null){
  			ctx.return_statement.accept(this).asInstanceOf[Stmt];
  		} else if(ctx.break_statement != null){
  			ctx.break_statement.accept(this).asInstanceOf[Stmt];
  		} else{
  			ctx.continue_statement.accept(this).asInstanceOf[Stmt];
  		}

  	override def visitIf_statement(ctx: If_statementContext)=
  		if(ctx.getChildCount < 7){
  			If(ctx.exp.accept(this).asInstanceOf[Expr],
  				ctx.statement(0).accept(this).asInstanceOf[Stmt],
  				None)
  		} else{
  			If(ctx.exp.accept(this).asInstanceOf[Expr],
  				ctx.statement(0).accept(this).asInstanceOf[Stmt],
  				Some(ctx.statement(1).accept(this).asInstanceOf[Stmt]))
  		}

  	override def visitFor_statement(ctx: For_statementContext)=
  		For(ctx.exp(0).accept(this).asInstanceOf[Expr],
  			ctx.exp(1).accept(this).asInstanceOf[Expr],
  			ctx.exp(2).accept(this).asInstanceOf[Expr],
  			ctx.statement.accept(this).asInstanceOf[Stmt])

  	override def visitDo_while_statement(ctx: Do_while_statementContext)=
  		Dowhile(ctx.statement.asScala.toList.map(x=>{
  						x.accept(this).asInstanceOf[Stmt]
  					}),
  				ctx.exp.accept(this).asInstanceOf[Expr]
  				)

  	override def visitExpression_statement(ctx :Expression_statementContext)=
  			ctx.exp.accept(this).asInstanceOf[Stmt]

  	override def visitReturn_statement(ctx :Return_statementContext)=
  		if(ctx.exp != null){
  			Return(Some(ctx.exp.accept(this).asInstanceOf[Expr]));
  		} else{
  			Return(None);
  		}

  	override def visitBreak_statement(ctx: Break_statementContext)=
  		Break;

  	override def visitContinue_statement(ctx: Continue_statementContext)=
  		Continue;

  	/*
  	* Expression
  	*
  	*/

  	/*
  	* Assign Exp
  	*/
  	override def visitExp(ctx: ExpContext)=
  		if(ctx.getChildCount < 2){
  			ctx.exp1.accept(this).asInstanceOf[Expr];
  		} else{
  			BinaryOp(ctx.ASSIGN.getText,
  				ctx.exp1.accept(this).asInstanceOf[Expr],
  				ctx.exp.accept(this).asInstanceOf[Expr]
  			)
  		}

  	/*
  	*	OR Exp
  	*/
  	override def visitExp1(ctx: Exp1Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp2.accept(this).asInstanceOf[Expr];
  		} else{
  			BinaryOp(ctx.OR.getText,
  				ctx.exp1.accept(this).asInstanceOf[Expr],
  				ctx.exp2.accept(this).asInstanceOf[Expr]
  				)
  		}

  	/*
  	*	AND Exp
  	*/
  	override def visitExp2(ctx: Exp2Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp3.accept(this).asInstanceOf[Expr];
  		} else{
  			BinaryOp(ctx.AND.getText,
  				ctx.exp2.accept(this).asInstanceOf[Expr],
  				ctx.exp3.accept(this).asInstanceOf[Expr]
  				)
  		}

  	/*
  	*	EQUAL and NOT EQUAL Exp
  	*/
  	override def visitExp3(ctx: Exp3Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp4(0).accept(this).asInstanceOf[Expr];
  		} else{
  			BinaryOp(if(ctx.EQUAL != null) ctx.EQUAL.getText
  					 else ctx.NOT_EQUAL.getText,
  				ctx.exp4(0).accept(this).asInstanceOf[Expr],
  				ctx.exp4(1).accept(this).asInstanceOf[Expr]
  				)
  		}

  	/*
  	*	LESS,LESS_OR_EQUAL,GREATER AND GREATER_OR_EQUAL Exp
  	*/
  	override def visitExp4(ctx: Exp4Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp5(0).accept(this).asInstanceOf[Expr];
  		} else{
	  			BinaryOp(ctx.getChild(1).getText,
	  				ctx.exp5(0).accept(this).asInstanceOf[Expr],
	  				ctx.exp5(1).accept(this).asInstanceOf[Expr]
	  			)
  		}
  	
  	/*
  	*	ADDITION AND SUBTRACTION Exp
  	*/
  	override def visitExp5(ctx: Exp5Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp6.accept(this).asInstanceOf[Expr];
  		} else{
  			BinaryOp(ctx.getChild(1).getText,
  				ctx.exp5.accept(this).asInstanceOf[Expr],
  				ctx.exp6.accept(this).asInstanceOf[Expr])
  		}

  	/*
  	*	MUL, DIV and MOD Exp
  	*/
  	override def visitExp6(ctx: Exp6Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp7.accept(this).asInstanceOf[Expr];
  		} else{
  			BinaryOp(ctx.getChild(1).getText,
  				ctx.exp6.accept(this).asInstanceOf[Expr],
  				ctx.exp7.accept(this).asInstanceOf[Expr])
  		}

  	/*
  	*	NEGATION and NOT Exp
  	*/
  	override def visitExp7(ctx: Exp7Context)=
  		if(ctx.getChildCount < 2){
  			ctx.exp8.accept(this).asInstanceOf[Expr]
  		} else{
  			UnaryOp(ctx.getChild(0).getText
  				, ctx.exp7.accept(this).asInstanceOf[Expr])
  		}

  	/*
  	*	Other
  	*/
  	override def visitExp8(ctx: Exp8Context)=
  		if(ctx.getChildCount == 1){
  			if(ctx.ID != null){
  				Id(ctx.ID.getText);
  			} else{
				ctx.getChild(0).accept(this).asInstanceOf[Expr];
  			}
  		} else if( ctx.getChildCount == 3){
  			ctx.exp.accept(this).asInstanceOf[Expr];
  		} else{
  			ArrayCell(ctx.exp8.accept(this).asInstanceOf[Expr],
  				ctx.exp.accept(this).asInstanceOf[Expr])
  		}

  
  	/*
  	* Literal and function call
  	*
  	*/

  	override def visitLiteral(ctx: LiteralContext)=
  		if(ctx.INT_LIT != null){
  			IntLiteral(ctx.INT_LIT.getText.toInt);
  		} else if(ctx.FLOAT_LIT != null){
  			FloatLiteral(ctx.FLOAT_LIT.getText.toFloat);
  		} else if(ctx.BOOLEAN_LIT != null){
  			BooleanLiteral(ctx.BOOLEAN_LIT.getText.toBoolean);
  		} else{
  			StringLiteral(ctx.STRING_LIT.getText);
  		}

  	override def visitFuncall(ctx: FuncallContext) =
  		CallExpr(Id(ctx.ID.getText),
  			ctx.exp.asScala.toList.map(x=>{
  				x.accept(this).asInstanceOf[Expr];
  			})
  		)

  	/*
  	* Type
  	*
  	*/

  	override def visitPrimary_type(ctx: Primary_typeContext)=
  		if(ctx.BOOLEAN_TYPE != null){
  			BoolType;
  		} else if(ctx.FLOAT_TYPE != null){
  			FloatType;
  		} else if(ctx.INT_TYPE != null){
  			IntType;
  		} else{
  			StringType;
  		}

  	override def visitReturn_type(ctx: Return_typeContext)=
  		if(ctx.primary_type != null){
  			ctx.primary_type.accept(this).asInstanceOf[Type];
  		} else if(ctx.VOID_TYPE != null){
  			VoidType
  		} else if(ctx.array_type != null){
  			ctx.array_type.accept(this).asInstanceOf[Type];
  		} else{
  			ctx.array_ponter_type.accept(this).asInstanceOf[Type];
  		}

  	override def visitArray_type(ctx: Array_typeContext)=
  		ArrayType(IntLiteral(ctx.INT_LIT.getText.toInt),
  			ctx.primary_type.accept(this).asInstanceOf[Type]);

  	override def visitArray_ponter_type(ctx: Array_ponter_typeContext)=
  		ArrayPointerType(ctx.primary_type.accept(this).asInstanceOf[Type]);

}
