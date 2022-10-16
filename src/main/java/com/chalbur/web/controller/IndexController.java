package com.chalbur.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")// 루트에서 인덱스를 요청하면 여기서 받음
public class IndexController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 서블릿에서 서블릿으로 : redirect : 걍 전달만 함?, forward : 현재 작업했던 내용을 다른곳에서 이어받음'
		request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
		// notice안에 있는 detail.jsp를 요청하면서 현재 사용하는 저장소(request)와 출력도구(response)를 공유
	}
}
