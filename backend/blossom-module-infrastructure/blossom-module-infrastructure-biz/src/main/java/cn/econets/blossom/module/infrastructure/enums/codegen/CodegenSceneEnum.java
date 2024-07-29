package cn.econets.blossom.module.infrastructure.enums.codegen;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static cn.hutool.core.util.ArrayUtil.firstMatch;

/**
 * Code-generated scene enumeration
 *
 *
 */
@AllArgsConstructor
@Getter
public enum CodegenSceneEnum {

    ADMIN(1, "Management Backend", "admin", ""),
    APP(2, "User APP", "app", "App");

    /**
     * Scene
     */
    private final Integer scene;
    /**
     * Scene name
     */
    private final String name;
    /**
     * Basic package name
     */
    private final String basePackage;
    /**
     * Controller Japanese VO Class prefix
     */
    private final String prefixClass;

    public static CodegenSceneEnum valueOf(Integer scene) {
        return firstMatch(sceneEnum -> sceneEnum.getScene().equals(scene), values());
    }

}
