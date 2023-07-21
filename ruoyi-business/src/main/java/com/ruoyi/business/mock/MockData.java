package com.ruoyi.business.mock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author cube.li
 * @date 2021/3/28 14:53
 * @description 模拟数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockData implements Serializable {

    private static final long serialVersionUID = -5736958265069712442L;

    private String id;

    @NotBlank(message = "内容不可为空")
    private String content;

    /**
     * 记录此条数据生成二维码后被扫描的次数
     */
    private Integer count;

}