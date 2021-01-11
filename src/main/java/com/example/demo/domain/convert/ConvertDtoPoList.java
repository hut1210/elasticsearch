package com.example.demo.domain.convert;

import java.util.List;

/**
 * dto与po转换.
 *
 * @author mafayun
 * @Date 2019-10-21 17:34
 */
public interface ConvertDtoPoList<DTO, PO> extends ConvertDtoPo<DTO, PO> {

    List<DTO> poToDto(List<PO> po);

    List<PO> dtoToPo(List<DTO> dto);

}
