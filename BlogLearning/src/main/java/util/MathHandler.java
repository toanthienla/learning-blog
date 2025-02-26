/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Asus
 */
public class MathHandler {

    /**
     * Helper method for displaying Math equation (both inline and block). This
     * method use KaTex [https://katex.org/]
     *
     * @param html - The HTML String that contains KaTex math formulas
     * @return - The new HTML String after conversion
     */
    public static String processLatexEquations(String html) {
        // Process display math equations ($$...$$)
        Pattern displayPattern = Pattern.compile("\\$\\$(.*?)\\$\\$", Pattern.DOTALL);
        Matcher displayMatcher = displayPattern.matcher(html);
        StringBuffer sb = new StringBuffer();

        while (displayMatcher.find()) {
            String equation = displayMatcher.group(1).trim();
            String replacement = "<div class=\"math-display\" data-katex=\"" + escapeHtml(equation) + "\"></div>";
            displayMatcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        displayMatcher.appendTail(sb);
        html = sb.toString();

        // Process inline math equations ($...$)
        Pattern inlinePattern = Pattern.compile("\\$([^$]+?)\\$");
        Matcher inlineMatcher = inlinePattern.matcher(html);
        sb = new StringBuffer();

        while (inlineMatcher.find()) {
            String equation = inlineMatcher.group(1).trim();
            String replacement = "<span class=\"math-inline\" data-katex=\"" + escapeHtml(equation) + "\"></span>";
            inlineMatcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        inlineMatcher.appendTail(sb);

        return sb.toString();
    }

    /**
     * Helper method for replacing all special character to escaped character in
     * HTML
     *
     * @param text - The String for replacing
     * @return - The result String after replacing
     */
    public static String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
