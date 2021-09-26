package blog.dongguabai.springbootmultidatasource.codetransaction.service;

import blog.dongguabai.springbootmultidatasource.mapper.mapper1.Demo1T1Mapper;
import blog.dongguabai.springbootmultidatasource.mapper.mapper2.Spv1T1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 03:19
 */
@Service
public class CodeTransactionService {

    @Autowired
    private Demo1T1Mapper demo1T1Mapper;

    @Autowired
    private Spv1T1Mapper spv1T1Mapper;

    /**
     * 事务操作
     * @param i
     * @return
     */
    @Transactional
    public Object test1(String i) {
        spv1T1Mapper.insertId2(Integer.valueOf(i));     //本地事务1
        spv1T1Mapper.insertId2(Integer.valueOf(i) + 1);   //本地事务2
        spv1T1Mapper.insertId2(Integer.valueOf(i) + 2);   //本地事务3
        rpc();//模拟 rpc
        return "OK";
    }

    public Object searchSpviAll() {
        return spv1T1Mapper.searchAll();
    }

    public void rpc() {
        System.out.println(new Date().toLocaleString() + "->rpc start...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date().toLocaleString() + "->rpc end...");
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    public Object test1_1(String i) {
        transactionTemplate.execute(new TransactionCallback<Object>() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                return null;
            }
        });
        int i1 = 1/0;
        rpc();//模拟 rpc
        return "OK";
    }
}
