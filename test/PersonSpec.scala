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
      val request = FakeRequest("POST", "/api/v1/person").withJsonBody(post_data)
      val response = route(request).get

      status(response) must equalTo(OK)
      contentAsJson(response) must equalTo(post_data)
    }
  }

  //parse失敗時のテストコード欲しいよね(あとで追加する)

}
