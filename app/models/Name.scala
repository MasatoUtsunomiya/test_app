package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Format

/**
 * Created by m-utsunomiya on 2015/09/04.
 */
case class Name(first:String, last:String)

object Name {
  implicit val nameFormatter : Format[Name] = (
    (__ \ "first").format[String] and
    (__ \ "last").format[String]
  )(Name.apply, unlift(Name.unapply))
}