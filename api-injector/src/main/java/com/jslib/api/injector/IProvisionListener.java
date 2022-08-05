package com.jslib.api.injector;

public interface IProvisionListener
{

  <T> void onProvision(IProvisionInvocation<T> provisionInvocation);

}
