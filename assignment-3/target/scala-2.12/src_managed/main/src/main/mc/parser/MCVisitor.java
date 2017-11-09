// Generated from src/main/mc/parser/MC.g4 by ANTLR 4.6

	package mc.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(MCParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#variable_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable_declaration(MCParser.Variable_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#function_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction_declaration(MCParser.Function_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#primary_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_type(MCParser.Primary_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#list_of_variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitList_of_variable(MCParser.List_of_variableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#return_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_type(MCParser.Return_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#block_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock_body(MCParser.Block_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(MCParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#array_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_type(MCParser.Array_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#array_ponter_type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArray_ponter_type(MCParser.Array_ponter_typeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MCParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MCParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(MCParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#do_while_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_while_statement(MCParser.Do_while_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#for_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_statement(MCParser.For_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#return_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_statement(MCParser.Return_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#break_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_statement(MCParser.Break_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#continue_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_statement(MCParser.Continue_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#expression_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression_statement(MCParser.Expression_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(MCParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp1(MCParser.Exp1Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp2(MCParser.Exp2Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp3(MCParser.Exp3Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp4(MCParser.Exp4Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp5(MCParser.Exp5Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp6}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp6(MCParser.Exp6Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp7(MCParser.Exp7Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#exp8}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp8(MCParser.Exp8Context ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(MCParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link MCParser#funcall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncall(MCParser.FuncallContext ctx);
}