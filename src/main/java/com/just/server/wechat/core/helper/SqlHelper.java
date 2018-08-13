package com.just.server.wechat.core.helper;

import com.just.server.wechat.core.exception.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
/**
 * @author wanghao
 * @date 16/9/18
 */
@Component
public class SqlHelper {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String ORDER_BY = " order by ";

    public void update(String sql, Object... params) {
        try {
            jdbcTemplate.update(sql, params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException();
        }
    }

    public long queryForCount(String sql, Object... args) {
        List<Long> list = jdbcTemplate.queryForList(sql, Long.class, args);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        Long count = list.get(0);
        return count == null ? 0 : count;
    }

    public double queryForDouble(String sql, Object... args) {
        List<Double> list = jdbcTemplate.queryForList(sql, Double.class, args);
        if (list == null || list.isEmpty()) {
            return 0;
        }
        Double count = list.get(0);
        return count == null ? 0 : count;
    }

    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        return jdbcTemplate.queryForList(sql, args);
    }

    public <T> List<T> query(String sql, Class<T> c, Object... args) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(c), args);
    }

    public <T> List<T> queryForList(String sql, Class<T> c, Object... args) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<T>(c), args);
    }

    public <T> T queryForObject(String sql, Class<T> c, Object... args) {
        return jdbcTemplate.queryForObject(sql, c, args);
    }

    public Map<String, Object> queryForMap(String sql, Object... args) {
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql, args);
        return data == null || data.isEmpty() ? null : data.get(0);
    }

    public Page<Map<String, Object>> queryForPage(String sql, Pageable pageable, Object... args) {
        if (pageable == null) {
            throw new DaoException("分页参数为空");
        }
        String pageSql = sql + " limit " + pageable.getPageSize() + " offset " + pageable.getOffset();
        List<Map<String, Object>> data = queryForList(pageSql, args);
        if (sql.toLowerCase().contains(ORDER_BY)) {
            sql = sql.substring(0, sql.toLowerCase().indexOf(ORDER_BY));
        }
        String countSql = " select count(1) from ( " + sql + ") p";
        long count = queryForCount(countSql, args);
        return new PageImpl(data, pageable, count);
    }

    public <T> Page<T> queryForPage(String sql, Class<T> clazz, Pageable pageable, Object... args) {
        if (pageable == null) {
            throw new DaoException("分页参数为空");
        }
        String pageSql = sql + " limit " + pageable.getPageSize() + " offset " + pageable.getOffset();
        List<T> data = queryForList(pageSql, clazz, args);
        if (sql.toLowerCase().contains(ORDER_BY)) {
            sql = sql.substring(0, sql.toLowerCase().indexOf(ORDER_BY));
        }
        String countSql = " select count(1) from ( " + sql + ") tmp_for_count_table";
        long count = queryForCount(countSql, args);
        return new PageImpl(data, pageable, count);
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
}
