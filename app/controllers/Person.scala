package controllers

import models.Person
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class PersonController extends Controller {

  //Futureを扱う場合、Action.asyncにする必要がある
  def register = Action.async(parse.json) {
    request => {

      //Personクラスに定義済みのバリデーションとFormatterを適用し、
      //Personクラスのインスタンスを得る
      request.body.validate[Person].map {

        //cleaned_data=Personクラスのインスタンス
        cleanedData => {

          // Future型の戻り値をもらう
          // yield式で評価する 評価したものがfuture_response変数に入る
          val futureResponse: Future[Person] = for {
            futureObj <- getFutureObject(cleanedData)
          } yield futureObj

          // 処理が成功してたらmap式の中に入ってレスポンスを返す
          // future_responseのmap式なので、Futureは完了している
          futureResponse.map { res =>
            Ok(Json.toJson(res))
          }
        }
      }
    }.recoverTotal { e =>
      // こっちもFuture型のレスポンスにしないといけない
      Future(BadRequest(JsError.toJson(e)))
    }
  }

  def getFutureObject(p : Person): Future[Person] = {
    // 何もせずすぐ返す
    Future.successful(p)
  }
}
