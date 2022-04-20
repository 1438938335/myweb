package com.wyy.config;


import com.wyy.vo.PageVO;
import com.wyy.vo.PagedResult;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Intercepts(
        {@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
        })
@Component
public class PageInterceptor implements Interceptor
{
    private static final String COUNT_SUFFIX = "Count";

    @Override
    public Object intercept(Invocation invocation) throws Throwable
    {
        // 拦截的方法的参数数组
        Object[] queryArgs = invocation.getArgs();

        Object param = queryArgs[1];

        PageVO pageVO = needPage(param);

        if (pageVO == null)
        {
            return invocation.proceed();
        }

        // 1.先调用Count的方法获取总数
        MappedStatement statement = (MappedStatement) queryArgs[0];
        String sqlId = statement.getId();
        Configuration configuration = statement.getConfiguration();
        // 更新resultMode字段，避免重复拦截
        pageVO.setResultMode(1);
        int count = queryCount(configuration, sqlId + COUNT_SUFFIX, param);
        pageVO.setTotalRows(count);

        List result = new ArrayList<>(0);
        if (count != 0)
        {
            result = queryList(configuration, sqlId, param);
        }
        // 初始化ResultMode
        pageVO.setResultMode(0);
        return Collections.singletonList(new PagedResult<>(pageVO, result));
    }

    private List queryList(Configuration configuration, String sqlId, Object param)
    {
        DefaultSqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(configuration);

        List list = null;
        try (SqlSession sqlSession = sessionFactory.openSession())
        {
            list = sqlSession.selectList(sqlId, param);
        }
        return list;
    }

    private int queryCount(Configuration configuration, String countSqlId, Object param)
    {
        DefaultSqlSessionFactory sessionFactory = new DefaultSqlSessionFactory(configuration);

        int count = 0;
        try (SqlSession sqlSession = sessionFactory.openSession())
        {
            count = sqlSession.selectOne(countSqlId, param);
        }
        return count;
    }

    /**
     * 请求参数中含有PageVO 且 pageVO的resultMode=0 则需要进行分页处理
     *
     * @param param mybatis组装的查询参数Object
     * @return 方法是否需要进行分页处理
     */
    private PageVO needPage(Object param)
    {
        if (param instanceof Map)
        {
            Map paramMap = (Map) param;
            for (Object entry : paramMap.values())
            {
                if (entry instanceof PageVO && ((PageVO) entry).getResultMode() == 0)
                {
                    return (PageVO) entry;
                }
            }
            return null;
        }
        // 单参数情况
        if (param instanceof PageVO && ((PageVO) param).getResultMode() == 0)
        {
            return (PageVO) param;
        }
        return null;
    }
}
