package com.serdeliverance.domain.user

case class User(id: Option[Long], username: String, password: String, email: String)
