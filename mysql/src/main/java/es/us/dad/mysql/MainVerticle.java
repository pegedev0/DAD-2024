package es.us.dad.mysql;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

public class MainVerticle extends AbstractVerticle {

	MySQLPool mySqlClient;

	@Override
	public void start(Promise<Void> startFuture) {
		MySQLConnectOptions connectOptions = new MySQLConnectOptions().setPort(3306).setHost("localhost")
				.setDatabase("dad").setUser("root").setPassword("root");

		PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

		mySqlClient = MySQLPool.pool(vertx, connectOptions, poolOptions);

		getAllSensors();
		getAllActuators();
		
		getAllSensorsWithConnection();
		getAllActuatorsWithConnection();

		for (int i = 0; i < 5; i++) {
			getByIdSensor(i);
			getByIdActuator(i);
		}

	}

	private void getAllSensors() {
		mySqlClient.query("SELECT * FROM dad.sensores;", res -> {
			if (res.succeeded()) {
				// Get the result set
				RowSet<Row> resultSet = res.result();
				System.out.println(resultSet.size());
				JsonArray result = new JsonArray();
				for (Row elem : resultSet) {
					result.add(JsonObject.mapFrom(new SensorImpl(elem.getInteger("idSensor"), elem.getInteger("idPlaca"),
							elem.getDouble("record"), elem.getLong("time"), elem.getInteger("tipoSensor"), elem.getInteger("idGrupo"))));
				}
				System.out.println(result.toString() + "\n");
			} else {
				System.out.println("Error: " + res.cause().getLocalizedMessage());
			}
		});
	}
	
	private void getAllActuators() {
		mySqlClient.query("SELECT * FROM dad.actuadores;", res -> {
			if (res.succeeded()) {
				// Get the result set
				RowSet<Row> resultSet = res.result();
				System.out.println(resultSet.size());
				JsonArray result = new JsonArray();
				for (Row elem : resultSet) {
					result.add(JsonObject.mapFrom(new ActuatorImpl(elem.getInteger("idActuador"), elem.getInteger("idPlaca"),
							elem.getInteger("state"), elem.getInteger("intensity"), elem.getInteger("idGrupo"))));
				}
				System.out.println(result.toString() + "\n\n");
			} else {
				System.out.println("Error: " + res.cause().getLocalizedMessage());
			}
		});
	}

	private void getAllSensorsWithConnection() {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().query("SELECT * FROM dad.sensores;", res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println(resultSet.size());
						JsonArray result = new JsonArray();
						for (Row elem : resultSet) {
							result.add(JsonObject.mapFrom(new SensorImpl(elem.getInteger("idSensor"), elem.getInteger("idPlaca"),
									elem.getDouble("record"), elem.getLong("time"), elem.getInteger("tipoSensor"), elem.getInteger("idGrupo"))));
						}
						System.out.println(result.toString());
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					connection.result().close();
				});
			} else {
				System.out.println(connection.cause().toString());
			}
		});
	}
	
	private void getAllActuatorsWithConnection() {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().query("SELECT * FROM dad.actuadores;", res -> {
					if (res.succeeded()) {
						// Get the result set
						RowSet<Row> resultSet = res.result();
						System.out.println("\n" + resultSet.size());
						JsonArray result = new JsonArray();
						for (Row elem : resultSet) {
							result.add(JsonObject.mapFrom(new ActuatorImpl(elem.getInteger("idActuador"), elem.getInteger("idPlaca"),
									elem.getInteger("state"), elem.getInteger("intensity"), elem.getInteger("idGrupo"))));
						}
						System.out.println(result.toString() + "\n\n");
					} else {
						System.out.println("Error: " + res.cause().getLocalizedMessage());
					}
					connection.result().close();
				});
			} else {
				System.out.println(connection.cause().toString());
			}
		});
	}
	
	
	private void getByIdSensor(Integer sensorId) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM dad.sensores WHERE idSensor = ?",
						Tuple.of(sensorId), res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								System.out.println(resultSet.size());
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new SensorImpl(elem.getInteger("idSensor"), elem.getInteger("idPlaca"),
											elem.getDouble("record"), elem.getLong("time"), elem.getInteger("tipoSensor"), elem.getInteger("idGrupo"))));
								}
								System.out.println(result.toString());
							} else {
								System.out.println("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				System.out.println(connection.cause().toString());
			}
		});
	}
	
	private void getByIdActuator(Integer actuatorId) {
		mySqlClient.getConnection(connection -> {
			if (connection.succeeded()) {
				connection.result().preparedQuery("SELECT * FROM dad.actuadores WHERE idActuador = ?",
						Tuple.of(actuatorId), res -> {
							if (res.succeeded()) {
								// Get the result set
								RowSet<Row> resultSet = res.result();
								System.out.println(resultSet.size());
								JsonArray result = new JsonArray();
								for (Row elem : resultSet) {
									result.add(JsonObject.mapFrom(new ActuatorImpl(elem.getInteger("idActuador"), elem.getInteger("idPlaca"),
											elem.getInteger("state"), elem.getInteger("intensity"), elem.getInteger("idGrupo"))));
								}
								System.out.println(result.toString() + "\n");
							} else {
								System.out.println("Error: " + res.cause().getLocalizedMessage());
							}
							connection.result().close();
						});
			} else {
				System.out.println(connection.cause().toString());
			}
		});
	}

	@Override
	public void stop(Future<Void> stopFuture) throws Exception {
		super.stop(stopFuture);
	}

}
