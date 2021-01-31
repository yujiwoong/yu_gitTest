package sec02.ex01;

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
/* @WebServlet("/member/*") */
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
		String nextPage = null;
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		//브라우저에서 요청 시 두 단계로 요청이 이루어 집니다.
		String action = request.getPathInfo();
		//URL에서 요청명을 가져옵니다.
		System.out.println("action:" + action);
		//최초 요청이거나 action값이 /memberList.do이면 회원 목록을 출력합니다.
		if(action == null || action.equals("/memberList.do")) {
			//요청에 대해 회원 정보를 조회합니다.
			List<MemberVO> membersList = memberDAO.listMembers(); 
			//조회한 회원 정보를 request에 바인딩합니다.
			request.setAttribute("membersList", membersList);
			//test02폴더의 listMember.jsp로 포워딩합니다.
			nextPage = "/test02/listMembers.jsp";
		//action값이 /addMember.do면 전송된 회원 정보를 가져와서 테이블에 추가합니다.
		}else if(action.equals("/addMember.do")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memverVO = new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memverVO);
			//회원 등록후 다시 회원 목록을 출력합니다.
			nextPage = "/member/listMembers.do";
		//action값이 /memberForm.do면 회원 가입창을 화면에 출력합니다.	
		}else if(action.equals("/memberForm.do")) {
			//test02폴더의 memberForm.jsp로 포워딩합니다.
			nextPage = "/test02/MemberForm.jsp";
		//그 외 다른 action값은 회원 목록을 출력합니다.
		}else {
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test02/listMembers.jsp";
		}
		//nextPage에 지정한 요청명으로 다시 서블릿에 요청합니다.
		RequestDispatcher dis = request.getRequestDispatcher(nextPage);
		dis.forward(request, response);
	}
}
