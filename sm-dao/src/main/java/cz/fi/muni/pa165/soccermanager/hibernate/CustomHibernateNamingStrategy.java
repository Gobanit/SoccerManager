/**
 * 
 */
package cz.fi.muni.pa165.soccermanager.hibernate;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.spi.MetadataBuildingContext;

/**
 * @author Michal Randak
 *
 */
public class CustomHibernateNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {
	private static final long serialVersionUID = 3134812133616719256L;

	@Override
    protected Identifier toIdentifier(String stringForm, MetadataBuildingContext buildingContext) {
        return super.toIdentifier(convert(stringForm), buildingContext);
    }
  
    protected String convert(String name) {
    	name = name.replaceAll("([a-z])([A-Z])", "$1_$2");
    	//name = name.toUpperCase();
    	name = name.toLowerCase();
    	return name;
    }
}