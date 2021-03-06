package de.is24.deadcode4j.analyzer;

import org.junit.Test;

public final class A_SpringXmlAnalyzer extends AnAnalyzer<SpringXmlAnalyzer> {

    @Override
    protected SpringXmlAnalyzer createAnalyzer() {
        return new SpringXmlAnalyzer();
    }

    @Test
    public void shouldParseSpringFiles() {
        analyzeFile("spring.xml");

        assertThatDependenciesAreReported("SpringXmlBean");
    }

}
