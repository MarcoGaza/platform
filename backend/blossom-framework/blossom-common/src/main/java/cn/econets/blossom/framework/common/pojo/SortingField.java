package cn.econets.blossom.framework.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Sort Field DTO
 *
 * Class name added ing The reason is，Avoid and ES SortField Duplicate name。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortingField implements Serializable {

    /**
     * Sequence - Ascending
     */
    public static final String ORDER_ASC = "asc";
    /**
     * Sequence - Descending
     */
    public static final String ORDER_DESC = "desc";

    /**
     * Field
     */
    private String field;
    /**
     * Sequence
     */
    private String order;

}
