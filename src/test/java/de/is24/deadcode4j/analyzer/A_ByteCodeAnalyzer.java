package de.is24.deadcode4j.analyzer;

import de.is24.deadcode4j.CodeContext;
import javassist.CtClass;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public final class A_ByteCodeAnalyzer extends AnAnalyzer<ByteCodeAnalyzer> {

    @Override
    protected ByteCodeAnalyzer createAnalyzer() {
        return new ByteCodeAnalyzer() {
            @Override
            protected void analyzeClass(@Nonnull CodeContext codeContext, @Nonnull CtClass clazz) {
                codeContext.addAnalyzedClass(clazz.getName());
            }
        };
    }

    @Test
    public void analyzesAClassFile() {
        objectUnderTest.doAnalysis(codeContext, getFile("SingleClass.class"));

        assertThatClassesAreReported("SingleClass");
        assertThatNoDependenciesAreReported();
    }

    @Test
    public void doesNotAnalyzeNonClassFile() {
        objectUnderTest.doAnalysis(codeContext, getFile("spring.xml"));

        assertThat("Should analyze no class", codeContext.getAnalyzedCode().getAnalyzedClasses(), hasSize(0));
        assertThatNoDependenciesAreReported();
    }
}
