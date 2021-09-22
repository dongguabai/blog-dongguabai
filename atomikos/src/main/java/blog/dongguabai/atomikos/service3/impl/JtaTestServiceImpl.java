package blog.dongguabai.atomikos.service3.impl;


import blog.dongguabai.atomikos.entity.TClass;
import blog.dongguabai.atomikos.entity.Teacher;
import blog.dongguabai.atomikos.service.TClassService;
import blog.dongguabai.atomikos.service2.TeacherService;
import blog.dongguabai.atomikos.service3.JtaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

@Service("jtaTestServiceImpl")
public class JtaTestServiceImpl implements JtaTestService{

    @Autowired
    @Qualifier("teacherServiceImpl")
    private TeacherService teacherService;
    @Autowired
    @Qualifier("tclassServiceImpl")
    private TClassService tclassService;

    @Override
    @Transactional(transactionManager = "xatx", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
    public Map<String, Object> test01() {
        LinkedHashMap<String,Object> resultMap=new LinkedHashMap<String,Object>();
        TClass tClass=new TClass();
        tClass.setName("8888");
        tclassService.saveOrUpdateTClass(tClass);

        Teacher teacher=new Teacher();
        teacher.setName("8888");
        teacherService.saveOrUpdateTeacher(teacher);

        System.out.println(1/0);

        resultMap.put("state","success");
        resultMap.put("message","分布式事务同步成功");
        return resultMap;
    }
}
