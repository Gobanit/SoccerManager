package cz.fi.muni.pa165.soccermanager.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
/**
 * implementation of bean mapping
 * @author Dominik Pilar
 *
 */
public class BeanMappingImpl implements BeanMapping {
    private ModelMapper modelMapper;

    @Autowired
    public BeanMappingImpl(ModelMapper modelMapper) {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        this.modelMapper = modelMapper;
    }

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            mappedCollection.add(modelMapper.map(object, mapToClass));
        }
        return mappedCollection;
    }


    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        return modelMapper.map(u, mapToClass);
    }

    public boolean isCollection(Object obj) {
        return (obj instanceof Collection) || (obj instanceof Map);
    }

}
