package com.example.demo.domain.convert;

/**
 * dto与po转换.
 *
 * @author mafayun
 * @Date 2019-10-21 17:34
 */
public interface ConvertDtoPo<DTO, PO> {

    DTO poToDto(PO po);

    PO dtoToPo(DTO dto);

}
