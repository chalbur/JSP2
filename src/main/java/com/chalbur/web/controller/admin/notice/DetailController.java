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

// ��Ʈ�ѷ� : ������� ��û�� �޾Ƽ� �����ϴ� ���Ҹ� ��� ���񽺷��̾ �����ͼ��� ���
// detail.jsp�� �����
@WebServlet("/admin/board/notice/detail")
public class DetailController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id")); // ����ڰ� �� ����� �ϳ��� ������ id�� ����

		NoticeService service = new NoticeService();
		Notice notice = service.getNotice(id); // ������ id�� �޾Ƽ� notice���� ����
		request.setAttribute("n", notice);
			
		// �������� �������� : redirect : �� ���޸� ��?, forward : ���� �۾��ߴ� ������ �ٸ������� �̾����'
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/detail.jsp").forward(request, response);
		// notice�ȿ� �ִ� detail.jsp�� ��û�ϸ鼭 ���� ����ϴ� �����(request)�� ��µ���(response)�� ����
			
	}
}
