package com.bheaver.ngl4.aa.model.http

import scala.beans.BeanProperty

trait GenericRequestHttpBody {
  @BeanProperty var requestId: String
  @BeanProperty var libCode: String
}
