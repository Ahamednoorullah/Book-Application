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

@WebServlet("/editurl")
public class EditUpdate extends HttpServlet {

	private static final String query = "update library set BookName=?,AuthorName=?,Price=?,Qty=? where BookId=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw = resp.getWriter();
		//set content type
		resp.setContentType("text/html");
		//get the id of record
		String id = req.getParameter("id");
		//get the edit data we want to edit
		String bookName=req.getParameter("bookName");
		String authorName=req.getParameter("authorName");
		double price=Double.parseDouble(req.getParameter("price"));
		int qty=Integer.parseInt(req.getParameter("qty"));
		
		
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
			ps.setString(2, bookName);
			ps.setDouble(3, price);
			ps.setInt(4, qty);
			ps.setString(5, id);
			int count = ps.executeUpdate();
			if (count == 1) {
				pw.println("<h2>Record is Edited Successfully</h2>");
			} else {
				pw.println("<h2>Record is Not Edited </h2>");
			}
			
		}catch (SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"</h1>");
		}catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
		pw.println("<a href='Home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='booklist'>Book List</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}


}
