package cn.yog.chess.dto;

import lombok.Data;

/**
 * @author yog
 * @date：Created in 2020/3/29 1:28
 */
@Data
public class ChessBoardDto {
    private Integer totalWith = 450;
    private Integer totalHeight = 450;

    private Integer widthLine = 15;
    private Integer heightLIne = 15;

    private Integer length = 30;



}
