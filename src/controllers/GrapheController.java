package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Servlet implementation class GrapheController
 */
@WebServlet("/GrapheController")
public class GrapheController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	@EJB
	private SmartphoneLocal sl;
	@EJB
	private PositionLocal pl;
    public GrapheController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int cmp=0;
		response.setContentType("application/json");
		List<Smartphone> smartphones = sl.findAlls();
		Gson json = new GsonBuilder().create();
		List<Position> positions = pl.findAll();
		for (Smartphone s : smartphones) {
			cmp=0;
			for (Position position : positions) {
				if(s.getId() == position.getSmartphone().getId()) {
					cmp++;
				}
			}
			s.setMarque(String.valueOf(cmp));
		}
		response.getWriter().write(json.toJson(smartphones));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
