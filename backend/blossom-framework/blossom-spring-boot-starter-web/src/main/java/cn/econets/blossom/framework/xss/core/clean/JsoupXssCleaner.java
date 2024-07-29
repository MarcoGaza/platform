package cn.econets.blossom.framework.xss.core.clean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

/**
 * Based on JSONP Realization XSS Filter string
 */
public class JsoupXssCleaner implements XssCleaner {

    private final Safelist safelist;

    /**
     * Used in src When using relative paths for properties，Force conversion to absolute path。 Not processed when empty，The value should be a prefix of the absolute path（Includes the protocol part）
     */
    private final String baseUri;

    /**
     * Constructor without parameters，Default use {@link JsoupXssCleaner#buildSafelist} Method to build a safe list
     */
    public JsoupXssCleaner() {
        this.safelist = buildSafelist();
        this.baseUri = "";
    }

    /**
     * Build a Xss Cleaned Safelist Rules。
     * Based on Safelist#relaxed() Based on:
     * 1. Extended support style Japanese class Properties
     * 2. a Additional support for tags target Properties
     * 3. img Additional support for tags data Protocol，Easy to support base64
     *
     */
    private Safelist buildSafelist() {
        // Use jsoup Default provided
        Safelist relaxedSafelist = Safelist.relaxed();
        // Some styles are used when editing rich text style To implement
        // For example, red font style="color:red;", So you need to add it to all tags style Properties
        // Attention：style Attributes may be injected at risk <img STYLE="background-image:url(javascript:alert('XSS'))">
        relaxedSafelist.addAttributes(":all", "style", "class");
        // Reserved a Tag target Properties
        relaxedSafelist.addAttributes("a", "target");
        // Supportimg forbase64
        relaxedSafelist.addProtocols("img", "src", "data");

        // Keep relative paths, When retaining relative paths，Must provide corresponding baseUri Properties，Otherwise it will still be deleted
        // WHITELIST.preserveRelativeLinks(false);

        // Remove a Tags and img Some protocol restrictions on tags，This will lead to xss Anti-injection failure，As <img src=javascript:alert("xss")>
        // WHITELIST.removeProtocols("a", "href", "ftp", "http", "https", "mailto");
        // Although it can be rewritten WhiteList#isSafeAttribute To handle，But there are hidden dangers，So relative paths are not supported for the time being
        // WHITELIST.removeProtocols("img", "src", "http", "https");
        return relaxedSafelist;
    }

    @Override
    public String clean(String html) {
        return Jsoup.clean(html, baseUri, safelist, new Document.OutputSettings().prettyPrint(false));
    }

}

