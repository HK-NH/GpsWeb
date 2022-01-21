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

import dao.PositionLocal;
import dao.SmartphoneLocal;
import entities.Position;
import entities.Smartphone;

/**
 * Servlet implementation class MapController
 */
@WebServlet("/MapController")
public class MapController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private PositionLocal pl;

	@EJB
	private SmartphoneLocal sl;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MapController() {
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
		if (request.getParameter("op").equals("load")) {
			response.setContentType("application/json");
			List<Smartphone> smartphones = sl.findAlls();
			Gson json = new GsonBuilder().create();
			response.getWriter().write(json.toJson(smartphones));

		} else {
			response.setContentType("application/json");
			List<Position> positions = pl.findAllBySmartphoneId(Integer.parseInt(request.getParameter("id")));
			Gson json = new GsonBuilder().create();
			response.getWriter().write(json.toJson(positions));
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
