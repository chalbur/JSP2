package com.chalbur.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")// ��Ʈ���� �ε����� ��û�ϸ� ���⼭ ����
public class IndexController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// �������� �������� : redirect : �� ���޸� ��?, forward : ���� �۾��ߴ� ������ �ٸ������� �̾����'
		request.getRequestDispatcher("/WEB-INF/view/index.jsp").forward(request, response);
		// notice�ȿ� �ִ� detail.jsp�� ��û�ϸ鼭 ���� ����ϴ� �����(request)�� ��µ���(response)�� ����
	}
}
