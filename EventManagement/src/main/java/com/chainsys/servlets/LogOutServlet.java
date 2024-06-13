package com.chainsys.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public LogOutServlet() {
        super();
        
    }

	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	     
        HttpSession session = request.getSession(false);
        
        
        if (session != null) {
            session.invalidate();
            System.out.println("invalidated");
        }
        
        try {
        response.sendRedirect("index.jsp");
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
	}


