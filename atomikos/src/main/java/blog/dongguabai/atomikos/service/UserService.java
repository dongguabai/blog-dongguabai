package blog.dongguabai.atomikos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import blog.dongguabai.atomikos.entity.business.UserInformations;
import blog.dongguabai.atomikos.entity.system.Users;
import blog.dongguabai.atomikos.mapper.business.UserInformationsMapper;
import blog.dongguabai.atomikos.mapper.system.UsersMapper;

@Service
public class UserService {

	@Autowired private UsersMapper usersMapper;
	
	@Autowired private UserInformationsMapper userInformationsMapper;
	
	@Transactional
	public void testJTA() {
		Users u = new Users();
		u.setUsername("hmj");
		u.setPassword("hmjbest");
		usersMapper.insertSelective(u);
		
		UserInformations ui = new UserInformations();
		ui.setUserid(666l);
		ui.setEmail("dsb");
		userInformationsMapper.insertSelective(ui);
		
//		int i = 10/0;
	}
	
}
