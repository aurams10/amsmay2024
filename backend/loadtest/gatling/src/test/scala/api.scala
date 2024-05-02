import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class api extends Simulation {

  val httpProtocol =
    http

      .baseUrl("https://reqres.in")


  val data = csv("data/users.csv").circular

  val headers = Map(
    "accept" -> "application/json",
    "Content-Type" -> "application/json")

    val scn=scenario("loadtest")
      .feed(data)

      .exec(
        http("create user")
        .post("/api/users")

        .headers(headers)
         .body(ElFileBody("bodies/createuser.json")).asJson
        .check(status.is (201))
          .check(jsonPath("$.name").saveAs("name"))
      )

     .exec(
        http("list user")
          .get("/api/users")
          .queryParam("page", "1")
          .check(status.is (200))
          .check( bodyString.saveAs( "Response" ) )
          .check(jsonPath("$.data[:].id").saveAs("Id"))
     )
     .exec(
        http("update user")
          .put("/api/users/${Id}")
            .headers(headers)
          .check(status.is (200))
          .check(substring("updatedAt").exists)
       )

       .exec(
        http("delete user")
          .delete("/api/users/${Id}")
          .headers(headers)
          .check(status.is (204))
      )



  setUp(scn.inject(atOnceUsers(1),
    rampUsersPerSec(1).to(5).during(30),
    rampUsersPerSec(5).to(10).during(30),
    constantUsersPerSec(30).during(30)
  )).protocols(httpProtocol)
        .assertions(
          global.responseTime.max.between(10, 500),
          global.failedRequests.percent.is(0),
          global.successfulRequests.percent.gt(95)
        )


  }