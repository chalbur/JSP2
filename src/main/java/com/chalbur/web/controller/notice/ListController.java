package com.chalbur.web.controller.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

@WebServlet("/notice/list")
public class ListController extends HttpServlet{
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
		List<NoticeView> list = service.getNoticePubList(field, query, page); // 검색
		// 사용자가 공개된 공지사항만 볼수 있게 해야함
		
		int count = service.getNoticeCount(field, query);
		
		
		request.setAttribute("list", list);
		request.setAttribute("count", count); // 뷰에게 전달
		
		// 서블릿에서 서블릿으로 : redirect : 걍 전달만 함?, forward : 현재 작업했던 내용을 다른곳에서 이어받음'
		request.getRequestDispatcher("/WEB-INF/view/notice/list.jsp").forward(request, response);
		// notice안에 있는 detail.jsp를 요청하면서 현재 사용하는 저장소(request)와 출력도구(response)를 공유


	
	}

}
