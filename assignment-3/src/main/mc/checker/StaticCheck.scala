package mc.checker

/**
 * ID: 1511524
 * Name: Tran Quoc Khanh
 * @author nhphung
 */

import mc.parser._
import mc.utils._
import java.io.{File, PrintWriter}

//import mc.codegen.Val
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree._

import scala.collection.JavaConverters._

case class Symbol(name:String,typ:Type) extends Decl
case class FunctionType(intput: List[Type], output: Type) extends Type

class StaticChecker(ast:AST) extends BaseVisitor with Utils {


    def check() = {
    	var global = new Global();
    	var env = global.visit(ast,List());

    	var local = new Local();
    	local.visit(ast,env);

    }
 
}


/**
 * Check global
 *
 */
class Global extends BaseVisitor with Utils {
	 
	override def visitProgram(ast: Program, c: Any) = {
		val builtInFunc = List[Symbol](Symbol("getInt",FunctionType(List(),IntType)),
	                   Symbol("putInt",      FunctionType(List(IntType),    VoidType)),
	                   Symbol("putIntLn",    FunctionType(List(IntType),    VoidType)),
	                   Symbol("getFloat",    FunctionType(List(),           FloatType)),
	                   Symbol("putFloat",    FunctionType(List(FloatType),  VoidType)),
	                   Symbol("putFloatLn",  FunctionType(List(FloatType),  VoidType)),
	                   Symbol("putBool",     FunctionType(List(BoolType),   VoidType)),
	                   Symbol("putBoolLn",   FunctionType(List(BoolType),   VoidType)),
	                   Symbol("putString",   FunctionType(List(StringType), VoidType)),
	                   Symbol("putStringLn", FunctionType(List(StringType), VoidType)),
	                   Symbol("putLn",       FunctionType(List(),           VoidType)));

		ast.decl.foldLeft(builtInFunc)((a,b)=>{
			if(lookup(getNameOfDecl(b),a,getNameOfSymbol) != None)
				 throw Redeclared(getExceptionType(b),getNameOfDecl(b))
			else
				Symbol(getNameOfDecl(b),getType(b))::a
		});
	}

}

class Local extends BaseVisitor with Utils{
	var returnType:Type =null;

	override def visitProgram(ast:Program, env:Any)={
		ast.decl.filter(_.isInstanceOf[FuncDecl]).map(x=>{
			x.accept(this,env);
		});
	}

	override def visitFuncDecl(ast:FuncDecl, env:Any)={
		returnType = ast.returnType;

		var param = ast.param.foldLeft(List[Symbol]())((a,b)=>{
			if(lookup(getNameOfDecl(b),a,getNameOfSymbol) != None)
				throw new Redeclared(Parameter,getNameOfDecl(b));
			else
				Symbol(getNameOfDecl(b),getType(b))::a;
		});

		var blockEnv = param.asInstanceOf[List[Symbol]]:::env.asInstanceOf[List[Symbol]];
		ast.body.asInstanceOf[Block].accept(this,blockEnv);

	}

	override def visitBlock(ast:Block, env:Any) ={
		val local_var = ast.decl.foldLeft(List[Symbol]())((x,y) => 
    		if(lookup(getNameOfDecl(y),x,getNameOfSymbol) == None)
    			{println("Add " + getNameOfDecl(y));
       			Symbol(getNameOfDecl(y),getType(y))::x}
    		else    { 	
    			println("Error");		
    			throw new Redeclared(getExceptionType(y),getNameOfDecl(y))
    		}
    	)

    	ast.stmt.map(_.accept(this,local_var:::env.asInstanceOf[List[Symbol]]))
	}

	override def visitIf (ast: If , env: Any) = {
		if (ast.expr.accept(this,env).asInstanceOf[Type] != BoolType) throw TypeMismatchInStatement(ast)
			ast.thenStmt.accept(this,env)
		if (ast.elseStmt != None)
			 ast.elseStmt.get.accept(this,env)
	}

	override def visitFor (ast: For, env: Any) = {
		if(ast.expr2.accept(this,env).asInstanceOf[Type] != BoolType ||
		 ast.expr1.accept(this,env).asInstanceOf[Type] != IntType || 
		 ast.expr3.accept(this,env).asInstanceOf[Type] != IntType)
			throw TypeMismatchInStatement (ast)
		ast.loop.accept(this,env)
	}

	override def visitDowhile (ast:Dowhile, env:Any) = {
		if(ast.exp.accept(this,env).asInstanceOf[Type] != BoolType)
			throw TypeMismatchInStatement (ast)
		ast.sl.map(x=> x.accept(this,env))
	}

	override def visitReturn (ast: Return, env: Any) = {
		ast.expr match {
			case Some(a) => if(!checkType(returnType,a.accept(this,env).asInstanceOf[Type])) 
				 throw TypeMismatchInStatement(ast) 
			case None => if (returnType != VoidType) 
				throw TypeMismatchInStatement (ast)
		}
	}

	override def visitBinaryOp (ast: BinaryOp, env: Any) ={
			val left_type = ast.left.accept(this,env).asInstanceOf[Type]
			val right_type = ast.right.accept(this,env).asInstanceOf[Type]
			ast.op match {
				case "=" => if (left_type.isInstanceOf[ArrayPointerType]) throw TypeMismatchInExpression (ast)
							else if (!checkType(left_type,right_type)) throw TypeMismatchInExpression (ast)
							else	left_type
				case "=="|"!=" => if (!(left_type == right_type && (left_type == IntType || left_type == BoolType))) throw TypeMismatchInExpression (ast)
									else BoolType
				case "<"|">"|"<="|">=" => if(!((left_type == IntType || left_type == FloatType)&& (right_type == IntType || right_type == FloatType))) throw TypeMismatchInExpression (ast)
											else BoolType
				case "&&"|"||" => if(left_type != BoolType || right_type != BoolType)	throw TypeMismatchInExpression (ast)
									else BoolType
				case "/"|"*"|"-"|"+" => if (!((left_type == IntType || left_type ==FloatType) && (right_type == FloatType || right_type == IntType))) throw TypeMismatchInExpression (ast)
											else {
												if (left_type == IntType && right_type == IntType) IntType
												else
													FloatType
											}
				case "%" => if (left_type != IntType || right_type != IntType) throw TypeMismatchInExpression (ast)
							else 
								IntType
			}
		}

		override def visitUnaryOp (ast: UnaryOp, env: Any) = {
			val body_type = ast.body.accept(this,env).asInstanceOf[Type]
			if(ast.op == "-" && (body_type == IntType || body_type == FloatType)) body_type
			else if (ast.op == "!" && body_type == BoolType) BoolType
			else
				throw TypeMismatchInExpression (ast)
		}

		override def visitCallExpr(ast: CallExpr, env: Any) = {
			val name_func = ast.method.name
			lookup(name_func,env.asInstanceOf[List[Symbol]],getNameOfSymbol) match {
				case Some(a:Symbol) =>  
					if (!a.typ.isInstanceOf[FunctionType]) 
						throw Undeclared(Function,name_func)
										
					else if(!checkType(a.typ,
						FunctionType(ast.params.map(x=>
							x.accept(this,env).asInstanceOf[Type]), null)))
						throw TypeMismatchInExpression (ast)
					else 
						a.typ.asInstanceOf[FunctionType].output
				case None =>  throw Undeclared(Function,name_func)
			}	
		}

		override def visitId  (ast: Id, env: Any) = {
			lookup(ast.name, env.asInstanceOf[List[Symbol]],getNameOfSymbol) match {
				case Some(a:Symbol) => if (a.typ.isInstanceOf[FunctionType])
					throw Undeclared(Identifier,ast.name) else a.typ
				case None =>  throw Undeclared(Identifier,ast.name)
			}
		}

		override def visitArrayCell  (ast: ArrayCell, env: Any) = {
			if (ast.idx.accept(this,env).asInstanceOf[Type] != IntType)
			 throw TypeMismatchInExpression (ast)
			else {
				ast.arr.accept(this,env).asInstanceOf[Type] match {
				case ArrayType(a,b) => b
				case ArrayPointerType (a) => a
				case _ => throw TypeMismatchInExpression (ast)
			}
		}
		}

		override def visitIntLiteral   (ast: IntLiteral, env: Any)  = IntType

		override def visitFloatLiteral  (ast: FloatLiteral, env: Any) = FloatType

		override def visitStringLiteral (ast: StringLiteral, env: Any) = StringType

		override  def visitBooleanLiteral(ast: BooleanLiteral, env: Any) = BoolType
}

/**
 * Utils
 *
 */
trait Utils {
	def lookup[T](name:String,lst:List[T],func:T=>String):Option[T] = lst match {
    	case List() => None
    	case head::tail => if (name == func(head)) Some(head) else lookup(name,tail,func)
  	}
	
	def getType(a:Decl)= a match {
		case VarDecl(x,y) => y
		case FuncDecl(name,para,rtype,body) => FunctionType(para.map(_.varType),rtype)
	}

	// def checkMain(a:Decl) = a match {
	// 	case FuncDecl(name,para,rtype,body) => if(name.equals("main") 
	// 			para.isEmpty 
	// 		else true; 
	// }

	def getNameOfSymbol(a:Symbol) = a.name;

	def getNameOfDecl(a:Decl)= a match {
		case v:VarDecl => v.variable.name;
		case f:FuncDecl => f.name.name;
	}

	def getExceptionType(a:Decl):Kind = a match {
		case VarDecl(x,y) => Variable;
		case  FuncDecl(n,p,r,b) => Function;
	}
	def checkType (lhs:Type,rhs:Type): Boolean = {
		lhs match {
			case VoidType =>  false	
			case ArrayType(a,b) => false
			case ArrayPointerType(c) => rhs match {
				case ArrayType(x,y) => c==y 
				case ArrayPointerType(m) => c==m
				case _ => false 
			}
			case FloatType => rhs == IntType || rhs == FloatType
			case FunctionType(p,_) => rhs match {
				case FunctionType(e,_) => p.size==e.size && p.zip(e).forall(z=>checkType(z._1,z._2))
				case _ => false 
			} 
			case _ => lhs== rhs
		}
	}
	
}