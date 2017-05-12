package com.jst.common.springmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jst.common.system.annotation.Privilege;

import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/main")
public class MainAction extends BaseAction{

	
	@ResponseBody
	@RequestMapping("prvg")
	public String getPrvg(String type,HttpSession session){
		Object prvg =  session.getAttribute("userPrvg");
		if(null!= prvg){
			Map<String, String> prvgMap = (Map<String, String>) prvg;
			return prvgMap.get(type);
		}
		return null;
	}
	
}
