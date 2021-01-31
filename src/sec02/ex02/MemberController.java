package sec02.ex02;

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
@WebServlet("/member/*")
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
			nextPage = "/test03/listMembers.jsp";
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
			nextPage = "/test03/MemberForm.jsp";
		//회원 수정창 요청 시 ID로 회원정보를 조회한 후 수정창으로 포워딩합니다.
		}else if(action.equals("/modMemberForm.do")){
			String id = request.getParameter("id");
			//회원정보수정창을 요청하면 전송된 ID를 이용해 수정 전 회원 정보를 조회합니다.
			MemberVO memInfo = memberDAO.findMember(id);
			//request에 바인딩하여 회원 정보 수정창에 수정하기 전 회원 정보를 전달합니다.
			request.setAttribute("memInfo", memInfo);
			nextPage = "/test03/modMemberForm.jsp";
		//테이블의 회원 정보를 수정합니다.	
		}else if(action.equals("/modMember.do")) {
			//회원 정보 수정항에서 전송된 수정 회원 정보를 가져온 후 MemberVO객체 속성에 설정합니다.
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO);
			//회원 목록창으로 수정작업완료 메세지를 전달합니다.
			request.setAttribute("msg", "modified");
			nextPage = "/member/listMembers.do";
		//회원ID를 SQL문으로 전달해 테이블의 회원 정보를 삭제합니다.	
		}else if(action.equals("/delMember.do")) {
			//삭제할 회원 ID를 받아옵니다.
			String id = request.getParameter("id");
			memberDAO.delMember(id);
			//회원 목록창으로 삭제 작업 메세지를 전달합니다.
			request.setAttribute("msg", "deleted");
			nextPage = "/member/listMembers.do";
		//그 외 다른 action값은 회원 목록을 출력합니다.	
		}else {
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/test03/listMembers.jsp";
		}
		//nextPage에 지정한 요청명으로 다시 서블릿에 요청합니다.
		RequestDispatcher dis = request.getRequestDispatcher(nextPage);
		dis.forward(request, response);
	}
}
