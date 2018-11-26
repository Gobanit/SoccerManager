package cz.fi.muni.pa165.soccermanager.service;

import java.util.Collection;
import java.util.List;
/**
 * That class is used for mapping entities to dto classes. Example of usage for this mapper shown
 * below:
 *
 * <pre>
 * <code>
 public class TeamFacadeImpl implements TeamFacade {

 &#64;Autowired
 private BeanMapping beanMapping;

 &#64;Override
 public List&lt;TeamDTO&gt; findAll() {
 return beanMapping.mapTo(teamService.findAll(), TeamDTO.class);
 }
 }
 * </code>
 * </pre>
 *
 * This example shows mapping list of team entities to list of team dto classes.
 *
 * @author Dominik Pilar
 *
 */
public interface BeanMapping {

    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
}
