package cz.muni.fi.pa165.config.core;

import cz.muni.fi.pa165.config.RestConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Filip Procházka <filip@prochazka.su>
 */
public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return null;
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/"};
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class<?>[]{RestConfig.class};
    }
}
