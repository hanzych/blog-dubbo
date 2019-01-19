/*
 * ====================================================================
 * 【个人网站】：http://www.2b2b92b.com
 * 【网站源码】：http://git.oschina.net/zhoubang85/zb
 * 【技术论坛】：http://www.2b2b92b.cn
 * 【开源中国】：https://gitee.com/zhoubang85
 *
 * 【支付-微信_支付宝_银联】技术QQ群：470414533
 * 【联系QQ】：842324724
 * 【联系Email】：842324724@qq.com
 * ====================================================================
 */
package com.yfancy.common.base.dao;

import com.yfancy.common.base.BaseEntity;
import com.yfancy.common.base.enums.SystemCodeMsgEnum;
import com.yfancy.common.base.exception.BizMsgException;
import com.yfancy.common.base.page.PageBean;
import com.yfancy.common.base.page.PageParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据访问层基础支撑类.
 *
 * @author zhoubang
 * @date 2017年10月18日 13:43:03
 *
 */
@Slf4j
public abstract class BaseDaoImpl<T extends BaseEntity> extends SqlSessionDaoSupport implements BaseDao<T> {


    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE_BY_ID = "updateByPrimaryKey";
    public static final String SQL_BATCH_UPDATE_BY_IDS = "batchUpdateByIds";
    public static final String SQL_BATCH_UPDATE_BY_COLUMN = "batchUpdateByColumn";
    public static final String SQL_SELECT_BY_ID = "selectByPrimaryKey";
    public static final String SQL_LIST_BY_COLUMN = "listByColumn";
    public static final String SQL_COUNT_BY_COLUMN = "getCountByColumn";
    public static final String SQL_DELETE_BY_ID = "deleteByPrimaryKey";
    public static final String SQL_BATCH_DELETE_BY_IDS = "batchDeleteByIds";
    public static final String SQL_BATCH_DELETE_BY_COLUMN = "batchDeleteByColumn";
    public static final String SQL_LIST_PAGE = "listPage";
    public static final String SQL_LIST_BY = "listBy";
    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam"; // 根据当前分页参数进行统计
    

    /**
     * 注入SqlSessionTemplate实例(要求Spring中进行SqlSessionTemplate的配置).
     * 可以调用sessionTemplate完成数据库操作.
     */
    @Autowired
    private SqlSessionTemplate sessionTemplate;

    public SqlSessionTemplate getSessionTemplate() {
        return sessionTemplate;
    }

    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = sessionTemplate;
    }

    public SqlSession getSqlSession() {
        return super.getSqlSession();
    }

    /**
     * 单条插入数据.
     */
    public int insert(T entity) {
        int result = sessionTemplate.insert(getStatement(SQL_INSERT), entity);
        if (result <= 0) {
            throw BizMsgException.DB_INSERT_EXCEPTION;
        }
        return result;
    }

    /**
     * 批量插入数据.
     */
    public int insert(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        int result = sessionTemplate.insert(getStatement(SQL_BATCH_INSERT), list);
        if (result <= 0) {
            throw BizMsgException.DB_INSERT_EXCEPTION;
        }
        return result;
    }

    /**
     * 根据id单条更新数据.
     */
    public int update(T entity) {
        entity.setEditTime(new Date());
        int result = sessionTemplate.update(getStatement(SQL_UPDATE_BY_ID), entity);
        if (result <= 0) {
            throw BizMsgException.DB_INSERT_EXCEPTION;
        }
        return result;
    }

    /**
     * 根据id批量更新数据.
     */
    public int update(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        int result = sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_IDS), list);
        if (result <= 0) {
            throw BizMsgException.DB_INSERT_EXCEPTION;
        }
        return result;
    }

    /**
     * 根据column批量更新数据.
     */
    public int update(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        }
        int result = sessionTemplate.update(getStatement(SQL_BATCH_UPDATE_BY_COLUMN), paramMap);
        if (result <= 0) {
            throw BizMsgException.DB_INSERT_EXCEPTION;
        }
        return result;
    }

    /**
     * 根据id查询数据.
     */
    public T getById(String id) {
        return sessionTemplate.selectOne(getStatement(SQL_SELECT_BY_ID), id);
    }

    /**
     * 根据column查询数据.
     */
    public T getByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY_COLUMN), paramMap);
    }

    /**
     * 根据条件查询 getBy: selectOne <br/>
     * 
     * @param paramMap
     * @return
     */
    public T getBy(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_LIST_BY), paramMap);
    }
    
    /**
     * 根据条件查询列表数据.
     */
    public List<T> listBy(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY), paramMap);
    }

    /**
     * 根据column查询列表数据.
     */
    public List<T> listByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectList(getStatement(SQL_LIST_BY_COLUMN), paramMap);
    }

    /**
     * 根据column查询记录数.
     */
    public Long getCountByColumn(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return null;
        }
        return sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_COLUMN), paramMap);
    }

    /**
     * 根据id删除数据.
     */
    public int delete(String id) {
        return (int) sessionTemplate.delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    /**
     * 根据id批量删除数据.
     */
    public int delete(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        } else {
            return (int) sessionTemplate.delete(getStatement(SQL_BATCH_DELETE_BY_IDS), list);
        }
    }

    /**
     * 根据column批量删除数据.
     */
    public int delete(Map<String, Object> paramMap) {
        if (paramMap == null) {
            return 0;
        } else {
            return (int) sessionTemplate.delete(getStatement(SQL_BATCH_DELETE_BY_COLUMN), paramMap);
        }
    }

    /**
     * 分页查询数据 .
     */
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        // 统计总记录数
        Long totalCount = sessionTemplate.selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);

        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(), pageParam.getNumPerPage(), pageParam.getPageNum());
        pageParam.setPageNum(currentPage); // 为当前页重新设值
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getNumPerPage()); // 校验每页记录数
        pageParam.setNumPerPage(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());
        paramMap.put("startRowNum", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("endRowNum", pageParam.getPageNum() * pageParam.getNumPerPage());

        // 获取分页数据集
        List<Object> list = sessionTemplate.selectList(getStatement(SQL_LIST_PAGE), paramMap);

        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = sessionTemplate.selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), totalCount.intValue(), list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), totalCount.intValue(), list);
        }
    }

    /**
     * 函数功能说明 ： 获取Mapper命名空间. 修改者名字： Along 修改日期： 2016-1-8 修改内容：
     * 
     * @参数：@param sqlId
     * @参数：@return
     * @return：String
     * @throws
     */
    public String getStatement(String sqlId) {
        String name = this.getClass().getName();
        // 单线程用StringBuilder，确保速度；多线程用StringBuffer,确保安全
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(sqlId);
        return sb.toString();
    }

}
