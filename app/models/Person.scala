package models

import play.api.libs.json.Format
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Created by m-utsunomiya on 2015/09/04.
 */
case class Person(
  name:Name,
  age:Int,
  blood_type:Option[String],
  favorite_number:Option[Seq[Int]]
)

object Person {
  implicit val personFormatter : Format[Person] = (
    (__ \ "name").format[Name] and
    (__ \ "age").format[Int] and
    (__ \ "blood_type").formatNullable[String] and
    (__ \ "favorite_number").formatNullable[Seq[Int]]
  )(Person.apply, unlift(Person.unapply))
}

