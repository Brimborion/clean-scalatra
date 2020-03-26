import com.brimborion.core._
import com.brimborion.core.controllers.DefaultController
import com.brimborion.core.module.Module
import com.brimborion.modules.catalog.config.CatalogModule
import com.brimborion.modules.user.config.UserModule
import javax.servlet.ServletContext
import org.scalatra._

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new DefaultController, "/*")
    loadModule(UserModule, context)
    loadModule(CatalogModule, context)
  }

  private def loadModule(module: Module, context: ServletContext): Unit =
    module.getControllers.foreach(controller => context.mount(controller.implementation, controller.urlPattern))
}
