package controllers;

import java.io.IOException;
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

/**
 * Servlet implementation class SmartphoneController
 */
@WebServlet("/SmartphoneController")
public class SmartphoneController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private SmartphoneLocal sl;
	@EJB
	private UserLocal ul;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SmartphoneController() {
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
			String marque = request.getParameter("marque");
			int imei = Integer.parseInt(request.getParameter("imei"));
			int userid = Integer.parseInt(request.getParameter("iduser"));
			sl.create(new Smartphone(imei, marque, ul.findById(userid)));
			response.setContentType("application/json");
			List<Smartphone> smartphones = sl.findAlls();
			Gson json = new GsonBuilder().create();
			response.getWriter().write(json.toJson(smartphones));
		} else if (request.getParameter("op").equals("delete")) {
			sl.delete(sl.findById(Integer.parseInt(request.getParameter("id"))));
		} else if (request.getParameter("op").equals("update")) {
			Smartphone s = sl.findById(Integer.parseInt(request.getParameter("id")));
			s.setImei(Integer.parseInt(request.getParameter("imei")));
			s.setMarque(request.getParameter("marque"));
			sl.update(s);
		}
		if (request.getParameter("op").equals("save") != true) {
			response.setContentType("application/json");
			List<Smartphone> smartphones = sl.findAll(Integer.parseInt(request.getParameter("iduser")));
			Gson json = new GsonBuilder().create();
			response.getWriter().write(json.toJson(smartphones));
		}
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
