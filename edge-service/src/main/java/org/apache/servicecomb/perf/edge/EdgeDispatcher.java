package org.apache.servicecomb.perf.edge;

import java.util.Map;

import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.apache.servicecomb.edge.core.EdgeInvocation;
import org.apache.servicecomb.serviceregistry.definition.DefinitionConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.CookieHandler;

/**
 *match /serviceName/operation
 */
public class EdgeDispatcher extends AbstractEdgeDispatcher {
  private static final Logger LOGGER = LoggerFactory.getLogger(EdgeDispatcher.class);

  public int getOrder() {
    return 10000;
  }

  public void init(Router router) {
    String regex = "/([^\\\\/]+)/(.*)";
    router.routeWithRegex(regex).handler(CookieHandler.create());
    router.routeWithRegex(regex).handler(createBodyHandler());
    router.routeWithRegex(regex).failureHandler(this::onFailure).handler(this::onRequest);
  }

  private void onRequest(RoutingContext context) {
    Map<String, String> pathParams = context.pathParams();
    final String service = pathParams.get("param0");
    String path = "/" + pathParams.get("param1");

    EdgeInvocation edgeInvocation = new EdgeInvocation();
    edgeInvocation.setVersionRule(DefinitionConst.VERSION_RULE_ALL);
    edgeInvocation.init(service, context, path, httpServerFilters);
    edgeInvocation.edgeInvoke();
  }
}