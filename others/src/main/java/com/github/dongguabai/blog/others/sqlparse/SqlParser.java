package com.github.dongguabai.blog.others.sqlparse;

/**
 * @author dongguabai
 * @date 2024-01-24 21:43
 *
 * 风险SQL定义：无 where 或者 limit 条件
 */
public class SqlParser {

    public static void main(String[] args) {

    }

    public boolean isRiskySql(String sql) {
        String lowerCaseSql = sql.toLowerCase();
        boolean hasWhereOrLimit = lowerCaseSql.contains("where") || lowerCaseSql.contains("limit");
        return !hasWhereOrLimit;
    }

    public boolean isRiskySqlOrMyBatis(String sql) {
        String lowerCaseSql = sql.toLowerCase();
        boolean hasWhereOrLimitOrIf = lowerCaseSql.contains("where") || lowerCaseSql.contains("limit") || lowerCaseSql.contains("<if");
        return !hasWhereOrLimitOrIf;
    }
}