package sec01.ex01;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class MemberController
 */
/* @WebServlet("/mem.do") */
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    MemberDAO memberDAO;   
    
    public void init() throws ServletException{
    	memberDAO = new MemberDAO();//DAO를 생성합니다.
    }
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		List<MemberVO> membersList = memberDAO.listMembers(); //요청에 대해 회원 정보를 조회합니다.
		request.setAttribute("membersList", membersList); //조회한 회원 정보를 request에 바인딩합니다.
		
		//컨트롤러에서 표시하고자 하는 JSP로 포워딩합니다.
		RequestDispatcher dis = request.getRequestDispatcher("/test01/ListMembers.jsp");
		dis.forward(request, response);
	}
}
