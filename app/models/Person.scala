package models

import play.api.data.validation.ValidationError
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
  def age_check(age: Int): Boolean = {
    if (age >= 30) false else true
  }

  val under30Validate = Reads.IntReads.filter(ValidationError("age must be under 30"))(age_check)

  implicit val personFormatter : Format[Person] = (
    (__ \ "name").format[Name] and
    (__ \ "age").format[Int](under30Validate) and
    (__ \ "blood_type").formatNullable[String] and
    (__ \ "favorite_number").formatNullable[Seq[Int]]
  )(Person.apply, unlift(Person.unapply))
}

