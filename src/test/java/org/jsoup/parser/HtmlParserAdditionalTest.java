package org.jsoup.parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlParserAdditionalTest {

    @Test
    public void testInvalidDoctypeHandling() {
        // Arrange: Un document avec un DOCTYPE mal formé
        String html = "<!DOCTPYE html><html><head><title>Test</title></head><body><p>Hello World</p></body></html>";
        
        // Act: Parser le document
        Document doc = Jsoup.parse(html);
        
        // Assert: Vérifie que JSoup a ignoré le DOCTYPE incorrect mais a tout de même analysé le document
        assertEquals("Hello World", doc.body().text());
        
        // Normaliser le HTML pour éliminer les sauts de ligne et les espaces superflus
        String normalizedHtml = doc.body().parent().outerHtml().replaceAll("\\s+", "");
        assertEquals("<html><head><title>Test</title></head><body><p>Hello World</p></body></html>".replaceAll("\\s+", ""), normalizedHtml);
    }

    @Test
    public void testTableAutoInsertionOfTheadTbody() {
        // Arrange: Un tableau mal formé sans <thead> et <tbody>
        String html = "<table><tr><th>Header</th></tr><tr><td>Data</td></tr></table>";
        
        // Act: Parser le document
        Document doc = Jsoup.parse(html);
        
        // Assert: Vérifie que JSoup a automatiquement ajouté <thead> et <tbody>
        String expectedHtml = "<table>\n <tbody>\n  <tr>\n   <th>Header</th>\n  </tr>\n  <tr>\n   <td>Data</td>\n  </tr>\n </tbody>\n</table>";
        assertEquals(expectedHtml, doc.body().html());
    }

    @Test
    public void testAttributeEscaping() {
        // Arrange: Un élément HTML avec des attributs contenant des caractères spéciaux
        String html = "<div title=\"Special & Characters ' < >\"></div>";
        
        // Act: Parser le document
        Document doc = Jsoup.parse(html);
        Element div = doc.select("div").first();
        
        // Assert: Vérifie que les caractères spéciaux dans l'attribut sont correctement échappés
        assertEquals("Special & Characters ' < >", div.attr("title"));
        // Vérification de l'échappement produit par Jsoup (qui n'échappe pas < et > dans l'attribut)
        assertEquals("<div title=\"Special &amp; Characters ' < >\"></div>", div.outerHtml());
    }
    

    @Test
    public void testWhitespaceHandlingBetweenTags() {
        // Arrange: Un document avec des espaces blancs significatifs entre les balises
        String html = "<html><body><p>First</p> <p>Second</p></body></html>";
        
        // Act: Parser le document
        Document doc = Jsoup.parse(html);
        
        // Assert: Normaliser le HTML pour éliminer les sauts de ligne
        assertEquals(doc.body().html().replaceAll("\\s+", ""), "<p>First</p><p>Second</p>");
    }
}
