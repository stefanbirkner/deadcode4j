package de.is24.javaparser;

import de.is24.deadcode4j.junit.AUtilityClass;
import de.is24.deadcode4j.junit.FileLoader;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class A_Nodes extends AUtilityClass {

    @Test
    public void prependsNothingForEmptyStringBuilder() {
        StringBuilder buffy = Nodes.prependSeparatorIfNecessary('.', new StringBuilder());

        assertThat(buffy.toString(), is(""));
    }

    @Test
    public void prependsSeparator() {
        StringBuilder buffy = Nodes.prependSeparatorIfNecessary('.', new StringBuilder("foo"));

        assertThat(buffy.toString(), is(".foo"));
    }

    @Test
    public void prependsNameExpression() {
        StringBuilder buffy = Nodes.prepend(new NameExpr("foo"), new StringBuilder("Bar"));

        assertThat(buffy.toString(), is("foo.Bar"));
    }

    @Test
    public void prependsQualifiedNameExpression() {
        QualifiedNameExpr qualifiedNameExpr =
                new QualifiedNameExpr(new QualifiedNameExpr(new NameExpr("de"), "is24"), "foo");
        StringBuilder buffy = Nodes.prepend(qualifiedNameExpr, new StringBuilder("Bar"));

        assertThat(buffy.toString(), is("de.is24.foo.Bar"));
    }

    @Test
    public void calculatesTypeNames() throws IOException, ParseException {
        CompilationUnit compilationUnit = JavaParser.parse(
                FileLoader.getFile("de/is24/javaparser/TypeNameTestClass.java"));

        compilationUnit.accept(new FixedVoidVisitorAdapter<Void>() {
            @Override
            public void visit(StringLiteralExpr n, Void arg) {
                assertThat("Name of anonymous class is invalid!", Nodes.getTypeName(n), is(n.getValue()));
            }
        }, null);
    }

    @Override
    protected Class<?> getType() {
        return Nodes.class;
    }

}
