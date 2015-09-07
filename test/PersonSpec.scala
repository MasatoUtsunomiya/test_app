import org.specs2.mutable._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

class PersonSpec extends Specification {

  "Person Controller" should {
    "registration person" in new WithApplication {
      val post_data = Json.parse(
        """
          |{
          |	"name" : {
          |		"first" : "Masato",
          |		"last" : "Utsunomiya"
          |	},
          |	"age": 33
          |}
        """.stripMargin)
      val response = route(FakeRequest("POST", "/api/v1/person").withJsonBody(post_data)).get

      status(response) must equalTo(OK)
      contentAsJson(response) must equalTo(post_data)
    }

    "json parameter(name.first) is invalid" in new WithApplication {
      val post_data = Json.parse(
        """
          |{
          |	"name" : {
          |		"first" : 123,
          |		"last" : "Utsunomiya"
          |	},
          |	"age": 33
          |}
        """.stripMargin)
      val response = route(FakeRequest("POST", "/api/v1/person").withJsonBody(post_data)).get

      status(response) must equalTo(BAD_REQUEST)
      contentAsJson(response) must equalTo(Json.parse(
        """
          |{
          | "obj.name.first":[
          |   {"msg":["error.expected.jsstring"],
          |    "args":[]
          |    }
          | ]
          |}
        """.stripMargin))
    }

    "append option type parameter" in new WithApplication {
      val post_data = Json.parse(
        """
          |{
          |	"name" : {
          |   "first" : "Masato",
          |   "last" : "Utsunomiya"
          |	},
          |	"age": 33,
          | "blood_type": "O",
          | "favorite_number": [1,2,3]
          |}
        """.stripMargin)
      val response = route(FakeRequest("POST", "/api/v1/person").withJsonBody(post_data)).get

      status(response) must equalTo(OK)
      contentAsJson(response) must equalTo(post_data)
    }
  }
}
