package com.chainsys.servlet.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.PreparableStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterBook extends HttpServlet {
	
	private static final String query = "insert into library(BookName,BookId,AuthorName,Price,Qty) values (?,?,?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw = resp.getWriter();
		//set content type
		resp.setContentType("text/html");
		//get book info
		String bookName = req.getParameter("bookName");
		String bookId = req.getParameter("bookId");
		String authorName = req.getParameter("authorName");
		double price =Double.parseDouble(req.getParameter("price"));
		int qty =Integer.parseInt(req.getParameter("qty"));
		//load jdbc driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			// TODO Auto-generated catch block
			cnfe.printStackTrace();
		}
		//generate the connection
		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cfje2?user=root&password=irfani");
				PreparedStatement ps = con.prepareStatement(query);){
			ps.setString(1, bookName);
			ps.setString(2, bookId);
			ps.setString(3, authorName);
			ps.setDouble(4, price);
			ps.setInt(5, qty);
			int count = ps.executeUpdate();
			pw.println("<div class='card' style='margin:auto;width:300px;margin-top:100px'>");
			if (count==1) {
				pw.println("<h2 class='bg-danger text-light text-center'>Record is Register Successfully</h2>");
			} else {
				pw.println("<h2 class='bg-danger text-light text-center'>Record is Not Registered</h2>");
			}
			
		}catch (SQLException se) {
			se.printStackTrace();
			pw.println("<h1 class='bg-danger text-light text-center'>"+se.getMessage()+"</h1>");
		}catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
		pw.println("<a href='Home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='booklist'>Book List</a>");
		pw.println("<br>");
		pw.println("<a href='login.html'>Logout</a>");
		pw.println("</div>");
		pw.close();
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
