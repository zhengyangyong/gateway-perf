package org.apache.servicecomb.perf.baffle;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/")
public class HelloImpl {

  @GetMapping(path = "/hello")
  public String hello() {
    return "Hello World!";
  }
}