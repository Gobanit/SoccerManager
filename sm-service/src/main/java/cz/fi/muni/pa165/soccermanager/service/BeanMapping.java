package cz.fi.muni.pa165.soccermanager.service;

import java.util.Collection;
import java.util.List;
/**
 * interface of bean mapping
 * @author Dominik Pilar
 *
 */
public interface BeanMapping {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
}
