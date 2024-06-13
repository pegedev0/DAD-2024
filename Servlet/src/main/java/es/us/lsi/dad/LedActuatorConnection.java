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

public class LedActuatorConnection extends HttpServlet {

	private static final long serialVersionUID = -6103308604277957254L;
	
	Map<Integer, LedActuator> actuatorPass;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer id = Integer.valueOf(req.getParameter("LEDid"));
		
		LedActuator actuator = actuatorPass.get(id);
		
		String json = new Gson().toJson(actuator);
		
		response(resp, json);
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    BufferedReader reader = req.getReader();
	    
	    Gson gson = new Gson();
	    LedActuator actuator = gson.fromJson(reader, LedActuator.class);
	
	    actuatorPass.put(actuator.getLEDid(), actuator);
		
		resp.getWriter().println(gson.toJson(actuator));
		resp.setStatus(201);
	};

	public void init() throws ServletException {		
		actuatorPass = new HashMap<Integer, LedActuator>();
		actuatorPass.put(0, new LedActuator(0, 0, 0));
		super.init();
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    BufferedReader reader = req.getReader();
	    
	    Gson gson = new Gson();
	    LedActuator actuator = gson.fromJson(reader, LedActuator.class);
		
	    actuatorPass.remove(actuator.getLEDid());
		
		resp.getWriter().println(gson.toJson(actuator));
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
