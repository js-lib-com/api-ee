package js.injector;

import java.util.List;

public interface IModule
{

  IModule configure(IInjector injector);

  List<IBinding<?>> bindings();

}
