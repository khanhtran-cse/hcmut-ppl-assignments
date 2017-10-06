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

  override def visitProgram(ctx:ProgramContext) = 
  		Program(ctx.declaration.asScala.toList.map(x=>x.accept(this).asInstanceOf[Decl]))

  	/*
  	* Declaration
  	*
  	*/

  	override def visitDeclaration(ctx:DeclarationContext) = 
  		if(ctx.variable_declaration != null)
  			ctx.variable_declaration.accept(this).asInstanceOf[List[VarDecl]];
  		 else
  			ctx.function_declaration.accept(this).asInstanceOf[List[FuncDecl]];
  		


  	override def visitVariable_declaration(ctx:Variable_declarationContext){
  		var list_var = ctx.list_of_variable().variable;
  		list_var.asScala.toList.map(x=>
  			if(x.INT_LIT != null){
  				VarDecl(Id(x.ID.getText),
  					ArrayType(IntLiteral(x.INT_LIT.getText.toInt),
  						ctx.primary_type.accept(this).asInstanceOf[Type]));
  			} else {
  				VarDecl(Id(x.ID.getText),
  					ctx.primary_type.accept(this).asInstanceOf[Type]);
  			}
  		)		
  	}

  	override def visitFunction_declaration(ctx:Function_declarationContext){
  		FuncDecl(Id(ctx.ID.getText),
  			ctx.nullable_list_of_param.accept(this).asInstanceOf[List[VarDecl]],
  			ctx.return_type.accept(this).asInstanceOf[Type],
  			ctx.block_body.accept(this).asInstanceOf[Stmt]
  		)
  	}

  	/*
  	* Function declaration components
  	*
  	*/


  	/*
  	* Statement
  	*
  	*/

  	override def visitBlock_body(ctx: Block_bodyContext){
  		Block(ctx.variable_declaration.asScala.toList.map(x=>{
  			x.accept(this).asInstanceOf[VarDecl];
  		}),
  			ctx.statement.asScala.toList.map(x=>{
  				x.accept(this).asInstanceOf[Stmt];
  			})
  		)
  	}

  	override def visitStatement(ctx: StatementContext){
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
  	}

  	override def visitIf_statement(ctx: If_statementContext){

  	}

  	override def visitFor_statement(ctx: For_statementContext){

  	}

  	override def visitDo_while_statement(ctx: Do_while_statementContext){

  	}

  	override def visitExpression_statement(ctx :Expression_statementContext){

  	}


  	override def visitReturn_statement(ctx :Return_statementContext){

  	}

  	override def visitBreak_statement(ctx: Break_statementContext){
  		Break;
  	}

  	override def visitContinue_statement(ctx: Continue_statementContext){
  		Continue;
  	}

  	/*
  	* Expression
  	*
  	*/

  	/*
  	* Assign Exp
  	*/
  	override def visitExp(ctx: ExpContext){

  	}

  	/*
  	*	OR Exp
  	*/
  	override def visitExp1(ctx: Exp1Context){

  	}

  	/*
  	*	AND Exp
  	*/
  	override def visitExp2(ctx: Exp2Context){

  	}

  	/*
  	*	EQUAL and NOT EQUAL Exp
  	*/
  	override def visitExp3(ctx: Exp3Context){

  	}

  	/*
  	*	LESS,LESS_OR_EQUAL,GREATER AND GREATER_OR_EQUAL Exp
  	*/
  	override def visitExp4(ctx: Exp4Context){

  	}

  	/*
  	*	ADDITION AND SUBTRACTION Exp
  	*/
  	override def visitExp5(ctx: Exp5Context){

  	}

  	/*
  	*	MUL, DIV and MOD Exp
  	*/
  	override def visitExp6(ctx: Exp6Context){

  	}

  	/*
  	*	NEGATION and NOT Exp
  	*/
  	override def visitExp7(ctx: Exp7Context){

  	}

  	/*
  	*	Other
  	*/
  	override def visitExp8(ctx: Exp8Context){

  	}

  
  	/*
  	* Literal and function call
  	*
  	*/

  	override def visitLiteral(ctx: LiteralContext){
  		if(ctx.INT_LIT != null){
  			IntLiteral(ctx.INT_LIT.getText.toInt);
  		} else if(ctx.FLOAT_LIT != null){
  			FloatLiteral(ctx.FLOAT_LIT.getText.toFloat);
  		} else if(ctx.BOOLEAN_LIT != null){
  			BooleanLiteral(ctx.BOOLEAN_LIT.getText.toBoolean);
  		} else{
  			StringLiteral(ctx.STRING_LIT.getText);
  		}
  	}

  	override def visitFuncall(ctx: FuncallContext){
  		CallExpr(Id(ctx.ID.getText),
  			ctx.exp.asScala.toList.map(x=>{
  				x.accept(this).asInstanceOf[Expr];
  			})
  		)
  	}

  	/*
  	* Type
  	*
  	*/

  	override def visitPrimary_type(ctx: Primary_typeContext){
  		if(ctx.BOOLEAN_TYPE != null){
  			BoolType;
  		} else if(ctx.FLOAT_TYPE != null){
  			FloatType;
  		} else if(ctx.INT_TYPE != null){
  			IntType;
  		} else if(ctx.STRING_TYPE != null){
  			StringType;
  		}
  	}

  	override def visitReturn_type(ctx: Return_typeContext){
  		if(ctx.primary_type != null){
  			ctx.primary_type.accept(this).asInstanceOf[Type];
  		} else if(ctx.VOID_TYPE != null){
  			VoidType;
  		} else if(ctx.array_type != null){
  			ctx.array_type.accept(this).asInstanceOf[Type];
  		} else if(ctx.array_ponter_type != null){
  			ctx.array_ponter_type.accept(this).asInstanceOf[Type];
  		}
  	}

  	override def visitArray_type(ctx: Array_typeContext){
  		ArrayType(IntLiteral(ctx.INT_LIT.getText.toInt),
  			ctx.primary_type.accept(this).asInstanceOf[Type]);
  	}

  	override def visitArray_ponter_type(ctx: Array_ponter_typeContext){
  		ArrayPointerType(ctx.primary_type.accept(this).asInstanceOf[Type]);
  	}

}
