package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author huteng5
 * @version 1.0
 * @date 2021/2/4 20:21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PieChatDto {
    private String sunTitle;
    private Integer num;
    private String percent;
}
