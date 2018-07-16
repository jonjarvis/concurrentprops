package org.pentaho.di.concurrentprops;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.PluginRegistryExtension;
import org.pentaho.di.core.plugins.PluginTypeInterface;
import org.pentaho.di.core.plugins.RegistryPlugin;

/**
 * This plugin only serves as a hook to modify System.properties before
 * the PDI-OSGI-Bridge is loaded.
 */
@RegistryPlugin(id = "ConcurrentPropertiesRegistryPlugin", name = "ConcurrentProps")
public class ConcurrentPropertiesPluginRegistry implements PluginRegistryExtension {

  private static volatile boolean isFirst = true;
  
  private static final Log LOG = LogFactory.getLog( ConcurrentPropertiesPluginRegistry.class );
  
  @Override
  public void init( PluginRegistry registry ) {
    // TODO Auto-generated method stub
    LOG.info( "Starting Concurrent Properties Registry Plugin" );
    
    synchronized(ClassLoader.getSystemClassLoader()) {
      if(isFirst) {
        
        System.setProperties( ConcurrentMapProperties.convertProperties( System.getProperties() ) );
        
        LOG.info( "Replaced System.props with " + System.getProperties().getClass() );
        isFirst = false;
      }
    }
    
  }

  @Override
  public void searchForType( PluginTypeInterface pluginType ) { }

  @Override
  public String getPluginId( Class<? extends PluginTypeInterface> pluginType, Object pluginClass ) {
    return null;
  }

}
