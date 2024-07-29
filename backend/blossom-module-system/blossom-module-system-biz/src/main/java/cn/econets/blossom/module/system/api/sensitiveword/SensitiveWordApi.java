package cn.econets.blossom.module.system.api.sensitiveword;

import java.util.List;

/**
 * Sensitive words API Interface
 *
 */
public interface SensitiveWordApi {

    /**
     * Get the array of illegal sensitive words contained in the text
     *
     * @param text Text
     * @param tags Tag array
     * @return Invalid sensitive word array
     */
    List<String> validateText(String text, List<String> tags);

    /**
     * Judge whether the text contains sensitive words
     *
     * @param text Text
     * @param tags Description array
     * @return Whether to include
     */
    boolean isTextValid(String text, List<String> tags);

}
