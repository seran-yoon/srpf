package com.doArtShow.controls.member;

import java.io.PrintWriter;
import java.util.Map;
import org.json.simple.JSONObject;

import com.doArtShow.controls.Controller;
import com.doArtShow.dao.ExhibitionDao;
import com.doArtShow.dao.MemberDao;
import com.doArtShow.dto.ExhibitionDto;
import com.doArtShow.dto.MemberDto;


// 전시회 등록 컨트롤러
public class KakaoMemberController implements Controller{
	MemberDao memberDao;
	
	public KakaoMemberController setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
  		String result= null;
  		int res = 0;
		MemberDto dto = new MemberDto();
		dto = (MemberDto)model.get("member");
		JSONObject jsonObj = (JSONObject)model.get("jsonObj");
		//로그인하면 되는지, 회원가입을 시켜야 하는지, 회원가입 해야하는데(kakaoId가 없는데) 이메일이 중복인지 체크함.
		res = memberDao.kakaoCheckMember( dto.getEmail(), dto.getKakaoId());		
		
		/*ExhibitionDto exhibition = (ExhibitionDto)model.get("exhibition");*/
		/*int rsCnt = memberDao.insertExhibition(exhibition);
		System.out.println("DispatcherServlet_add.do_rsCnt: " + rsCnt);
        jsonObj.put("res",rsCnt);
        result = jsonObj.toJSONString();*/
		
		return "json:"+result;
		
		
		
		
	}
	
	
	
	
	
	
	
	
}
