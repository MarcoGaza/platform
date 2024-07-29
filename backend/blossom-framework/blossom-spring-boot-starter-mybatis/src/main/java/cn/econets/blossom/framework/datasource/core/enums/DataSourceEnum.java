package cn.econets.blossom.framework.datasource.core.enums;

/**
 * Corresponding to different data source configurations in multiple data sources
 *
 * Through the method，Use {@link com.baomidou.dynamic.datasource.annotation.DS} Annotation，Set the data source to be used。
 * Attention，Default is {@link #MASTER} Data Source
 *
 * The corresponding official document is http://dynamic-datasource.com/guide/customize/Annotation.html
 */
public interface DataSourceEnum {

    /**
     * Main Library，Recommended {@link com.baomidou.dynamic.datasource.annotation.Master} Annotation
     */
    String MASTER = "master";
    /**
     * From library，Recommended {@link com.baomidou.dynamic.datasource.annotation.Slave} Annotation
     */
    String SLAVE = "slave";

}
