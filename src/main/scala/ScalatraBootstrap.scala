import com.atlassian.oai.validator.OpenApiInteractionValidator
import com.atlassian.oai.validator.whitelist.ValidationErrorsWhitelist
import com.atlassian.oai.validator.whitelist.rule.WhitelistRules
import com.brimborion.core.module.Module
import com.brimborion.modules.catalog.config.CatalogModule
import com.brimborion.modules.user.config.UserModule
import javax.servlet.ServletContext
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.setAttribute("openApiValidator", openApiValidator)
    loadModule(UserModule, context)
    loadModule(CatalogModule, context)
  }

  private def loadModule(module: Module, context: ServletContext): Unit =
    module.getControllers.foreach(controller => context.mount(controller.implementation, controller.urlPattern))

  private def openApiValidator: OpenApiInteractionValidator = {
    val whitelist = ValidationErrorsWhitelist.create()
      .withRule(
        "Avoid unknown errors",
        WhitelistRules.messageHasKey("validation.request.body.schema.unknownError"
      ))

    OpenApiInteractionValidator
      .createFor("openapi.yaml")
      .withWhitelist(whitelist)
      .build
  }
}

