package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.Task

object Application extends Controller {

  def index = Action {
    Redirect(routes.Application.tasks())
  }

  def hell = Action {
    Ok("hey, hey")
  }

  val taskForm = Form(
    "label" -> nonEmptyText
  )

  def tasks = Action{
    Ok(views.html.tasks(Task.all, taskForm))
  }

  def newTask = Action{ implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.tasks(Task.all(), errors)),
      label => {
        Task.create(label)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }

}