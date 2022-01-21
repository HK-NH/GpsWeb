package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.SmartphoneLocal;
import dao.UserLocal;
import entities.Smartphone;
import entities.User;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private UserLocal ul;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("op").equals("save")) {
			String nom = request.getParameter("nomu");
			String daten = request.getParameter("datenu").replace("-", "/");
			String prenom = request.getParameter("prenomu");
			String email = request.getParameter("emailu");
			String telephone = request.getParameter("telephoneu");
			User u = new User(nom, prenom, email, new Date(daten), telephone);
			ul.create(u);
		} else if (request.getParameter("op").equals("dele")) {
			ul.delete(ul.findById(Integer.parseInt(request.getParameter("id"))));
		}
		else if (request.getParameter("op").equals("update")){
			int id = Integer.parseInt(request.getParameter("id"));
			String nom = request.getParameter("nom");
			String daten = request.getParameter("dateNaissance").replace("-", "/");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("email");
			String telephone = request.getParameter("telephone");
			User u = new User(nom, prenom, email, new Date(daten), telephone);
			u.setId(id);
			ul.update(u);
		}
		response.setContentType("application/json");
		List<User> users = ul.findAll();
		Gson json = new GsonBuilder().create();
		response.getWriter().write(json.toJson(users));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
