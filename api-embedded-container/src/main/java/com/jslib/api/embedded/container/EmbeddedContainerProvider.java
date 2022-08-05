package com.jslib.api.embedded.container;

public interface EmbeddedContainerProvider
{

  EmbeddedContainer createAppContainer(Object... arguments);

}
