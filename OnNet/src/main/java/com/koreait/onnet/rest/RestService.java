package com.koreait.onnet.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.koreait.onnet.CommonUtils;
import com.koreait.onnet.FileUtils;
import com.koreait.onnet.model.CodeVO;
import com.koreait.onnet.model.CommonMapper;
import com.koreait.onnet.rest.model.RestDMI;
import com.koreait.onnet.rest.model.RestFile;
import com.koreait.onnet.rest.model.RestPARAM;
import com.koreait.onnet.rest.model.RestRecMenuVO;

@Service
public class RestService {

	@Autowired
	private RestMapper mapper;

	@Autowired
	private CommonMapper cMapper;
	
	public List<RestDMI> selRestList(RestPARAM param) {
		return mapper.selRestList(param);
	}
	
	public List<RestRecMenuVO> selRestRecMenu(RestPARAM param) {
		return mapper.selRestRecMenus(param);
	}

	public List<CodeVO> selCategoryList() {
		CodeVO p = new CodeVO();
		p.setI_m(1); //카테고리 코드 = 1
		return cMapper.selCodeList(p);
	}
	
	public int insRest(RestPARAM param) {
		return mapper.insRest(param);
	}
	
	public RestDMI selRest(RestPARAM param) {
		return mapper.selRest(param);
	}
	
	@Transactional
	public void delRestTran(RestPARAM param) {
		mapper.delrestRecMenu(param);
		mapper.delRestMenu(param);
		mapper.delRest(param);
	}
	
	public int delRestRecMenu(RestPARAM param) {
		return mapper.delrestRecMenu(param);
	}
	
	public int delRestMenu(RestPARAM param) {
		return mapper.delRestMenu(param);
	}
	
	public int insRecMenus(MultipartHttpServletRequest mReq) {
		
		int i_rest = Integer.parseInt(mReq.getParameter("i_rest"));
		List<MultipartFile> fileList = mReq.getFiles("menu_pic");
		String[] menuNmArr = mReq.getParameterValues("menu_nm");
		String[] menuPriceArr = mReq.getParameterValues("menu_price");
		
		String path = mReq.getServletContext().getRealPath("/resources/img/rest/" + i_rest + "/rec_menu/");
		
		List<RestRecMenuVO> list = new ArrayList();
		
		for(int i=0; i<menuNmArr.length; i++) {
			RestRecMenuVO vo = new RestRecMenuVO();
			list.add(vo);
			
			String menu_nm = menuNmArr[i];
			int menu_price = CommonUtils.parseStringToInt(menuPriceArr[i]);
			vo.setI_rest(i_rest);
			vo.setMenu_nm(menu_nm);
			vo.setMenu_price(menu_price);
			
			//파일 각 저장
			MultipartFile mf = fileList.get(i);
			
			if(mf.isEmpty()) { continue; } //.파일이 없으면 스킵
			
			String originFileNm = mf.getOriginalFilename();
			String ext = FileUtils.getExt(originFileNm);
			String saveFileNm = UUID.randomUUID() + ext;
			try {
				mf.transferTo(new File(path + saveFileNm));
				vo.setMenu_pic(saveFileNm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for(RestRecMenuVO vo : list) {
			mapper.insRestRecMenu(vo);
		}
		return i_rest;
	}
	
	public int delRecMenu(RestPARAM param, String realPath) {
		List<RestRecMenuVO> list = mapper.selRestRecMenus(param);
		if(list.size() == 1) {
			RestRecMenuVO item = list.get(0);
			
			if(item.getMenu_pic() != null && !item.getMenu_pic().equals("")) {
				File file = new File(realPath + item.getMenu_pic());
				if(file.exists()) {
					if(file.delete()) {
						return mapper.delrestRecMenu(param);
					} else {
						return 0;
					}
				}
			}
		}
		
		return mapper.delrestRecMenu(param);
	}

	public int insRecMenus(RestFile param, int i_user) {
		// TODO Auto-generated method stub
		return 0;
	}
}