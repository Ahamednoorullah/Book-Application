package com.chainsys.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

	private static final String query ="select Email,Password from registrationform";
	
    @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  	//get writter
  	  PrintWriter pw = resp.getWriter();
  	  //set content type
  	  resp.setContentType("text/html");
  	  //get Register Info
  	  String email = req.getParameter("email");
  	  String password = req.getParameter("password");
  	  
  	  //get load Jdbc Driver
  	  try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			// TODO Auto-generated catch block
			cnfe.printStackTrace();
			
		}
  	  int usercount = 0;
  	  int logincount =0;
  	  try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chainsys?user=root&password=irfani");
				PreparedStatement ps = con.prepareStatement(query);){
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				usercount++;
				String loginEmail = rs.getString(1);
				String loginPassword = rs.getString(2);
				if ((loginEmail.equalsIgnoreCase(email)) && (loginPassword.equalsIgnoreCase(password))) {
					if ((loginEmail.equalsIgnoreCase("ahamednoorullah@gmail.com")) && (loginPassword.equalsIgnoreCase("12345"))) {
						resp.sendRedirect("add.html");
						break;
					}else {
						resp.sendRedirect("userbooklist");
					}
				} else {
					logincount++;
					
				}
			}
			if (usercount==logincount) {
				pw.println("<script >");
				pw.println("var conf = confirm('Login Failed')");
				pw.println("if(conf==true){window.location.href='login.html';}");
				pw.println("else{window.location.href='login.html';}");
				pw.println("</script>");
			}
		}catch (SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"</h1>");
		}catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}

  }
    
    @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  	
  	doGet(req, resp);
  }

}
