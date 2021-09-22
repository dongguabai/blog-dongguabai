package blog.dongguabai.atomikos.service;

import blog.dongguabai.atomikos.entity.TClass;
import blog.dongguabai.atomikos.model.QueryTClassList;
import blog.dongguabai.atomikos.util.Page;

import java.util.List;
import java.util.Map;

public interface TClassService  extends IService<TClass>{
    public List<TClass> queryTClassList(Page<QueryTClassList> page);

    public Map<String,Object> saveOrUpdateTClass(TClass tclass);


}
