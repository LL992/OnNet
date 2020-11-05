package com.koreait.onnet.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.koreait.onnet.Const;
import com.koreait.onnet.SecurityUtils;
import com.koreait.onnet.ViewRef;
import com.koreait.onnet.rest.model.RestDMI;
import com.koreait.onnet.rest.model.RestPARAM;

@Controller
@RequestMapping("/rest")
public class RestController {

	@Autowired
	private RestService service;
	
	@RequestMapping("/map")
	public String restMap(Model model) {
		model.addAttribute(Const.TITLE, "지도보기");
		model.addAttribute(Const.VIEW, "rest/restMap");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/ajaxGetList", produces = {"application/json; chareset=UTF-8"})
	@ResponseBody
	public List<RestDMI> ajaxGetList(RestPARAM param){
		System.out.println("sw_lat : " + param.getSw_lat());
		System.out.println("sw_lng : " + param.getSw_lng());
		System.out.println("ne_lat : " + param.getNe_lat());
		System.out.println("ne_lng : " + param.getNe_lng());

		return service.selRestList(param);
		
	}
	
	@RequestMapping("/reg")
	public String restReg(Model model) {
		model.addAttribute("categoryList", service.selCategoryList());
		
		model.addAttribute(Const.TITLE, "가게 등록");
		model.addAttribute(Const.VIEW, "rest/restReg");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping(value="/reg", method=RequestMethod.POST)
	public String restReg(RestPARAM param, HttpSession hs) {
		param.setI_user(SecurityUtils.getLoginUserPk(hs));
		int result = service.insRest(param);
		return "redirect:/";
	}
	
	@RequestMapping("/detail")
	public String detail(RestPARAM param, Model model) {
		RestDMI data = service.selRest(param);
		model.addAttribute("data", data);
		model.addAttribute(Const.TITLE, data.getNm()); //가게명
		model.addAttribute(Const.VIEW, "rest/restDetail");
		return ViewRef.TEMP_MENU_TEMP;
	}
	
	@RequestMapping("/del")
	public String del(RestPARAM param, HttpSession hs) {
		int loginI_user = SecurityUtils.getLoginUserPk(hs);
		param.setI_user(loginI_user);
		int result = 1;
		try {
			service.delRestTran(param);
		} catch(Exception e) {
			result = 0;
		}
		System.out.println("result : " + result);
		return "redirect:/";
	}
	
	public String recMenus(MultipartHttpServletRequest mReq, RedirectAttributes ra) {
		
		int i_rest = service.insRecMenus(mReq);
		
		ra.addAttribute("i_rest", i_rest);
		return "redirect:/rest/detail";
	}
}