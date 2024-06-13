package es.us.lsi.dad;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class LedServer extends AbstractVerticle {

	private Map<Integer, LedActuatorEntity> ledmap = new HashMap<Integer, LedActuatorEntity>();
	private Gson gson;

	public void start(Promise<Void> startFuture) {
		// Creating some synthetic data
		createSomeData(25);

		// Instantiating a Gson serialize object using specific date format
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		// Defining the router object
		Router router = Router.router(vertx);

		// Handling any server startup result
		vertx.createHttpServer().requestHandler(router::handle).listen(8080, result -> {
			if (result.succeeded()) {
				startFuture.complete();
			} else {
				startFuture.fail(result.cause());
			}
		});

		// Defining URI paths for each method in RESTful interface, including body
		// handling by /api/Humidityledmap* or /api/Humidityledmap/*
		router.route("/api/ledmap*").handler(BodyHandler.create());
		router.get("/api/ledmap/all").handler(this::getAll);
		router.get("/api/ledmap/last/Time").handler(this::getLastUpdate);
		router.get("/api/ledmap/:LEDid").handler(this::getOne);
		router.post("/api/ledmap").handler(this::addOne);
		
		router.get("/api/ledmap/Actuadores/Grupo/:IdGroup").handler(this::getAllGroups);
		router.get("/api/ledmap/Actuadores/Grupo/:IdGroup/LastTime").handler(this::getAllGroupsTiempo);//IdGrupos
		
	}

	@SuppressWarnings("unused")
	private void getAll(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(gson.toJson(new LedActuatorEntityListWrapper(ledmap.values())));
	}
	
	private void getLastUpdate(RoutingContext routingContext) {
		Optional<LedActuatorEntity> res = ledmap.values().stream().max(Comparator.comparing(LedActuatorEntity::getTime));
		String json = res.isPresent() ? gson.toJson(res.get()) : "";
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(json);
	}
	

	private void getOne(RoutingContext routingContext) {
		int id=0;
		id = Integer.parseInt(routingContext.request().getParam("LEDid"));
		if (ledmap.containsKey(id)) {
			LedActuatorEntity ds = ledmap.get(id);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(ds));
		} else {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(300)
					.end();
		}
		
	}

	private void addOne(RoutingContext routingContext) {
		final LedActuatorEntity user = gson.fromJson(routingContext.getBodyAsString(), LedActuatorEntity.class);
		ledmap.put(user.getLEDid(), user);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(user));
	}
	
	private void getAllGroups(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("IdGroup"));
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(new LedActuatorEntityListWrapper(ledmap.values()).getLedList().stream().filter(x->x.getIdGroup()==idgrupo).collect(Collectors.toList())));
	}
	
	private void getAllGroupsTiempo(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("IdGroup"));
		
		Optional<LedActuatorEntity> res = ledmap.values().stream().filter(x->x.getIdGroup()==idgrupo).max(Comparator.comparing(LedActuatorEntity::getTime));
		String json = res.isPresent() ? gson.toJson(res.get()) : "";
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(json));
	}
	
	
	

	private void createSomeData(int number) {
		Random rnd = new Random();
		Long time=Long.valueOf(0);
		Double Record=0.;
		
		
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			int idgroup=1;
			if(id%2==0) {
				idgroup=2;
			}
			ledmap.put(id, new LedActuatorEntity(id,idgroup, id, id,time));
		});
	}
	
}
