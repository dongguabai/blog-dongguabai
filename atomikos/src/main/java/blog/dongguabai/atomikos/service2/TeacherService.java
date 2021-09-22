package blog.dongguabai.atomikos.service2;

import blog.dongguabai.atomikos.entity.Teacher;
import blog.dongguabai.atomikos.model.QueryTeacherList;
import blog.dongguabai.atomikos.service.IService;
import blog.dongguabai.atomikos.util.Page;

import java.util.List;
import java.util.Map;

public interface TeacherService extends IService<Teacher>{
    public List<Teacher> queryTeacherList(Page<QueryTeacherList> page);

    public Map<String,Object> saveOrUpdateTeacher(Teacher teacher);


}
