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

@WebServlet("/booklist")
public class BookList extends HttpServlet {


	private static final String query = "select BookName,BookId,AuthorName,Price,Qty from library";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get printwriter
		PrintWriter pw = resp.getWriter();
		//set content type
		resp.setContentType("text/html");
		//link the boostrap
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		pw.println("<marquee><h2 class='text-primary'>Book Data</h2></marquee>");
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
			ResultSet rs = ps.executeQuery();
			pw.println("<div style='margin:auto;width:800px;margin-top:100px;'>");
			pw.println("<table class='table table-hover table-striped'>");
			pw.println("<tr style='background-color:blue; color:White;'>");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Id</th>");
			pw.println("<th>Author Name</th>");
			pw.println("<th>Price</th>");
			pw.println("<th>Qty</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+rs.getString(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+"</td>");
				pw.println("<td>"+rs.getDouble(4)+"</td>");
				pw.println("<td>"+rs.getInt(5)+"</td>");
				pw.println("<td><button style='background-color:#90EE90;'><a href='editScreen?id="+rs.getString(2)+"' style='color:black;text-decoration:none;'>Edit</a></button></td>");
				pw.println("<td><button style='background-color:#e97451;'><a href='deleteurl?id="+rs.getString(2)+"' style='color:black;text-decoration:none;'>Delete</a></td>");
				pw.println("</tr>");
			}
			pw.println("</table>");
		}catch (SQLException se) {
			se.printStackTrace();
			pw.println("<h1>"+se.getMessage()+"</h1>");
		}catch (Exception e) {
			e.printStackTrace();
			pw.println("<h1>"+e.getMessage()+"</h1>");
		}
		pw.println("<a href='Home.html'><button class='btn btn-outline-success'>Home</button></a>");
		pw.println("<a href='add.html'><button class='btn btn-outline-success'>Books Added</button></a>");
		pw.println("<br>");
		pw.println("<a href='login.html'><button class='btn btn-outline-danger'>Logout</button></a>");
		pw.println("</div>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
