/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Asus
 */
public class MarkdownParser {

    //Parser object for parsing the markdown file
    private final Parser parser;
    //Renderer object for rendering HTML
    private final HtmlRenderer renderer;

    /**
     * Constructor method which configure the parser and renderer before
     * initialize them.
     */
    public MarkdownParser() {
        // Configure Flexmark options
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(
                TablesExtension.create(),
                StrikethroughExtension.create(),
                TaskListExtension.create()
        ));

        // Set HTML renderer options
        options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        this.parser = Parser.builder(options).build();
        this.renderer = HtmlRenderer.builder(options).build();
    }

    /**
     * Method for converting markdown string to HTML
     *
     * @param path - The content of the markdown
     * @return - The HTML String equivalences
     */
    public String convertToHtml(String path) {
        //Read content from file
        String markdown = Util.readFileFromResources(path);
        // Parse and render markdown
        Node document = parser.parse(markdown);
        String html = renderer.render(document);
        // Process LaTeX equations
        html = MathHandler.processLatexEquations(html);
        return html;
    }

    public static void main(String[] args) {
        // Convert markdown to HTML
        MarkdownParser converter = new MarkdownParser();
        String html = converter.convertToHtml("article_example.md");
        System.out.println(html);
    }
}
