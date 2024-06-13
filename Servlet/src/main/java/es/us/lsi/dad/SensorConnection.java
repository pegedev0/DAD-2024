package es.us.lsi.dad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SensorConnection extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5581936480104414707L;
	Map<Integer, Sensor> sensorPass;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.valueOf(req.getParameter("IdSensor"));
		
		Sensor sensor = sensorPass.get(id);
		
		String json = new Gson().toJson(sensor);
		
		response(resp, json);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    BufferedReader reader = req.getReader();
	    
	    Gson gson = new Gson();
	    Sensor sensor = gson.fromJson(reader, Sensor.class);
	
		sensorPass.put(sensor.getIdSensor(), sensor);
		resp.getWriter().println(gson.toJson(sensor));
		resp.setStatus(201);
	};

	public void init() throws ServletException {
		Long initNumber = Integer.toUnsignedLong(0);
		sensorPass = new HashMap<Integer, Sensor>();
		sensorPass.put(0, new Sensor(0, 2.582, initNumber,0));
		sensorPass.put(1, new Sensor(1, 1.8, initNumber,1));
		sensorPass.put(2, new Sensor(2, 1.91, initNumber,4));
		super.init();
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    BufferedReader reader = req.getReader();
	    
	    Gson gson = new Gson();
		Sensor sensor = gson.fromJson(reader, Sensor.class);
		
		sensorPass.remove(sensor.getIdSensor());
		
		resp.getWriter().println(gson.toJson(sensor));
		resp.setStatus(201); 
	}

	private void response(HttpServletResponse resp, String msg) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<t1>" + msg + "</t1>");
		out.println("</body>");
		out.println("</html>");
	}
}
