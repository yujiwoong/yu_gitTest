package sec02.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	
	private DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<MemberVO> listMembers(){
		List<MemberVO> membersList = new ArrayList<MemberVO>();
		try {
			conn = dataFactory.getConnection();
			String sql = "select * from t_member order by joinDate desc";//1.sql문을 작성합니다.
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);//2.PrepareStatement 객체를 생성하면서 SQL문을 인자로 전달합니다.
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				//3.조회한 회원 정보를 레코드별로 MemberVO객체들을 차례대로 저장합니다.
				MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate);
				membersList.add(memberVO);//4.membersList에 MemberVO 객체들을 차례대로 저장합니다.
			}
			rs.close();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membersList;
	}
	
	public void addMember(MemberVO m) {
		try {
			conn = dataFactory.getConnection();
			String id = m.getId();
			String pwd = m.getPwd();
			String name = m.getName();
			String email = m.getEmail();
			String sql = "insert into t_member(id, pwd, name, email)" + "values(?,?,?,?)";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);//5.
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate();//6.sql문을 실행합니다.
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public MemberVO findMember(String _id) {
		MemberVO memInfo = null;
		try {
			conn = dataFactory.getConnection();
			String sql = "select * from t_member where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, _id);	
			System.out.println(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id");
			String pwd = rs.getString("pwd");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date joinDate = rs.getDate("joinDate");
			memInfo = new MemberVO(id, pwd, name, email, joinDate);
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memInfo;
	}
	
	public void modMember(MemberVO memberVO) {
		String id = memberVO.getId();
		String pwd = memberVO.getPwd();
		String name = memberVO.getName();
		String email = memberVO.getEmail();
		try {
			conn = dataFactory.getConnection();
			//전달된 수정 회원 정보를 update문을 이용해 수정합니다.
			String sql = "update t_member set pwd=?, name=?, email=? where id=?";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pwd);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, id);
			//SQL문을 실행합니다.
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delMember(String id) {
		try {
			conn = dataFactory.getConnection();
			String sql = "delete from t_member where id=?";
			System.out.println(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
            e.printStackTrace(); 	
		}
	}
}
