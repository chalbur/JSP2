package com.chalbur.web.controller.admin.notice;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chalbur.web.entity.Notice;
import com.chalbur.web.entity.NoticeView;
import com.chalbur.web.service.NoticeService;

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet{
	// 404 : 아예 연결할수 있는 url이 없음
	// 405 : 받을수 있는 메소드가 없음
	// 403 : url도있고 메소드도 있지만 권한이 없다(보안오류)
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 같은 키값으로 오는데 값은 다를경우, 문자열로 오는데 키는 같고 값은 다를경우 ->getParameterValues
		String[] openIds = request.getParameterValues("open-id"); //ex 3,5,8 오픈해야할 공지
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		
		String ids_ = request.getParameter("ids"); // 어드민 공지사항 글의 공개여부
		String[] ids = ids_.trim().split(" ");	// ex 1,2,3,4,5,6,7,8,9,10 공지들 = 3, 5, 8만 오픈상태인 1로 설정
		// trim : 좌우의 빈공백을 없앰
		NoticeService service = new NoticeService();
		switch(cmd) {
		case "일괄공개" :
			for(String openId : openIds)
				System.out.printf("open id : %s\n", openId);
			
			List<String> oids = Arrays.asList(openIds); // 배열을 리스트형태로 바꿔줌, openIds를 리스트로 바꿔줌
			// 전체 열에서 공개해야할 id를 뺌 -> 비공개만 남음
			List<String> cids = new ArrayList(Arrays.asList(ids));
			// Arrays.asList(ids)에선 추가 삭제가 불가능해서 데이터를 새로운 객체에 담음
			cids.removeAll(oids);
			System.out.println(Arrays.asList(ids));
			System.out.println(oids);
			System.out.println(cids);
			
				
			// Transaction : 생각하는 기본 단위, 하나의 영역으로 실행행되게 함, 한번에 이루어지기 원하는 업무단위
			service.pubNoticeAll(oids, cids); // ex update NOTICE set pub=1 where id in (...)
			//service.pubNoticeList(clsIds); // 두 함수가 전부 실행되거나 실패해야함, 하나만 성공하면 안도
			
			break;
		case "일괄삭제" :
			
			int[] ids1 = new int[delIds.length];
			for(int i=0;i<delIds.length;i++) {
				ids1[i] = Integer.parseInt(delIds[i]);
			}
			service.deleteNoticeAll(ids1);
			break;
		}
		
		response.sendRedirect("list"); // post에서 처리 후 결과를 보여주는 다른페이지로 이동(요청)함
		// url은 같이 쓰고있지만 post에서 get을 호출, 현재 알고있는 list목록을 재요청, get이 다시 요청을 받음
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list?f=title&q=a 형태로 검색을 하면 전달됨, 검색은 필수가 아니라 옵션
		
		String field_ = request.getParameter("f"); // list.jsp 검색창에서 넘겨주는 값
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		String field = "title"; // 기본값(예외) 처리
		if(field_ != null && !field_.equals(""))
			field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals(""))
			query = query_;
		
		int page = 1;
		if(page_ != null && !page_.equals(""))
			page = Integer.parseInt(page_);
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page); // 검색
		
		int count = service.getNoticeCount(field, query);
		
		
		request.setAttribute("list", list);
		request.setAttribute("count", count); // 뷰에게 전달
		
		// 서블릿에서 서블릿으로 : redirect : 걍 전달만 함?, forward : 현재 작업했던 내용을 다른곳에서 이어받음'
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request, response);
		// notice안에 있는 detail.jsp를 요청하면서 현재 사용하는 저장소(request)와 출력도구(response)를 공유


	
	}

}
