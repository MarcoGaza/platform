package cn.econets.blossom.framework.xss.core.clean;

/**
 * Yes html In the text Xss Cleaning up risky data
 */
public interface XssCleaner {

    /**
     * Clean up Xss Risk text
     *
     * @param html Original html
     * @return After cleaning html
     */
    String clean(String html);

}
