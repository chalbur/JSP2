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
	// 404 : �ƿ� �����Ҽ� �ִ� url�� ����
	// 405 : ������ �ִ� �޼ҵ尡 ����
	// 403 : url���ְ� �޼ҵ嵵 ������ ������ ����(���ȿ���)
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ���� Ű������ ���µ� ���� �ٸ����, ���ڿ��� ���µ� Ű�� ���� ���� �ٸ���� ->getParameterValues
		String[] openIds = request.getParameterValues("open-id"); //ex 3,5,8 �����ؾ��� ����
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		
		String ids_ = request.getParameter("ids"); // ���� �������� ���� ��������
		String[] ids = ids_.trim().split(" ");	// ex 1,2,3,4,5,6,7,8,9,10 ������ = 3, 5, 8�� ���»����� 1�� ����
		// trim : �¿��� ������� ����
		NoticeService service = new NoticeService();
		switch(cmd) {
		case "�ϰ�����" :
			for(String openId : openIds)
				System.out.printf("open id : %s\n", openId);
			
			List<String> oids = Arrays.asList(openIds); // �迭�� ����Ʈ���·� �ٲ���, openIds�� ����Ʈ�� �ٲ���
			// ��ü ������ �����ؾ��� id�� �� -> ������� ����
			List<String> cids = new ArrayList(Arrays.asList(ids));
			// Arrays.asList(ids)���� �߰� ������ �Ұ����ؼ� �����͸� ���ο� ��ü�� ����
			cids.removeAll(oids);
			System.out.println(Arrays.asList(ids));
			System.out.println(oids);
			System.out.println(cids);
			
				
			// Transaction : �����ϴ� �⺻ ����, �ϳ��� �������� ������ǰ� ��, �ѹ��� �̷������ ���ϴ� ��������
			service.pubNoticeAll(oids, cids); // ex update NOTICE set pub=1 where id in (...)
			//service.pubNoticeList(clsIds); // �� �Լ��� ���� ����ǰų� �����ؾ���, �ϳ��� �����ϸ� �ȵ�
			
			break;
		case "�ϰ�����" :
			
			int[] ids1 = new int[delIds.length];
			for(int i=0;i<delIds.length;i++) {
				ids1[i] = Integer.parseInt(delIds[i]);
			}
			service.deleteNoticeAll(ids1);
			break;
		}
		
		response.sendRedirect("list"); // post���� ó�� �� ����� �����ִ� �ٸ��������� �̵�(��û)��
		// url�� ���� ���������� post���� get�� ȣ��, ���� �˰��ִ� list����� ���û, get�� �ٽ� ��û�� ����
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// list?f=title&q=a ���·� �˻��� �ϸ� ���޵�, �˻��� �ʼ��� �ƴ϶� �ɼ�
		
		String field_ = request.getParameter("f"); // list.jsp �˻�â���� �Ѱ��ִ� ��
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		String field = "title"; // �⺻��(����) ó��
		if(field_ != null && !field_.equals(""))
			field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals(""))
			query = query_;
		
		int page = 1;
		if(page_ != null && !page_.equals(""))
			page = Integer.parseInt(page_);
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page); // �˻�
		
		int count = service.getNoticeCount(field, query);
		
		
		request.setAttribute("list", list);
		request.setAttribute("count", count); // �信�� ����
		
		// �������� �������� : redirect : �� ���޸� ��?, forward : ���� �۾��ߴ� ������ �ٸ������� �̾����'
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request, response);
		// notice�ȿ� �ִ� detail.jsp�� ��û�ϸ鼭 ���� ����ϴ� �����(request)�� ��µ���(response)�� ����


	
	}

}
