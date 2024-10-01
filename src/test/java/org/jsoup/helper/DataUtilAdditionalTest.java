package org.jsoup.helper;

import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.internal.ControllableInputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class DataUtilAdditionalTest {

    // Test pour vérifier la méthode 'parseInputStream' avec un flux vide
    @Test
    public void testParseInputStream_EmptyStream() throws IOException {
        // Arrange
        String emptyHtml = "";

        // Act
        Document doc = DataUtil.parseInputStream(stream(emptyHtml), null, "http://example.com", Parser.htmlParser());

        // Assert
        assertNotNull(doc, "Document should not be null for an empty input stream");
        assertEquals("", doc.body().text(), "Expected empty body text for an empty input");
    }

    // Test pour vérifier la méthode 'parseInputStream' avec un HTML incorrect
    @Test
    public void testParseInputStream_InvalidHtml() throws IOException {
        // Arrange
        String invalidHtml = "<html><head><title>Test</title></head><body><div><u>unterminated</div></body></html>";

        // Act
        Document doc = DataUtil.parseInputStream(stream(invalidHtml), null, "http://example.com", Parser.htmlParser());

        // Assert
        assertEquals("Test", doc.title(), "Expected title to be 'Test'");
        assertTrue(doc.body().text().contains("unterminated"), "Expected body to contain 'unterminated'");
    }

    // Helper method to create a ControllableInputStream from a String
    private ControllableInputStream stream(String data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes());
        return ControllableInputStream.wrap(byteArrayInputStream, 0);
    }
}
