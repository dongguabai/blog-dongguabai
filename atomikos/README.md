# Spring Boot + Atomikos 分布式事务 Demo

## 说明

* systemDB->spvi->systemDataSource->sqlSessionFactory->mapper->TMapper->TService
* businessDB->Demo1->businessDataSource->sqlSessionFactory2->mapper2->T2Mapper->T2Service
* 验证分布式事务：http://localhost:8080/test/jtaTest/test01

## References
* https://www.cnblogs.com/zhaojiatao/p/8407276.html
