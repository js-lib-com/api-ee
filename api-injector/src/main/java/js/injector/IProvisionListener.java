package js.injector;

public interface IProvisionListener
{

  <T> void onProvision(IProvisionInvocation<T> provisionInvocation);

}
