package com.jslib.api.container;

public interface EmbeddedContainerProvider
{

  EmbeddedContainer createAppContainer(Object... arguments);

}
