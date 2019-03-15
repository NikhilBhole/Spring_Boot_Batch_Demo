package com.yash.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.yash.model.User;

@Component
public class Processor implements ItemProcessor<User, User> {
	
	/*private static final Map<String, String> DEPT_NAMES = new HashMap<>();
	
	public Processor() {
		DEPT_NAMES.put("", value)
	}*/

	@Override
	public User process(User user) throws Exception {
		
		//user.setDepartment(user.getDepartment());
		System.out.println("in process method...");
		return user;
	}

}
