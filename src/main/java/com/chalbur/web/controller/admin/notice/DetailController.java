package com.chalbur.web.controller.admin.notice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chalbur.web.entity.Notice;
import com.chalbur.web.service.NoticeService;

// 컨트롤러 : 사용자의 요청을 받아서 제어하는 역할만 담당 서비스레이어가 데이터서비스 담당
// detail.jsp와 연결됨
@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id")); // 사용자가 글 목록중 하나를 누르면 id를 전달

		NoticeService service = new NoticeService();
		Notice notice = service.getNotice(id); // 전달한 id를 받아서 notice에서 꺼냄
		request.setAttribute("n", notice);
			
		// 서블릿에서 서블릿으로 : redirect : 걍 전달만 함?, forward : 현재 작업했던 내용을 다른곳에서 이어받음'
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp").forward(request, response);
		// notice안에 있는 detail.jsp를 요청하면서 현재 사용하는 저장소(request)와 출력도구(response)를 공유
			
	}
}
