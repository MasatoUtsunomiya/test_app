package controllers

import models.Person
import play.api.mvc._
import play.api.libs.json._

/**
 * Created by m-utsunomiya on 2015/09/04.
 */
class PersonController extends Controller {
  def register = Action(parse.json) {
    request => {
      request.body.validate[Person].map {
        cleaned_data => {
          Ok(Json.toJson(cleaned_data))
        }
      }
    }.recoverTotal {
      e => BadRequest(JsError.toJson(e))
    }
  }
}
