package com.example.demo.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.demo.base.BaseEntity;
import com.example.demo.domain.convert.ConvertDtoPoList;
import com.example.demo.dto.TenantEntity;
import com.example.demo.exception.BusinessExceptionHelper;
import com.example.demo.exception.LPreconditions;
import com.example.demo.mybatisplus.MybatisPlusPageAdapter;
import com.example.demo.util.ReflectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

/**
 * @author huteng5
 * @version 1.0
 * @date 2022/6/17 11:31
 */
@Slf4j
public class SmartManager<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl {

    /**
     * @Description: 批量查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public List<T> smartQueryList(Object t) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(t);
        return baseMapper.selectList(queryWrapper);
    }

    public <Q extends BaseEntity, DTO extends BaseEntity>  DTO smartQueryOne(Q query, ConvertDtoPoList<DTO, T> convertDtoPoList){
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        return convertDtoPoList.poToDto((T) baseMapper.selectOne(queryWrapper));
    }

    /**
     * @Description: 批量查询, 提供自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <Q extends BaseEntity, DTO extends BaseEntity> List<DTO> smartQueryList(Q query, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        return convertDtoPoList.poToDto(baseMapper.selectList(queryWrapper));
    }

    /**
     * @Description: 批量查询, 提供自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <Q extends BaseEntity, DTO extends BaseEntity> List<DTO> smartQueryList(Q query, ConvertDtoPoList<DTO, T> convertDtoPoList, boolean orderByAsc, String[] orderByColumn, int limit) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        if (orderByAsc) {
            queryWrapper.orderByAsc(Arrays.asList(orderByColumn));
        } else {
            queryWrapper.orderByDesc(Arrays.asList(orderByColumn));
        }
        queryWrapper.last("limit 0," + limit);
        return convertDtoPoList.poToDto(baseMapper.selectList(queryWrapper));
    }

    public <Q extends BaseEntity, DTO extends BaseEntity> List<DTO> smartQueryList(Q query, ConvertDtoPoList<DTO, T> convertDtoPoList, boolean orderByAsc, String... orderByColumn){
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        if (orderByAsc) {
            queryWrapper.orderByAsc(Arrays.asList(orderByColumn));
        } else {
            queryWrapper.orderByDesc(Arrays.asList(orderByColumn));
        }
        return convertDtoPoList.poToDto(baseMapper.selectList(queryWrapper));
    }

    /**
     * @Description: 批量查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public List<T> smartQueryList(T t, boolean orderByAsc, String orderByColumn, int limit) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(t);
        if(orderByAsc){
            queryWrapper.orderByAsc(orderByColumn);
        }else{
            queryWrapper.orderByDesc(orderByColumn);
        }
        queryWrapper.last("limit 0,"+limit);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * @Description: 查询是否存在
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public boolean smartQueryExist(T t) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(t);
        queryWrapper.last("limit 0,1");
        List<T> list = baseMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        return true;
    }
    /**
     * @Description: 查询是否存在
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public boolean smartQueryExist(T t,Long exclude) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(t);
        if(Objects.nonNull(exclude)) {
            queryWrapper.ne("id", exclude);
        }
        queryWrapper.last("limit 0,1");
        List<T> list = baseMapper.selectList(queryWrapper);
        if(CollectionUtils.isEmpty(list)){
            return false;
        }
        return true;
    }


    /**
     * @Description: 批量查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public List<T> smartQueryList(T t, boolean orderByAsc, String orderByColumn) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(t);
        if(orderByAsc){
            queryWrapper.orderByAsc(orderByColumn);
        }else{
            queryWrapper.orderByDesc(orderByColumn);
        }
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * @Description: 分页查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <Q extends BaseEntity> Page<T> smartQueryListPage(Page page, Q query) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        queryWrapper.orderByAsc("id");
        return (Page<T>) baseMapper.selectPage(page, queryWrapper);
    }

    public <Q extends BaseEntity> Page<T> smartQueryListPage(Page page, Q query, boolean orderByAsc, String orderByColumn) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        if(orderByAsc){
            queryWrapper.orderByAsc(orderByColumn);
        }else{
            queryWrapper.orderByDesc(orderByColumn);
        }
        return (Page<T>) baseMapper.selectPage(page, queryWrapper);
    }

    /**
     * @Description: 兼容mybatisPlus分页查询，提供自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <Q extends BaseEntity, DTO extends BaseEntity> com.example.demo.page.Page<DTO> smartQueryListPage(com.example.demo.page.Page  page,
                                                                                                               Q query, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        MybatisPlusPageAdapter ipage = MybatisPlusPageAdapter.newInstance(page);
//      Page<T> ipage = new Page<>(page.getPageIndex(), page.getPageSize());
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        queryWrapper.orderByAsc("id");
        IPage<T> iPage = baseMapper.selectPage(ipage, queryWrapper);
//      page.setTotal((int) iPage.getTotal());
        page.setRows(convertDtoPoList.poToDto(iPage.getRecords()));
        return page;
    }

    /**
     * @Description: 兼容mybatisPlus分页查询，提供自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <Q extends BaseEntity, DTO extends BaseEntity> com.example.demo.page.Page<DTO> smartQueryListPage(com.example.demo.page.Page page,
                                                                                                               Q query, ConvertDtoPoList<DTO, T> convertDtoPoList
            ,String groupByColumn) {
        MybatisPlusPageAdapter ipage = MybatisPlusPageAdapter.newInstance(page);
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        queryWrapper.orderByAsc("id");
        if(StringUtils.isNotBlank(groupByColumn)){
            queryWrapper.groupBy(groupByColumn);
        }
        IPage<T> iPage = baseMapper.selectPage(ipage, queryWrapper);
        page.setRows(convertDtoPoList.poToDto(iPage.getRecords()));
        return page;
    }

    public <Q extends BaseEntity, DTO extends BaseEntity> com.example.demo.page.Page<DTO> smartQueryListPage(com.example.demo.page.Page page, Q query, boolean orderByAsc, String orderByColumn,
                                                                                                               ConvertDtoPoList<DTO, T> convertDtoPoList) {
        MybatisPlusPageAdapter ipage = MybatisPlusPageAdapter.newInstance(page);
//      Page<T> ipage = new Page<>(page.getPageIndex(), page.getPageSize());
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        if(orderByAsc){
            queryWrapper.orderByAsc(orderByColumn);
        }else{
            queryWrapper.orderByDesc(orderByColumn);
        }
        IPage<T> iPage = baseMapper.selectPage(ipage, queryWrapper);
        page.setTotal((int) iPage.getTotal());
        page.setRows(convertDtoPoList.poToDto(iPage.getRecords()));
        return page;
    }

    /**
     * @Description: 兼容mybatisPlus分页查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <Q extends BaseEntity> com.example.demo.page.Page<T> smartQueryListPage(com.example.demo.page.Page page, Q query) {
//      Page<T> ipage = new Page<>(page.getPageIndex(), page.getPageSize());
        MybatisPlusPageAdapter ipage = MybatisPlusPageAdapter.newInstance(page);
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        queryWrapper.orderByAsc("id");
        IPage<T> iPage = baseMapper.selectPage(ipage, queryWrapper);
        page.setTotal((int) iPage.getTotal());
        page.setRows(iPage.getRecords());
        return page;
    }

    public <Q extends BaseEntity> com.example.demo.page.Page<T> smartQueryListPage(com.example.demo.page.Page page, Q query, boolean orderByAsc, String orderByColumn) {
//      Page<T> ipage = new Page<>(page.getPageIndex(), page.getPageSize());
        MybatisPlusPageAdapter ipage = MybatisPlusPageAdapter.newInstance(page);
        QueryWrapper<T> queryWrapper = smartQueryWrapper(query);
        if(orderByAsc){
            queryWrapper.orderByAsc(orderByColumn);
        }else{
            queryWrapper.orderByDesc(orderByColumn);
        }
        IPage<T> iPage = baseMapper.selectPage(ipage, queryWrapper);
        page.setTotal((int) iPage.getTotal());
        page.setRows(iPage.getRecords());
        return page;
    }


    /**
     * @Description: 多条件复合查询(目前只支持String和Integer类型的去重优化查询)
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public List<T> smartQueryList(List<T> list) {
        List<T> resultList = new ArrayList<>();
        LPreconditions.checkParameterNotNull(list, "查询条件集合为空！");
        Map<String, T> map = new HashMap<>();
        for (T t : list) {
            StringBuilder stringBuilder = new StringBuilder();
            List<Field> fields = ReflectionUtil.getObjectAllField(t);
            for (Field field : fields) {
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = field.get(t);
                } catch (IllegalAccessException e) {
                    log.error("字段取值异常！", e);
                }
                Class<?> type = field.getType();

                //若值为空或是空格等，直接返回
                if (value == null || (type == String.class && StringUtils.isBlank((String) value))) {
                    continue;
                }
                BusinessExceptionHelper.checkArgument(type == String.class || type == Integer.class, "目前只支持String和Integer类型的去重优化查询！");
                stringBuilder.append((String) value).append("-");
            }
            map.put(stringBuilder.toString(), t);
        }
        LPreconditions.checkParameterNotNull(map, "查询条件集合为空！");
        for (T value : map.values()) {
            List<T> list1 = smartQueryList(value);
            if(!CollectionUtils.isEmpty(list1)){
                resultList.addAll(list1);
            }
        }
        return resultList;
    }

    /**
     * @Description: 单个查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public T smartQueryOne(T t) {
        QueryWrapper<T> queryWrapper = smartQueryWrapper(t);
        return (T) baseMapper.selectOne(queryWrapper);
    }

    /**
     * @Description: 单个查询,自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <DTO extends BaseEntity> DTO smartQueryById(Long id, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        return convertDtoPoList.poToDto((T) baseMapper.selectById(id));
    }

    /**
     * @Description: 更新
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public boolean smartUpdateById(T t) {
        return SqlHelper.retBool(baseMapper.updateById(t));
    }

    /**
     * @Description: 更新，自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <DTO extends BaseEntity> boolean smartUpdateById(DTO dto, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        T po = convertDtoPoList.dtoToPo(dto);
        return SqlHelper.retBool(baseMapper.updateById(po));
    }

    /**
     * @Description: 更新，自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <DTO extends TenantEntity> boolean smartUpdateById(DTO dto, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        T po = convertDtoPoList.dtoToPo(dto);
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", dto.getId());
        queryWrapper.eq("tenant_no", dto.getTenantNo());
        return SqlHelper.retBool(baseMapper.update(po, queryWrapper));
    }

    /**
     * @Description: 删除
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public boolean smartDeleteByIds(List<Long> idList) {
        idList.sort(Comparator.comparing((Function<? super Long, ? extends Long>) id -> id));
        return SqlHelper.retBool(baseMapper.deleteBatchIds(idList));
    }

    /**
     * @Description: 删除
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public boolean smartDeleteByIds(List<Long> idList, String tenantNo) {
        idList.sort(Comparator.comparing((Function<? super Long, ? extends Long>) id -> id));
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        queryWrapper.eq("tenant_no", tenantNo);
        queryWrapper.in("id", idList);
        return SqlHelper.retBool(baseMapper.delete(queryWrapper));
    }


    /**
     * @Description: 更新，自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <DTO extends BaseEntity> boolean smartSave(DTO dto, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        T po = convertDtoPoList.dtoToPo(dto);
        int result = baseMapper.insert(po);
        dto.setId(po.getId());
        return SqlHelper.retBool(result);
    }

    /**
     * @Description: 更新，自动转换
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public <DTO extends BaseEntity> boolean smartSaveBatch(List<DTO> dtoList, ConvertDtoPoList<DTO, T> convertDtoPoList) {
        List<T> poList = convertDtoPoList.dtoToPo(dtoList);
        return saveBatch(poList);
    }


    /**
     * @Description: 智能查询
     * @Param:
     * @return:
     * @Author: hanpeng
     * @Date: 2020/9/26
     */
    public QueryWrapper<T> smartQueryWrapper(Object t) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>();
        if (t == null) {
            return queryWrapper;
        }
        List<Field> fields = ReflectionUtil.getObjectAllField(t);
        if (CollectionUtils.isEmpty(fields)) {
            return queryWrapper;
        }
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (fieldName.equals("isDelete")
                    || fieldName.equals("ts")
                    || fieldName.equals("version")) {
                continue;
            }
            Object value = null;
            try {
                value = field.get(t);
            } catch (IllegalAccessException e) {
                log.error("字段取值异常！", e);
            }
            Class<?> type = field.getType();

            //若值为空或是空格或空集合等，直接返回
            if (value == null || (field.getType() == String.class && StringUtils.isBlank((String) value))
                    ||(type == List.class && CollectionUtils.isEmpty((Collection<?>) value))) {
                continue;
            }
            if (type == Double.class || type == Long.class || type == Float.class) {
                queryWrapper.eq(ReflectionUtil.propertyToField(fieldName), value);
            } else if(type == Integer.class || type == BigDecimal.class){
                if(fieldName.contains("LessOrEqual")){
                    queryWrapper.le(ReflectionUtil.propertyToField(fieldName.replace("LessOrEqual", "")), value);
                }else if (fieldName.contains("GreatOrEqual")) {
                    queryWrapper.ge(ReflectionUtil.propertyToField(fieldName.replace("GreatOrEqual", "")), value);
                }else if(fieldName.endsWith("NotEq")){
                    queryWrapper.ne(ReflectionUtil.propertyToField(fieldName.replace("NotEq", "")), value);
                }else{
                    queryWrapper.eq(ReflectionUtil.propertyToField(fieldName), value);
                }
            }else if (type == Date.class) {
                if (fieldName.contains("End")) {
                    queryWrapper.le(ReflectionUtil.propertyToField(fieldName.replace("End", "")), value);
                } else if (fieldName.contains("Start")) {
                    queryWrapper.ge(ReflectionUtil.propertyToField(fieldName.replace("Start", "")), value);
                }
            } else if (type == List.class) {
                if(fieldName.endsWith("NotInList")){
                    queryWrapper.notIn(ReflectionUtil.propertyToField(fieldName.replace("NotInList", "")), (Collection<?>) value);
                }else if(fieldName.endsWith("List")){
                    queryWrapper.in(ReflectionUtil.propertyToField(fieldName.replace("List", "")), (Collection<?>) value);
                }
            } else if(type == String.class){
                if(fieldName.endsWith("Fuzzy")){
                    queryWrapper.likeRight(ReflectionUtil.propertyToField(fieldName.replace("Fuzzy", "")), value);
                }else if(fieldName.endsWith("NotEq")){
                    queryWrapper.ne(ReflectionUtil.propertyToField(fieldName.replace("NotEq", "")), value);
                }else{
                    queryWrapper.eq(ReflectionUtil.propertyToField(fieldName), value);
                }
            }
        }
        return queryWrapper;
    }
}
