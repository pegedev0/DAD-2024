package es.us.lsi.dad;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

public class SensorRest extends AbstractVerticle {

	private Map<Integer, SensorEntity> SensorMap = new HashMap<Integer, SensorEntity>();
	private Gson gson;
	private Map<Integer, LedActuatorEntity> ledmap = new HashMap<Integer, LedActuatorEntity>();


	public void start(Promise<Void> startFuture) {
		// Creating some synthetic data
		createSomeData(25);
		createSomeData2(25);

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
		// handling by /api/HumiditySensorMap* or /api/HumiditySensorMap/*
		router.route("/api/SensorMap*").handler(BodyHandler.create());
		router.get("/api/SensorMap/all").handler(this::getAll); //Get a todos los sensores
		router.get("/api/SensorMap/last/Time").handler(this::getLastUpdate); //Ultimo timestamp
		router.get("/api/SensorMap/:IdSensor").handler(this::getOne); //get a un idSensor
		router.post("/api/SensorMap").handler(this::addOne); //Añadir uno
		router.get("/api/SensorMap/Sensores/:TipoSensor").handler(this::getAllTipo); //Extra
		router.get("/api/SensorMap/Sensores/Grupo/:idGrupo").handler(this::getAllGroups);
		router.get("/api/SensorMap/Sensores/Grupo/:idGrupo/LastTime").handler(this::getAllGroupsTiempo);//IdGrupos
		
		router.route("/api/ledmap*").handler(BodyHandler.create());
		router.get("/api/ledmap/all").handler(this::getAll2);
		router.get("/api/ledmap/last/Time").handler(this::getLastUpdate2);
		router.get("/api/ledmap/:LEDid").handler(this::getOne2);
		router.post("/api/ledmap").handler(this::addOne2);		
		router.get("/api/ledmap/Actuadores/Grupo/:IdGroup").handler(this::getAllGroups2);
		router.get("/api/ledmap/Actuadores/Grupo/:IdGroup/LastTime").handler(this::getAllGroupsTiempo2);//IdGrupos
	}

	@SuppressWarnings("unused")
	private void getAll(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(gson.toJson(new SensorEntityListWrapper(SensorMap.values())));
	}
	
	private void getLastUpdate(RoutingContext routingContext) {
		Optional<SensorEntity> res = SensorMap.values().stream().max(Comparator.comparing(SensorEntity::getTime));
		String json = res.isPresent() ? gson.toJson(res.get()) : "";
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(json);
	}
	
	
	private void getAllTipo(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("TipoSensor"));
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(new SensorEntityListWrapper(SensorMap.values()).getSensorList().stream().filter(x->x.getTipoSensor()==idgrupo).collect(Collectors.toList())));
	}
	
	private void getAllGroups(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("idGrupo"));
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(new SensorEntityListWrapper(SensorMap.values()).getSensorList().stream().filter(x->x.getidGrupo()==idgrupo).collect(Collectors.toList())));
	}
	
	private void getAllGroupsTiempo(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("idGrupo"));
		
		Optional<SensorEntity> res = SensorMap.values().stream().filter(x->x.getidGrupo()==idgrupo).max(Comparator.comparing(SensorEntity::getTime));
		String json = res.isPresent() ? gson.toJson(res.get()) : "";
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(json));
	}
	
	


	private void getOne(RoutingContext routingContext) {
		int id=0;
		id = Integer.parseInt(routingContext.request().getParam("IdSensor"));
		if (SensorMap.containsKey(id)) {
			SensorEntity ds = SensorMap.get(id);
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
					.end(gson.toJson(ds));
		} else {
			routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(300)
					.end();
		}
		
	}
	

	private void addOne(RoutingContext routingContext) {
		final SensorEntity user = gson.fromJson(routingContext.getBodyAsString(), SensorEntity.class);
		SensorMap.put(user.getIdSensor(), user);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(user));
	}

	private void createSomeData(int number) {
		Random rnd = new Random();
		Long time=Long.valueOf(0);
		Double Record=0.;
		
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			Integer clasi=0;
			Integer idgroup=1;
			if(id%2==0) {
				clasi=1;
			}
			if(id%2==0) {
				idgroup=2;
			}
			
			SensorMap.put(id, new SensorEntity(id,1,Record, time,clasi,idgroup));
		});
	}
	
	private void getAll2(RoutingContext routingContext) {
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(gson.toJson(new LedActuatorEntityListWrapper(ledmap.values())));
	}
	
	private void getLastUpdate2(RoutingContext routingContext) {
		Optional<LedActuatorEntity> res = ledmap.values().stream().max(Comparator.comparing(LedActuatorEntity::getTime));
		String json = res.isPresent() ? gson.toJson(res.get()) : "";
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
				.end(json);
	}
	

	private void getOne2(RoutingContext routingContext) {
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

	private void addOne2(RoutingContext routingContext) {
		final LedActuatorEntity user = gson.fromJson(routingContext.getBodyAsString(), LedActuatorEntity.class);
		ledmap.put(user.getLEDid(), user);
		routingContext.response().setStatusCode(201).putHeader("content-type", "application/json; charset=utf-8")
				.end(gson.toJson(user));
	}
	
	private void getAllGroups2(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("IdGroup"));
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(new LedActuatorEntityListWrapper(ledmap.values()).getLedList().stream().filter(x->x.getIdGroup()==idgrupo).collect(Collectors.toList())));
	}
	
	private void getAllGroupsTiempo2(RoutingContext routingContext) {
		Integer idgrupo = Integer.parseInt(routingContext.request().getParam("IdGroup"));
		
		Optional<LedActuatorEntity> res = ledmap.values().stream().filter(x->x.getIdGroup()==idgrupo).max(Comparator.comparing(LedActuatorEntity::getTime));
		String json = res.isPresent() ? gson.toJson(res.get()) : "";
		
		routingContext.response().putHeader("content-type", "application/json; charset=utf-8").setStatusCode(200)
		.end(gson.toJson(json));
	}
	
	
	

	private void createSomeData2(int number) {
		Random rnd = new Random();
		Long time=Long.valueOf(0);
		Double Record=0.;
		
		
		IntStream.range(0, number).forEach(elem -> {
			int id = rnd.nextInt();
			int idgroup=1;
			if(id%2==0) {
				idgroup=2;
			}
			ledmap.put(id, new LedActuatorEntity(id,1,idgroup, id, id,time));
		});
	}
	
	
}