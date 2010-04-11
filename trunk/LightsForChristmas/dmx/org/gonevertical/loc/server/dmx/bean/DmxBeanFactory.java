package org.gonevertical.loc.server.dmx.bean;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.gonevertical.loc.client.DmxSend;

public class DmxBeanFactory implements ObjectFactory {

  public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws NamingException {

    // Acquire an instance of our specified bean class
    DmxSend bean = new DmxSend();

    // Customize the bean properties from our attributes
    Reference ref = (Reference) obj;
    Enumeration addrs = ref.getAll();
    while (addrs.hasMoreElements()) {

      // RefAddr addr = (RefAddr) addrs.nextElement();
      // String name = addr.getType();
      // String value = (String) addr.getContent();

    }

    // Return the customized instance
    return (bean);

  }

}


