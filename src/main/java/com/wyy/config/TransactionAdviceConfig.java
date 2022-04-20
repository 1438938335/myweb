package com.wyy.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;

@Configuration
@Aspect
public class TransactionAdviceConfig
{
    /**
     * 切入点
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.wyy.service.impl.*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 定义事务增强
     */
    private TransactionInterceptor txAdvice()
    {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        // 查询等；只读
        DefaultTransactionAttribute readyOnly = new DefaultTransactionAttribute();
        readyOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        readyOnly.setReadOnly(true);

        source.addTransactionalMethod("get*", readyOnly);
        source.addTransactionalMethod("select*", readyOnly);
        source.addTransactionalMethod("query*", readyOnly);
        source.addTransactionalMethod("load*", readyOnly);
        source.addTransactionalMethod("search*", readyOnly);
        source.addTransactionalMethod("find*", readyOnly);
        source.addTransactionalMethod("list*", readyOnly);
        source.addTransactionalMethod("count*", readyOnly);
        source.addTransactionalMethod("is*", readyOnly);

        // 增删改
        RuleBasedTransactionAttribute required = new RuleBasedTransactionAttribute();
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        required.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        source.addTransactionalMethod("create*", required);
        source.addTransactionalMethod("delete*", required);
        source.addTransactionalMethod("insert*", required);
        source.addTransactionalMethod("batch*", required);

        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 织入事务
     */
    @Bean
    public Advisor txAdviceAdvisor()
    {
        // 切入点
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }
}
